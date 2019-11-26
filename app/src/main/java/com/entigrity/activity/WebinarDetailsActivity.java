package com.entigrity.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.entigrity.MainActivity;
import com.entigrity.R;
import com.entigrity.adapter.CertificatesListWebinarDetailsPopUpAdapter;
import com.entigrity.databinding.ActivityWebinardetailsBinding;
import com.entigrity.model.registerwebinar.ModelRegisterWebinar;
import com.entigrity.model.review_answer.AddReview;
import com.entigrity.model.timezones;
import com.entigrity.model.video_duration.Video_duration_model;
import com.entigrity.model.webinar_details_new.MyCertificateLinksItem;
import com.entigrity.model.webinar_details_new.WebinarDetail;
import com.entigrity.model.webinar_details_new.WebinarTestimonialItem;
import com.entigrity.model.webinar_details_new.Webinar_details;
import com.entigrity.model.webinar_like_dislike.Webinar_Like_Dislike_Model;
import com.entigrity.utility.AppSettings;
import com.entigrity.utility.Constant;
import com.entigrity.view.DialogsUtils;
import com.entigrity.view.SimpleDividerItemDecoration;
import com.entigrity.webinarDetail.CompanyFragment;
import com.entigrity.webinarDetail.DescriptionFragment;
import com.entigrity.webinarDetail.DetailsFragment;
import com.entigrity.webinarDetail.OtherFragment;
import com.entigrity.webinarDetail.OverviewOfTopicsFragment;
import com.entigrity.webinarDetail.PresenterFragment;
import com.entigrity.webinarDetail.TestimonialFragment;
import com.entigrity.webinarDetail.WhyYouShouldAttend;
import com.entigrity.webservice.APIService;
import com.entigrity.webservice.ApiUtilsNew;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WebinarDetailsActivity extends AppCompatActivity {
    public Context context;
    ActivityWebinardetailsBinding binding;
    private APIService mAPIService;
    ProgressDialog progressDialog;
    private static final String TAG = WebinarDetailsActivity.class.getName();
    public int webinarid = 0;
    public String webinar_type = "";
    public String Webinar_title = "";
    public String keyterms = "";
    public String overviewoftopic = "";
    public String whyshouldattend = "";
    public Dialog myDialog;


    public String webinar_share_link = "", ctec_course_id = "";
    private String is_favorite = "";
    ProgressDialog mProgressDialog;
    LayoutInflater inflater_new;
    LayoutInflater inflater;
    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
    public ArrayList<String> arrayListhandout = new ArrayList<>();
    public ArrayList<String> arrayListCertificate = new ArrayList<>();

    public List<MyCertificateLinksItem> arraylistmycertificate = new ArrayList<>();

    public Dialog dialogCertificate;
    public CertificatesListWebinarDetailsPopUpAdapter certificatesListPopUpAdapter;
    LinearLayoutManager linearLayoutManager;

    private String VIDEO_URL = "";


    // Testing lines for the commit changes issue..
    // private String VIDEO_URL = "https://my-cpe.com/uploads/webinar_video/united-states-taxation-of-foreign-real-estate-Investors.mp4";
    TextView tv_who_attend, tv_lerning_objectives;
    LinearLayout lv_row_testimonial;
    public boolean ispause = false;
    DownloadTask downloadTask;
    DownloadTaskCerificate downloadTaskCerificate;
    public int mSeekPosition = 0;
    private int cachedHeight;
    private boolean isFullscreen;
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 123;
    public static final int PERMISSIONS_MULTIPLE_REQUEST_CAlENDER = 1234;
    public static final int PERMISSIONS_MULTIPLE_REQUEST_CERTIFICATE = 12345;

    public ArrayList<String> arrayListtimezone = new ArrayList<String>();
    public ArrayList<timezones> arrayliattimezones = new ArrayList<>();


    String[] mhandoutArray;
    String[] mcertificateArray;
    public ArrayList<String> whoshouldattend = new ArrayList<>();

    public boolean boolean_timezone = true;
    private String webinar_status = "";

    private String year, month, day, hour, min, min_calendar, month_calendar = "";


    public Handler handler = new Handler();
    //PlaybackControlView controlView;

    public String join_url = "";
    public int schedule_id = 0;
    public int start_utc_time = 0;
    public int end_utc_time = 0;
    public int screen_details = 0;
    private String calenderstartdate = "";
    private String calender_hour = "", calender_min = "";

    private String calenderenddate = "";
    private String calander_end_hour = "", calander_end_min = "";


    //exo player


    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    public SimpleExoPlayerView mExoPlayerView;
    private boolean mExoPlayerFullscreen = false;
    private FrameLayout mFullScreenButton;
    private ImageView mFullScreenIcon, exo_pause, exo_play;
    private Dialog mFullScreenDialog;
    private long play_time_duration = 0;
    private long mResumePosition = 0;
    private long presentation_length = 0;
    SimpleExoPlayer exoPlayer;
    public boolean checkpause = false;
    public int checkPlay = 0;
    public String watched_duration = "0.00";
    private boolean isNotification = false;
    public String Cost = "";
    private boolean isCardSaved = false;
    private String strPaymentRedirectionURL = "";


    private DownloadManager downloadManager;
    public long refid;
    public ArrayList<Long> list = new ArrayList<>();
    public boolean videostatus = false;

    public String course_id = "";
    public String instructional_document = "";
    public String credit = "";
    public String cecredit = "";
    public String refund_and_cancelation = "";
    public int duration = 0;
    public String subject_area = "";
    public String course_level = "";
    public String instructional_method = "";
    public String prerequisite = "";
    public String advancePreparation = "";
    public String programDescription = "";
    public String learningObjective = "";
    public String aboutpresentername = "";

    public String aboutpresenterDesgnination = "";
    public String aboutpresenterEmailId = "";
    public String aboutpresenterCompanyName = "";
    public String aboutpresenterCompanyWebsite = "";
    public String aboutpresenterCompanyDesc = "";
    public String aboutpresenternameSpeakerDesc = "";
    public String aboutpresenternameQualification = "";


    public String faq = "";
    public String getwebinar_type = "";


    public String nasba_address = "";
    public String nasba_description = "";
    public String nasba_profile_pic = "";
    public String nasba_profile_pic_qas = "";


    public String ea_address = "";
    public String ea_description = "";
    public String ea_profile_pic = "";

    public String irs_address = "";
    public String irs_description = "";
    public String irs_profile_pic = "";

    public String ctec_address = "";
    public String ctec_description = "";
    public String ctec_profile_pic = "";

    public List<WebinarTestimonialItem> webinartestimonial = new ArrayList<WebinarTestimonialItem>();


    private static WebinarDetailsActivity instance;

    private String time_zone = "";
    public int timezoneselection = 0;
    public String presenter_image = "";
    public String company_logo = "";
    private String payment_link = "";

    public int watched = 1;
    private boolean isAnswered = false;


    public String published_date = "";
    public String recorded_date = "";

    private boolean isReview = false;
    public Dialog myDialogaddreview;

    private int rating = 0;
    private boolean isLikeToKnowMore = false;
    private int is_like = 0;
    private String strReview = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_webinardetails);
        context = WebinarDetailsActivity.this;
        instance = WebinarDetailsActivity.this;
        dialogCertificate = new Dialog(context);
        mAPIService = ApiUtilsNew.getAPIService();
        inflater_new = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mProgressDialog = new ProgressDialog(context);
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        mExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exoplayer);

        Log.e("*+*+*", "CallBack ON CREATE");

        registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        Constant.setLightStatusBar(WebinarDetailsActivity.this);


        if (savedInstanceState != null) {
            play_time_duration = savedInstanceState.getLong(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }


        Intent intent = getIntent();
        if (intent != null) {
            webinarid = intent.getIntExtra(getResources().getString(R.string.pass_webinar_id), 0);
            screen_details = intent.getIntExtra(getResources().getString(R.string.screen_detail), 0);
            webinar_type = intent.getStringExtra(getResources().getString(R.string.pass_webinar_type));

            Constant.Log(TAG, "webinar_id" + webinarid);

            if (getIntent().hasExtra(getResources().getString(R.string.str_is_notification))) {
                isNotification = true;
            }

            if (Constant.isNetworkAvailable(context)) {
                progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                GetWebinarDetailsNew();
            } else {
                Snackbar.make(binding.relView, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
            }
        }

        binding.relWebinarStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webinarStatusClickEvent();
            }
        });

        binding.tvWebinarStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webinarStatusClickEvent();
            }
        });

        binding.tvWebinarStatusNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webinarStatusClickEvent();
            }
        });

        binding.relWebinarStatusNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webinarStatusClickEvent();
            }
        });

        binding.tvPlayIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webinarStatusClickEvent();
            }
        });

        binding.tvPlayIconNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webinarStatusClickEvent();
            }
        });


        /*binding.tvRevieQuestion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ActivityReviewQuestion.class);
                i.putExtra(getResources().getString(R.string.pass_who_you_are_list_review_question), webinarid);
                i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                startActivity(i);
                finish();

            }
        });*/

        binding.tvRvQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ActivityReviewQuestion.class);
                i.putExtra(getResources().getString(R.string.pass_who_you_are_list_review_question), webinarid);
                i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                startActivity(i);
                finish();

            }
        });

        binding.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webinar_type.equalsIgnoreCase(getResources().getString(R.string.str_filter_live))) {
                    if (webinar_status.equalsIgnoreCase(getResources().getString(R.string.str_webinar_status_register))) {
                        Snackbar.make(binding.ivPlay, context.getResources().getString(R.string.str_video_validation), Snackbar.LENGTH_SHORT).show();
                    } else if (webinar_status.equalsIgnoreCase(context
                            .getResources().getString(R.string.str_webinar_status_enroll))) {
                        String url = join_url;
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    } else if (webinar_status.equalsIgnoreCase(context
                            .getResources().getString(R.string.str_webinar_status_in_progress))) {
                        if (!join_url.equalsIgnoreCase("")) {
                            String url = join_url;
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }

                    }/*else {
                        String url = join_url;
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }*/
                } else if (webinar_type.equalsIgnoreCase(getResources().getString(R.string.str_self_study_on_demand))) {
                    if (webinar_status.equalsIgnoreCase(getResources().getString(R.string.str_webinar_status_register))) {
                        Snackbar.make(binding.ivPlay, context.getResources().getString(R.string.str_video_validation), Snackbar.LENGTH_SHORT).show();
                    } else {
                        if (!VIDEO_URL.equalsIgnoreCase("")) {
                            PlayVideo();
                        } else {
                            Snackbar.make(binding.ivPlay, context.getResources().getString(R.string.str_video_link_not_avilable), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (!VIDEO_URL.equalsIgnoreCase("")) {
                        PlayVideo();
                    } else {
                        Snackbar.make(binding.ivPlay, context.getResources().getString(R.string.str_video_link_not_avilable), Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });


        binding.ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNotification) {
                    Intent i = new Intent(WebinarDetailsActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();

                } else {

                    if (screen_details == 0) {
                        // handler.removeCallbacks(runnable);
                        Intent i = new Intent(context, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else if (screen_details == 1) {
                        Intent i = new Intent(context, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else if (screen_details == 2) {
                        Constant.isdataupdate = true;
                        finish();
                    } else if (screen_details == 3) {
                        Intent i = new Intent(context, NotificationActivity.class);
                        startActivity(i);
                        finish();
                    } else if (screen_details == 4) {
                        Intent i = new Intent(context, ActivityFavorite.class);
                        startActivity(i);
                        finish();
                    } else if (screen_details == 5) {
                        finish();
                    }

                }


            }
        });


        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (boolean_timezone) {
                    boolean_timezone = false;
                } else {

                    if (arrayliattimezones.size() > 0) {
                        if (!arrayliattimezones.get(position).getStart_date().equalsIgnoreCase("")) {
                            StringTokenizer tokens = new StringTokenizer(arrayliattimezones.get(position).getStart_date(), "-");
                            year = tokens.nextToken();// this will contain year
                            month = tokens.nextToken();//this will contain month

                            schedule_id = arrayliattimezones.get(position).getSchedule_id();

                            start_utc_time = arrayliattimezones.get(position).getStart_utc_time();

                            end_utc_time = arrayliattimezones.get(position).getEnd_utc_time();

                            calenderstartdate = getDateCurrentTimeZone(start_utc_time);

                            calenderenddate = getDateCurrentTimeZone(end_utc_time);


                            StringTokenizer token = new StringTokenizer(calenderstartdate, " ");

                            String date = token.nextToken();
                            String time = token.nextToken();


                            StringTokenizer tok = new StringTokenizer(time, ":");

                            calender_hour = tok.nextToken();
                            calender_min = tok.nextToken();
                            String second = tok.nextToken();

                            if (calender_min.equalsIgnoreCase("00")) {
                                calender_min = "0";
                            }


                            StringTokenizer tokenenddate = new StringTokenizer(calenderenddate, " ");

                            String dateend = tokenenddate.nextToken();
                            String timeend = tokenenddate.nextToken();


                            StringTokenizer tokend = new StringTokenizer(timeend, ":");

                            calander_end_hour = tokend.nextToken();
                            calander_end_min = tokend.nextToken();
                            String secondend = tokend.nextToken();

                            if (calander_end_min.equalsIgnoreCase("00")) {
                                calander_end_min = "0";
                            }


                            month_calendar = month;

                            day = tokens.nextToken();//this will contain day

                            // year = year.substring(2);


                            if (month.equalsIgnoreCase("01")) {
                                month = getResources().getString(R.string.jan);

                            } else if (month.equalsIgnoreCase("02")) {
                                month = getResources().getString(R.string.feb);

                            } else if (month.equalsIgnoreCase("03")) {
                                month = getResources().getString(R.string.march);

                            } else if (month.equalsIgnoreCase("04")) {
                                month = getResources().getString(R.string.april);

                            } else if (month.equalsIgnoreCase("05")) {
                                month = getResources().getString(R.string.may);

                            } else if (month.equalsIgnoreCase("06")) {
                                month = getResources().getString(R.string.june);

                            } else if (month.equalsIgnoreCase("07")) {
                                month = getResources().getString(R.string.july);

                            } else if (month.equalsIgnoreCase("08")) {
                                month = getResources().getString(R.string.aug);

                            } else if (month.equalsIgnoreCase("09")) {
                                month = getResources().getString(R.string.sept);

                            } else if (month.equalsIgnoreCase("10")) {
                                month = getResources().getString(R.string.oct);

                            } else if (month.equalsIgnoreCase("11")) {
                                month = getResources().getString(R.string.nov);

                            } else if (month.equalsIgnoreCase("12")) {
                                month = getResources().getString(R.string.dec);

                            }

                            StringTokenizer tokens_time = new StringTokenizer(arrayliattimezones.get(position).getStart_time(), ":");
                            hour = tokens_time.nextToken();// this will contain year
                            min = tokens_time.nextToken();//this will contain month

                            StringTokenizer tokens_time_min = new StringTokenizer(min, " ");
                            min_calendar = tokens_time_min.nextToken();
                            if (min_calendar.equalsIgnoreCase("00")) {
                                min_calendar = "0";
                            }


                            binding.tvWebinardate.setText(day + " " + month + " " + year +
                                    " | " + arrayliattimezones.get(position).getStart_time()

                            );


                        }

                    } else {
                        Constant.toast(context, getResources().getString(R.string.str_time_zone_not_found));
                    }


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        String htmlString = "<u>Add to Calender</u>";
        binding.tvAddtocalendar.setText(Html.fromHtml(htmlString));


        binding.tvAddtocalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowPopupAddtoCalender(Webinar_title);

            }
        });


        binding.ivnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WebinarDetailsActivity.this, NotificationActivity.class);
                startActivity(i);

            }
        });


        binding.ivfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Constant.isNetworkAvailable(context)) {
                    binding.ivfavorite.setEnabled(false);
                    /* progressDialog = DialogsUtils.showProgressDialog(context, context.getResources().getString(R.string.progrees_msg));*/
                    WebinarFavoriteLikeDislike(webinarid, binding.ivfavorite);
                } else {
                    Snackbar.make(binding.ivfavorite, context.getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                }


            }
        });

        binding.ivshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!webinar_share_link.equalsIgnoreCase("")) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, webinar_share_link);
                    context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
                } else {
                    Constant.toast(context, getResources().getString(R.string.str_sharing_not_avilable));
                }


            }
        });

        if (webinartestimonial.size() > 0) {
            setupViewPager(binding.viewpager);
            binding.tabs.setupWithViewPager(binding.viewpager);

        } else {
            setupViewPagerWithoutTestimonial(binding.viewpager);
            binding.tabs.setupWithViewPager(binding.viewpager);

        }

    }

    private void webinarStatusClickEvent() {

        if (webinar_type.equalsIgnoreCase(getResources().getString(R.string.str_filter_live))) {
            if (binding.tvWebinarStatus.getText().toString().equalsIgnoreCase(getResources()
                    .getString(R.string.str_webinar_status_register))) {

                if (!Cost.equalsIgnoreCase("")) {
                    // Constant.ShowPopUp(getResources().getString(R.string.payment_validate_msg), context);

                    if (isCardSaved) {
                        if (Constant.isNetworkAvailable(context)) {
                            progressDialog = DialogsUtils.showProgressDialog(context, context.getResources().getString(R.string.progrees_msg));
                            RegisterWebinar(webinarid, binding.tvWebinarStatus);
                        } else {
                            Snackbar.make(binding.ivfavorite, context.getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                        }
                    } else {

//                        String url = "https://my-cpe.com/";
                        String url = "" + strPaymentRedirectionURL;
                        try {
                            Intent i = new Intent("android.intent.action.MAIN");
                            i.setComponent(ComponentName.unflattenFromString("" + strPaymentRedirectionURL));
                            i.addCategory("android.intent.category.LAUNCHER");
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        } catch (ActivityNotFoundException e) {
                            // Chrome is not installed
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(i);
                        }
                    }
                  /*  Intent i = new Intent(WebinarDetailsActivity.this, PaymentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra(getResources().getString(R.string.pass_webinar_id), webinarid);
                    i.putExtra(getResources().getString(R.string.str_payment_link), payment_link);
                    i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();*/


                } else {
                    if (Constant.isNetworkAvailable(context)) {
                        progressDialog = DialogsUtils.showProgressDialog(context, context.getResources().getString(R.string.progrees_msg));
                        RegisterWebinar(webinarid, binding.tvWebinarStatus);
                    } else {
                        Snackbar.make(binding.ivfavorite, context.getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                    }
                }
            } else if (binding.tvWebinarStatus.getText().toString().equalsIgnoreCase(context
                    .getResources().getString(R.string.str_webinar_status_certificate))) {
                //checkAndroidVersionCertificate();

                if (arraylistmycertificate.size() == 0) {
                    Constant.toast(context, context.getResources().getString(R.string.str_certificate_link_not_found));
                } else {
                    if (arraylistmycertificate.size() == 1) {
                        Intent i = new Intent(context, PdfViewActivity.class);
                        i.putExtra(context.getResources().getString(R.string.str_document_link),
                                arraylistmycertificate.get(0).getCertificateLink());
                        i.putExtra(getResources().getString(R.string.pass_who_you_are_list_review_question), webinarid);
                        i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                        i.putExtra(context.getResources().getString(R.string.str_pdf_view_titile), context.getString(R.string.str_certificate));
                        context.startActivity(i);
                    } else {
                        displayCertificateDialog(arraylistmycertificate);
                    }

                }


            } else if (binding.tvWebinarStatus.getText().toString().equalsIgnoreCase(context
                    .getResources().getString(R.string.str_webinar_status_pending_evoluation))) {
                Intent i = new Intent(context, ActivityEvolutionForm.class);
                i.putExtra(getResources().getString(R.string.screen), getResources().getString(R.string.webinardetail));
                i.putExtra(getResources().getString(R.string.pass_who_you_are_list_review_question), webinarid);
                i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                startActivity(i);
                finish();
            } else if (binding.tvWebinarStatus.getText().toString().equalsIgnoreCase(context
                    .getResources().getString(R.string.str_webinar_status_enroll))) {
                String url = join_url;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            } else if (binding.tvWebinarStatus.getText().toString().equalsIgnoreCase(context
                    .getResources().getString(R.string.str_webinar_status_in_progress))) {
                if (!join_url.equalsIgnoreCase("")) {
                    String url = join_url;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            }
        } else if (webinar_type.equalsIgnoreCase(getResources().getString(R.string.str_self_study_on_demand))) {

            if (binding.tvWebinarStatus.getText().toString().equalsIgnoreCase(context
                    .getResources().getString(R.string.str_webinar_status_watchnow)) || binding.tvWebinarStatus.getText().toString().equalsIgnoreCase(context
                    .getResources().getString(R.string.str_webinar_status_resume_watching))) {
                if (!VIDEO_URL.equalsIgnoreCase("")) {
                    checkpause = false;
                    if(checkPlay == 0 || checkPlay ==2){
                        handler.removeCallbacks(runnable);
                        PlayVideo();
                    }
                } else {
                    Snackbar.make(binding.ivPlay, context.getResources().getString(R.string.str_video_link_not_avilable), Snackbar.LENGTH_SHORT).show();
                }

            } /*else if (binding.tvWebinarStatus.getText().toString().equalsIgnoreCase(context
                    .getResources().getString(R.string.str_webinar_status_resume_watching))) {
                if (!VIDEO_URL.equalsIgnoreCase("")) {
                    PlayVideo();
                } else {
                    Snackbar.make(binding.ivPlay, context.getResources().getString(R.string.str_video_link_not_avilable), Snackbar.LENGTH_SHORT).show();
                }
            } */ else if (binding.tvWebinarStatus.getText().toString().equalsIgnoreCase(context
                    .getResources().getString(R.string.str_webinar_status_completed)) && !isReview) {
                // Load review Popup here..
                showAddReviewPopUp();
            } else if (binding.tvWebinarStatus.getText().toString().equalsIgnoreCase(context
                    .getResources().getString(R.string.str_webinar_status_certificate))) {
                // checkAndroidVersionCertificate();

                if (arraylistmycertificate.size() == 0) {
                    Constant.toast(context, getResources().getString(R.string.str_certificate_link_not_found));
                } else if (arraylistmycertificate.size() == 1) {
                    Intent i = new Intent(context, PdfViewActivity.class);
                    i.putExtra(context.getResources().getString(R.string.str_document_link),
                            arraylistmycertificate.get(0).getCertificateLink());
                    i.putExtra(getResources().getString(R.string.pass_who_you_are_list_review_question), webinarid);
                    i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                    i.putExtra(context.getResources().getString(R.string.str_pdf_view_titile), context.getString(R.string.str_certificate));
                    context.startActivity(i);
                } else {
                    displayCertificateDialog(arraylistmycertificate);
                }


            } else if (binding.tvWebinarStatus.getText().toString().equalsIgnoreCase(context
                    .getResources().getString(R.string.str_webinar_status_quiz_pending))) {
                Intent i = new Intent(context, ActivityFinalQuiz.class);
                i.putExtra(getResources().getString(R.string.pass_who_you_are_list_review_question), webinarid);
                i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                startActivity(i);
                finish();

            } else if (binding.tvWebinarStatus.getText().toString().equalsIgnoreCase(getResources()
                    .getString(R.string.str_webinar_status_register))) {


                if (Constant.isNetworkAvailable(context)) {
                    progressDialog = DialogsUtils.showProgressDialog(context, context.getResources().getString(R.string.progrees_msg));
                    RegisterWebinar(webinarid, binding.tvWebinarStatus);
                } else {
                    Snackbar.make(binding.ivfavorite, context.getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                }
            }


        }

    }

    private void showAddReviewPopUp() {

        myDialogaddreview = new Dialog(context);
        myDialogaddreview.setContentView(R.layout.rating_popup);
        myDialogaddreview.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialogaddreview.setCanceledOnTouchOutside(false);
        myDialogaddreview.setCancelable(false);

        ImageView iv_close = (ImageView) myDialogaddreview.findViewById(R.id.ivclose);
        Button btn_submit = (Button) myDialogaddreview.findViewById(R.id.btn_submit);


        btn_submit.setImeOptions(EditorInfo.IME_ACTION_DONE);
        btn_submit.setRawInputType(InputType.TYPE_CLASS_TEXT);

        final ImageView iv_one = (ImageView) myDialogaddreview.findViewById(R.id.iv_one);
        final ImageView iv_two = (ImageView) myDialogaddreview.findViewById(R.id.iv_two);
        final ImageView iv_three = (ImageView) myDialogaddreview.findViewById(R.id.iv_three);
        final ImageView iv_four = (ImageView) myDialogaddreview.findViewById(R.id.iv_four);
        final ImageView iv_five = (ImageView) myDialogaddreview.findViewById(R.id.iv_five);

        final EditText edt_review = (EditText) myDialogaddreview.findViewById(R.id.edt_review);

        final TextView tv_title = (TextView) myDialogaddreview.findViewById(R.id.tv_title);

        final RelativeLayout relChecked_yes = (RelativeLayout) myDialogaddreview.findViewById(R.id.relChecked_yes);
        final RelativeLayout relChecked_no = (RelativeLayout) myDialogaddreview.findViewById(R.id.relChecked_no);
        final TextView tv_yes = (TextView) myDialogaddreview.findViewById(R.id.tv_yes);
        final TextView tv_no = (TextView) myDialogaddreview.findViewById(R.id.tv_no);

        rating = 0;
        isLikeToKnowMore = false;
        is_like = 0;
        strReview = "";

        tv_title.setText(getResources().getString(R.string.str_title_question_rating_popup) + " " + WebinarDetailsActivity.getInstance().aboutpresenterCompanyName + "?");

        relChecked_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relChecked_yes.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_select));
                relChecked_no.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_not_select));
                isLikeToKnowMore = true;
                is_like = 1;
            }
        });

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relChecked_yes.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_select));
                relChecked_no.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_not_select));
                isLikeToKnowMore = true;
                is_like = 1;
            }
        });

        relChecked_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relChecked_no.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_select));
                relChecked_yes.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_not_select));
                isLikeToKnowMore = true;
                is_like = 0;
            }
        });

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relChecked_no.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_select));
                relChecked_yes.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_not_select));
                isLikeToKnowMore = true;
                is_like = 0;
            }
        });

        iv_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iv_one.setImageResource(R.mipmap.add_review_star_hover);
                iv_two.setImageResource(R.mipmap.add_review_star);
                iv_three.setImageResource(R.mipmap.add_review_star);
                iv_four.setImageResource(R.mipmap.add_review_star);
                iv_five.setImageResource(R.mipmap.add_review_star);

                rating = 1;

            }
        });


        iv_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_one.setImageResource(R.mipmap.add_review_star_hover);
                iv_two.setImageResource(R.mipmap.add_review_star_hover);
                iv_three.setImageResource(R.mipmap.add_review_star);
                iv_four.setImageResource(R.mipmap.add_review_star);
                iv_five.setImageResource(R.mipmap.add_review_star);

                rating = 2;

            }
        });


        iv_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_one.setImageResource(R.mipmap.add_review_star_hover);
                iv_two.setImageResource(R.mipmap.add_review_star_hover);
                iv_three.setImageResource(R.mipmap.add_review_star_hover);
                iv_four.setImageResource(R.mipmap.add_review_star);
                iv_five.setImageResource(R.mipmap.add_review_star);

                rating = 3;

            }
        });

        iv_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_one.setImageResource(R.mipmap.add_review_star_hover);
                iv_two.setImageResource(R.mipmap.add_review_star_hover);
                iv_three.setImageResource(R.mipmap.add_review_star_hover);
                iv_four.setImageResource(R.mipmap.add_review_star_hover);
                iv_five.setImageResource(R.mipmap.add_review_star);

                rating = 4;

            }
        });

        iv_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_one.setImageResource(R.mipmap.add_review_star_hover);
                iv_two.setImageResource(R.mipmap.add_review_star_hover);
                iv_three.setImageResource(R.mipmap.add_review_star_hover);
                iv_four.setImageResource(R.mipmap.add_review_star_hover);
                iv_five.setImageResource(R.mipmap.add_review_star_hover);

                rating = 5;
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Take API CALL here then on the success call add the following code there..
                /*Intent i = new Intent(context, WebinarDetailsActivity.class);
                i.putExtra(getResources().getString(R.string.pass_webinar_id), webinar_id);
                i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                startActivity(i);
                finish();*/

                // Check for validation..
                strReview = edt_review.getText().toString();

                if (!isLikeToKnowMore) {
                    Snackbar.make(binding.relView, getResources().getString(R.string.str_validation_like_know_more), Snackbar.LENGTH_SHORT).show();
                } else if (rating == 0) {
                    Snackbar.make(binding.relView, getResources().getString(R.string.str_validation_rating), Snackbar.LENGTH_SHORT).show();
                } else {

                    // Take the API calls here..
                    if (Constant.isNetworkAvailable(context)) {
                        progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
//                        GetSubmitAnswer(questionsParams, ansParams, "" + percentage);
                        GetSubmitReviewAnswer(is_like, rating, strReview, webinarid);
                    } else {
                        Snackbar.make(binding.relView, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialogaddreview.dismiss();
            }
        });


        myDialogaddreview.show();

    }

    private void GetSubmitReviewAnswer(int is_like, int rating, String strReview, int webinarid) {

        mAPIService.AddReview(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context), webinarid
                , rating, is_like, strReview).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AddReview>() {
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
                            Snackbar.make(binding.ivback, message, Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNext(AddReview addReview) {

                        if (addReview.isSuccess() == true) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (myDialogaddreview.isShowing()) {
                                myDialogaddreview.dismiss();
                            }


                            showPopUp(addReview.getMessage(), true);

                            /*Intent i = new Intent(context, WebinarDetailsActivity.class);
                            i.putExtra(getResources().getString(R.string.pass_webinar_id), webinar_id);
                            i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                            startActivity(i);
                            finish();*/

                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }

    private void showPopUp(String msg, final boolean flag) {

        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.final_quiz_popup);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.setCancelable(false);

        TextView popup_description, tv_ok;

        popup_description = (TextView) myDialog.findViewById(R.id.popup_description);
        tv_ok = (TextView) myDialog.findViewById(R.id.tv_ok);

        popup_description.setText("" + msg);
        if (flag) {
            popup_description.setTextColor(context.getResources().getColor(R.color.correct_ans));
        } else {
            popup_description.setTextColor(context.getResources().getColor(R.color.wrong_ans));
        }

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag) {
                    if (myDialog.isShowing()) {
                        myDialog.dismiss();
                    }
                    recreate();
                } else {
                    if (myDialog.isShowing()) {
                        myDialog.dismiss();
                    }
                }

            }
        });

        myDialog.show();

    }

    private void displayCertificateDialog(List<MyCertificateLinksItem> arraylistmycertificate) {


        dialogCertificate.setContentView(R.layout.popup_certificates);
        dialogCertificate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView tv_submit = (TextView) dialogCertificate.findViewById(R.id.tv_submit);
        TextView tv_cancel = (TextView) dialogCertificate.findViewById(R.id.tv_cancel);
        RecyclerView rv_professional_credential = (RecyclerView) dialogCertificate.findViewById(R.id.rv_certificate);

        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv_professional_credential.setLayoutManager(linearLayoutManager);
        rv_professional_credential.addItemDecoration(new SimpleDividerItemDecoration(context));


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogCertificate.isShowing()) {
                    dialogCertificate.dismiss();
                }
            }
        });

        dialogCertificate.show();

        if (arraylistmycertificate.size() > 0) {
            certificatesListPopUpAdapter = new CertificatesListWebinarDetailsPopUpAdapter(context, arraylistmycertificate);
            rv_professional_credential.setAdapter(certificatesListPopUpAdapter);
        }


    }


    public static WebinarDetailsActivity getInstance() {
        return instance;

    }


    public String getDateCurrentTimeZone(long timestamp) {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            //sdf.setTimeZone(TimeZone.getDefault());
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        } catch (Exception e) {
        }
        return "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onComplete);
    }


    public void ShowPopupAddtoCalender(String webinar_titile) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(WebinarDetailsActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("Add to Calender");

        // Setting Dialog Message
        alertDialog.setMessage(webinar_titile);


        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event
                checkAndroidVersion_Calender();

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


    BroadcastReceiver onComplete = new BroadcastReceiver() {

        public void onReceive(Context ctxt, Intent intent) {


            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);


            list.remove(referenceId);


            if (list.isEmpty()) {


                Toast.makeText(context, "Download complete", Toast.LENGTH_LONG).show();
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.mipmap.app_icon)
                                .setContentTitle("Document")
                                .setContentText("MYCpe");


                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, mBuilder.build());


            }

        }
    };


    public void DownloadCertificate(ArrayList<String> arrayListcertificate) {
        list.clear();

        for (int i = 0; i < arrayListcertificate.size(); i++) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(arrayListcertificate.get(i)));
            String extension = arrayListcertificate.get(i).substring(arrayListcertificate.get(i).lastIndexOf('.') + 1).trim();
            request.setAllowedOverRoaming(false);
            request.setTitle("Downloading Certificate");
            request.setVisibleInDownloadsUi(true);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/MyCpe/" + "/" + "Certificate" + i + "." + extension);

            refid = downloadManager.enqueue(request);
        }


        list.add(refid);


    }


    public void DownloadHandouts(ArrayList<String> arrayListhandout) {
        list.clear();

        for (int i = 0; i < arrayListhandout.size(); i++) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(arrayListhandout.get(i)));
            String extension = arrayListhandout.get(i).substring(arrayListhandout.get(i).lastIndexOf('.') + 1).trim();
            request.setAllowedOverRoaming(false);
            request.setTitle("Downloading Handouts");
            request.setVisibleInDownloadsUi(true);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/MyCpe/" + "/" + "Webinar_handouts" + i + "." + extension);

            refid = downloadManager.enqueue(request);
        }


        list.add(refid);


    }


    private void initFullscreenDialog() {

        mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }

    private void closeFullscreenDialog() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        ((FrameLayout) findViewById(R.id.video_layout)).addView(mExoPlayerView);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(WebinarDetailsActivity.this, R.drawable.ic_fullscreen_expand));
    }


    private void openFullscreenDialog() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(WebinarDetailsActivity.this, R.drawable.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }


    private void initFullscreenButton() {
        PlaybackControlView controlView = mExoPlayerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        exo_pause = controlView.findViewById(R.id.exo_pause);

        exo_play = controlView.findViewById(R.id.exo_play);

        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog();
                else
                    closeFullscreenDialog();
            }
        });


        exo_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mExoPlayerView != null && mExoPlayerView.getPlayer() != null) {
                    play_time_duration = mExoPlayerView.getPlayer().getCurrentWindowIndex();
                    mResumePosition = Math.max(0, mExoPlayerView.getPlayer().getContentPosition());
                    presentation_length = exoPlayer.getDuration();

                    handler.removeCallbacks(runnable);

                    exo_play.setVisibility(View.VISIBLE);
                    exo_play.setVisibility(View.GONE);
                    checkpause = true;
                    exoPlayer.setPlayWhenReady(false);
                    handler.removeCallbacks(runnable);

                    checkPlay = 2;


                    Log.e("exo_pause", "+++" + mResumePosition + "   " + play_time_duration + "   " + presentation_length);
                }

            }
        });

        exo_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mExoPlayerView != null && mExoPlayerView.getPlayer() != null) {

                    play_time_duration = mExoPlayerView.getPlayer().getCurrentWindowIndex();
                    mResumePosition = Math.max(0, mExoPlayerView.getPlayer().getContentPosition());
                    presentation_length = exoPlayer.getDuration();

                    exo_pause.setVisibility(View.VISIBLE);
                    exo_play.setVisibility(View.GONE);
                    checkpause = false;
                    exoPlayer.setPlayWhenReady(true);
//                    PlayVideo();

                    checkPlay = 1;


                    Log.e("exo_play", "+++" + mResumePosition + "   " + play_time_duration + "   " + presentation_length);

                }

            }
        });


    }

    private void initExoPlayer() {

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        String userAgent = Util.getUserAgent(context, "com.entigrity");

// Default parameters, except allowCrossProtocolRedirects is true
        DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(
                userAgent,
                null /* listener */,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
                true /* allowCrossProtocolRedirects */
        );

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(
                context,
                null /* listener */,
                httpDataSourceFactory
        );
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        Uri videoURI = Uri.parse(VIDEO_URL);
        // DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);
        mExoPlayerView.setPlayer(exoPlayer);
        /*if (!videostatus) {

        } else {
            exoPlayer.seekTo(0);
        }*/
        boolean haveResumePosition = mResumePosition != C.INDEX_UNSET;
        if (haveResumePosition) {
            exoPlayer.seekTo(mResumePosition);
        }

        exoPlayer.prepare(mediaSource);
        exoPlayer.setPlayWhenReady(true);

        exoPlayer.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                switch (playbackState) {
                    case Player.STATE_BUFFERING:
                        Log.e("STATE_BUFFERING", "STATE_BUFFERING");
                        //checkpause = true;
                        handler.removeCallbacks(runnable);
                        checkPlay = 1;
                        binding.progressBar.setVisibility(View.VISIBLE);
                        break;
                    case Player.STATE_ENDED:
                        //do what you want
                        Log.e("STATE_ENDED", "STATE_ENDED");
                        handler.removeCallbacks(runnable);
                        checkpause = true;
                        checkPlay = 0;
                        exoPlayer.setPlayWhenReady(false);
                        break;
                    case Player.STATE_IDLE:
                        Log.e("STATE_IDLE", "STATE_IDLE");
                        handler.removeCallbacks(runnable);
                        /*handler.removeCallbacks(runnable);
                        checkpause = true;*/

                        break;
                    case Player.STATE_READY:
                        Log.e("STATE_READY", "STATE_READY");

                        handler.removeCallbacks(runnable);
                        binding.progressBar.setVisibility(View.GONE);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                handler.post(runnable);
                            }
                        }, 5000);

                       /* if (!videostatus) {
                            if (!checkpause) {
                                *//*new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        handler.post(runnable);
                                    }
                                }, 10000);*//*
                            } else {
                                handler.removeCallbacks(runnable);
                            }
                        }*/


                        // Log.e("STATE_READY", "STATE_READY");
                        break;
                    default:
                        break;
                }


            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                //handler.removeCallbacks(runnable);
                exoPlayer.setPlayWhenReady(false);
            }

            @Override
            public void onPositionDiscontinuity() {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }
        });


    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            if (!videostatus) {
                if (!checkpause) {
                    if (Constant.isNetworkAvailable(context)) {
                        mResumePosition = Math.max(0, mExoPlayerView.getPlayer().getContentPosition());
                        presentation_length = exoPlayer.getDuration();
                        mResumePosition = TimeUnit.MILLISECONDS.toSeconds(mResumePosition);
                        presentation_length = TimeUnit.MILLISECONDS.toMinutes(presentation_length);

                        Log.e("exo_save", "+++" + mResumePosition + "   " + play_time_duration + "   " + presentation_length);
                        Log.e("*+*+*", "CallBack API");
                        SaveDuration(webinarid, mResumePosition, presentation_length, binding.tvWebinarStatus);
                    } else {
                        Snackbar.make(binding.ivfavorite, context.getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                    }
                    handler.postDelayed(runnable, 5000);
                }
            }
           /* watched = watched + 1;

            binding.tvWatchedduration.setText("You have completed only " + watched + "% of the video.");

            handler.postDelayed(runnable, 10000);*/


        }
    };


    public void PlayVideo() {
        binding.relTimezone.setVisibility(View.GONE);
        binding.relWatchedDuration.setVisibility(View.VISIBLE);
        binding.tvErrorMsgDrag.setVisibility(View.VISIBLE);
        if (!videostatus) {
            binding.tvWatchedduration.setText("You have completed only " + watched_duration + "% of the video.");
        } else {
//            binding.tvWatchedduration.setText("You have completed " + watched_duration + "% of the video.");
            binding.tvWatchedduration.setText("You have completed watching video.");
            binding.tvErrorMsgDrag.setVisibility(View.GONE);
        }
        binding.ivPlay.setVisibility(View.GONE);
        binding.ivthumbhel.setVisibility(View.GONE);
        binding.videoLayout.setVisibility(View.VISIBLE);

        mExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exoplayer);


        initFullscreenDialog();
        initFullscreenButton();

        initExoPlayer();
        if (mExoPlayerFullscreen) {
            ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
            mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(WebinarDetailsActivity.this, R.drawable.ic_fullscreen_skrink));
            mFullScreenDialog.show();
        }


    }


    public void SaveDuration(int webinar_id, long play_time_duration, long presentation_length, final TextView button) {
        mAPIService.SaveVideoDuration(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context), webinar_id
                , play_time_duration, presentation_length).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Video_duration_model>() {
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
                            Snackbar.make(button, message, Snackbar.LENGTH_SHORT).show();

                        }


                    }

                    @Override
                    public void onNext(Video_duration_model video_duration_model) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (video_duration_model.isSuccess() == true) {

                            if (!video_duration_model.getPayload().getWatched().equalsIgnoreCase("")) {
                                watched_duration = video_duration_model.getPayload().getWatched();
                                videostatus = video_duration_model.getPayload().getVideostatus();

                                Constant.watchedDuration = watched_duration;

                                if (!videostatus) {
                                    binding.tvWatchedduration.setText("You have completed only " + watched_duration + "% of the video.");
                                } else {
//                                    binding.tvWatchedduration.setText("You have completed " + watched_duration + "% of the video.");
                                    binding.tvWatchedduration.setText("You have completed watching video.");
                                    if (isAnswered) {
                                        binding.tvPlayIcon.setVisibility(View.GONE);
                                        binding.tvWebinarStatus.setText(getResources().getString(R.string.str_webinar_status_quiz_pending));
                                    }
                                }


                            }

                        } else {

                            Snackbar.make(binding.ivback, video_duration_model.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }


                    }

                });

    }


    public void RegisterWebinar(int webinar_id, final TextView button) {
        mAPIService.RegisterWebinar(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context), webinar_id
                , schedule_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModelRegisterWebinar>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        assert e != null;
                        if (e instanceof HttpException) {
                            assert button != null;
                            if (((HttpException) e).code() == HttpURLConnection.HTTP_BAD_REQUEST) {
                                // Snackbar.make(button, "Socket Timeout", Snackbar.LENGTH_SHORT).show();
                            } else {
                                String message = Constant.GetReturnResponse(context, e);
                                if (Constant.status_code == 401) {
                                    MainActivity.getInstance().AutoLogout();
                                } else {
                                    //   Snackbar.make(button, message, Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            String message = Constant.GetReturnResponse(context, e);
                            if (Constant.status_code == 401) {
                                MainActivity.getInstance().AutoLogout();
                            } else {
                                //Snackbar.make(button, message, Snackbar.LENGTH_SHORT).show();
                            }
                        }


                    }

                    @Override
                    public void onNext(ModelRegisterWebinar modelRegisterWebinar) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        if (modelRegisterWebinar.isSuccess() == true) {
                            Snackbar.make(button, modelRegisterWebinar.getMessage(), Snackbar.LENGTH_SHORT).show();
                            // recreate();
                            button.setText(modelRegisterWebinar.getPayload().getRegisterStatus());
//                            button.setBackgroundResource(R.drawable.rounded_webinar_status);

                            if (!modelRegisterWebinar.getPayload().getJoinUrl().equalsIgnoreCase("")) {
                                join_url = modelRegisterWebinar.getPayload().getJoinUrl();
                                webinar_status = modelRegisterWebinar.getPayload().getRegisterStatus();
                                binding.relWebinarStatus.setBackgroundResource(R.drawable.rounded_webinar_status);
                                Constant.Log("joinurl", "joinurl" + join_url);
                            } else {
                                binding.relWebinarStatus.setVisibility(View.GONE);
                                binding.tvWebinarStatus.setVisibility(View.GONE);
                                binding.linTags.setVisibility(View.VISIBLE);
                                webinar_status = modelRegisterWebinar.getPayload().getRegisterStatus();
                                binding.tvWebinarStatusNew.setText(modelRegisterWebinar.getPayload().getRegisterStatus());
                                showHidePlayButton();
                            }


                            if (modelRegisterWebinar.getPayload().getRegisterStatus().equalsIgnoreCase(getResources().getString(R.string.str_webinar_status_enroll))) {
                                binding.tvAddtocalendar.setVisibility(View.VISIBLE);
                            } else {
                                binding.tvAddtocalendar.setVisibility(View.GONE);
                            }
                        } else {
                            Snackbar.make(button, modelRegisterWebinar.getMessage(), Snackbar.LENGTH_SHORT).show();

                        }
                    }

                });

    }

    private void showHidePlayButton() {

//        if(webinar_status.equalsIgnoreCase(getResources().getString(R.string.str_webinar_status_watchnow)) ||
//                webinar_status.equalsIgnoreCase(getResources().getString(R.string.str_webinar_status_resume_watching))){
        String videoStatus = "" + binding.tvWebinarStatusNew.getText();
        if (videoStatus.equalsIgnoreCase(getResources().getString(R.string.str_webinar_status_watchnow)) ||
                videoStatus.equalsIgnoreCase(getResources().getString(R.string.str_webinar_status_resume_watching))) {
            binding.tvPlayIconNew.setVisibility(View.VISIBLE);
        } else {
            binding.tvPlayIconNew.setVisibility(View.GONE);
        }


    }

    private void WebinarFavoriteLikeDislike(final int webinar_id, final ImageView ImageView) {

        mAPIService.PostWebinarLikeDislike(getResources().getString(R.string.accept),
                getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context),
                webinar_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Webinar_Like_Dislike_Model>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                      /*  if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }*/

                        String message = Constant.GetReturnResponse(context, e);

                        if (Constant.status_code == 401) {
                            MainActivity.getInstance().AutoLogout();
                        } else {
                            Snackbar.make(ImageView, message, Snackbar.LENGTH_SHORT).show();
                        }

                        binding.ivfavorite.setEnabled(true);


                    }

                    @Override
                    public void onNext(Webinar_Like_Dislike_Model webinar_like_dislike_model) {

                     /*   if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }*/

                        if (webinar_like_dislike_model.isSuccess()) {
                            if (webinar_like_dislike_model.getPayload().getIsLike().equalsIgnoreCase(context
                                    .getResources().getString(R.string.fav_yes))) {
                                ImageView.setImageResource(R.mipmap.round_like_icon);
                            } else {
                                ImageView.setImageResource(R.mipmap.round_like_icon_one);
                            }
                            //   Snackbar.make(ImageView, webinar_like_dislike_model.getMessage(), Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(ImageView, webinar_like_dislike_model.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }

                        binding.ivfavorite.setEnabled(true);


                    }
                });


    }


    private void checkAndroidVersion_Calender() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            CheckPermission_Calender();
        } else {
            AddToCalender();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_MULTIPLE_REQUEST:
                if (grantResults.length > 0) {
                    boolean writePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (writePermission && readExternalFile) {
                        // write your logic here
                        if (arrayListhandout.size() > 0) {
                           /* mhandoutArray = new String[arrayListhandout.size()];
                            mhandoutArray = arrayListhandout.toArray(mhandoutArray);*/

                            DownloadHandouts(arrayListhandout);
                            /*if (mhandoutArray.length > 0) {
                             *//*   downloadTask = new DownloadTask(context);
                                downloadTask.execute(mhandoutArray);*//*


                            }*/
                        } else {
                            Constant.toast(context, getResources().getString(R.string.str_download_link_not_found));
                        }
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
                            Intent intent = new Intent();
                            intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        } else {
                            requestPermissions(
                                    new String[]{Manifest.permission
                                            .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    PERMISSIONS_MULTIPLE_REQUEST);
                        }
                    }


                }
                break;
            case PERMISSIONS_MULTIPLE_REQUEST_CAlENDER:
                if (grantResults.length > 0) {

                    boolean writePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (writePermission && readExternalFile) {
                        AddToCalender();
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
                            Intent intent = new Intent();
                            intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        } else {
                            requestPermissions(
                                    new String[]{Manifest.permission
                                            .READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},
                                    PERMISSIONS_MULTIPLE_REQUEST_CAlENDER);
                        }
                    }


                    break;
                }

            case PERMISSIONS_MULTIPLE_REQUEST_CERTIFICATE:

                if (grantResults.length > 0) {
                    boolean writePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (writePermission && readExternalFile) {

                        if (arrayListCertificate.size() > 0) {


                            DownloadCertificate(arrayListCertificate);

                        } else {
                            Constant.toast(context, context.getResources().getString(R.string.str_certificate_link_not_found));
                        }

                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
                            Intent intent = new Intent();
                            intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        } else {
                            requestPermissions(
                                    new String[]{Manifest.permission
                                            .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    PERMISSIONS_MULTIPLE_REQUEST_CERTIFICATE);
                        }
                    }
                }
                break;


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    ((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            ((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                requestPermissions(
                        new String[]{Manifest.permission
                                .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSIONS_MULTIPLE_REQUEST);
            } else {
                requestPermissions(
                        new String[]{Manifest.permission
                                .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSIONS_MULTIPLE_REQUEST);
            }
        } else {
            // write your logic code if permission already granted

            if (arrayListhandout.size() > 0) {


                DownloadHandouts(arrayListhandout);

            } else {
                Constant.toast(context, getResources().getString(R.string.str_download_link_not_found));
            }

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermission_Certificate() {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    ((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            ((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                requestPermissions(
                        new String[]{Manifest.permission
                                .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSIONS_MULTIPLE_REQUEST_CERTIFICATE);
            } else {
                requestPermissions(
                        new String[]{Manifest.permission
                                .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSIONS_MULTIPLE_REQUEST_CERTIFICATE);
            }
        } else {
            // write your logic code if permission already granted

            if (arraylistmycertificate.size() == 0) {
                Constant.toast(context, getResources().getString(R.string.str_certificate_link_not_found));
            } else if (arrayListCertificate.size() == 1) {
                Intent i = new Intent(context, PdfViewActivity.class);
                i.putExtra(context.getResources().getString(R.string.str_document_link), arrayListCertificate.
                        get(0));
                i.putExtra(getResources().getString(R.string.pass_who_you_are_list_review_question), webinarid);
                i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                i.putExtra(context.getResources().getString(R.string.str_pdf_view_titile), context.getString(R.string.str_certificate));
                context.startActivity(i);
            } else {
                if (arrayListCertificate.size() > 0) {
                    DownloadCertificate(arrayListCertificate);
                } else {
                    Constant.toast(context, context.getResources().getString(R.string.str_certificate_link_not_found));
                }
            }
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void CheckPermission_Calender() {

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_CALENDAR) + ContextCompat
                .checkSelfPermission(context,
                        Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    ((Activity) context, Manifest.permission.READ_CALENDAR) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            ((Activity) context, Manifest.permission.WRITE_CALENDAR)) {

                requestPermissions(
                        new String[]{Manifest.permission
                                .READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},
                        PERMISSIONS_MULTIPLE_REQUEST_CAlENDER);
            } else {
                requestPermissions(
                        new String[]{Manifest.permission
                                .READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},
                        PERMISSIONS_MULTIPLE_REQUEST_CAlENDER);
            }
        } else {
            // write your logic code if permission already granted

            AddToCalender();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        checkpause = true;
        handler.removeCallbacks(runnable);
        Log.e("*+*+*", "CallBack PAUSE");

        if (mExoPlayerView != null && mExoPlayerView.getPlayer() != null) {
            exoPlayer.setPlayWhenReady(false);
            ispause = true;
            play_time_duration = mExoPlayerView.getPlayer().getCurrentWindowIndex();
            mResumePosition = Math.max(0, mExoPlayerView.getPlayer().getContentPosition());
            mExoPlayerView.getPlayer().release();
        }

        if (mExoPlayerFullscreen)
            closeFullscreenDialog();

        if (mFullScreenDialog != null)
            mFullScreenDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ispause) {

            checkpause = false;
            mExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exoplayer);
            Log.e("*+*+*", "CallBack RESUME");

            if (mExoPlayerView != null) {

                exoPlayer.setPlayWhenReady(false);

                initFullscreenDialog();
                initFullscreenButton();

                initExoPlayer();
                if (mExoPlayerFullscreen) {
                    ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
                    mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(WebinarDetailsActivity.this, R.drawable.ic_fullscreen_skrink));
                    mFullScreenDialog.show();
                }
            }

        }


    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.e("*+*+*", "CallBack RESTART");
        /*if (ispause) {
            checkpause = false;
            mExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exoplayer);
            exoPlayer.setPlayWhenReady(false);
            if (mExoPlayerView != null) {
                initFullscreenDialog();
                initFullscreenButton();
                initExoPlayer();
                if (mExoPlayerFullscreen) {
                    ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
                    mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(WebinarDetailsActivity.this, R.drawable.ic_fullscreen_skrink));
                    mFullScreenDialog.show();
                }
            }
        }*/
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(STATE_RESUME_WINDOW, play_time_duration);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mExoPlayerFullscreen) {
            closeFullscreenDialog();
        } else if (isNotification) {
            Intent i = new Intent(WebinarDetailsActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();

        } else {
            if (screen_details == 0) {
                // handler.removeCallbacks(runnable);
                Intent i = new Intent(context, MainActivity.class);
                startActivity(i);
                finish();
            } else if (screen_details == 1) {
                Intent i = new Intent(context, MainActivity.class);
                startActivity(i);
                finish();
            } else if (screen_details == 2) {
                Constant.isdataupdate = true;
                finish();
            } else if (screen_details == 3) {
                Intent i = new Intent(context, NotificationActivity.class);
                startActivity(i);
                finish();
            } else if (screen_details == 4) {
                Intent i = new Intent(context, ActivityFavorite.class);
                startActivity(i);
                finish();
            } else if (screen_details == 5) {
                finish();
            }
        }
    }


    private void GetWebinarDetailsNew() {
        mAPIService.GetWebinardetails(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context), webinarid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Webinar_details>() {
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
                            Snackbar.make(binding.relView, message, Snackbar.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onNext(Webinar_details webinar_details) {

                        if (webinar_details.isSuccess() == true) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (!webinar_details.getPayload().getWebinarDetail().getWebinarTitle().equalsIgnoreCase("")) {
                                Webinar_title = webinar_details.getPayload().getWebinarDetail().getWebinarTitle();
                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getKeyterms().equalsIgnoreCase("")) {
                                keyterms = webinar_details.getPayload().getWebinarDetail().getKeyterms();
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getOverviewoftopic().equalsIgnoreCase("")) {
                                overviewoftopic = webinar_details.getPayload().getWebinarDetail().getOverviewoftopic();
                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getPublisheddate().equalsIgnoreCase("")) {
                                published_date = webinar_details.getPayload().getWebinarDetail().getPublisheddate();
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getRecordeddate().equalsIgnoreCase("")) {
                                recorded_date = webinar_details.getPayload().getWebinarDetail().getRecordeddate();
                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getWhyshouldattend().equalsIgnoreCase("")) {
                                whyshouldattend = webinar_details.getPayload().getWebinarDetail().getWhyshouldattend();
                            }


                            if (!webinar_details.getPayload().getWebinarDetail()
                                    .getPaymentlink().equalsIgnoreCase("")) {
                                payment_link = webinar_details.getPayload().getWebinarDetail().getPaymentlink();
                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getPresenterImage()
                                    .equalsIgnoreCase("")) {
                                presenter_image = webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getPresenterImage();
                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getCompanyLogo().
                                    equalsIgnoreCase("")) {
                                company_logo = webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getCompanyLogo();
                            }

                            isReview = webinar_details.getPayload().getWebinarDetail().isReviewed();

                            isCardSaved = webinar_details.getPayload().getWebinarDetail().isCardSave();

                            if (!webinar_details.getPayload().getWebinarDetail().getRedirectionUrl().equalsIgnoreCase("")) {
                                strPaymentRedirectionURL = webinar_details.getPayload().getWebinarDetail().getRedirectionUrl();
                            }


                           /* if (!webinar_details.getPayload().getWebinarDetail().getWebinarTitle().equalsIgnoreCase("")) {
                                binding.tvWebinartitle.setText("" + webinar_details.getPayload().getWebinarDetail().getWebinarTitle());
                            }*/

                            if (!webinar_details.getPayload().getWebinarDetail().getTimeZone().equalsIgnoreCase("")) {
                                time_zone = webinar_details.getPayload().getWebinarDetail().getTimeZone();
                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getJoinUrl().equalsIgnoreCase("")) {
                                join_url = webinar_details.getPayload().getWebinarDetail().getJoinUrl();
                            }


                            videostatus = webinar_details.getPayload().getWebinarDetail().isVideoStatus();
                            if (videostatus) {
                                binding.relWatchedDuration.setVisibility(View.VISIBLE);
                                binding.tvWatchedduration.setText("You have completed watching video.");
                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getCourseId().equalsIgnoreCase("")) {
                                course_id = webinar_details.getPayload().getWebinarDetail().getCourseId();
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getInstructionalDocuement().equalsIgnoreCase("")) {
                                instructional_document = webinar_details.getPayload().getWebinarDetail().getInstructionalDocuement();
                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getWatched().equalsIgnoreCase("")) {
                                watched_duration = webinar_details.getPayload().getWebinarDetail().getWatched();
                                Constant.watchedDuration = watched_duration;
                            }


//                            if (webinar_details.getPayload().getWebinarDetail().getPlayTimeDuration() != 0) {
                            if (webinar_details.getPayload().getWebinarDetail().getTimespent() != 0) {
                                mResumePosition = webinar_details.getPayload().getWebinarDetail().getTimespent() * 1000;
//                                mResumePosition = webinar_details.getPayload().getWebinarDetail().getPlayTimeDuration() * 1000;
                                Log.e("mResumePosition", "+++" + mResumePosition);
                            }

                            if (webinar_details.getPayload().getWebinarDetail().isReviewAnswered()) {
                                isAnswered = true;
                            } else {
                                isAnswered = false;
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getCredit().equalsIgnoreCase("")) {
                                credit = webinar_details.getPayload().getWebinarDetail().getCredit();
                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getCeCredit().equalsIgnoreCase("")) {
                                cecredit = webinar_details.getPayload().getWebinarDetail().getCeCredit();
                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getWebinarVideoUrl().equalsIgnoreCase("")) {
                                VIDEO_URL = webinar_details.getPayload().getWebinarDetail().getWebinarVideoUrl();
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getCost().equalsIgnoreCase("")) {
                                Cost = webinar_details.getPayload().getWebinarDetail().getCost();
                            }

                            if (webinar_details.getPayload().getWebinarDetail().getStatictimezones().size() > 0) {
                                for (int i = 0; i < webinar_details.getPayload().getWebinarDetail().getStatictimezones().size(); i++) {
                                    arrayListtimezone.add(webinar_details.getPayload().getWebinarDetail().getStatictimezones().get(i)
                                            .getTitle());
                                    timezones timezones = new timezones();
                                    timezones.setStart_date(webinar_details.getPayload().getWebinarDetail()
                                            .getStatictimezones().get(i).getStartDate());
                                    timezones.setStart_time(webinar_details.getPayload().getWebinarDetail()
                                            .getStatictimezones().get(i).getStartTime());
                                    timezones.setEnd_utc_time(webinar_details.getPayload().getWebinarDetail()
                                            .getStatictimezones().get(i).getStartutctime());
                                    timezones.setTimezone(webinar_details.getPayload().getWebinarDetail()
                                            .getStatictimezones().get(i).getTimezone());
                                    timezones.setTimezone_short(webinar_details.getPayload().getWebinarDetail()
                                            .getStatictimezones().get(i).getTimezoneShort());
                                    timezones.setSchedule_id(webinar_details.getPayload().getWebinarDetail()
                                            .getStatictimezones().get(i).getScheduleId());
                                    timezones.setStart_utc_time(webinar_details.getPayload().getWebinarDetail().getStatictimezones()
                                            .get(i).getStartutctime());
                                    timezones.setTitle(webinar_details.getPayload().getWebinarDetail().getStatictimezones()
                                            .get(i).getTitle());
                                    arrayliattimezones.add(timezones);
                                }


                                for (int k = 0; k < arrayliattimezones.size(); k++) {
                                    if (time_zone.equalsIgnoreCase(arrayliattimezones.get(k).getTimezone())) {
                                        timezoneselection = k;
                                        Constant.Log("selection", "selection" + timezoneselection);
                                    }


                                }


                                ShowAdapter();
                            }

                            if (webinar_details.getPayload().getWebinarDetail().getScheduleId() != 0) {
                                schedule_id = webinar_details.getPayload().getWebinarDetail().getScheduleId();
                            }

                            if (webinar_details.getPayload().getWebinarDetail().getStartutctime() != 0) {
                                start_utc_time = webinar_details.getPayload().getWebinarDetail().getStartutctime();

                                end_utc_time = webinar_details.getPayload().getWebinarDetail().getEndutctime();

                                calenderstartdate = getDateCurrentTimeZone(start_utc_time);


                                calenderenddate = getDateCurrentTimeZone(end_utc_time);


                                StringTokenizer tokens_tim = new StringTokenizer(calenderstartdate, " ");

                                String date = tokens_tim.nextToken();
                                String time = tokens_tim.nextToken();

                                Constant.Log("time", time + " " + time);


                                StringTokenizer tokens_time_mi = new StringTokenizer(time, ":");

                                calender_hour = tokens_time_mi.nextToken();
                                calender_min = tokens_time_mi.nextToken();


                                if (calender_min.equalsIgnoreCase("00")) {
                                    calender_min = "0";
                                }

                                StringTokenizer tokenenddate = new StringTokenizer(calenderenddate, " ");

                                String dateend = tokenenddate.nextToken();
                                String timeend = tokenenddate.nextToken();


                                StringTokenizer tokend = new StringTokenizer(timeend, ":");

                                calander_end_hour = tokend.nextToken();
                                calander_end_min = tokend.nextToken();
                                String secondend = tokend.nextToken();

                                if (calander_end_min.equalsIgnoreCase("00")) {
                                    calander_end_min = "0";
                                }


                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getStartDate().equalsIgnoreCase("")) {


                                StringTokenizer tokens = new StringTokenizer(webinar_details.getPayload().getWebinarDetail().getStartDate(), "-");
                                year = tokens.nextToken();// this will contain year
                                month = tokens.nextToken();//this will contain month
                                month_calendar = month;
                                day = tokens.nextToken();//this will contain day

                                // year = year.substring(2);


                                if (month.equalsIgnoreCase("01")) {
                                    month = getResources().getString(R.string.jan);

                                } else if (month.equalsIgnoreCase("02")) {
                                    month = getResources().getString(R.string.feb);

                                } else if (month.equalsIgnoreCase("03")) {
                                    month = getResources().getString(R.string.march);

                                } else if (month.equalsIgnoreCase("04")) {
                                    month = getResources().getString(R.string.april);

                                } else if (month.equalsIgnoreCase("05")) {
                                    month = getResources().getString(R.string.may);

                                } else if (month.equalsIgnoreCase("06")) {
                                    month = getResources().getString(R.string.june);

                                } else if (month.equalsIgnoreCase("07")) {
                                    month = getResources().getString(R.string.july);

                                } else if (month.equalsIgnoreCase("08")) {
                                    month = getResources().getString(R.string.aug);

                                } else if (month.equalsIgnoreCase("09")) {
                                    month = getResources().getString(R.string.sept);

                                } else if (month.equalsIgnoreCase("10")) {
                                    month = getResources().getString(R.string.oct);

                                } else if (month.equalsIgnoreCase("11")) {
                                    month = getResources().getString(R.string.nov);

                                } else if (month.equalsIgnoreCase("12")) {
                                    month = getResources().getString(R.string.dec);

                                }


                                StringTokenizer tokens_time = new StringTokenizer(webinar_details.getPayload().getWebinarDetail().getStartTime(), ":");
                                hour = tokens_time.nextToken();// this will contain year
                                min = tokens_time.nextToken();//this will contain month

                                StringTokenizer tokens_time_min = new StringTokenizer(min, " ");
                                min_calendar = tokens_time_min.nextToken();

                                if (min_calendar.equalsIgnoreCase("00")) {
                                    min_calendar = "0";
                                }


                                binding.tvWebinardate.setText(month + " " + day + ", " + year +
                                        " | " + webinar_details.getPayload().getWebinarDetail().getStartTime()

                                );

                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getRefundAndCancelationPolicy().equalsIgnoreCase("")) {

                                refund_and_cancelation = webinar_details.getPayload().getWebinarDetail().getRefundAndCancelationPolicy();

                            }

                            if (webinar_details.getPayload().getWebinarDetail().getDuration() != 0) {
                                duration = webinar_details.getPayload().getWebinarDetail().getDuration();
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getSubjectArea().equalsIgnoreCase("")) {
                                subject_area = webinar_details.getPayload().getWebinarDetail().getSubjectArea();
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getCourseLevel().equalsIgnoreCase("")) {
                                course_level = webinar_details.getPayload().getWebinarDetail().getCourseLevel();
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getInstructionalMethod().equalsIgnoreCase("")) {
                                instructional_method = webinar_details.getPayload().getWebinarDetail().getInstructionalMethod();
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getPrerequisite().equalsIgnoreCase("")) {
                                prerequisite = webinar_details.getPayload().getWebinarDetail().getPrerequisite();
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getAdvancePreparation().equalsIgnoreCase("")) {
                                advancePreparation = webinar_details.getPayload().getWebinarDetail().getAdvancePreparation();
                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getProgramDescription().equalsIgnoreCase("")) {
                                programDescription = webinar_details.getPayload().getWebinarDetail().getProgramDescription();


                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getLearningObjective().equalsIgnoreCase("")) {
                                learningObjective = webinar_details.getPayload().getWebinarDetail().getLearningObjective();
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getName().equalsIgnoreCase("")) {
                                aboutpresentername = webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getName();
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getDesgnination().equalsIgnoreCase("")) {
                                aboutpresenterDesgnination = webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getDesgnination();
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getEmailId().equalsIgnoreCase("")) {
                                aboutpresenterEmailId = webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getEmailId();
                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getCompanyName().equalsIgnoreCase("")) {
                                aboutpresenterCompanyName = webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getCompanyName();
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getCompanyWebsite().equalsIgnoreCase("")) {
                                aboutpresenterCompanyWebsite = webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getCompanyWebsite();
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getCompanyDesc().equalsIgnoreCase("")) {
                                aboutpresenterCompanyDesc = webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getCompanyDesc();
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getSpeakerDesc().equalsIgnoreCase("")) {
                                aboutpresenternameSpeakerDesc = webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getSpeakerDesc();
                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getQualification().equalsIgnoreCase("")) {
                                aboutpresenternameQualification = webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getQualification();
                            }


                            if (webinar_details.getPayload().getWebinarDetail().getWhoShouldAttend().size() > 0) {

                                for (int i = 0; i < webinar_details.getPayload().getWebinarDetail().getWhoShouldAttend().size(); i++) {
                                    whoshouldattend.add(webinar_details.getPayload().getWebinarDetail().getWhoShouldAttend().get(i));
                                }
                            }

                            if (webinar_details.getPayload().getWebinarDetail().getWebinarTestimonial().size() > 0) {

                                for (int i = 0; i < webinar_details.getPayload().getWebinarDetail().getWebinarTestimonial().size(); i++) {
                                    webinartestimonial.add(webinar_details.getPayload().getWebinarDetail().getWebinarTestimonial().get(i));
                                }


                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getFaq().equalsIgnoreCase("")) {
                                faq = webinar_details.getPayload().getWebinarDetail().getFaq();
                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getStatus().equalsIgnoreCase("")) {
                                if (webinar_details.getPayload().getWebinarDetail().getStatus().equalsIgnoreCase(getResources()
                                        .getString(R.string.str_webinar_status_register))) {
                                    // binding.tvWebinarStatus.setBackgroundResource(R.drawable.squrebutton_webinar_status);
                                    binding.relWebinarStatus.setBackgroundResource(R.drawable.rounded_webinar_home);
                                } else {
                                    //binding.tvWebinarStatus.setBackgroundResource(R.drawable.squrebutton_webinar_status);
                                    binding.relWebinarStatus.setBackgroundResource(R.drawable.squrebutton_webinar_status);
                                }


                                webinar_status = webinar_details.getPayload().getWebinarDetail().getStatus();


                                binding.tvWebinarStatus.setText("" + webinar_details.getPayload().getWebinarDetail().getStatus());
                                binding.tvWebinarStatusNew.setText("" + webinar_details.getPayload().getWebinarDetail().getStatus());
                                showHidePlayButton();
                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getWebinarThumbnail().equalsIgnoreCase("")) {
                                Picasso.with(context).load(webinar_details.getPayload().getWebinarDetail().getWebinarThumbnail())
                                        .placeholder(R.mipmap.webinar_placeholder)
                                        .into((binding.ivthumbhel));
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getWebinarType().equalsIgnoreCase("")) {
                                getwebinar_type = webinar_details.getPayload().getWebinarDetail().getWebinarType();

                            }
                            if (!webinar_details.getPayload().getWebinarDetail().getNasbaApproved().getAddress().equalsIgnoreCase("")) {

                                nasba_address = webinar_details.getPayload().getWebinarDetail().getNasbaApproved().getAddress();

                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getNasbaApproved().getNasbaDesc().equalsIgnoreCase("")) {

                                nasba_description = webinar_details.getPayload().getWebinarDetail().getNasbaApproved().getNasbaDesc();

                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getNasbaApproved().getNasbaProfileIcon().equalsIgnoreCase("")) {

                                nasba_profile_pic = webinar_details.getPayload().getWebinarDetail().getNasbaApproved().getNasbaProfileIcon();

                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getNasbaApproved().getNasbaProfileIconQas().equalsIgnoreCase("")) {

                                nasba_profile_pic_qas = webinar_details.getPayload().getWebinarDetail().getNasbaApproved().getNasbaProfileIconQas();

                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getEaApproved().getAddress().equalsIgnoreCase("")) {
                                ea_address = webinar_details.getPayload().getWebinarDetail().getEaApproved().getAddress();

                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getEaApproved().getEaDesc().equalsIgnoreCase("")) {
                                ea_description = webinar_details.getPayload().getWebinarDetail().getEaApproved().getEaDesc();

                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getEaApproved().getEaProfileIcon().equalsIgnoreCase("")) {
                                ea_profile_pic = webinar_details.getPayload().getWebinarDetail().getEaApproved().getEaProfileIcon();

                            }

                            if(!webinar_details.getPayload().getWebinarDetail().getIrsApproved().getIrsAddress().equalsIgnoreCase("")){
                                irs_address = webinar_details.getPayload().getWebinarDetail().getIrsApproved().getIrsAddress();
                            }

                            if(!webinar_details.getPayload().getWebinarDetail().getIrsApproved().getIrsDesc().equalsIgnoreCase("")){
                                irs_description = webinar_details.getPayload().getWebinarDetail().getIrsApproved().getIrsDesc();
                            }

                            if(!webinar_details.getPayload().getWebinarDetail().getIrsApproved().getIrsProfileIcon().equalsIgnoreCase("")){
                                irs_profile_pic = webinar_details.getPayload().getWebinarDetail().getIrsApproved().getIrsProfileIcon();
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getCtecApproved().getAddress().equalsIgnoreCase("")) {
                                ctec_address = webinar_details.getPayload().getWebinarDetail().getCtecApproved().getAddress();
                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getCtecApproved().getCtecdesc().equalsIgnoreCase("")) {
                                ctec_description = webinar_details.getPayload().getWebinarDetail().getCtecApproved().getCtecdesc();

                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getCtecApproved().getCtecprofileicon().equalsIgnoreCase("")) {
                                ctec_profile_pic = webinar_details.getPayload().getWebinarDetail().getCtecApproved().getCtecprofileicon();

                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getShareableLink().equalsIgnoreCase("")) {
                                webinar_share_link = webinar_details.getPayload().getWebinarDetail().getShareableLink();
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getCteccourseid().equalsIgnoreCase("")) {
                                ctec_course_id = webinar_details.getPayload().getWebinarDetail().getCteccourseid();
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getLikeDislikeStatus().equalsIgnoreCase("")) {

                                is_favorite = webinar_details.getPayload().getWebinarDetail().getLikeDislikeStatus();

                                if (is_favorite.equalsIgnoreCase(getResources()
                                        .getString(R.string.fav_yes))) {
                                    binding.ivfavorite.setImageResource(R.mipmap.round_like_icon);
                                } else {
                                    binding.ivfavorite.setImageResource(R.mipmap.round_like_icon_one);
                                }

                            }


                            if (webinar_details.getPayload().getWebinarDetail().getPresentationHandout().size() > 0) {
                                for (int i = 0; i < webinar_details.getPayload().getWebinarDetail().getPresentationHandout().size(); i++) {
                                    arrayListhandout.add(webinar_details.getPayload().getWebinarDetail().getPresentationHandout().get(i));
                                }

                            }

                            if (webinar_details.getPayload().getWebinarDetail().getCertificateLink().size() > 0) {
                                for (int i = 0; i < webinar_details.getPayload().getWebinarDetail().getCertificateLink().size(); i++) {
                                    arrayListCertificate.add(webinar_details.getPayload().getWebinarDetail().getCertificateLink().get(i));
                                }

                            }

                            if (webinar_details.getPayload().getWebinarDetail().getMyCertificateLinks().size() > 0) {
                                arraylistmycertificate.addAll(webinar_details.getPayload().getWebinarDetail().getMyCertificateLinks());
                            }

                            //Constant.Log("certificate_array","certificate_array"+arraylistmycertificate.size());


                            if (webinar_type.equalsIgnoreCase(getResources().getString(R.string.str_filter_live))) {
                                binding.relTimezone.setVisibility(View.VISIBLE);

                                if (webinar_status.equalsIgnoreCase(getResources().getString(R.string.str_webinar_status_register))) {
                                    binding.tvAddtocalendar.setVisibility(View.GONE);
                                } else {
                                    if (webinar_status.equalsIgnoreCase(getResources().getString(R.string.str_webinar_status_enroll))) {
                                        binding.tvAddtocalendar.setVisibility(View.VISIBLE);
                                    } else {
                                        binding.tvAddtocalendar.setVisibility(View.GONE);
                                    }

                                }
                            } else if (webinar_type.equalsIgnoreCase(getResources().getString(R.string.str_self_study_on_demand))) {
                                if (webinar_status.equalsIgnoreCase(getResources().getString(R.string.str_webinar_status_register))) {
                                    binding.relTimezone.setVisibility(View.INVISIBLE);
//                                    binding.tvRevieQuestion.setVisibility(View.INVISIBLE);
                                    binding.linTags.setVisibility(View.GONE);
                                    binding.relWebinarStatus.setVisibility(View.VISIBLE);
                                    binding.tvWebinarStatus.setVisibility(View.VISIBLE);
                                } else {

                                    if (webinar_status.equalsIgnoreCase(getResources().getString(R.string.str_webinar_status_watchnow)) ||
                                            webinar_status.equalsIgnoreCase(getResources().getString(R.string.str_webinar_status_resume_watching))) {
                                        binding.tvPlayIcon.setVisibility(View.VISIBLE);
                                        if (isAnswered) {
                                            binding.linTags.setVisibility(View.GONE);
                                            binding.relWebinarStatus.setVisibility(View.VISIBLE);
                                            binding.tvWebinarStatus.setVisibility(View.VISIBLE);
                                        } else {
                                            binding.linTags.setVisibility(View.VISIBLE);
                                            binding.relWebinarStatus.setVisibility(View.GONE);
                                            binding.tvWebinarStatus.setVisibility(View.GONE);
                                        }
                                        binding.relTimezone.setVisibility(View.INVISIBLE);
//                                        binding.tvRevieQuestion.setVisibility(View.VISIBLE);

                                        if (webinar_status.equalsIgnoreCase(getResources().getString(R.string.str_webinar_status_resume_watching))) {
                                            binding.relWatchedDuration.setVisibility(View.VISIBLE);
                                            binding.tvErrorMsgDrag.setVisibility(View.VISIBLE);
                                            if (!videostatus) {
                                                binding.tvWatchedduration.setText("You have completed only " + watched_duration + "% of the video.");
                                            } else {
                                                binding.tvWatchedduration.setText("You have completed watching video.");
                                                binding.tvErrorMsgDrag.setVisibility(View.GONE);
                                            }
                                        }

                                    } else {
                                        binding.tvPlayIcon.setVisibility(View.GONE);
                                        binding.relTimezone.setVisibility(View.INVISIBLE);
//                                        binding.tvRevieQuestion.setVisibility(View.INVISIBLE);
                                        binding.linTags.setVisibility(View.GONE);
                                        binding.relWebinarStatus.setVisibility(View.VISIBLE);
                                        binding.tvWebinarStatus.setVisibility(View.VISIBLE);
                                    }

                                }
                            } else {
                                binding.relTimezone.setVisibility(View.INVISIBLE);
                                binding.tvAddtocalendar.setVisibility(View.INVISIBLE);
//                                binding.tvRevieQuestion.setVisibility(View.INVISIBLE);
                                binding.linTags.setVisibility(View.GONE);
                                binding.relWebinarStatus.setVisibility(View.VISIBLE);
                                binding.tvWebinarStatus.setVisibility(View.VISIBLE);

                            }

                            if (binding.tvWebinarStatus.getText().toString().equalsIgnoreCase(context
                                    .getResources().getString(R.string.str_webinar_status_completed)) && !isReview) {

                                showAddReviewPopUp();

                            }

                            if (webinartestimonial.size() > 0) {
                                setupViewPager(binding.viewpager);
                                binding.tabs.setupWithViewPager(binding.viewpager);

                            } else {
                                setupViewPagerWithoutTestimonial(binding.viewpager);
                                binding.tabs.setupWithViewPager(binding.viewpager);

                            }


                        } else {
                            Snackbar.make(binding.relView, webinar_details.getMessage(), Snackbar.LENGTH_SHORT).show();


                        }


                    }

                });

    }

    public void ShowAdapter() {
        if (arrayliattimezones.size() > 0) {
            //Getting the instance of Spinner and applying OnItemSelectedListener on it

            //Creating the ArrayAdapter instance having the user type list
            ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item, arrayListtimezone);
            aa.setDropDownViewResource(R.layout.spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            binding.spinner.setAdapter(aa);

            binding.spinner.setSelection(timezoneselection);
        }
    }

    private void checkAndroidVersionCertificate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission_Certificate();
        } else {
            // write your logic here
            if (arrayListCertificate.size() == 1) {
                Intent i = new Intent(context, PdfViewActivity.class);
                i.putExtra(context.getResources().getString(R.string.str_document_link), arrayListCertificate.
                        get(0));
                i.putExtra(getResources().getString(R.string.pass_who_you_are_list_review_question), webinarid);
                i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                i.putExtra(context.getResources().getString(R.string.str_pdf_view_titile), context.getString(R.string.str_certificate));
                context.startActivity(i);
            } else {
                if (arrayListCertificate.size() > 0) {
                    DownloadCertificate(arrayListCertificate);
                } else {
                    Constant.toast(context, context.getResources().getString(R.string.str_certificate_link_not_found));
                }
            }
        }
    }

    public String formatHoursAndMinutes(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        return (totalMinutes / 60) + ":" + minutes;
    }

    public void AddToCalender() {
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(Integer.parseInt(year), Integer.parseInt(month_calendar) - 1, Integer.parseInt(day),
                Integer.parseInt(calender_hour), Integer.parseInt(calender_min));


        Constant.Log("beginTime", calender_hour + " " + calender_min);

        Calendar endtime = Calendar.getInstance();
        endtime.set(Integer.parseInt(year), Integer.parseInt(month_calendar) - 1, Integer.parseInt(day),
                Integer.parseInt(calander_end_hour), Integer.parseInt(calander_end_min));


        Constant.Log("endTime", calander_end_hour + " " + calander_end_min);


        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endtime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, Webinar_title)
                .putExtra(Intent.EXTRA_EMAIL, AppSettings.get_email_id(context));
        startActivity(intent);


    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        //private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            /*PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();*/

            mProgressDialog.show();
            mProgressDialog.setMessage("downloading");
            mProgressDialog.setMax(100);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setCancelable(true);
        }


        @Override
        protected String doInBackground(String... sUrl) {
            int count;
            try {

                for (int i = 0; i < sUrl.length; i++) {
                    URL url = new URL(sUrl[i]);
                    URLConnection conection = url.openConnection();
                    conection.connect();
                    // getting file length
                    int lenghtOfFile = conection.getContentLength();


                    String extension = sUrl[i].substring(sUrl[i].lastIndexOf('.') + 1).trim();


                    // input stream to read file - with 8k buffer
                    InputStream input = new BufferedInputStream(
                            url.openStream(), 8192);
                    // System.out.println("Data::" + sUrl[i]);
                    // Output stream to write file
                    OutputStream output = new FileOutputStream(
                            "/sdcard/handout" + new Date().getTime() + "." + extension);

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                        publishProgress((int) ((total * 100) / lenghtOfFile));

                        // writing data to file
                        output.write(data, 0, count);
                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();
                }
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
           /* mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);*/
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, "Download complete", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadTaskCerificate extends AsyncTask<String, Integer, String> {

        private Context context;
        //private PowerManager.WakeLock mWakeLock;

        public DownloadTaskCerificate(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            /*PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();*/

            mProgressDialog.show();
            mProgressDialog.setMessage("downloading");
            mProgressDialog.setMax(100);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setCancelable(true);
        }


        @Override
        protected String doInBackground(String... sUrl) {
            int count;
            try {

                for (int i = 0; i < sUrl.length; i++) {
                    URL url = new URL(sUrl[i]);
                    URLConnection conection = url.openConnection();
                    conection.connect();
                    // getting file length
                    int lenghtOfFile = conection.getContentLength();


                    String extension = sUrl[i].substring(sUrl[i].lastIndexOf('.') + 1).trim();


                    // input stream to read file - with 8k buffer
                    InputStream input = new BufferedInputStream(
                            url.openStream(), 8192);
                    // System.out.println("Data::" + sUrl[i]);
                    // Output stream to write file
                    OutputStream output = new FileOutputStream(
                            "/sdcard/certificate" + new Date().getTime() + "." + extension);

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                        publishProgress((int) ((total * 100) / lenghtOfFile));

                        // writing data to file
                        output.write(data, 0, count);
                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();
                }
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
           /* mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);*/
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, "Download complete", Toast.LENGTH_SHORT).show();
        }
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DetailsFragment(), getResources().getString(R.string.str_details));
        adapter.addFragment(new DescriptionFragment(), getResources().getString(R.string.str_description));
        if (webinar_type.equalsIgnoreCase(getResources().getString(R.string.str_filter_live))) {
            if (!whyshouldattend.equalsIgnoreCase("")) {
                adapter.addFragment(new WhyYouShouldAttend(), getResources().getString(R.string.str_why_you_should_attend));
            }
        } else if (webinar_type.equalsIgnoreCase(getResources().getString(R.string.str_self_study_on_demand))) {
            if (!overviewoftopic.equalsIgnoreCase("")) {
                adapter.addFragment(new OverviewOfTopicsFragment(), getResources().getString(R.string.str_overview_of_topics));
            }
            if (!whyshouldattend.equalsIgnoreCase("")) {
                adapter.addFragment(new WhyYouShouldAttend(), getResources().getString(R.string.str_why_you_should_attend));
            }
        }
        adapter.addFragment(new PresenterFragment(), getResources().getString(R.string.str_presenter));
        adapter.addFragment(new CompanyFragment(), getResources().getString(R.string.str_detail_company));
        adapter.addFragment(new TestimonialFragment(), getResources().getString(R.string.str_testimonials));
        adapter.addFragment(new OtherFragment(), getResources().getString(R.string.str_others));
        viewPager.setAdapter(adapter);
    }

    private void setupViewPagerWithoutTestimonial(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DetailsFragment(), getResources().getString(R.string.str_details));
        adapter.addFragment(new DescriptionFragment(), getResources().getString(R.string.str_description));
        if (webinar_type.equalsIgnoreCase(getResources().getString(R.string.str_filter_live))) {
            if (!whyshouldattend.equalsIgnoreCase("")) {
                adapter.addFragment(new WhyYouShouldAttend(), getResources().getString(R.string.str_why_you_should_attend));
            }
        } else if (webinar_type.equalsIgnoreCase(getResources().getString(R.string.str_self_study_on_demand))) {
            if (!overviewoftopic.equalsIgnoreCase("")) {
                adapter.addFragment(new OverviewOfTopicsFragment(), getResources().getString(R.string.str_overview_of_topics));
            }
            if (!whyshouldattend.equalsIgnoreCase("")) {
                adapter.addFragment(new WhyYouShouldAttend(), getResources().getString(R.string.str_why_you_should_attend));
            }
        }
        adapter.addFragment(new PresenterFragment(), getResources().getString(R.string.str_presenter));
        adapter.addFragment(new CompanyFragment(), getResources().getString(R.string.str_detail_company));
        adapter.addFragment(new OtherFragment(), getResources().getString(R.string.str_others));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
