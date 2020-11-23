package com.myCPE.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.adapter.MyCreditAdapter;
import com.myCPE.databinding.FragmentMycreditNewBinding;
import com.myCPE.model.My_Credit_New.Model_My_Credit_New;
import com.myCPE.model.My_Credit_New.MyCreditsItem;
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

public class MyCreditsFragment extends Fragment {

    View view;
    public Context context;
    FragmentMycreditNewBinding binding;
    MyCreditAdapter adapter;
    private APIService mAPIService;
    Typeface font;
    public int filter_type = 0;
    ProgressDialog progressDialog;
    LinearLayoutManager linearLayoutManager;
    private List<MyCreditsItem> mlistmycredit = new ArrayList<>();
    public int start = 0, limit = 10;
    private boolean loading = true;
    public boolean islast = false;
    private static final String TAG = MyCreditsFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mycredit_new, null, false);
        context = getActivity();
        mAPIService = ApiUtilsNew.getAPIService();

        font = Typeface.createFromAsset(getActivity().getAssets(), "Montserrat-Light.ttf");

        Constant.isFromSpeakerCompanyWebinarList = false;
        Constant.isCpdSelected = false;
        Constant.is_cpd = 0;
        Constant.isFromCSPast = false;

        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.recyclerviewMycredit.setLayoutManager(linearLayoutManager);
//        binding.recyclerviewMycredit.addItemDecoration(new SimpleDividerItemDecoration(context));
        binding.recyclerviewMycredit.addItemDecoration(new DividerItemDecoration(context, 0));
        binding.recyclerviewMycredit.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerviewMycredit.setHasFixedSize(true);

        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            GetMyCredit(start, limit);
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }

        binding.lvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                binding.lvAll.setBackgroundResource(R.mipmap.my_certi_gray_select);
                binding.lvAll.setBackgroundResource(R.drawable.rounded_bg_selection_darkblue);
//                binding.lvLive.setBackgroundResource(R.mipmap.my_certi_gray);
                binding.lvLive.setBackgroundResource(R.drawable.rounded_bg_gray_blue);
//                binding.lvSelfStudy.setBackgroundResource(R.mipmap.my_certi_gray);
                binding.lvSelfStudy.setBackgroundResource(R.drawable.rounded_bg_gray_blue);

                binding.tvAll.setTextColor(getResources().getColor(R.color.White));
                binding.tvLive.setTextColor(getResources().getColor(R.color.color_text_black));
                binding.tvSelfStudy.setTextColor(getResources().getColor(R.color.color_text_black));


                filter_type = 0;
                start = 0;
                limit = 10;
                loading = true;
                if (Constant.isNetworkAvailable(context)) {
                    progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                    GetMyCredit(start, limit);
                } else {
                    Snackbar.make(binding.recyclerviewMycredit, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                }

            }
        });
        binding.lvLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                binding.lvAll.setBackgroundResource(R.mipmap.my_certi_gray);
                binding.lvAll.setBackgroundResource(R.drawable.rounded_bg_gray_blue);
//                binding.lvLive.setBackgroundResource(R.mipmap.my_certi_gray_select);
                binding.lvLive.setBackgroundResource(R.drawable.rounded_bg_selection_darkblue);
