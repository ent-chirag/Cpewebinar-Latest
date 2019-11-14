package com.entigrity.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.entigrity.MainActivity;
import com.entigrity.R;
import com.entigrity.adapter.CertificatesListPopUpAdapter;
import com.entigrity.adapter.MyCreditAdapter;
import com.entigrity.databinding.FragmentMycreditBinding;
import com.entigrity.model.MyCreditNew.ModelMyCertificateLinksItem;
import com.entigrity.model.MyCreditNew.MyCertificateLinksItem;
import com.entigrity.model.MyCreditNew.MyCreditsItem;
import com.entigrity.model.MyCreditNew.Response;
//import com.entigrity.model.My_Credit.MyCreditsItem;
import com.entigrity.model.My_Credit.My_Credit;
import com.entigrity.model.Proffesional_Credential.Model_proffesional_Credential;
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

public class MyCreditsFragment extends Fragment {

    View view;
    public Context context;
    FragmentMycreditBinding binding;
    MyCreditAdapter adapter;
    private APIService mAPIService;
    Typeface font;
    public int filter_type = 0;
    ProgressDialog progressDialog;
    LinearLayoutManager linearLayoutManager;
    private List<MyCreditsItem> mlistmycredit = new ArrayList<MyCreditsItem>();
    public int start = 0, limit = 10;
    private boolean loading = true;
    public boolean islast = false;
    private static final String TAG = MyCreditsFragment.class.getName();
    private static MyCreditsFragment instance;

    public Dialog dialogCertificate;

//    public ArrayList<MyCreditsItem> arrayCertificateList = new ArrayList<>();
//    public ArrayList<MyCertificateLinksItem> arrayCertificateList = new ArrayList<>();
    public ArrayList<ModelMyCertificateLinksItem> arrayCertificateList = new ArrayList<>();
    public CertificatesListPopUpAdapter certificatesListPopUpAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mycredit, null, false);
        context = getActivity();
        mAPIService = ApiUtilsNew.getAPIService();
        instance = MyCreditsFragment.this;

        font = Typeface.createFromAsset(getActivity().getAssets(), "Montserrat-Light.ttf");

        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.recyclerviewMycredit.setLayoutManager(linearLayoutManager);
        binding.recyclerviewMycredit.addItemDecoration(new SimpleDividerItemDecoration(context));
        binding.recyclerviewMycredit.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerviewMycredit.setHasFixedSize(true);

        dialogCertificate = new Dialog(context);

        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
