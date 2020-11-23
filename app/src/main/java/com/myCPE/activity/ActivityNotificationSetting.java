package com.myCPE.activity;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.databinding.ActivityNotificationSettingNewBinding;
import com.myCPE.model.getnotificationsetting.GetNotificationModel;
import com.myCPE.model.savenotificationsetting.SubmitNotification;
import com.myCPE.utility.AppSettings;
import com.myCPE.utility.Constant;
import com.myCPE.view.DialogsUtils;
import com.myCPE.webservice.APIService;
import com.myCPE.webservice.ApiUtilsNew;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivityNotificationSetting extends AppCompatActivity {

    ActivityNotificationSettingNewBinding binding;
    private static final String TAG = ActivityNotificationSetting.class.getName();
    public Context context;
    public boolean checkexpand_pushnoti_webinar = true;
    public boolean checkexpand_pushnoti_pending_action = true;
    public boolean checkexpand_text_webinar = true;
    public boolean checkexpand_text_pending_action = true;
    ProgressDialog progressDialog;
    private APIService mAPIService;

    private ArrayList<Boolean> arraypushboolean = new ArrayList<>();
    private ArrayList<Boolean> arraytextboolean = new ArrayList<>();


    private ArrayList<String> arraypush_string = new ArrayList<>();
    private ArrayList<String> arraytext_string = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification_setting_new);
        context = ActivityNotificationSetting.this;
        mAPIService = ApiUtilsNew.getAPIService();


        binding.cbPushWebinarRegister.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isPressed()) {
                    if (isChecked) {
                        arraypushboolean.set(0, true);
                        arraypush_string.set(0, "true");
                    } else {
                        arraypushboolean.set(0, false);
                        arraypush_string.set(0, "false");
                    }
                }

            }
        });
        binding.cbPushWebinarReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        arraypushboolean.set(1, true);
                        arraypush_string.set(1, "true");
                    } else {
                        arraypushboolean.set(1, false);
                        arraypush_string.set(1, "false");
                    }
                }

            }
        });
        binding.cbPushWebinarUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isPressed()) {
                    if (isChecked) {
                        arraypushboolean.set(2, true);
                        arraypush_string.set(2, "true");
                    } else {
                        arraypushboolean.set(2, false);
                        arraypush_string.set(2, "false");
                    }
                }


            }
        });
        binding.cbPushQuizPending.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isPressed()) {
                    if (isChecked) {
                        arraypushboolean.set(3, true);
                        arraypush_string.set(3, "true");
                    } else {
                        arraypushboolean.set(3, false);
                        arraypush_string.set(3, "false");
                    }
                }


            }
        });
        binding.cbPushPendingEvoluation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        arraypushboolean.set(4, true);
                        arraypush_string.set(4, "true");
                    } else {
                        arraypushboolean.set(4, false);
                        arraypush_string.set(4, "false");
                    }
                }


            }
        });

        binding.cbTextWebinarRegister.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        arraytextboolean.set(0, true);
                        arraytext_string.set(0, "true");
                    } else {
                        arraytextboolean.set(0, false);
                        arraytext_string.set(0, "false");
                    }
                }


            }
        });
        binding.cbTextWebinarReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        arraytextboolean.set(1, true);
                        arraytext_string.set(1, "true");
                    } else {
                        arraytextboolean.set(1, false);
                        arraytext_string.set(1, "false");
                    }
                }


            }
        });
        binding.cbTextWebinarUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        arraytextboolean.set(2, true);
                        arraytext_string.set(2, "true");
                    } else {
                        arraytextboolean.set(2, false);
                        arraytext_string.set(2, "false");
                    }
                }


            }
        });
        binding.cbTextQuizPending.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {

                    if (isChecked) {
                        arraytextboolean.set(3, true);
                        arraytext_string.set(3, "true");
                    } else {
                        arraytextboolean.set(3, false);
                        arraytext_string.set(3, "false");
                    }
                }


            }
        });
        binding.cbTextPendingEvoluation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        arraytextboolean.set(4, true);
                        arraytext_string.set(4, "true");
                    } else {
                        arraytextboolean.set(4, false);
                        arraytext_string.set(4, "false");
                    }
                }


            }
        });


        binding.pushnotificationSeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, ActivityNotificationSetting.this.getPackageName());
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                    intent.putExtra("app_package", ActivityNotificationSetting.this.getPackageName());
                    intent.putExtra("app_uid", ActivityNotificationSetting.this.getApplicationInfo().uid);
                } else {
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setData(Uri.parse("package:" + ActivityNotificationSetting.this.getPackageName()));
                }
                ActivityNotificationSetting.this.startActivity(intent);
            }
        });


        binding.relWebinarText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkexpand_text_webinar) {
                    checkexpand_text_webinar = false;
                    collapse(binding.relTextRegister, 500, 0);
                    collapse(binding.relTextReminder, 500, 0);

                } else {
                    checkexpand_text_webinar = true;
                    expand(binding.relTextRegister, 500, 50);
                    expand(binding.relTextReminder, 500, 50);
                }

            }
        });

        binding.relTextPendingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkexpand_text_pending_action) {
                    checkexpand_text_pending_action = false;
                    collapse(binding.quizTextPending, 500, 0);
                    collapse(binding.relTextPendingEvolution, 500, 0);

                } else {
                    checkexpand_text_pending_action = true;
                    expand(binding.quizTextPending, 500, 50);
                    expand(binding.relTextPendingEvolution, 500, 50);
                }

            }
        });

        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            GetNotificationSettingList();
        } else {
            Snackbar.make(binding.quizTextPending, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }


        binding.relImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selectedlistpush = android.text.TextUtils.join(",", arraypush_string);
                System.out.println(selectedlistpush);

                String selectedlisttext = android.text.TextUtils.join(",", arraytext_string);
                System.out.println(selectedlisttext);


                if (Constant.isNetworkAvailable(context)) {
                    progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                    SaveNotificationSettingList(selectedlistpush, selectedlisttext);
                } else {
                    Snackbar.make(binding.quizTextPending, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        String selectedlistpush = android.text.TextUtils.join(",", arraypush_string);
        System.out.println(selectedlistpush);

        String selectedlisttext = android.text.TextUtils.join(",", arraytext_string);
        System.out.println(selectedlisttext);


        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            SaveNotificationSettingList(selectedlistpush, selectedlisttext);
        } else {
            Snackbar.make(binding.quizTextPending, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }

    }

    private void SaveNotificationSettingList(String push, String text) {
        mAPIService.SubmitNotification(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context),
                push, text).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SubmitNotification>() {
                    @Override
                    public void onCompleted() {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        String message = Constant.GetReturnResponse(context, e);
                        if (Constant.status_code == 401) {
                            MainActivity.getInstance().AutoLogout();
                        } else {
                            Snackbar.make(binding.relImgBack, message, Snackbar.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onNext(SubmitNotification submitNotification) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        if (submitNotification.isSuccess() == true) {
                            finish();
                        } else {
                            finish();

                        }
                    }

                });


    }

    private void GetNotificationSettingList() {
        mAPIService.GetNotificationSetting(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetNotificationModel>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        String message = Constant.GetReturnResponse(context, e);
                        if (Constant.status_code == 401) {
                            MainActivity.getInstance().AutoLogout();
                        } else {
                            Snackbar.make(binding.relImgBack, message, Snackbar.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onNext(GetNotificationModel getNotificationModel) {

                        if (getNotificationModel.isSuccess() == true) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }


                            for (int i = 0; i < getNotificationModel.getPayload().size(); i++) {
                                for (int k = 0; k < getNotificationModel.getPayload().get(i).getNotificationSettings().getPush().size(); k++) {
                                    arraypushboolean.add(getNotificationModel.getPayload().get(i).getNotificationSettings().getPush().get(k).isStatus());
                                }

                                for (int k = 0; k < getNotificationModel.getPayload().get(i).getNotificationSettings().getText().size(); k++) {
                                    arraytextboolean.add(getNotificationModel.getPayload().get(i).getNotificationSettings().getText().get(k).isStatus());
                                }
                            }


                            for (int i = 0; i < arraypushboolean.size(); i++) {
                                if (arraypushboolean.get(i) == true) {
                                    arraypush_string.add("true");
                                } else {
                                    arraypush_string.add("false");
                                }
                            }

                            for (int k = 0; k < arraytextboolean.size(); k++) {
                                if (arraytextboolean.get(k) == true) {
                                    arraytext_string.add("true");
                                } else {
                                    arraytext_string.add("false");
                                }
                            }


                            if (arraypushboolean.size() > 0) {
                                if (arraypushboolean.get(0)) {
                                    binding.cbPushWebinarRegister.setChecked(true);
                                } else {
                                    binding.cbPushWebinarRegister.setChecked(false);
                                }


                                if (arraypushboolean.get(1)) {
                                    binding.cbPushWebinarReminder.setChecked(true);
                                } else {
                                    binding.cbPushWebinarReminder.setChecked(false);
                                }

                                if (arraypushboolean.get(2)) {
                                    binding.cbPushWebinarUpdate.setChecked(true);
                                } else {
                                    binding.cbPushWebinarUpdate.setChecked(false);
                                }

                                if (arraypushboolean.get(3)) {
                                    binding.cbPushQuizPending.setChecked(true);
                                } else {
                                    binding.cbPushQuizPending.setChecked(false);
                                }


                                if (arraypushboolean.get(4)) {
                                    binding.cbPushPendingEvoluation.setChecked(true);
                                } else {
                                    binding.cbPushPendingEvoluation.setChecked(false);
                                }
                            }

                            if (arraytextboolean.size() > 0) {


                                if (arraytextboolean.get(0)) {
                                    binding.cbTextWebinarRegister.setChecked(true);
                                } else {
                                    binding.cbTextWebinarRegister.setChecked(false);
                                }


                                if (arraytextboolean.get(1)) {

                                    binding.cbTextWebinarReminder.setChecked(true);
                                } else {
                                    binding.cbTextWebinarReminder.setChecked(false);
                                }

                                if (arraytextboolean.get(2)) {
                                    binding.cbTextWebinarUpdate.setChecked(true);
                                } else {
                                    binding.cbTextWebinarUpdate.setChecked(false);
                                }

                                if (arraytextboolean.get(3)) {
                                    binding.cbTextQuizPending.setChecked(true);
                                } else {
                                    binding.cbTextQuizPending.setChecked(false);
                                }

                                if (arraytextboolean.get(4)) {
                                    binding.cbTextPendingEvoluation.setChecked(true);
                                } else {
                                    binding.cbTextPendingEvoluation.setChecked(false);
                                }

                            }


                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Snackbar.make(binding.relImgBack, getNotificationModel.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }


                });


    }


    public static void expand(final View v, int duration, int targetHeight) {

        int prevHeight = v.getHeight();

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void collapse(final View v, int duration, int targetHeight) {
        int prevHeight = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (NotificationManagerCompat.from(ActivityNotificationSetting.this).areNotificationsEnabled()) {
            binding.relPushNotification.setVisibility(View.GONE);
            binding.relRelPushSettings.setVisibility(View.GONE);
        } else {
            binding.relPushNotification.setVisibility(View.VISIBLE);
            binding.relRelPushSettings.setVisibility(View.VISIBLE);
        }
    }
}
