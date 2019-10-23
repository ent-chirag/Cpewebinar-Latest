package com.entigrity.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.entigrity.MainActivity;
import com.entigrity.R;
import com.entigrity.activity.NotificationActivity;
import com.entigrity.adapter.HomeALLAdapter;
import com.entigrity.adapter.TopicsFilterHomePopUpAdapter;
import com.entigrity.databinding.FragmentAllBinding;
import com.entigrity.model.homewebinarnew.Webinar_Home_New;
import com.entigrity.model.subject_area.Subject_Area;
import com.entigrity.model.subjects_store.Model_Subject_Area;
import com.entigrity.utility.AppSettings;
import com.entigrity.utility.Constant;
import com.entigrity.view.DialogsUtils;
import com.entigrity.view.SimpleDividerItemDecoration;
import com.entigrity.webservice.APIService;
import com.entigrity.webservice.ApiUtilsNew;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.entigrity.utility.Constant.arraylistselectedvalue;
import static com.entigrity.utility.Constant.checkmywebinardotstatusset;

public class HomeAllFragment extends Fragment {
    private FragmentAllBinding binding;
    public Context context;
    HomeALLAdapter adapter;
    private static final String TAG = HomeAllFragment.class.getName();
    private APIService mAPIService_new;
    ProgressDialog progressDialog;
    LinearLayoutManager linearLayoutManager;
    private List<com.entigrity.model.homewebinarnew.WebinarItem> arrHomelistnew = new ArrayList<com.entigrity.model.homewebinarnew.WebinarItem>();

    public ArrayList<Model_Subject_Area> arraylistModelSubjectArea = new ArrayList<>();

    private String webinartype = "live";
    private String topicsofinterest = "";
    private List<Boolean> arrsavebooleanstate = new ArrayList();
    private List<String> arraysavefilter = new ArrayList<String>();
    private boolean loading = true;
    public int start = 0, limit = 10;
    public boolean islast = false;
    private static HomeAllFragment instance;

    public Dialog myDialog_topics;
    private TextView tv_header, tv_submit, tv_cancel;
    public RecyclerView rv_topics;