//            GetMyCredit(start, limit);
            GetTestAPI(start, limit);
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }

        binding.lvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                binding.lvAll.setBackgroundResource(R.mipmap.my_certi_gray_select);
                binding.lvLive.setBackgroundResource(R.mipmap.my_certi_gray);
                binding.lvSelfStudy.setBackgroundResource(R.mipmap.my_certi_gray);


                filter_type = 0;
                start = 0;
                limit = 10;
                loading = true;
                if (Constant.isNetworkAvailable(context)) {
                    progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
//                    GetMyCredit(start, limit);
                    GetTestAPI(start, limit);
                } else {
                    Snackbar.make(binding.recyclerviewMycredit, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                }

            }
        });
        binding.lvLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                binding.lvAll.setBackgroundResource(R.mipmap.my_certi_gray);
                binding.lvLive.setBackgroundResource(R.mipmap.my_certi_gray_select);
                binding.lvSelfStudy.setBackgroundResource(R.mipmap.my_certi_gray);


                filter_type = 1;
                start = 0;
                limit = 10;
                loading = true;
                if (Constant.isNetworkAvailable(context)) {
                    progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
//                    GetMyCredit(start, limit);
                    GetTestAPI(start, limit);
                } else {
                    Snackbar.make(binding.recyclerviewMycredit, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        binding.lvSelfStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.lvAll.setBackgroundResource(R.mipmap.my_certi_gray);
                binding.lvLive.setBackgroundResource(R.mipmap.my_certi_gray);
                binding.lvSelfStudy.setBackgroundResource(R.mipmap.my_certi_gray_select);


                filter_type = 2;
                start = 0;
                limit = 10;
                loading = true;
                if (Constant.isNetworkAvailable(context)) {
                    progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
//                    GetMyCredit(start, limit);
                    GetTestAPI(start, limit);
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

    public static MyCreditsFragment getInstance() {
        return instance;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Constant.isdataupdate) {
            Constant.isdataupdate = false;
            if (Constant.isNetworkAvailable(context)) {
                progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
//                GetMyCredit(start, limit);
                GetTestAPI(start, limit);
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
//            GetMyCredit(start, limit);
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
//            GetMyCredit(start, limit);
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

    private void GetTestAPI(final int start,final int limit) {

        mAPIService.Test(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context), start, limit)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
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
                    public void onNext(Response response) {
                        if (response.isSuccess() == true) {

                            /*arrayCertificateList.clear();
                            Log.e("*+*+*","Size for PayLoad is : "+response.getPayload().size());
                            Log.e("*+*+*","Size for mlistmycredit is : "+mlistmycredit.size());
                            for (int i = 0; i < response.getPayload().size(); i++) {
                                Log.e("*+*+*","Size for getMyCredits is : i : "+i+" size :"+response.getPayload().get(i).getMyCredits().size());
                                for (int j = 0; j < response.getPayload().get(i).getMyCredits().get(j).getMyCertificateLinks().size(); j++) {
                                    Log.e("*+*+*","Size for getMyCertificateLink is : i : "+i+" size :"+response.getPayload().get(i).getMyCredits().get(j).getMyCertificateLinks().size());
                                    for (int k = 0; k < response.getPayload().get(i).getMyCredits().get(j).getMyCertificateLinks().size(); k++) {
                                        ModelMyCertificateLinksItem myCertificateLinksItem = new ModelMyCertificateLinksItem();
                                        myCertificateLinksItem.setCertificateLink(response.getPayload().get(i).getMyCredits().get(j).getMyCertificateLinks().get(j).getCertificateLink());
                                        myCertificateLinksItem.setCertificateType(response.getPayload().get(i).getMyCredits().get(j).getMyCertificateLinks().get(j).getCertificateType());
                                    }
                                }
                            }*/

                            /*for (int i = 0; i < response.getPayload().get(i).getMyCredits().size() ; i++) {
                                for (int j = 0; j < response.getPayload().get(i).getMyCredits().get(j).getMyCertificateLinks().size(); j++) {
//                                    arrayCertificateList.add(response.getPayload().get(i).getMyCredits().get(j).getMyCertificateLinks());

                                    ModelMyCertificateLinksItem myCertificateLinksItem = new ModelMyCertificateLinksItem();
                                    myCertificateLinksItem.setCertificateLink(response.getPayload().get(i).getMyCredits().get(j).getMyCertificateLinks().get(j).getCertificateLink());
                                    myCertificateLinksItem.setCertificateType(response.getPayload().get(i).getMyCredits().get(j).getMyCertificateLinks().get(j).getCertificateType());

                                    arrayCertificateList.add(myCertificateLinksItem);
                                }
                                Log.e("*+*+*","arrayCertificateList size : "+arrayCertificateList.size());
                            }*/


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

                            islast = response.getPayload().get(0).isIsLast();

                            if (start == 0 && limit == 10) {
                                for (int i = 0; i < response.getPayload().size(); i++) {
                                    mlistmycredit = response.getPayload().get(i).getMyCredits();
                                }
                            } else {

                                if (mlistmycredit.size() > 20) {
                                    mlistmycredit.remove(mlistmycredit.size() - 1);
                                }


                                List<MyCreditsItem> webinaritems = new ArrayList<>();
                                for (int i = 0; i < response.getPayload().size(); i++) {
                                    webinaritems = response.getPayload().get(i).getMyCredits();
                                }


                                adapter.addAll(webinaritems);

                            }

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
                            Snackbar.make(binding.ivnotification, response.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void displayCertificateDialog(){

        dialogCertificate.setContentView(R.layout.popup_certificates);
        dialogCertificate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tv_header = (TextView) dialogCertificate.findViewById(R.id.tv_header);
        TextView tv_submit = (TextView) dialogCertificate.findViewById(R.id.tv_submit);
        TextView tv_cancel = (TextView) dialogCertificate.findViewById(R.id.tv_cancel);
        RecyclerView rv_professional_credential = (RecyclerView) dialogCertificate.findViewById(R.id.rv_professional_credential);

        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv_professional_credential.setLayoutManager(linearLayoutManager);
        rv_professional_credential.addItemDecoration(new SimpleDividerItemDecoration(context));
        tv_header.setText(context.getResources().getString(R.string.str_certificate_list));

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogCertificate.isShowing()) {
                    dialogCertificate.dismiss();
                }
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogCertificate.isShowing()) {
                    dialogCertificate.dismiss();
                }
            }
        });

        dialogCertificate.show();

        if(arrayCertificateList.size() > 0) {
            certificatesListPopUpAdapter = new CertificatesListPopUpAdapter(context, arrayCertificateList);
            rv_professional_credential.setAdapter(certificatesListPopUpAdapter);
        }
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


    boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) binding.recyclerviewMycredit.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = binding.recyclerviewMycredit.getAdapter().getItemCount() - 1;
        return (pos >= numItems);
    }


}
