package com.myCPE.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.activity.NotificationActivity;
import com.myCPE.adapter.HomeMyWebinarAdapter;
import com.myCPE.databinding.FragmentMywebinarNewBinding;
import com.myCPE.model.homewebinarnew.Webinar_Home_New;
import com.myCPE.utility.AppSettings;
import com.myCPE.utility.Constant;
import com.myCPE.view.DialogsUtils;
import com.myCPE.view.SimpleDividerItemDecoration;
import com.myCPE.webservice.APIService;
import com.myCPE.webservice.ApiUtilsNew;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.myCPE.utility.Constant.arraylistselectedvalue;
import static com.myCPE.utility.Constant.checkmywebinardotstatusset;

public class MyWebinarFragment extends Fragment {

    View view;
    private FragmentMywebinarNewBinding binding;
    HomeMyWebinarAdapter adapter;
    private static final String TAG = MyWebinarFragment.class.getName();
    public Context context;
    ProgressDialog progressDialog;
    LinearLayoutManager linearLayoutManager;
    private List<com.myCPE.model.homewebinarnew.WebinarItem> arrHomeMyWebinarlistnew = new ArrayList<com.myCPE.model.homewebinarnew.WebinarItem>();
    private List<Boolean> arrsavebooleanstateMyWebinar = new ArrayList();
    private List<String> arraysavefilterMyWebinar = new ArrayList<String>();
    private APIService mAPIService_new;
    private boolean loading = true;
    private String webinartypemywebinar = "";
    public boolean islast = false;
    public boolean isprogress = false;
    public int start = 0, limit = 10;
    private String topicsofinterest = "";
    private static MyWebinarFragment instance;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mywebinar_new, null, false);
        context = getActivity();
        mAPIService_new = ApiUtilsNew.getAPIService();
        instance = MyWebinarFragment.this;


        //for animation

//        final Animation slide_up = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
//                R.anim.slide_up);

        final Animation slide_down = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.slide_down);


        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.rvhomewebinar.setLayoutManager(linearLayoutManager);