    public TopicsFilterHomePopUpAdapter topicsFilterHomePopUpAdapter;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all, null, false);
        mAPIService_new = ApiUtilsNew.getAPIService();
        instance = HomeAllFragment.this;
        context = getActivity();
        myDialog_topics = new Dialog(context);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.rvhome.setLayoutManager(linearLayoutManager);
        binding.rvhome.addItemDecoration(new SimpleDividerItemDecoration(context));
        binding.rvhome.setItemAnimator(new DefaultItemAnimator());
        binding.rvhome.setHasFixedSize(true);


        arrsavebooleanstate.add(0, false);
        arrsavebooleanstate.add(1, false);
        arrsavebooleanstate.add(2, false);
        arrsavebooleanstate.add(3, false);
        arrsavebooleanstate.add(4, false);
        arrsavebooleanstate.add(5, false);


        arraysavefilter.add(0, "");
        arraysavefilter.add(1, "");
        arraysavefilter.add(2, "");
        arraysavefilter.add(3, "");
        arraysavefilter.add(4, "");
        arraysavefilter.add(5, "");

        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            GetHomeListNew(webinartype, topicsofinterest, start, limit);
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();

        }

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

        binding.btnTopics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //   ShowTopics();


                start = 0;
                limit = 10;
                loading = true;


                if (arrsavebooleanstate.get(0) == false) {
                    arrsavebooleanstate.set(0, true);
                    arraysavefilter.set(0, getResources().getString(R.string.str_filter_home_fields_of_study));
                    webinartype = arraysavefilter.toString().replace("[", "").replace("]", "")
                            .replace(" ", "");
                    binding.btnTopics.setBackgroundResource(R.drawable.chipsetview_filter_home);
                    binding.btnTopics.setTextColor(getResources().getColor(R.color.White));
                } else {
                    arrsavebooleanstate.set(0, false);
                    arraysavefilter.set(0, "");


                    if (arraysavefilter.get(0).equalsIgnoreCase("") &&
                            arraysavefilter.get(1).equalsIgnoreCase("") &&
                            arraysavefilter.get(2).equalsIgnoreCase("") &&
                            arraysavefilter.get(3).equalsIgnoreCase("") &&
                            arraysavefilter.get(4).equalsIgnoreCase("") &&
                            arraysavefilter.get(5).equalsIgnoreCase("")) {
                        webinartype = "";
                    } else {
                        webinartype = arraysavefilter.toString().replace("[", "").replace("]", "")
                                .replace(" ", "");
                    }


                    binding.btnTopics.setBackgroundResource(R.drawable.chipsetview_filter_home_unselected);
                    binding.btnTopics.setTextColor(getResources().getColor(R.color.home_tab_color_unselected));
                }


            }
        });

        binding.btnLiveWebinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start = 0;
                limit = 10;
                loading = true;


                if (arrsavebooleanstate.get(1) == false) {
                    arrsavebooleanstate.set(1, true);
                    arraysavefilter.set(1, getResources().getString(R.string.str_filter_home_live));
                    webinartype = arraysavefilter.toString().replace("[", "").replace("]", "")
                            .replace(" ", "");
                    binding.btnLiveWebinar.setBackgroundResource(R.drawable.chipsetview_filter_home);
                    binding.btnLiveWebinar.setTextColor(getResources().getColor(R.color.White));
                } else {
                    arrsavebooleanstate.set(1, false);
                    arraysavefilter.set(1, "");


                    if (arraysavefilter.get(0).equalsIgnoreCase("") &&
                            arraysavefilter.get(1).equalsIgnoreCase("") &&
                            arraysavefilter.get(2).equalsIgnoreCase("") &&
                            arraysavefilter.get(3).equalsIgnoreCase("") &&
                            arraysavefilter.get(4).equalsIgnoreCase("") &&
                            arraysavefilter.get(5).equalsIgnoreCase("")) {
                        webinartype = "";
                    } else {
                        webinartype = arraysavefilter.toString().replace("[", "").replace("]", "")
                                .replace(" ", "");
                    }


                    binding.btnLiveWebinar.setBackgroundResource(R.drawable.chipsetview_filter_home_unselected);
                    binding.btnLiveWebinar.setTextColor(getResources().getColor(R.color.home_tab_color_unselected));
                }

            }
        });

        binding.btnSelfStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start = 0;
                limit = 10;
                loading = true;


                if (arrsavebooleanstate.get(2) == false) {
                    arrsavebooleanstate.set(2, true);
                    arraysavefilter.set(2, getResources().getString(R.string.str_filter_home_self_study));
                    webinartype = arraysavefilter.toString().replace("[", "").replace("]", "")
                            .replace(" ", "");
                    binding.btnSelfStudy.setBackgroundResource(R.drawable.chipsetview_filter_home);
                    binding.btnSelfStudy.setTextColor(getResources().getColor(R.color.White));
                } else {
                    arrsavebooleanstate.set(2, false);
                    arraysavefilter.set(2, "");


                    if (arraysavefilter.get(0).equalsIgnoreCase("") &&
                            arraysavefilter.get(1).equalsIgnoreCase("") &&
                            arraysavefilter.get(2).equalsIgnoreCase("") &&
                            arraysavefilter.get(3).equalsIgnoreCase("") &&
                            arraysavefilter.get(4).equalsIgnoreCase("") &&
                            arraysavefilter.get(5).equalsIgnoreCase("")) {
                        webinartype = "";
                    } else {
                        webinartype = arraysavefilter.toString().replace("[", "").replace("]", "")
                                .replace(" ", "");
                    }


                    binding.btnSelfStudy.setBackgroundResource(R.drawable.chipsetview_filter_home_unselected);
                    binding.btnSelfStudy.setTextColor(getResources().getColor(R.color.home_tab_color_unselected));
                }

            }
        });

        binding.btnPremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start = 0;
                limit = 10;
                loading = true;


                if (arrsavebooleanstate.get(3) == false) {
                    arrsavebooleanstate.set(3, true);
                    arraysavefilter.set(3, getResources().getString(R.string.str_filter_home_price_premium));
                    webinartype = arraysavefilter.toString().replace("[", "").replace("]", "")
                            .replace(" ", "");
                    binding.btnPremium.setBackgroundResource(R.drawable.chipsetview_filter_home);
                    binding.btnPremium.setTextColor(getResources().getColor(R.color.White));
                } else {
                    arrsavebooleanstate.set(3, false);
                    arraysavefilter.set(3, "");


                    if (arraysavefilter.get(0).equalsIgnoreCase("") &&
                            arraysavefilter.get(1).equalsIgnoreCase("") &&
                            arraysavefilter.get(2).equalsIgnoreCase("") &&
                            arraysavefilter.get(3).equalsIgnoreCase("") &&
                            arraysavefilter.get(4).equalsIgnoreCase("") &&
                            arraysavefilter.get(5).equalsIgnoreCase("")) {
                        webinartype = "";
                    } else {
                        webinartype = arraysavefilter.toString().replace("[", "").replace("]", "")
                                .replace(" ", "");
                    }


                    binding.btnPremium.setBackgroundResource(R.drawable.chipsetview_filter_home_unselected);
                    binding.btnPremium.setTextColor(getResources().getColor(R.color.home_tab_color_unselected));
                }

            }
        });

        binding.btnFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start = 0;
                limit = 10;
                loading = true;


                if (arrsavebooleanstate.get(4) == false) {
                    arrsavebooleanstate.set(4, true);
                    arraysavefilter.set(4, getResources().getString(R.string.str_filter_home_price_premium));
                    webinartype = arraysavefilter.toString().replace("[", "").replace("]", "")
                            .replace(" ", "");
                    binding.btnFree.setBackgroundResource(R.drawable.chipsetview_filter_home);
                    binding.btnFree.setTextColor(getResources().getColor(R.color.White));
                } else {
                    arrsavebooleanstate.set(4, false);
                    arraysavefilter.set(4, "");


                    if (arraysavefilter.get(0).equalsIgnoreCase("") &&
                            arraysavefilter.get(1).equalsIgnoreCase("") &&
                            arraysavefilter.get(2).equalsIgnoreCase("") &&
                            arraysavefilter.get(3).equalsIgnoreCase("") &&
                            arraysavefilter.get(4).equalsIgnoreCase("") &&
                            arraysavefilter.get(5).equalsIgnoreCase("")) {
                        webinartype = "";
                    } else {
                        webinartype = arraysavefilter.toString().replace("[", "").replace("]", "")
                                .replace(" ", "");
                    }


                    binding.btnFree.setBackgroundResource(R.drawable.chipsetview_filter_home_unselected);
                    binding.btnFree.setTextColor(getResources().getColor(R.color.home_tab_color_unselected));
                }



            }
        });

        binding.btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start = 0;
                limit = 10;
                loading = true;


                if (arrsavebooleanstate.get(5) == false) {
                    arrsavebooleanstate.set(5, true);
                    arraysavefilter.set(5, getResources().getString(R.string.str_filter_home_price_premium));
                    webinartype = arraysavefilter.toString().replace("[", "").replace("]", "")
                            .replace(" ", "");
                    binding.btnDate.setBackgroundResource(R.drawable.chipsetview_filter_home);
                    binding.btnDate.setTextColor(getResources().getColor(R.color.White));
                } else {
                    arrsavebooleanstate.set(5, false);
                    arraysavefilter.set(5, "");


                    if (arraysavefilter.get(0).equalsIgnoreCase("") &&
                            arraysavefilter.get(1).equalsIgnoreCase("") &&
                            arraysavefilter.get(2).equalsIgnoreCase("") &&
                            arraysavefilter.get(3).equalsIgnoreCase("") &&
                            arraysavefilter.get(4).equalsIgnoreCase("") &&
                            arraysavefilter.get(5).equalsIgnoreCase("")) {
                        webinartype = "";
                    } else {
                        webinartype = arraysavefilter.toString().replace("[", "").replace("]", "")
                                .replace(" ", "");
                    }


                    binding.btnDate.setBackgroundResource(R.drawable.chipsetview_filter_home_unselected);
                    binding.btnDate.setTextColor(getResources().getColor(R.color.home_tab_color_unselected));
                }

            }
        });


        binding.rvhome.addOnScrollListener(new RecyclerView.OnScrollListener() {
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


        binding.rvhome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && MainActivity.getInstance().rel_top_bottom.isShown()) {
                    getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                    MainActivity.getInstance().rel_top_bottom.setVisibility(View.GONE);

                  /*  Animation bottomDown = AnimationUtils.loadAnimation(getContext(),
                            R.anim.bottom_down);
                    MainActivity.getInstance().rel_top_bottom.startAnimation(bottomDown);
                    MainActivity.getInstance().rel_top_bottom.setVisibility(View.GONE);*/


                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    getActivity().overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
                    MainActivity.getInstance().rel_top_bottom.setVisibility(View.VISIBLE);

                   /* Animation bottomUp = AnimationUtils.loadAnimation(getContext(),
                            R.anim.bottom_up);
                    MainActivity.getInstance().rel_top_bottom.startAnimation(bottomUp);
                    MainActivity.getInstance().rel_top_bottom.setVisibility(View.VISIBLE);*/
                }

                super.onScrollStateChanged(recyclerView, newState);
            }
        });


        binding.swipeRefreshLayouthome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });


        binding.btnLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start = 0;
                limit = 10;
                loading = true;


                if (arrsavebooleanstate.get(0) == false) {
                    arrsavebooleanstate.set(0, true);
                    arraysavefilter.set(0, getResources().getString(R.string.str_filter_live));
                    webinartype = arraysavefilter.toString().replace("[", "").replace("]", "")
                            .replace(" ", "");
                    binding.btnLive.setBackgroundResource(R.drawable.col_three_bg_new_hover);
                    binding.btnLive.setTextColor(getResources().getColor(R.color.White));
                } else {
                    arrsavebooleanstate.set(0, false);
                    arraysavefilter.set(0, "");


                    if (arraysavefilter.get(0).equalsIgnoreCase("") &&
                            arraysavefilter.get(1).equalsIgnoreCase("") &&
                            arraysavefilter.get(2).equalsIgnoreCase("")) {
                        webinartype = "";
                    } else {
                        webinartype = arraysavefilter.toString().replace("[", "").replace("]", "")
                                .replace(" ", "");
                    }


                    binding.btnLive.setBackgroundResource(R.drawable.col_three_bg_new);
                    binding.btnLive.setTextColor(getResources().getColor(R.color.home_tab_color_unselected));
                }


                if (Constant.isNetworkAvailable(context)) {
                    progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                    GetHomeListNew(webinartype, topicsofinterest, start, limit);
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();

                }


            }
        });


        binding.btnSelfstudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start = 0;
                limit = 10;
                loading = true;


                if (arrsavebooleanstate.get(1) == false) {
                    arrsavebooleanstate.set(1, true);
                    arraysavefilter.set(1, getResources().getString(R.string.str_filter_selfstudy));
                    webinartype = arraysavefilter.toString().replace("[", "").replace("]", "")
                            .replace(" ", "");


                    binding.btnSelfstudy.setBackgroundResource(R.drawable.col_three_bg_new_hover);
                    binding.btnSelfstudy.setTextColor(getResources().getColor(R.color.White));
                } else {
                    arrsavebooleanstate.set(1, false);
                    arraysavefilter.set(1, "");
                    if (arraysavefilter.get(0).equalsIgnoreCase("") &&
                            arraysavefilter.get(1).equalsIgnoreCase("") &&
                            arraysavefilter.get(2).equalsIgnoreCase("")) {
                        webinartype = "";


                    } else {
                        webinartype = arraysavefilter.toString().replace("[", "").replace("]", "")
                                .replace(" ", "");

                    }


                    binding.btnSelfstudy.setBackgroundResource(R.drawable.col_three_bg_new);
                    binding.btnSelfstudy.setTextColor(getResources().getColor(R.color.home_tab_color_unselected));
                }


                if (Constant.isNetworkAvailable(context)) {
                    progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                    GetHomeListNew(webinartype, topicsofinterest, start, limit);
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();

                }

            }
        });


        binding.btnArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start = 0;
                limit = 10;
                loading = true;


                if (arrsavebooleanstate.get(2) == false) {
                    arrsavebooleanstate.set(2, true);
                    arraysavefilter.set(2, getResources().getString(R.string.str_filter_archieve));
                    webinartype = arraysavefilter.toString().replace("[", "").replace("]", "")
                            .replace(" ", "");
                    binding.btnArchive.setBackgroundResource(R.drawable.col_three_bg_new_hover);
                    binding.btnArchive.setTextColor(getResources().getColor(R.color.White));
                } else {
                    arrsavebooleanstate.set(2, false);
                    arraysavefilter.set(2, "");
                    if (arraysavefilter.get(0).equalsIgnoreCase("") &&
                            arraysavefilter.get(1).equalsIgnoreCase("") &&
                            arraysavefilter.get(2).equalsIgnoreCase("")) {
                        webinartype = "";
                    } else {
                        webinartype = arraysavefilter.toString().replace("[", "").replace("]", "")
                                .replace(" ", "");
                    }

                    binding.btnArchive.setBackgroundResource(R.drawable.col_three_bg_new);
                    binding.btnArchive.setTextColor(getResources().getColor(R.color.home_tab_color_unselected));
                }


                if (Constant.isNetworkAvailable(context)) {
                    progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                    GetHomeListNew(webinartype, topicsofinterest, start, limit);
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


    public void GetTopics() {
        mAPIService_new.GetSubjectAreaTopics(getResources().getString(R.string.accept)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Subject_Area>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {


                        String message = Constant.GetReturnResponse(context, e);

                        if (Constant.status_code == 401) {
                            MainActivity.getInstance().AutoLogout();
                        } else {
                            Snackbar.make(binding.rvhome, message, Snackbar.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onNext(Subject_Area subject_area) {

                        if (subject_area.isSuccess()) {


                            arraylistModelSubjectArea.clear();

                            for (int i = 0; i < subject_area.getPayload().getSubjectArea().size(); i++) {
                                Model_Subject_Area model_subject_area = new Model_Subject_Area();

                                model_subject_area.setName(subject_area.getPayload().getSubjectArea().get(i).getName());
                                model_subject_area.setId(subject_area.getPayload().getSubjectArea().get(i).getId());
                                model_subject_area.setChecked(false);

                               /* Constant.hashmap_professional_credential.put(userTypeModel.getPayload().getUserType().get(i).getName(),
                                        false);*/

                                arraylistModelSubjectArea.add(model_subject_area);


                            }


                        } else {

                            Snackbar.make(binding.rvhome, subject_area.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }


                    }
                });
    }


    public void ShowTopics() {


        myDialog_topics.setContentView(R.layout.popup_professional_credential);
        myDialog_topics.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tv_header = (TextView) myDialog_topics.findViewById(R.id.tv_header);
        tv_submit = (TextView) myDialog_topics.findViewById(R.id.tv_submit);
        tv_cancel = (TextView) myDialog_topics.findViewById(R.id.tv_cancel);
        rv_topics = (RecyclerView) myDialog_topics.findViewById(R.id.rv_professional_credential);

        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv_topics.setLayoutManager(linearLayoutManager);
        rv_topics.addItemDecoration(new SimpleDividerItemDecoration(context));
        tv_header.setText(context.getResources().getString(R.string.str_topic_home));

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (myDialog_topics.isShowing()) {
                    myDialog_topics.cancel();
                }

                /*ArrayList<Integer> myArrayList = new ArrayList<Integer>(new LinkedHashSet<Integer>(Constant.arraylistselectedproffesionalcredentialID));


                if (myArrayList.size() > 0) {

                    StringBuilder commaSepValueBuilder = new StringBuilder();

                    //Looping through the list
                    for (int i = 0; i < myArrayList.size(); i++) {
                        //append the value into the builder
                        commaSepValueBuilder.append(myArrayList.get(i));

                        //if the value is not the last element of the list
                        //then append the comma(,) as well
                        if (i != myArrayList.size() - 1) {
                            commaSepValueBuilder.append(",");
                        }
                    }
                    //System.out.println(commaSepValueBuilder.toString());
                    selected_proffesional_credential = commaSepValueBuilder.toString();

                    System.out.println(selected_proffesional_credential);


                } else {
                    selected_proffesional_credential = "";
                    Snackbar.make(tv_submit, context.getResources().getString(R.string.validation_professional_credential), Snackbar.LENGTH_SHORT).show();
                }


*/

            }
        });


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (myDialog_topics.isShowing()) {
                    myDialog_topics.dismiss();
                }

            }
        });


        myDialog_topics.show();

        if (arraylistModelSubjectArea.size() > 0) {
            topicsFilterHomePopUpAdapter = new TopicsFilterHomePopUpAdapter(context,
                    arraylistModelSubjectArea);
            rv_topics.setAdapter(topicsFilterHomePopUpAdapter);
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
                getActivity().finish();


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


    public static HomeAllFragment getInstance() {
        return instance;
    }


    @Override
    public void onResume() {
        super.onResume();

        if (arraylistselectedvalue.size() > 0) {
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
                GetHomeListNew(webinartype, topicsofinterest, start, limit);
            } else {
                Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();

            }

        }

    }


    private void loadNextPage() {
        if (Constant.isNetworkAvailable(context)) {
            binding.progressBar.setVisibility(View.VISIBLE);
            GetHomeListNew(webinartype, topicsofinterest, start, limit);
        } else {
            Snackbar.make(binding.rvhome, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }
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
            GetHomeListNew(webinartype, topicsofinterest, start, limit);
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }
    }


    public void GetHomeListNew(final String webinartype, final String topicsofinterest, final int start, final int limit) {

        mAPIService_new.GetHomeWebinarListNew(getResources().getString(R.string.accept),
                getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context), start, limit, webinartype, topicsofinterest).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Webinar_Home_New>() {
                    @Override
                    public void onCompleted() {

                        if (binding.progressBar.getVisibility() == View.VISIBLE) {
                            binding.progressBar.setVisibility(View.GONE);
                        } else if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }


                        if (Constant.isNetworkAvailable(context)) {
                            GetTopics();
                        } else {
                            Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();

                        }

                        loading = true;

                        if (start == 0 && limit == 10) {
                            if (arrHomelistnew.size() > 0) {
                                adapter = new HomeALLAdapter(context, arrHomelistnew);
                                binding.rvhome.setAdapter(adapter);

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
                            Snackbar.make(binding.rvhome, message, Snackbar.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onNext(Webinar_Home_New webinar_home_new) {

                        if (webinar_home_new.isSuccess() == true) {

                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            } else {
                                if (binding.swipeRefreshLayouthome.isRefreshing()) {
                                    binding.swipeRefreshLayouthome.setRefreshing(false);
                                }
                            }

                            arraylistselectedvalue.clear();


                            islast = webinar_home_new.getPayload().isIsLast();


                            if (start == 0 && limit == 10) {
                                if (arrHomelistnew.size() > 0) {
                                    arrHomelistnew.clear();
                                }

                            }


                            if (start == 0 && limit == 10) {
                                arrHomelistnew = webinar_home_new.getPayload().getWebinar();

                            } else {

                                if (arrHomelistnew.size() > 20) {
                                    arrHomelistnew.remove(arrHomelistnew.size() - 1);
                                }


                                List<com.entigrity.model.homewebinarnew.WebinarItem> webinaritems = webinar_home_new.getPayload().getWebinar();
                                adapter.addAll(webinaritems);


                            }


                            if (arrHomelistnew.size() > 0) {
                                binding.swipeRefreshLayouthome.setVisibility(View.VISIBLE);
                                binding.tvNodatafound.setVisibility(View.GONE);
                            } else {
                                binding.swipeRefreshLayouthome.setVisibility(View.GONE);
                                binding.tvNodatafound.setVisibility(View.VISIBLE);
                            }


                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            } else {
                                if (binding.swipeRefreshLayouthome.isRefreshing()) {
                                    binding.swipeRefreshLayouthome.setRefreshing(false);
                                }
                            }
                            Snackbar.make(binding.rvhome, webinar_home_new.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }


                    }


                });


    }


    boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) binding.rvhome.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = binding.rvhome.getAdapter().getItemCount() - 1;

        return (pos >= numItems);
    }


}