//                binding.lvSelfStudy.setBackgroundResource(R.mipmap.my_certi_gray);
                binding.lvSelfStudy.setBackgroundResource(R.drawable.rounded_bg_gray_blue);

                binding.tvAll.setTextColor(getResources().getColor(R.color.color_text_black));
                binding.tvLive.setTextColor(getResources().getColor(R.color.White));
                binding.tvSelfStudy.setTextColor(getResources().getColor(R.color.color_text_black));

                filter_type = 1;
                start = 0;
                limit = 10;
                loading = true;
                if (Constant.isNetworkAvailable(context)) {
                    progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                    GetMyCredit(start, limit);
                } else {
                    Snackbar.make(binding.recyclerviewMycredit, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        binding.lvSelfStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                binding.lvAll.setBackgroundResource(R.mipmap.my_certi_gray);
                binding.lvAll.setBackgroundResource(R.drawable.rounded_bg_gray_blue);
//                binding.lvLive.setBackgroundResource(R.mipmap.my_certi_gray);
                binding.lvLive.setBackgroundResource(R.drawable.rounded_bg_gray_blue);
//                binding.lvSelfStudy.setBackgroundResource(R.mipmap.my_certi_gray_select);
                binding.lvSelfStudy.setBackgroundResource(R.drawable.rounded_bg_selection_darkblue);

                binding.tvAll.setTextColor(getResources().getColor(R.color.color_text_black));
                binding.tvLive.setTextColor(getResources().getColor(R.color.color_text_black));
                binding.tvSelfStudy.setTextColor(getResources().getColor(R.color.White));

                filter_type = 2;
                start = 0;
                limit = 10;
                loading = true;
                if (Constant.isNetworkAvailable(context)) {
                    progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                    GetMyCredit(start, limit);
                } else {
                    Snackbar.make(binding.recyclerviewMycredit, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                }
            }
        });


        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });


        binding.recyclerviewMycredit.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    @Override
    public void onResume() {
        super.onResume();
        if (Constant.isdataupdate) {
            Constant.isdataupdate = false;
            if (Constant.isNetworkAvailable(context)) {
                progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                GetMyCredit(start, limit);
            } else {
                Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (adapter != null) {
            adapter.unregister(getActivity());
        }

    }


    private void loadNextPage() {
        if (Constant.isNetworkAvailable(context)) {
            binding.progressBar.setVisibility(View.VISIBLE);
            GetMyCredit(start, limit);
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }
    }

    public void refreshItems() {
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        start = 0;
        limit = 10;
        loading = true;

        if (Constant.isNetworkAvailable(context)) {
            GetMyCredit(start, limit);
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }
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


    /*private void GetMyCredit(final int start, final int limit) {


        mAPIService.GetMyCredit(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context), filter_type
                , start, limit).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<My_Credit>() {
                    @Override
                    public void onCompleted() {

                        if (binding.progressBar.getVisibility() == View.VISIBLE) {
                            binding.progressBar.setVisibility(View.GONE);
                        }


                        loading = true;
                        if (start == 0 && limit == 10) {
                            if (mlistmycredit.size() > 0) {
                                adapter = new MyCreditAdapter(context, mlistmycredit);
                                binding.recyclerviewMycredit.setAdapter(adapter);
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
                            Snackbar.make(binding.ivnotification, message, Snackbar.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onNext(My_Credit myCredit) {

                        if (myCredit.isSuccess() == true) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            } else {
                                if (binding.swipeRefreshLayout.isRefreshing()) {
                                    binding.swipeRefreshLayout.setRefreshing(false);
                                }
                            }


                            if (start == 0 && limit == 10) {
                                if (mlistmycredit.size() > 0) {
                                    mlistmycredit.clear();
                                }
                            }

                            islast = myCredit.getPayload().get(0).isIsLast();

                            if (start == 0 && limit == 10) {
                                for (int i = 0; i < myCredit.getPayload().size(); i++) {
                                    mlistmycredit = myCredit.getPayload().get(i).getMyCredits();
                                }
                            } else {

                                if (mlistmycredit.size() > 20) {
                                    mlistmycredit.remove(mlistmycredit.size() - 1);
                                }


                                List<MyCreditsItem> webinaritems = new ArrayList<>();
                                for (int i = 0; i < myCredit.getPayload().size(); i++) {
                                    webinaritems = myCredit.getPayload().get(i).getMyCredits();
                                }


                                adapter.addAll(webinaritems);


                            }


                            *//*if (!myCredit.getPayload().get(0).getFullName().equalsIgnoreCase("")) {
                                binding.tvUsername.setText(myCredit.getPayload().get(0).getFullName());
                            }
                            if (!myCredit.getPayload().get(0).getEmail().equalsIgnoreCase("")) {
                                binding.tvUseremailid.setText(myCredit.getPayload().get(0).getEmail());
                            }
*//*


                            if (mlistmycredit.size() > 0) {
                                binding.swipeRefreshLayout.setVisibility(View.VISIBLE);
                                binding.tvNodatafound.setVisibility(View.GONE);
                            } else {
                                binding.tvNodatafound.setVisibility(View.VISIBLE);
                                binding.swipeRefreshLayout.setVisibility(View.GONE);
                            }


                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            } else {
                                if (binding.swipeRefreshLayout.isRefreshing()) {
                                    binding.swipeRefreshLayout.setRefreshing(false);
                                }
                            }
                            Snackbar.make(binding.ivnotification, myCredit.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }


                });

    }*/


    private void GetMyCredit(final int start, final int limit) {


        mAPIService.GetMyCredit(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context),
                 filter_type, start, limit).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Model_My_Credit_New>() {
                    @Override
                    public void onCompleted() {

                        if (binding.progressBar.getVisibility() == View.VISIBLE) {
                            binding.progressBar.setVisibility(View.GONE);
                        }


                        loading = true;
                        if (start == 0 && limit == 10) {
                            if (mlistmycredit.size() > 0) {
                                adapter = new MyCreditAdapter(context, mlistmycredit);
                                binding.recyclerviewMycredit.setAdapter(adapter);
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
                            Snackbar.make(binding.ivnotification, message, Snackbar.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onNext(Model_My_Credit_New model_my_credit_new) {

                        if (model_my_credit_new.isSuccess() == true) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            } else {
                                if (binding.swipeRefreshLayout.isRefreshing()) {
                                    binding.swipeRefreshLayout.setRefreshing(false);
                                }
                            }


                            if (start == 0 && limit == 10) {
                                if (mlistmycredit.size() > 0) {
                                    mlistmycredit.clear();
                                }
                            }

                            islast = model_my_credit_new.getPayload().get(0).isIsLast();

                            if (start == 0 && limit == 10) {
                                for (int i = 0; i < model_my_credit_new.getPayload().size(); i++) {
                                    mlistmycredit = model_my_credit_new.getPayload().get(i).getMyCredits();
                                }
                            } else {

                                if (mlistmycredit.size() > 20) {
                                    mlistmycredit.remove(mlistmycredit.size() - 1);
                                }


                                List<MyCreditsItem> webinaritems = new ArrayList<>();
                                for (int i = 0; i < model_my_credit_new.getPayload().size(); i++) {
                                    webinaritems = model_my_credit_new.getPayload().get(i).getMyCredits();
                                }


                                adapter.addAll(webinaritems);


                            }


                            /*if (!myCredit.getPayload().get(0).getFullName().equalsIgnoreCase("")) {
                                binding.tvUsername.setText(myCredit.getPayload().get(0).getFullName());
                            }
                            if (!myCredit.getPayload().get(0).getEmail().equalsIgnoreCase("")) {
                                binding.tvUseremailid.setText(myCredit.getPayload().get(0).getEmail());
                            }
*/


                            if (mlistmycredit.size() > 0) {
                                binding.swipeRefreshLayout.setVisibility(View.VISIBLE);
                                binding.tvNodatafound.setVisibility(View.GONE);
                            } else {
                                binding.tvNodatafound.setVisibility(View.VISIBLE);
                                binding.swipeRefreshLayout.setVisibility(View.GONE);
                            }


                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            } else {
                                if (binding.swipeRefreshLayout.isRefreshing()) {
                                    binding.swipeRefreshLayout.setRefreshing(false);
                                }
                            }
                            Snackbar.make(binding.ivnotification, model_my_credit_new.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }


                });

    }


    boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) binding.recyclerviewMycredit.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = binding.recyclerviewMycredit.getAdapter().getItemCount() - 1;
        return (pos >= numItems);
    }


}