//        binding.rvhomewebinar.addItemDecoration(new SimpleDividerItemDecoration(context));
        binding.rvhomewebinar.addItemDecoration(new DividerItemDecoration(context, 0));
        binding.rvhomewebinar.setItemAnimator(new DefaultItemAnimator());
        binding.rvhomewebinar.setHasFixedSize(true);


        arrsavebooleanstateMyWebinar.add(0, false);
        arrsavebooleanstateMyWebinar.add(1, false);
        arrsavebooleanstateMyWebinar.add(2, false);


        arraysavefilterMyWebinar.add(0, "");
        arraysavefilterMyWebinar.add(1, "");
        arraysavefilterMyWebinar.add(2, "");


        if (!AppSettings.get_login_token(context).equalsIgnoreCase("")) {
            binding.btnLive.setEnabled(true);
            binding.btnSelfstudy.setEnabled(true);
            binding.btnArchive.setEnabled(true);
        } else {
            binding.btnLive.setEnabled(false);
            binding.btnSelfstudy.setEnabled(false);
            binding.btnArchive.setEnabled(false);
        }


        if (!AppSettings.get_login_token(context).isEmpty()) {
            if (Constant.isNetworkAvailable(context)) {
                progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                GetMyWebinarListNew(webinartypemywebinar, topicsofinterest, start, limit);
            } else {
                Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();

            }

        } else {
            binding.swipeRefreshLayouthomemywebinar.setVisibility(View.GONE);
            binding.tvNodatafound.setVisibility(View.VISIBLE);
            binding.tvNodatafound.setText(getResources().getString(R.string.str_guest_user_dialog_msg));
        }

        binding.ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.relSection1.setVisibility(View.GONE);
                binding.rvtitle1.setVisibility(View.VISIBLE);
            }
        });

        binding.txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.relSection1.setVisibility(View.VISIBLE);
                binding.rvtitle1.setVisibility(View.GONE);

                if (!binding.edtSearch.getText().toString().equalsIgnoreCase("")) {
//                    binding.edtSearch.setText("");
                    if (Constant.isNetworkAvailable(context)) {
                        progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                        GetMyWebinarListNew(webinartypemywebinar, topicsofinterest, start, limit);
                    } else {
                        Snackbar.make(binding.edtSearch, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 0) {
                    Constant.hideKeyboard((Activity) context);

                    if (Constant.isNetworkAvailable(context)) {
                        progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                        GetMyWebinarListNew(webinartypemywebinar, topicsofinterest, start, limit);
                    } else {
                        Snackbar.make(binding.edtSearch, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                    }

                }

                // TODO Auto-generated method stub
            }
        });


        binding.edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Do you job here which you want to done through event

                    Constant.hideKeyboard(getActivity());

                    if (!Constant.Trim(binding.edtSearch.getText().toString()).isEmpty()) {

                        HomeAllFragment ldf = new HomeAllFragment();
                        Bundle args = new Bundle();
                        args.putString("actionsearch", Constant.Trim(binding.edtSearch.getText().toString()));
                        args.putString(context.getResources().getString(R.string.str_premium_lable), "home");
                        ldf.setArguments(args);
                        MainActivity.getInstance().SetImageBackground(2);
                        getFragmentManager().beginTransaction().add(R.id.content_frame, ldf).commit();

                    } else {
                        Snackbar.make(binding.edtSearch, getResources().getString(R.string.str_val_search_text), Snackbar.LENGTH_SHORT).show();
                    }


                }
                return false;
            }
        });

        /*binding.rvhomewebinar.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    getActivity().overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
                    MainActivity.getInstance().rel_top_bottom.startAnimation(slide_up);
                    MainActivity.getInstance().rel_top_bottom.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity.getInstance().rel_top_bottom.setVisibility(View.VISIBLE);
                        }
                    }, 500);

                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
//                    MainActivity.getInstance().rel_top_bottom.startAnimation(slide_down);
                    MainActivity.getInstance().rel_top_bottom.setVisibility(View.GONE);
                    *//*Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity.getInstance().rel_top_bottom.setVisibility(View.GONE);
                        }
                    }, 500);*//*
                }

                super.onScrollStateChanged(recyclerView, newState);
            }
        });*/



       /* binding.rvhomewebinar.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && MainActivity.getInstance().rel_top_bottom.isShown()) {
                    getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                    MainActivity.getInstance().rel_top_bottom.setVisibility(View.GONE);

                  *//*  Animation bottomDown = AnimationUtils.loadAnimation(getContext(),
                            R.anim.bottom_down);
                    MainActivity.getInstance().rel_top_bottom.startAnimation(bottomDown);
                    MainActivity.getInstance().rel_top_bottom.setVisibility(View.GONE);*//*


                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    getActivity().overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
                    MainActivity.getInstance().rel_top_bottom.setVisibility(View.VISIBLE);

                   *//* Animation bottomUp = AnimationUtils.loadAnimation(getContext(),
                            R.anim.bottom_up);
                    MainActivity.getInstance().rel_top_bottom.startAnimation(bottomUp);
                    MainActivity.getInstance().rel_top_bottom.setVisibility(View.VISIBLE);*//*
                }

                super.onScrollStateChanged(recyclerView, newState);
            }
        });*/


        binding.swipeRefreshLayouthomemywebinar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });


        binding.ivnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppSettings.get_login_token(context).isEmpty()) {
                    Intent i = new Intent(getActivity(), NotificationActivity.class);
                    startActivity(i);

                } else {
                    MainActivity.getInstance().ShowPopUp();
                }


            }
        });


        binding.rvhomewebinar.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (loading) {
                    if (!islast) {
                        if (isLastVisible()) {
                            loading = false;
                            start = start + 10;
                            limit = 10;
                            loadNextPage();
                        }
                    }
                }


            }
        });


        /*binding.rvhomewebinar.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && MainActivity.getInstance().rel_top_bottom.isShown()) {
//                    MainActivity.getInstance().rel_top_bottom.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    MainActivity.getInstance().rel_top_bottom.setVisibility(View.VISIBLE);
                }

                super.onScrollStateChanged(recyclerView, newState);
            }
        });*/


        binding.btnLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start = 0;
                limit = 10;
                loading = true;

                if (arrsavebooleanstateMyWebinar.get(0) == false) {
                    arrsavebooleanstateMyWebinar.set(0, true);
                    arraysavefilterMyWebinar.set(0, getResources().getString(R.string.str_filter_live));
                    webinartypemywebinar = arraysavefilterMyWebinar.toString().replace("[", "").replace("]", "")
                            .replace(" ", "");
                    binding.btnLive.setBackgroundResource(R.drawable.col_four_bg_new_hover);
                    binding.btnLive.setTextColor(getResources().getColor(R.color.White));
                } else {
                    arrsavebooleanstateMyWebinar.set(0, false);
                    arraysavefilterMyWebinar.set(0, "");


                    if (arraysavefilterMyWebinar.get(0).equalsIgnoreCase("") &&
                            arraysavefilterMyWebinar.get(1).equalsIgnoreCase("") &&
                            arraysavefilterMyWebinar.get(2).equalsIgnoreCase("")) {
                        webinartypemywebinar = "";
                    } else {
                        webinartypemywebinar = arraysavefilterMyWebinar.toString().replace("[", "").replace("]", "")
                                .replace(" ", "");
                    }


                    binding.btnLive.setBackgroundResource(R.drawable.col_four_bg_new);
                    binding.btnLive.setTextColor(getResources().getColor(R.color.home_tab_color_unselected));
                }


                if (Constant.isNetworkAvailable(context)) {
                    progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                    GetMyWebinarListNew(webinartypemywebinar, topicsofinterest, start, limit);

                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();

                }


            }
        });

        binding.btnSelfstudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loading = true;
                start = 0;
                limit = 10;

                if (arrsavebooleanstateMyWebinar.get(1) == false) {
                    arrsavebooleanstateMyWebinar.set(1, true);
                    arraysavefilterMyWebinar.set(1, getResources().getString(R.string.str_filter_selfstudy));
                    webinartypemywebinar = arraysavefilterMyWebinar.toString().replace("[", "").replace("]", "")
                            .replace(" ", "");


                    binding.btnSelfstudy.setBackgroundResource(R.drawable.col_four_bg_new_hover);
                    binding.btnSelfstudy.setTextColor(getResources().getColor(R.color.White));
                } else {
                    arrsavebooleanstateMyWebinar.set(1, false);
                    arraysavefilterMyWebinar.set(1, "");
                    if (arraysavefilterMyWebinar.get(0).equalsIgnoreCase("") &&
                            arraysavefilterMyWebinar.get(1).equalsIgnoreCase("") &&
                            arraysavefilterMyWebinar.get(2).equalsIgnoreCase("")) {
                        webinartypemywebinar = "";


                    } else {
                        webinartypemywebinar = arraysavefilterMyWebinar.toString().replace("[", "").replace("]", "")
                                .replace(" ", "");

                    }


                    binding.btnSelfstudy.setBackgroundResource(R.drawable.col_four_bg_new);
                    binding.btnSelfstudy.setTextColor(getResources().getColor(R.color.home_tab_color_unselected));
                }


                if (Constant.isNetworkAvailable(context)) {
                    progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                    GetMyWebinarListNew(webinartypemywebinar, topicsofinterest, start, limit);

                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();

                }


            }
        });


        binding.btnArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                loading = true;
                start = 0;
                limit = 10;


                if (arrsavebooleanstateMyWebinar.get(2) == false) {
                    arrsavebooleanstateMyWebinar.set(2, true);
                    arraysavefilterMyWebinar.set(2, getResources().getString(R.string.str_filter_archieve));
                    webinartypemywebinar = arraysavefilterMyWebinar.toString().replace("[", "").replace("]", "")
                            .replace(" ", "");
                    binding.btnArchive.setBackgroundResource(R.drawable.col_four_bg_new_hover);
                    binding.btnArchive.setTextColor(getResources().getColor(R.color.White));
                } else {
                    arrsavebooleanstateMyWebinar.set(2, false);
                    arraysavefilterMyWebinar.set(2, "");
                    if (arraysavefilterMyWebinar.get(0).equalsIgnoreCase("") &&
                            arraysavefilterMyWebinar.get(1).equalsIgnoreCase("") &&
                            arraysavefilterMyWebinar.get(2).equalsIgnoreCase("")
                    ) {
                        webinartypemywebinar = "";
                    } else {
                        webinartypemywebinar = arraysavefilterMyWebinar.toString().replace("[", "").replace("]", "")
                                .replace(" ", "");
                    }

                    binding.btnArchive.setBackgroundResource(R.drawable.col_four_bg_new);
                    binding.btnArchive.setTextColor(getResources().getColor(R.color.home_tab_color_unselected));
                }


                if (Constant.isNetworkAvailable(context)) {
                    progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                    GetMyWebinarListNew(webinartypemywebinar, topicsofinterest, start, limit);
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();

                }


            }
        });


        binding.getRoot().setFocusableInTouchMode(true);
        binding.getRoot().requestFocus();
        binding.getRoot().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    ConfirmationPopup();
                    return true;
                }
                return false;
            }
        });


        return view = binding.getRoot();
    }

    public void ConfirmationPopup() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage(getResources().getString(R.string.exit_validation));


        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event
                dialog.cancel();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getActivity().finishAffinity();
                    System.exit(0);
                } else {
                    getActivity().finish();
                }


            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();


    }

    public static MyWebinarFragment getInstance() {
        return instance;
    }


    @Override
    public void onResume() {
        super.onResume();


     /*   if (arraylistselectedvalue.size() > 0) {

            topicsofinterest = "";

            StringBuilder commaSepValueBuilder = new StringBuilder();

            //Looping through the list
            for (int i = 0; i < arraylistselectedvalue.size(); i++) {
                //append the value into the builder
                commaSepValueBuilder.append(arraylistselectedvalue.get(i));

                //if the value is not the last element of the list
                //then append the comma(,) as well
                if (i != arraylistselectedvalue.size() - 1) {
                    commaSepValueBuilder.append(",");
                }
            }

            topicsofinterest = commaSepValueBuilder.toString();

            System.out.println(topicsofinterest);

            start = 0;
            limit = 10;
            loading = true;


            if (Constant.isNetworkAvailable(context)) {
                progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                GetMyWebinarListNew(webinartypemywebinar, topicsofinterest, start, limit);
            } else {
                Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
            }


        }*/
    }

    public void refreshItems() {

        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {

        start = 0;
        limit = 10;
        loading = true;
        checkmywebinardotstatusset = true;


        if (Constant.isNetworkAvailable(context)) {
            GetMyWebinarListNew(webinartypemywebinar, topicsofinterest, start, limit);
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();

        }
    }


    private void loadNextPage() {
        if (Constant.isNetworkAvailable(context)) {
            binding.progressBar.setVisibility(View.VISIBLE);
            GetMyWebinarListNew(webinartypemywebinar, topicsofinterest, start, limit);
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }

    }


    public void GetMyWebinarListNew(final String webinartype, final String topicsofinterest, final int start, final int limit) {

        mAPIService_new.GetMyWebinarListNew(getResources().getString(R.string.accept),
                getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context),
                start, limit, webinartype, topicsofinterest).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Webinar_Home_New>() {
                    @Override
                    public void onCompleted() {

                        if (binding.progressBar.getVisibility() == View.VISIBLE) {
                            binding.progressBar.setVisibility(View.GONE);
                        } else if (progressDialog.isShowing()) {
                        } else if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        loading = true;

                        if (start == 0 && limit == 10) {
                            if (arrHomeMyWebinarlistnew.size() > 0) {
                                adapter = new HomeMyWebinarAdapter(context, arrHomeMyWebinarlistnew);
                                binding.rvhomewebinar.setAdapter(adapter);
                            }

                        } else {
                            adapter.addLoadingFooter();
                        }


                    }

                    @Override
                    public void onError(Throwable e) {

                        if (start == 0 && limit == 10) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        } else {
                            if (binding.progressBar.getVisibility() == View.VISIBLE) {
                                binding.progressBar.setVisibility(View.GONE);
                            }
                        }


                        String message = Constant.GetReturnResponse(context, e);

                        if (Constant.status_code == 401) {
                            MainActivity.getInstance().AutoLogout();
                        } else {
                            Snackbar.make(binding.rvhomewebinar, message, Snackbar.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onNext(Webinar_Home_New webinar_home_new) {

                        if (webinar_home_new.isSuccess() == true) {

                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            } else {
                                if (binding.swipeRefreshLayouthomemywebinar.isRefreshing()) {
                                    binding.swipeRefreshLayouthomemywebinar.setRefreshing(false);
                                }
                            }

                            arraylistselectedvalue.clear();

                            islast = webinar_home_new.getPayload().isIsLast();

                            isprogress = webinar_home_new.getPayload().isIsprogress();

                            /*if (topicsofinterest.equalsIgnoreCase("")) {
                                UserDashBoardFragment.getInstance().setupTabIcons(isprogress);
                            }*/


                            if (start == 0 && limit == 10) {
                                if (arrHomeMyWebinarlistnew.size() > 0) {
                                    arrHomeMyWebinarlistnew.clear();
                                }
                            }


                            if (start == 0 && limit == 10) {
                                arrHomeMyWebinarlistnew = webinar_home_new.getPayload().getWebinar();
                            } else {


                                if (arrHomeMyWebinarlistnew.size() > 20) {
                                    arrHomeMyWebinarlistnew.remove(arrHomeMyWebinarlistnew.size() - 1);
                                }


                                List<com.myCPE.model.homewebinarnew.WebinarItem> webinaritems = webinar_home_new.getPayload().getWebinar();
                                adapter.addAll(webinaritems);
                            }


                            if (arrHomeMyWebinarlistnew.size() > 0) {
                                binding.swipeRefreshLayouthomemywebinar.setVisibility(View.VISIBLE);
                                binding.tvNodatafound.setVisibility(View.GONE);
                            } else {
                                binding.swipeRefreshLayouthomemywebinar.setVisibility(View.GONE);
                                binding.tvNodatafound.setVisibility(View.VISIBLE);
                            }
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            } else {
                                if (binding.swipeRefreshLayouthomemywebinar.isRefreshing()) {
                                    binding.swipeRefreshLayouthomemywebinar.setRefreshing(false);
                                }
                            }
                            Snackbar.make(binding.rvhomewebinar, webinar_home_new.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }


                    }


                });

    }


    boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) binding.rvhomewebinar.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = binding.rvhomewebinar.getAdapter().getItemCount() - 1;

        return (pos >= numItems);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (adapter != null) {
            adapter.unregister(getActivity());
        }

    }


}
