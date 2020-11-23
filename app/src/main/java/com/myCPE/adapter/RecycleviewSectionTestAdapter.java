package com.myCPE.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.activity.TopicsOfInterestActivity;
import com.myCPE.model.savetopicsofinterest.SaveTopicsInterest;
import com.myCPE.model.topics_subcategory.Topics_subcategory;
import com.myCPE.utility.AppSettings;
import com.myCPE.utility.Constant;
import com.myCPE.view.DialogsUtils;
import com.myCPE.view.GridSpacingItemDecoration;
import com.myCPE.view.SimpleDividerItemDecoration;
import com.myCPE.webservice.APIService;
import com.myCPE.webservice.ApiUtilsNew;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RecycleviewSectionTestAdapter extends RecyclerView.Adapter<RecycleviewSectionTestAdapter.SectionViewHolder> {


    class SectionViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_category;
        private ImageView iv_edit;
        private RecyclerView item_recycler_view;


        public SectionViewHolder(View itemView) {
            super(itemView);
            setIsRecyclable(false);
            tv_category = (TextView) itemView.findViewById(R.id.tv_category);
            item_recycler_view = (RecyclerView) itemView.findViewById(R.id.item_recycler_view);
            iv_edit = (ImageView) itemView.findViewById(R.id.iv_edit);

        }
    }

    private List<com.myCPE.model.view_interest_favorite.TopicOfInterestsItem> mlist;
    public static Context mContext;
    private APIService mAPIService;
    ProgressDialog progressDialog;
    public Dialog myDialog;
    private String header;
    private TextView tv_header, tv_submit, tv_cancel;
    private RecyclerView recyclerview_topics_interest_subcategory;
    public ArrayList<Integer> arraylistselectedtopicsofinterest = new ArrayList<Integer>();
    LinearLayoutManager linearLayoutManager;
    TopicsofinterestPopUpAdapter topicsofinterestPopUpAdapter;
    List<com.myCPE.model.view_interest_favorite.TagsItem> itemsInSection;
    NeastedAdapter adapter;
    GridLayoutManager gridLayoutManager;
    private ArrayList<com.myCPE.model.topics_subcategory.TopicOfInterestsItem> mListrtopicsofinterestsubcategory = new ArrayList<>();


    public RecycleviewSectionTestAdapter(Context context, List<com.myCPE.model.view_interest_favorite.TopicOfInterestsItem> mlist) {
        this.mContext = context;
        this.mlist = mlist;
        myDialog = new Dialog(context);
        mAPIService = ApiUtilsNew.getAPIService();

    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_section, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SectionViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        holder.item_recycler_view.setNestedScrollingEnabled(false);

        holder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                header = mlist.get(position).getName();

                if (mListrtopicsofinterestsubcategory.size() > 0) {
                    mListrtopicsofinterestsubcategory.clear();
                }

                if (Constant.isNetworkAvailable(mContext)) {
                    progressDialog = DialogsUtils.showProgressDialog(mContext, mContext.getResources().getString(R.string.progrees_msg));
                    GetSubcategory(mlist.get(position).getId(), holder.iv_edit);
                } else {
                    Snackbar.make(holder.iv_edit, mContext.getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                }

            }
        });


        holder.tv_category.setText(mlist.get(position).getName());


        gridLayoutManager = new GridLayoutManager(mContext, 3);
        int spacing = (int) mContext.getResources().getDimension(R.dimen._3sdp); // 50px
        boolean includeEdge = false;
        holder.item_recycler_view.addItemDecoration(new GridSpacingItemDecoration(3, spacing, includeEdge));
        holder.item_recycler_view.setLayoutManager(gridLayoutManager);

        itemsInSection = mlist.get(position).getTags();

        if (itemsInSection.size() > 0) {
            adapter = new NeastedAdapter(mContext, itemsInSection);
            holder.item_recycler_view.setAdapter(adapter);
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }


    public void GetSubcategory(int category_id, final ImageView imageView) {

        mAPIService.GetSubcategoryTopics(mContext.getResources().getString(R.string.accept), mContext.getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(mContext), category_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Topics_subcategory>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        String message = Constant.GetReturnResponse(mContext, e);
                        if (Constant.status_code == 401) {
                            MainActivity.getInstance().AutoLogout();
                        } else {
                            Snackbar.make(imageView, message, Snackbar.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onNext(Topics_subcategory topics_subcategory) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        if (topics_subcategory.isSuccess() == true) {


                            for (int i = 0; i < topics_subcategory.getPayload().getTopicOfInterests().size(); i++) {
                                com.myCPE.model.topics_subcategory.TopicOfInterestsItem topicOfInterestsItem
                                        = new com.myCPE.model.topics_subcategory.TopicOfInterestsItem();

                                topicOfInterestsItem.setId(topics_subcategory.getPayload().getTopicOfInterests().get(i).getId());
                                topicOfInterestsItem.setName(topics_subcategory.getPayload().getTopicOfInterests().get(i).getName());
                                topicOfInterestsItem.setIsChecked(topics_subcategory.getPayload().getTopicOfInterests().get(i).isIsChecked());

                                mListrtopicsofinterestsubcategory.add(topicOfInterestsItem);
                            }

                            if (mListrtopicsofinterestsubcategory.size() > 0) {
                                ShowSubcategoryPopup();
                            }


                        } else {
                            Snackbar.make(imageView, topics_subcategory.getMessage(), Snackbar.LENGTH_SHORT).show();


                        }


                    }

                });

    }

    public void ShowSubcategoryPopup() {
        myDialog.setContentView(R.layout.popup_topics_of_interest);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tv_header = (TextView) myDialog.findViewById(R.id.tv_header);
        tv_submit = (TextView) myDialog.findViewById(R.id.tv_submit);
        tv_cancel = (TextView) myDialog.findViewById(R.id.tv_cancel);
        recyclerview_topics_interest_subcategory = (RecyclerView) myDialog.findViewById(R.id.recyclerview_topics_interest_subcategory);

        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerview_topics_interest_subcategory.setLayoutManager(linearLayoutManager);
        recyclerview_topics_interest_subcategory.addItemDecoration(new SimpleDividerItemDecoration(mContext));
        tv_header.setText(header);


        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                arraylistselectedtopicsofinterest = Constant.arraylistselectedtopicsofinterest;
                ArrayList<Integer> myArrayList = new ArrayList<Integer>(new LinkedHashSet<Integer>(arraylistselectedtopicsofinterest));


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
                    String selectedlist = commaSepValueBuilder.toString();

                    System.out.println(selectedlist);


                    if (Constant.isNetworkAvailable(mContext)) {
                        progressDialog = DialogsUtils.showProgressDialog(mContext, mContext.getResources().getString(R.string.progrees_msg));
                        SaveTopicsofInterest(selectedlist);
                    } else {
                        if (myDialog.isShowing()) {
                            myDialog.dismiss();
                        }
                        Snackbar.make(tv_submit, mContext.getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                    }

                } else {
                    if (myDialog.isShowing()) {
                        myDialog.dismiss();
                    }

                    Snackbar.make(tv_submit, mContext.getResources().getString(R.string.val_topics), Snackbar.LENGTH_SHORT).show();
                }


            }
        });


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (myDialog.isShowing()) {
                    myDialog.dismiss();
                }

            }
        });


        if (mListrtopicsofinterestsubcategory.size() > 0) {
            topicsofinterestPopUpAdapter = new TopicsofinterestPopUpAdapter(mContext, mListrtopicsofinterestsubcategory);
            recyclerview_topics_interest_subcategory.setAdapter(topicsofinterestPopUpAdapter);
        }


        myDialog.show();


    }

    private void SaveTopicsofInterest(String selectedlist) {
        mAPIService.PostTopicsOfInterest(mContext.getResources().getString(R.string.accept), mContext.getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(mContext), selectedlist).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SaveTopicsInterest>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        String message = Constant.GetReturnResponse(mContext, e);
                        if (Constant.status_code == 401) {
                            MainActivity.getInstance().AutoLogout();
                        } else {
                            Snackbar.make(tv_submit, message, Snackbar.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onNext(SaveTopicsInterest saveTopicsInterest) {

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }


                        if (saveTopicsInterest.isSuccess() == true) {


                            if (Constant.arraylistselectedtopicsofinterest.size() > 0) {
                                Constant.arraylistselectedtopicsofinterest.clear();
                            }
                            if (arraylistselectedtopicsofinterest.size() > 0) {
                                arraylistselectedtopicsofinterest.clear();
                            }


                            if (myDialog.isShowing()) {
                                myDialog.dismiss();
                            }

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    TopicsOfInterestActivity.getInstance().ReCreate();
                                }
                            }, 500);


                        } else {

                            if (myDialog.isShowing()) {
                                myDialog.dismiss();
                            }

                            Snackbar.make(tv_submit, saveTopicsInterest.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }


                    }
                });


    }


}

