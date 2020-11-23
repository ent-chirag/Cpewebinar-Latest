package com.myCPE.activity;

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
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.CalendarContract;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.exoplayer2.util.LibraryLoader;
import com.google.android.material.snackbar.Snackbar;
import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.adapter.CertificatesListWebinarDetailsPopUpAdapter;
import com.myCPE.databinding.ActivityWebinardetailsNewBinding;
import com.myCPE.model.registerwebinar.ModelRegisterWebinar;
import com.myCPE.model.review_answer.AddReview;
import com.myCPE.model.timezones;
import com.myCPE.model.video_duration.Video_duration_model;
import com.myCPE.model.webinar_details_new.MyCertificateLinksItem;
import com.myCPE.model.webinar_details_new.WebinarTestimonialItem;
import com.myCPE.model.webinar_details_new.Webinar_details;
import com.myCPE.model.webinar_like_dislike.Webinar_Like_Dislike_Model;
import com.myCPE.utility.AppSettings;
import com.myCPE.utility.Constant;
import com.myCPE.view.DialogsUtils;
import com.myCPE.view.SimpleDividerItemDecoration;
import com.myCPE.webinarDetail.CompanyFragment;
import com.myCPE.webinarDetail.DescriptionFragment;
import com.myCPE.webinarDetail.DetailsFragment;
import com.myCPE.webinarDetail.OtherFragment;
import com.myCPE.webinarDetail.OverviewOfTopicsFragment;
import com.myCPE.webinarDetail.PresenterFragment;
import com.myCPE.webinarDetail.ReviewQuestionsFragment;
import com.myCPE.webinarDetail.TestimonialFragment;
import com.myCPE.webinarDetail.WhyYouShouldAttend;
import com.myCPE.webservice.APIService;
import com.myCPE.webservice.ApiUtilsNew;
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

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class WebinarDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    public Context context;
    //    ActivityWebinardetailsBinding binding;
    ActivityWebinardetailsNewBinding binding;
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
    public String webinar_type_details = "";

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
    //    private boolean isexpandContentOpen = false;
    private boolean isExpandDetails = false;
    private boolean isExpandDescription = false;
    private boolean isExpandOverviewOfTopics = false;
    private boolean isExpandWhoShouldAttend = false;
    private boolean isExpandPresenter = false;
    private boolean isExpandCompany = false;
    private boolean isExpandTestimonials = false;
    private boolean isExpandOthers = false;


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

    public int speaker_id = 0;
    public int company_id = 0;

    private boolean isFromSpeakerCompanyProfile = false;

    // Details Components..
    private TextView tv_cost, tv_credit, tv_couse_id, tv_ctec_course_id, tv_duration, tv_subjectarea, tv_course_level, tv_instructionalmethod;
    private TextView tv_prerequisite, tv_advance_preparation, tv_recorded_date, tv_published_date;
    private TextView tv_download, tv_download_key_terms, tv_download_instructions;
    private LinearLayout lv_course_id, lv_ctec_course_id, lv_recorded_date, lv_published_date, lv_who_attend;
    private View view_irs_course_id, view_ctec, view_recorded_date, view_published_date;

    // Description Components..
    private TextView tv_description, tv_learning_objective;

    // Presenter Components..
    private TextView tv_presenter_name, tv_designation_company, tv_about_presenter;
    private ImageView ivprofilepicture;

    // Company Components..
    private TextView tv_company_name, tv_company_website, tv_company_description;
    private ImageView ivprofilepictureCompany;

    // Testimonials Components..
    private TextView tv_view_more_testimonial, tv_no_testimonial;
    private LinearLayout lv_testimonial_set;

    // Others Components..
    private TextView tv_faq, tv_refund_cancelation_policy, txt_nasba_address, txt_nasba_description, txt_ea_address, txt_ea_description, txt_irs_address, txt_irs_description, txt_ctec_address, txt_ctec_description;
    private RelativeLayout rel_nasba, rel_nasba_desc, rel_ea, rel_ea_desc, rel_irs, rel_irs_desc, rel_ctec, rel_ctec_desc;
    private ImageView iv_nasba_profile, iv_nasba_profile_qas, iv_ea_profile, iv_irs_profile, iv_ctec_profile;

    // Overview of topics Components..
    private TextView tv_overview_of_topics;

    // Why should attend..
    private TextView tv_why_you_should_attend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_webinardetails);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_webinardetails_new);
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

//        Constant.setLightStatusBar(WebinarDetailsActivity.this);
        Constant.hashmap_answer_state.clear();
        Constant.hashmap_asnwer_string_review_question.clear();
        Constant.hashmap_answer_string_final_question.clear();
        Constant.hashmap_asnwer_review_question.clear();

        binding.relImgBack.setOnClickListener(this);
        binding.ivback.setOnClickListener(this);

        binding.relPopupSubmitReview.setOnClickListener(this);
        binding.relCheckedYes.setOnClickListener(this);
        binding.relCheckedNo.setOnClickListener(this);
        binding.linPopupReview.setOnClickListener(this);
        binding.txtPopupReviewCancel.setOnClickListener(this);
        binding.tvYes.setOnClickListener(this);
        binding.tvNo.setOnClickListener(this);
        binding.ivOne.setOnClickListener(this);
        binding.ivTwo.setOnClickListener(this);
        binding.ivThree.setOnClickListener(this);
        binding.ivFour.setOnClickListener(this);
        binding.ivFive.setOnClickListener(this);

        if (savedInstanceState != null) {
            play_time_duration = savedInstanceState.getLong(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }


        Intent intent = getIntent();
        if (intent != null) {
            webinarid = intent.getIntExtra(getResources().getString(R.string.pass_webinar_id), 0);
            screen_details = intent.getIntExtra(getResources().getString(R.string.screen_detail), 0);
            Log.e("*+*+*","screen_detailsm on getIntent is : "+screen_details);
            webinar_type = intent.getStringExtra(getResources().getString(R.string.pass_webinar_type));
//            isFromSpeakerCompanyProfile = intent.getBooleanExtra("isFromCompanySpeakerList", false);
            isFromSpeakerCompanyProfile = Constant.isFromSpeakerCompanyWebinarList;

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

        init();

        if (webinar_type.equalsIgnoreCase(getResources().getString(R.string.str_filter_live))) {
            // This is for the Live Webinars..
            binding.relLiveWebinar.setVisibility(View.VISIBLE);
            binding.relView.setVisibility(View.GONE);
            binding.ivthumbhelDummy.setVisibility(View.GONE);
            binding.rvwebinartitle.setVisibility(View.GONE);
            binding.txtSelfStudyWebinarTitle.setVisibility(View.GONE);
            binding.txtSelfStudyWebinarAuthor.setVisibility(View.GONE);

            // Set Data for the Live Webinars..
//            binding.txtLiveWebinarTitle.setText("" + webinar_details);
        } else if (webinar_type.equalsIgnoreCase(getResources().getString(R.string.str_self_study_on_demand))) {
            // This is for the Self-Study Webinars..
            binding.relLiveWebinar.setVisibility(View.GONE);
            binding.relView.setVisibility(View.VISIBLE);
            binding.relSelfStudy.setVisibility(View.VISIBLE);
            binding.txtSelfStudyWebinarTitle.setVisibility(View.VISIBLE);
            binding.txtSelfStudyWebinarAuthor.setVisibility(View.VISIBLE);
//            binding.ivthumbhelDummy.setVisibility(View.VISIBLE);
            // As of now we have no option to show the rvWebinarTitle section which contains timezones, add to calancer feature, and progress alert option..
            binding.rvwebinartitle.setVisibility(View.GONE);
        }

        binding.relDetails.setOnClickListener(this);
        binding.relDescription.setOnClickListener(this);
        binding.relPresenter.setOnClickListener(this);
        binding.relCompany.setOnClickListener(this);
        binding.relTestimonials.setOnClickListener(this);
        binding.relOthers.setOnClickListener(this);
        binding.relOverviewOfTopics.setOnClickListener(this);
        binding.relWhoShouldAttend.setOnClickListener(this);

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

//                Intent i = new Intent(context, ActivityReviewQuestion.class);
                Intent i = new Intent(context, ActivityReviewQuestionNew.class);
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

    private void init() {

        //Details Components..
        tv_cost = (TextView) findViewById(R.id.tv_cost);
        tv_credit = (TextView) findViewById(R.id.tv_credit);
        tv_couse_id = (TextView) findViewById(R.id.tv_couse_id);
        tv_ctec_course_id = (TextView) findViewById(R.id.tv_ctec_course_id);
        tv_duration = (TextView) findViewById(R.id.tv_duration);
        tv_subjectarea = (TextView) findViewById(R.id.tv_subjectarea);
        tv_course_level = (TextView) findViewById(R.id.tv_course_level);
        tv_instructionalmethod = (TextView) findViewById(R.id.tv_instructionalmethod);
        tv_prerequisite = (TextView) findViewById(R.id.tv_prerequisite);
        tv_advance_preparation = (TextView) findViewById(R.id.tv_advance_preparation);
        tv_recorded_date = (TextView) findViewById(R.id.tv_recorded_date);
        tv_published_date = (TextView) findViewById(R.id.tv_published_date);
        tv_download = (TextView) findViewById(R.id.tv_download);
        tv_download_key_terms = (TextView) findViewById(R.id.tv_download_key_terms);
        tv_download_instructions = (TextView) findViewById(R.id.tv_download_instructions);

        lv_course_id = (LinearLayout) findViewById(R.id.lv_course_id);
        lv_ctec_course_id = (LinearLayout) findViewById(R.id.lv_ctec_course_id);
        lv_recorded_date = (LinearLayout) findViewById(R.id.lv_recorded_date);
        lv_published_date = (LinearLayout) findViewById(R.id.lv_published_date);
        lv_who_attend = (LinearLayout) findViewById(R.id.lv_who_attend);

        view_irs_course_id = (View) findViewById(R.id.view_irs_course_id);
        view_ctec = (View) findViewById(R.id.view_ctec);
        view_recorded_date = (View) findViewById(R.id.view_recorded_date);
        view_published_date = (View) findViewById(R.id.view_published_date);

        // Description Components..
        tv_description = (TextView) findViewById(R.id.tv_description);
        tv_learning_objective = (TextView) findViewById(R.id.tv_learning_objective);

        // Presenter Components..
        tv_presenter_name = (TextView) findViewById(R.id.tv_presenter_name);
        tv_designation_company = (TextView) findViewById(R.id.tv_designation_company);
        tv_about_presenter = (TextView) findViewById(R.id.tv_about_presenter);

        ivprofilepicture = (ImageView) findViewById(R.id.ivprofilepicture);

        // Company Components..
        tv_company_name = (TextView) findViewById(R.id.tv_company_name);
        tv_company_website = (TextView) findViewById(R.id.tv_company_website);
        tv_company_description = (TextView) findViewById(R.id.tv_company_description);

        ivprofilepictureCompany = (ImageView) findViewById(R.id.ivprofilepictureCompany);

        // Testimonials Components..
        tv_view_more_testimonial = (TextView) findViewById(R.id.tv_view_more_testimonial);
        tv_no_testimonial = (TextView) findViewById(R.id.tv_no_testimonial);
        lv_testimonial_set = (LinearLayout) findViewById(R.id.lv_testimonial_set);

        // Others Components..
        tv_faq = (TextView) findViewById(R.id.tv_faq);
        tv_refund_cancelation_policy = (TextView) findViewById(R.id.tv_refund_cancelation_policy);
        txt_nasba_address = (TextView) findViewById(R.id.txt_nasba_address);
        txt_nasba_description = (TextView) findViewById(R.id.txt_nasba_description);
        txt_ea_address = (TextView) findViewById(R.id.txt_ea_address);
        txt_ea_description = (TextView) findViewById(R.id.txt_ea_description);
        txt_irs_address = (TextView) findViewById(R.id.txt_irs_address);
        txt_irs_description = (TextView) findViewById(R.id.txt_irs_description);
        txt_ctec_address = (TextView) findViewById(R.id.txt_ctec_address);
        txt_ctec_description = (TextView) findViewById(R.id.txt_ctec_description);

        rel_nasba = (RelativeLayout) findViewById(R.id.rel_nasba);
        rel_nasba_desc = (RelativeLayout) findViewById(R.id.rel_nasba_desc);
        rel_ea = (RelativeLayout) findViewById(R.id.rel_ea);
        rel_ea_desc = (RelativeLayout) findViewById(R.id.rel_ea_desc);
        rel_irs = (RelativeLayout) findViewById(R.id.rel_irs);
        rel_irs_desc = (RelativeLayout) findViewById(R.id.rel_irs_desc);
        rel_ctec = (RelativeLayout) findViewById(R.id.rel_ctec);
        rel_ctec_desc = (RelativeLayout) findViewById(R.id.rel_ctec_desc);

        iv_nasba_profile = (ImageView) findViewById(R.id.iv_nasba_profile);
        iv_nasba_profile_qas = (ImageView) findViewById(R.id.iv_nasba_profile_qas);
        iv_ea_profile = (ImageView) findViewById(R.id.iv_ea_profile);
        iv_irs_profile = (ImageView) findViewById(R.id.iv_irs_profile);
        iv_ctec_profile = (ImageView) findViewById(R.id.iv_ctec_profile);

        // Overview of topics components..
        tv_overview_of_topics = (TextView) findViewById(R.id.tv_overview_of_topics);

        // Why should attend components..
        tv_why_you_should_attend = (TextView) findViewById(R.id.tv_why_you_should_attend);

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
                        openDialogConfirmRedirect(url);
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
//                finish();
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
                    if (checkPlay == 0 || checkPlay == 2) {
                        handler.removeCallbacks(runnable);
                        PlayVideo();
                    }
                } else {
                    Snackbar.make(binding.ivPlay, context.getResources().getString(R.string.str_video_link_not_avilable), Snackbar.LENGTH_SHORT).show();
                }

            /*else if (binding.tvWebinarStatus.getText().toString().equalsIgnoreCase(context
                    .getResources().getString(R.string.str_webinar_status_resume_watching))) {
                if (!VIDEO_URL.equalsIgnoreCase("")) {
                    PlayVideo();
                } else {
                    Snackbar.make(binding.ivPlay, context.getResources().getString(R.string.str_video_link_not_avilable), Snackbar.LENGTH_SHORT).show();
                }
            } */
            } else if (binding.tvWebinarStatus.getText().toString().equalsIgnoreCase(context
                    .getResources().getString(R.string.str_webinar_status_completed)) && !isReview && webinar_type.equalsIgnoreCase(getResources().getString(R.string.str_self_study_on_demand))) {
                // Load review Popup here..
                if (!Constant.isFromCSPast) {
                    Log.e("*+*+*", "Popup webinar load from here 0");
//                    showAddReviewPopUp();
                    showPopupReview();
                    /*Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity.getInstance().showPopupReview("Test");
                        }
                    },1000);*/
                }
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

                if (!Cost.equalsIgnoreCase("")) {
                    if (isCardSaved) {
                        if (Constant.isNetworkAvailable(context)) {
                            progressDialog = DialogsUtils.showProgressDialog(context, context.getResources().getString(R.string.progrees_msg));
                            RegisterWebinar(webinarid, binding.tvWebinarStatus);
                        } else {
                            Snackbar.make(binding.ivfavorite, context.getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                        }
                    } else {
                        String url = "" + strPaymentRedirectionURL;
                        openDialogConfirmRedirect(url);
                    }
                } else {
                    if (Constant.isNetworkAvailable(context)) {
                        progressDialog = DialogsUtils.showProgressDialog(context, context.getResources().getString(R.string.progrees_msg));
                        RegisterWebinar(webinarid, binding.tvWebinarStatus);
                    } else {
                        Snackbar.make(binding.ivfavorite, context.getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        }

    }

    private void openDialogConfirmRedirect(final String UrlRedirtect) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Message");
        builder.setMessage(getResources().getString(R.string.str_save_card));

//                        builder.setPositiveButton("OK", null);
        builder.setPositiveButton("Save Card",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        try {
                            Intent i = new Intent("android.intent.action.MAIN");
                            i.setComponent(ComponentName.unflattenFromString("" + strPaymentRedirectionURL));
                            i.addCategory("android.intent.category.LAUNCHER");
                            i.setData(Uri.parse(UrlRedirtect));
                            startActivity(i);
                        } catch (ActivityNotFoundException e) {
                            // Chrome is not installed
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(UrlRedirtect));
                            startActivity(i);
                        }
                    }
                });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /*private void showAddReviewPopUp() {

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
                *//*Intent i = new Intent(context, WebinarDetailsActivity.class);
                i.putExtra(getResources().getString(R.string.pass_webinar_id), webinar_id);
                i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                startActivity(i);
                finish();*//*

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

    }*/

    private void GetSubmitReviewAnswer(int is_like, int rating, String strReview, int webinarid) {

        mAPIService.AddReview(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context), webinarid
                , rating, is_like, strReview).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AddReview>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(AddReview addReview) {

                        if (addReview.isSuccess() == true) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            binding.relPopupReview.setVisibility(View.GONE);
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
//                    presentation_length = exoPlayer.getDuration();

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
//                    presentation_length = exoPlayer.getDuration();

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

                        presentation_length = exoPlayer.getDuration();
                        presentation_length = TimeUnit.MILLISECONDS.toMinutes(presentation_length);

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
//                        presentation_length = exoPlayer.getDuration();
                        mResumePosition = TimeUnit.MILLISECONDS.toSeconds(mResumePosition);
//                        presentation_length = TimeUnit.MILLISECONDS.toMinutes(presentation_length);

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
                                binding.relWebinarStatus.setBackgroundResource(R.drawable.rounded_login);
                                Constant.Log("joinurl", "joinurl" + join_url);
                            } else {
                                binding.relWebinarStatus.setVisibility(View.GONE);
                                binding.tvWebinarStatus.setVisibility(View.GONE);
                                binding.linTags.setVisibility(View.VISIBLE);
                                webinar_status = modelRegisterWebinar.getPayload().getRegisterStatus();
                                binding.tvWebinarStatusNew.setText(Constant.toTitleCase(modelRegisterWebinar.getPayload().getRegisterStatus()));
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
//            binding.tvPlayIconNew.setVisibility(View.VISIBLE);
            binding.tvPlayIconNew.setVisibility(View.GONE);
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
        } else if (isFromSpeakerCompanyProfile) {
            finish();
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

                            if (webinar_details.getPayload().getWebinarDetail().getIsWebinarCpd() == 1) {
                                Constant.isWebinarCPD = true;
                                Constant.cpdCredit = webinar_details.getPayload().getWebinarDetail().getCpdCredit();
                            } else {
                                Constant.isWebinarCPD = false;
                            }

                            if (webinar_type.equalsIgnoreCase(getResources().getString(R.string.str_filter_live))) {
                                binding.txtLiveWebinarTitle.setText(webinar_details.getPayload().getWebinarDetail().getWebinarTitle());
                                binding.txtLiveWebinarAuthor.setText(webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getName());
                            } else if (webinar_type.equalsIgnoreCase(getResources().getString(R.string.str_self_study_on_demand))) {
                                binding.txtSelfStudyWebinarTitle.setText(webinar_details.getPayload().getWebinarDetail().getWebinarTitle());
                                binding.txtSelfStudyWebinarAuthor.setText(webinar_details.getPayload().getWebinarDetail().getAboutPresententer().getName());
                            }

                            webinar_type_details = webinar_details.getPayload().getWebinarDetail().getWebinarType();

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

                            if (webinar_details.getPayload().getWebinarDetail().getCompanyId() != 0) {
                                company_id = webinar_details.getPayload().getWebinarDetail().getCompanyId();
                            }

                            if (webinar_details.getPayload().getWebinarDetail().getSpeakerId() != 0) {
                                speaker_id = webinar_details.getPayload().getWebinarDetail().getSpeakerId();
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

                                /*binding.txtWebinarDate.setText(month + " " + day + ", " + year +
                                        " | " + webinar_details.getPayload().getWebinarDetail().getStartTime() + " " + webinar_details.getPayload().getWebinarDetail().getTimeZone());*/

                                binding.txtWebinarDate.setText(day + " " + month + ", "+ webinar_details.getPayload().getWebinarDetail().getStartTime()
                                + " " + webinar_details.getPayload().getWebinarDetail().getTimeZone());
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
//                                    binding.relWebinarStatus.setBackgroundResource(R.drawable.rounded_signup);
                                    binding.relWebinarStatus.setBackgroundResource(R.drawable.rounded_login);
                                } else {
                                    //binding.tvWebinarStatus.setBackgroundResource(R.drawable.squrebutton_webinar_status);
                                    binding.relWebinarStatus.setBackgroundResource(R.drawable.rounded_login);
                                }


                                webinar_status = webinar_details.getPayload().getWebinarDetail().getStatus();


                                binding.tvWebinarStatus.setText("" + Constant.toTitleCase(webinar_details.getPayload().getWebinarDetail().getStatus()));
                                binding.tvWebinarStatusNew.setText("" + Constant.toTitleCase(webinar_details.getPayload().getWebinarDetail().getStatus()));
                                showHidePlayButton();
                            }


                            if (!webinar_details.getPayload().getWebinarDetail().getWebinarThumbnail().equalsIgnoreCase("")) {

//                                (context).ivwebinar_thumbhel.getLayoutParams().height = Math.round(Constant.progHeigth);
                                binding.ivthumbhel.getLayoutParams().height = Math.round(Constant.progHeigth);

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

                            if (!webinar_details.getPayload().getWebinarDetail().getIrsApproved().getIrsAddress().equalsIgnoreCase("")) {
                                irs_address = webinar_details.getPayload().getWebinarDetail().getIrsApproved().getIrsAddress();
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getIrsApproved().getIrsDesc().equalsIgnoreCase("")) {
                                irs_description = webinar_details.getPayload().getWebinarDetail().getIrsApproved().getIrsDesc();
                            }

                            if (!webinar_details.getPayload().getWebinarDetail().getIrsApproved().getIrsProfileIcon().equalsIgnoreCase("")) {
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
//                            binding.linWhoShouldAttend.setVisibility(View.VISIBLE);
//                            binding.linOverOfTopics.setVisibility(View.GONE);
                            if (webinar_type.equalsIgnoreCase(getResources().getString(R.string.str_filter_live))) {
                                if (!whyshouldattend.equalsIgnoreCase("")) {
//                                    adapter.addFragment(new WhyYouShouldAttend(), getResources().getString(R.string.str_why_you_should_attend));
                                    binding.linWhoShouldAttend.setVisibility(View.VISIBLE);
                                }
                            } else if (webinar_type.equalsIgnoreCase(getResources().getString(R.string.str_self_study_on_demand))) {
                                if (!overviewoftopic.equalsIgnoreCase("")) {
//                                    adapter.addFragment(new OverviewOfTopicsFragment(), getResources().getString(R.string.str_overview_of_topics));
                                    binding.linOverOfTopics.setVisibility(View.VISIBLE);
                                }
                                if (!whyshouldattend.equalsIgnoreCase("")) {
//                                    adapter.addFragment(new WhyYouShouldAttend(), getResources().getString(R.string.str_why_you_should_attend));
                                    binding.linWhoShouldAttend.setVisibility(View.VISIBLE);
                                }
                            }

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
//                                        binding.tvPlayIcon.setVisibility(View.VISIBLE);
                                        binding.tvPlayIcon.setVisibility(View.GONE);
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
                                    .getResources().getString(R.string.str_webinar_status_completed)) && !isReview && webinar_type.equalsIgnoreCase(getResources().getString(R.string.str_self_study_on_demand))) {
                                if (!Constant.isFromCSPast) {
                                    Log.e("*+*+*", "Popup webinar load from here 1");
//                                    showAddReviewPopUp();
                                    showPopupReview();
                                    /*Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            MainActivity.getInstance().showPopupReview("Test");
                                        }
                                    },1000);*/

                                }
                            }

                            if (webinartestimonial.size() > 0) {
                                setupViewPager(binding.viewpager);
                                binding.tabs.setupWithViewPager(binding.viewpager);

                            } else {
                                setupViewPagerWithoutTestimonial(binding.viewpager);
                                binding.tabs.setupWithViewPager(binding.viewpager);

                            }

                            loadDataDetailComponents();
                            loadDataDescriptionComponents();
                            loadDataPresenterComponents();
                            loadDataCompanyComponents();
                            loadDataTestimonialsComponents();
                            loadDataOthersComponents();
                            loadDataOverviewOfTopics();
                            loadDataWhyShouldAttend();

                        } else {
                            Snackbar.make(binding.relView, webinar_details.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }

                });

    }

    private void loadDataWhyShouldAttend() {
        if (!whyshouldattend.equalsIgnoreCase("")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tv_why_you_should_attend.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tv_why_you_should_attend.setText(Html.fromHtml(whyshouldattend, Html.FROM_HTML_MODE_COMPACT));
            } else {
                tv_why_you_should_attend.setText(Html.fromHtml(whyshouldattend));
            }

        }
    }

    private void loadDataOverviewOfTopics() {
        if (!overviewoftopic.equalsIgnoreCase("")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tv_overview_of_topics.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tv_overview_of_topics.setText(Html.fromHtml(overviewoftopic, Html.FROM_HTML_MODE_COMPACT));
            } else {
                tv_overview_of_topics.setText(Html.fromHtml(overviewoftopic));
            }
        }
    }

    private void loadDataOthersComponents() {
        if (!faq.equalsIgnoreCase("")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tv_faq.setText(Html.fromHtml(faq, Html.FROM_HTML_MODE_COMPACT));
            } else {
                tv_faq.setText(Html.fromHtml(faq));
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tv_faq.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

        if (!refund_and_cancelation.equalsIgnoreCase("")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tv_refund_cancelation_policy.setText(Html.fromHtml(refund_and_cancelation, Html.FROM_HTML_MODE_COMPACT));
            } else {
                tv_refund_cancelation_policy.setText(Html.fromHtml(refund_and_cancelation));
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tv_refund_cancelation_policy.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

        Log.e("*+*+*", "");
        if (getwebinar_type.equalsIgnoreCase("CPE/CE")) {

            rel_nasba.setVisibility(View.VISIBLE);
            rel_nasba_desc.setVisibility(View.VISIBLE);

            // Previously we are showing the ea approved data there in CPE/CE and only CE conditions and not showing this in CPE type only
            // Now instead of showing data for EA Approved we have to show IRS Data there only.. So have to Hide EA Approved Data
            rel_ea.setVisibility(View.GONE);
            rel_ea_desc.setVisibility(View.GONE);
            rel_irs.setVisibility(View.VISIBLE);
            rel_irs_desc.setVisibility(View.VISIBLE);


            if (!nasba_address.equalsIgnoreCase("")) {
                txt_nasba_address.setText(nasba_address);
            }

            if (!nasba_description.equalsIgnoreCase("")) {
                txt_nasba_description.setText(nasba_description);
            }

            if (!ea_address.equalsIgnoreCase("")) {
                txt_ea_address.setText(ea_address);
            }

            if (!ea_description.equalsIgnoreCase("")) {
                txt_ea_description.setText(ea_description);
            }

            if (!nasba_profile_pic.equalsIgnoreCase("")) {
                Picasso.with(WebinarDetailsActivity.this).load(nasba_profile_pic)
                        .placeholder(R.mipmap.webinar_placeholder)
                        .into((iv_nasba_profile));
            }

            if (!nasba_profile_pic_qas.equalsIgnoreCase("")) {
                iv_nasba_profile_qas.setVisibility(View.VISIBLE);
                Picasso.with(WebinarDetailsActivity.this).load(nasba_profile_pic_qas)
                        .placeholder(R.mipmap.webinar_placeholder)
                        .into((iv_nasba_profile_qas));
            } else {
                iv_nasba_profile_qas.setVisibility(View.GONE);
            }

            if (!ea_profile_pic.equalsIgnoreCase("")) {
                Picasso.with(WebinarDetailsActivity.this).load(ea_profile_pic)
                        .placeholder(R.mipmap.webinar_placeholder)
                        .into((iv_ea_profile));
            }

            ShowCtecApproved();
            showIrsApproved();

        } else if (getwebinar_type.equalsIgnoreCase("CPE")) {

            rel_nasba.setVisibility(View.VISIBLE);
            rel_nasba_desc.setVisibility(View.VISIBLE);

            rel_ea.setVisibility(View.GONE);
            rel_ea_desc.setVisibility(View.GONE);
            rel_irs.setVisibility(View.GONE);
            rel_irs_desc.setVisibility(View.GONE);

            if (!nasba_address.equalsIgnoreCase("")) {
                txt_nasba_address.setText(nasba_address);
            }

            if (!nasba_description.equalsIgnoreCase("")) {
                txt_nasba_description.setText(nasba_description);
            }

            if (!nasba_profile_pic.equalsIgnoreCase("")) {
                Picasso.with(WebinarDetailsActivity.this).load(nasba_profile_pic)
                        .placeholder(R.mipmap.webinar_placeholder)
                        .into((iv_nasba_profile));
            }

            if (!nasba_profile_pic_qas.equalsIgnoreCase("")) {
                iv_nasba_profile_qas.setVisibility(View.VISIBLE);
                Picasso.with(WebinarDetailsActivity.this).load(nasba_profile_pic_qas)
                        .placeholder(R.mipmap.webinar_placeholder)
                        .into((iv_nasba_profile_qas));
            } else {
                iv_nasba_profile_qas.setVisibility(View.GONE);
            }

            ShowCtecApproved();
            showIrsApproved();

        } else if (getwebinar_type.equalsIgnoreCase("CE")) {

            rel_nasba.setVisibility(View.GONE);
            rel_nasba_desc.setVisibility(View.GONE);

            // Previously we are showing the ea approved data there in CPE/CE and only CE conditions and not showing this in CPE type only
            // Now instead of showing data for EA Approved we have to show IRS Data there only.. So have to Hide EA Approved Data
            rel_ea.setVisibility(View.GONE);
            rel_ea_desc.setVisibility(View.GONE);
            rel_irs.setVisibility(View.VISIBLE);
            rel_irs_desc.setVisibility(View.VISIBLE);

            if (!ea_address.equalsIgnoreCase("")) {
                txt_ea_address.setText(ea_address);
            }

            if (!ea_description.equalsIgnoreCase("")) {
                txt_ea_description.setText(ea_description);
            }

            if (!ea_profile_pic.equalsIgnoreCase("")) {
                Picasso.with(WebinarDetailsActivity.this).load(ea_profile_pic)
                        .placeholder(R.mipmap.webinar_placeholder)
                        .into((iv_ea_profile));
            }

            if (!nasba_profile_pic_qas.equalsIgnoreCase("")) {
                iv_nasba_profile_qas.setVisibility(View.VISIBLE);
                Picasso.with(WebinarDetailsActivity.this).load(nasba_profile_pic_qas)
                        .placeholder(R.mipmap.webinar_placeholder)
                        .into((iv_nasba_profile_qas));
            } else {
                iv_nasba_profile_qas.setVisibility(View.GONE);
            }

            ShowCtecApproved();
            showIrsApproved();
        }
    }

    private void showIrsApproved() {
        if (!irs_address.equalsIgnoreCase("")) {
            txt_irs_address.setText(irs_address);
        }

        if (!irs_description.equalsIgnoreCase("")) {
            txt_irs_description.setText(irs_description);
        }

        if (!irs_profile_pic.equalsIgnoreCase("")) {
            Picasso.with(WebinarDetailsActivity.this).load(irs_profile_pic)
                    .placeholder(R.mipmap.webinar_placeholder)
                    .into((iv_irs_profile));
        }
    }

    private void ShowCtecApproved() {
        if (!ctec_course_id.equalsIgnoreCase("")) {
            rel_ctec.setVisibility(View.VISIBLE);
            rel_ctec_desc.setVisibility(View.VISIBLE);
        } else {
            rel_ctec.setVisibility(View.GONE);
            rel_ctec_desc.setVisibility(View.GONE);
        }


        if (!ctec_address.equalsIgnoreCase("")) {
            txt_ctec_address.setText(ctec_address);
        }

        if (!ctec_description.equalsIgnoreCase("")) {
            txt_ctec_description.setText(ctec_description);
        }
        if (!ctec_profile_pic.equalsIgnoreCase("")) {
            Picasso.with(WebinarDetailsActivity.this).load(ctec_profile_pic)
                    .placeholder(R.mipmap.webinar_placeholder)
                    .into((iv_ctec_profile));
        }
    }

    private void loadDataTestimonialsComponents() {

        if (webinartestimonial.size() > 0) {

            if (webinartestimonial.size() >= 2) {
                tv_view_more_testimonial.setVisibility(View.VISIBLE);
            } else {
                tv_view_more_testimonial.setVisibility(View.GONE);
            }

            lv_testimonial_set.setVisibility(View.VISIBLE);
            tv_no_testimonial.setVisibility(View.GONE);

            for (int i = 0; i < webinartestimonial.size(); i++) {

                LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View _itemRow = inflate.inflate(R.layout.row_tetimonial, null);

                final TextView tv_username_name = (TextView) _itemRow.findViewById(R.id.tv_username_name);
                final ImageView iv_testimonial_star = (ImageView) _itemRow.findViewById(R.id.iv_testimonial_star);
                final TextView tv_review_decription = (TextView) _itemRow.findViewById(R.id.tv_review_decription);
                final TextView tv_date = (TextView) _itemRow.findViewById(R.id.tv_date);
                final View viewBlack = (View) _itemRow.findViewById(R.id.viewBlack);

                if (i == 0) {
                    if (webinartestimonial.size() > 1) {
                        viewBlack.setVisibility(View.VISIBLE);
                    } else {
                        viewBlack.setVisibility(View.GONE);
                    }
                } else {
                    viewBlack.setVisibility(View.GONE);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    tv_username_name.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    tv_review_decription.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
                }

                if (!webinartestimonial.get(i).getFirstName().equalsIgnoreCase("")) {

                    tv_username_name.setText(webinartestimonial.get(i).getFirstName()
                            + " " + webinartestimonial.get(i).getLastName()
                            + " " + webinartestimonial.get(i).getDesignation());
                }

                if (!webinartestimonial.get(i).getDate().equalsIgnoreCase("")) {
                    tv_date.setText(webinartestimonial.get(i).getDate());
                }

                if (!webinartestimonial.get(i).getReview().equalsIgnoreCase("")) {
                    tv_review_decription.setText(webinartestimonial.get(i).getReview());
                }
                if (!webinartestimonial.get(i).getRate()
                        .equalsIgnoreCase("")) {

                    if (webinartestimonial.get(i).getRate().equalsIgnoreCase("0")) {

                        iv_testimonial_star.setImageResource(R.mipmap.rev_star_zero);

                    } else if (webinartestimonial.get(i).getRate().equalsIgnoreCase("1")) {

                        iv_testimonial_star.setImageResource(R.mipmap.rev_star_one);
                    } else if (webinartestimonial.get(i).getRate().equalsIgnoreCase("2")) {
                        iv_testimonial_star.setImageResource(R.mipmap.rev_star_two);

                    } else if (webinartestimonial.get(i).getRate().equalsIgnoreCase("3")) {

                        iv_testimonial_star.setImageResource(R.mipmap.rev_star_three);

                    } else if (webinartestimonial.get(i).getRate().equalsIgnoreCase("4")) {

                        iv_testimonial_star.setImageResource(R.mipmap.rev_star_four);

                    } else if (webinartestimonial.get(i).getRate().equalsIgnoreCase("5")) {

                        iv_testimonial_star.setImageResource(R.mipmap.rev_star_five);
                    }
                }
                lv_testimonial_set.addView(_itemRow);
            }
        } else {
            lv_testimonial_set.setVisibility(View.GONE);
            tv_view_more_testimonial.setVisibility(View.GONE);
            tv_no_testimonial.setVisibility(View.VISIBLE);
        }

        tv_view_more_testimonial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WebinarDetailsActivity.this, TestimonialActivity.class);
                i.putExtra(getResources().getString(R.string.pass_webinar_id), webinarid);
                i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                startActivity(i);
                finish();

            }
        });
    }

    private void loadDataCompanyComponents() {
        if (!company_logo.equalsIgnoreCase("")) {
            Picasso.with(WebinarDetailsActivity.this).load(company_logo)
                    .placeholder(R.drawable.profile_place_holder)
                    .fit()
                    .into((ivprofilepictureCompany));
        } else {
            ivprofilepictureCompany.setImageResource(R.drawable.profile_place_holder);
        }

        if (!aboutpresenterCompanyName.equalsIgnoreCase("")) {
            tv_company_name.setText(aboutpresenterCompanyName);
        }

        if (!aboutpresenterCompanyWebsite.equalsIgnoreCase("")) {
            tv_company_website.setText(aboutpresenterCompanyWebsite);
        }

        if (!aboutpresenterCompanyDesc.equalsIgnoreCase("")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tv_company_description.setText(Html.fromHtml(aboutpresenterCompanyDesc, Html.FROM_HTML_MODE_COMPACT));
            } else {
                tv_company_description.setText(Html.fromHtml(aboutpresenterCompanyDesc));
            }
        }
    }

    private void loadDataPresenterComponents() {
        if (!presenter_image.equalsIgnoreCase("")) {
            Picasso.with(WebinarDetailsActivity.this).load(presenter_image)
                    .placeholder(R.drawable.profile_place_holder)
                    .fit()
                    .into((ivprofilepicture));
        } else {
            ivprofilepicture.setImageResource(R.drawable.profile_place_holder);
        }

        if (!aboutpresentername.equalsIgnoreCase("")) {
            tv_presenter_name.setText(aboutpresentername + " " + aboutpresenternameQualification);
        }

        if (!aboutpresenterDesgnination.equalsIgnoreCase("")) {
            tv_designation_company.setText(aboutpresenterDesgnination + ", " + aboutpresenterCompanyName);
        }

        if (!aboutpresenternameSpeakerDesc.equalsIgnoreCase("")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tv_about_presenter.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tv_about_presenter.setText(Html.fromHtml(aboutpresenternameSpeakerDesc, Html.FROM_HTML_MODE_COMPACT));
            } else {
                tv_about_presenter.setText(Html.fromHtml(aboutpresenternameSpeakerDesc));
            }
        }
    }

    private void loadDataDescriptionComponents() {
        if (!programDescription.equalsIgnoreCase("")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tv_description.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tv_description.setText(Html.fromHtml(programDescription, Html.FROM_HTML_MODE_COMPACT));
            } else {
                tv_description.setText(Html.fromHtml(programDescription));
            }
        }

        if (!learningObjective.equalsIgnoreCase("")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tv_learning_objective.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tv_learning_objective.setText(Html.fromHtml(learningObjective, Html.FROM_HTML_MODE_COMPACT));
            } else {
                tv_learning_objective.setText(Html.fromHtml(learningObjective));
            }
        }
    }

    private void loadDataDetailComponents() {

        if (!Cost.equalsIgnoreCase("")) {
            tv_cost.setText("$" + Cost);
        } else {
            tv_cost.setText("Free");
        }

        if (!credit.equalsIgnoreCase("")) {
            tv_credit.setText("" + credit);
        }

        if (!course_id.equalsIgnoreCase("")) {
            lv_course_id.setVisibility(View.VISIBLE);
            view_irs_course_id.setVisibility(View.VISIBLE);
            tv_couse_id.setText(course_id);
        } else {
            lv_course_id.setVisibility(View.GONE);
            view_irs_course_id.setVisibility(View.GONE);
        }

        if (!ctec_course_id.equalsIgnoreCase("")) {
            lv_ctec_course_id.setVisibility(View.VISIBLE);
            view_ctec.setVisibility(View.VISIBLE);
            tv_ctec_course_id.setText(ctec_course_id);
        } else {
            lv_ctec_course_id.setVisibility(View.GONE);
            view_ctec.setVisibility(View.GONE);
        }

        if (duration != 0) {

            String result = formatHoursAndMinutes(duration);

            StringTokenizer tokens = new StringTokenizer(result, ":");
            String hour = tokens.nextToken();// this will contain year
            String min = tokens.nextToken();//this will contain month
            Constant.Log("hour", "hour" + hour);
            if (min.equalsIgnoreCase("00")) {
                if (Integer.parseInt(hour) > 1) {
                    tv_duration.setText(hour + " " + getResources().getString(R.string.str_hours));
                } else {
                    tv_duration.setText(hour + " " + getResources().getString(R.string.str_hour));
                }
            } else {
                if (hour.equalsIgnoreCase("0")) {
                    tv_duration.setText(min +
                            " " + getResources().getString(R.string.str_min));
                } else {
                    if (Integer.parseInt(hour) > 1) {
                        tv_duration.setText(hour + " " + getResources().getString(R.string.str_hours) + " " + min +
                                " " + getResources().getString(R.string.str_min));
                    } else {
                        tv_duration.setText(hour + " " + getResources().getString(R.string.str_hour) + " " + min +
                                " " + getResources().getString(R.string.str_min));
                    }
                }
            }
        }

        if (!subject_area.equalsIgnoreCase("")) {
            tv_subjectarea.setText("" + subject_area);
        }

        if (!course_level.equalsIgnoreCase("")) {
            tv_course_level.setText("" + course_level);
        }

        if (!instructional_method.equalsIgnoreCase("")) {
            tv_instructionalmethod.setText("" + instructional_method);
        }

        if (!prerequisite.equalsIgnoreCase("")) {
            tv_prerequisite.setText("" + prerequisite);
        } else {
            tv_prerequisite.setText("None");
        }

        if (!advancePreparation.equalsIgnoreCase("")) {
            tv_advance_preparation.setText("" + advancePreparation);
        } else {
            tv_advance_preparation.setText("None");
        }

        if (!recorded_date.equalsIgnoreCase("")) {
            lv_recorded_date.setVisibility(View.VISIBLE);
            view_recorded_date.setVisibility(View.VISIBLE);
            tv_recorded_date.setText(recorded_date);
        } else {
            lv_recorded_date.setVisibility(View.GONE);
            view_recorded_date.setVisibility(View.GONE);
        }

        if (!published_date.equalsIgnoreCase("")) {
            lv_published_date.setVisibility(View.VISIBLE);
            view_published_date.setVisibility(View.VISIBLE);
            tv_published_date.setText(published_date);
        } else {
            lv_published_date.setVisibility(View.GONE);
            view_published_date.setVisibility(View.GONE);
        }

        tv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndroidVersion();
            }
        });

//        tv_download_key_terms
        tv_download_key_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                checkAndroidVersion_keyterms();
                if (!keyterms.equalsIgnoreCase("")) {
                    Intent i = new Intent(WebinarDetailsActivity.this, PdfViewActivity.class);
                    i.putExtra(getResources().getString(R.string.str_document_link), keyterms);
                    i.putExtra(getResources().getString(R.string.str_pdf_view_titile), getString(R.string.str_keyterms));
                    i.putExtra(getResources().getString(R.string.pass_who_you_are_list_review_question), webinarid);
                    i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                    startActivity(i);
                    finish();
                } else {
                    Constant.toast(WebinarDetailsActivity.this, getResources().getString(R.string.str_key_terms_link_not_found));
                }
            }
        });

        tv_download_instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!instructional_document.equalsIgnoreCase("")) {
                    Intent i = new Intent(WebinarDetailsActivity.this, PdfViewActivity.class);
                    i.putExtra(getResources().getString(R.string.str_document_link), instructional_document);
                    i.putExtra(getResources().getString(R.string.str_pdf_view_titile), getString(R.string.str_instructional_document));
                    i.putExtra(getResources().getString(R.string.pass_who_you_are_list_review_question), webinarid);
                    i.putExtra(getResources().getString(R.string.pass_webinar_type), WebinarDetailsActivity.getInstance()
                            .webinar_type);
                    startActivity(i);
                    finish();
                } else {
                    Constant.toast(WebinarDetailsActivity.this, getResources().getString(R.string.str_instruction_doc_not_found));
                }
            }
        });

        if (whoshouldattend.size() > 0) {
            final LinearLayout[] myview = new LinearLayout[whoshouldattend.size()];

            final TextView[] myTextViews = new TextView[whoshouldattend.size()]; // create an empty array;

            if (whoshouldattend.size() == 1) {
                final View myView_inflat = inflater_new.inflate(R.layout.row_who_should_attend, null);
                tv_who_attend = (TextView) myView_inflat.findViewById(R.id.tv_who_attend);
                tv_who_attend.setText(whoshouldattend.get(0));
                lv_who_attend.addView(tv_who_attend);
            } else if (whoshouldattend.size() == 2) {
                for (int i = 0; i < 2; i++) {
                    final View myView_inflat = inflater_new.inflate(R.layout.row_who_should_attend, null);
                    tv_who_attend = (TextView) myView_inflat.findViewById(R.id.tv_who_attend);

                    if (i == 0) {
                        tv_who_attend.setText(whoshouldattend.get(i));
                    } else {
                        int count = whoshouldattend.size() - 1;
                        tv_who_attend.setText("+" + count
                                + " more");
                    }

                    lv_who_attend.addView(tv_who_attend);
                    LinearLayout.LayoutParams tvlp = (LinearLayout.LayoutParams) tv_who_attend.getLayoutParams();
                    tvlp.topMargin = 5;
                    tvlp.bottomMargin = 5;
                    tvlp.leftMargin = 20;
                    tv_who_attend.setLayoutParams(tvlp);
                    myTextViews[i] = tv_who_attend;
                }

            } else if (whoshouldattend.size() == 3) {

                for (int i = 0; i < 3; i++) {
                    final View myView_inflat = inflater_new.inflate(R.layout.row_who_should_attend, null);
                    tv_who_attend = (TextView) myView_inflat.findViewById(R.id.tv_who_attend);

                    if (i == 0) {
                        tv_who_attend.setText(whoshouldattend.get(i));
                    } else if (i == 1) {
                        tv_who_attend.setText(whoshouldattend.get(i));
                    } else {
                        int count = whoshouldattend.size() - 2;
                        tv_who_attend.setText("+" + count
                                + " more");
                    }

                    lv_who_attend.addView(tv_who_attend);
                    LinearLayout.LayoutParams tvlp = (LinearLayout.LayoutParams) tv_who_attend.getLayoutParams();
                    tvlp.topMargin = 5;
                    tvlp.bottomMargin = 5;
                    tvlp.leftMargin = 20;
                    tv_who_attend.setLayoutParams(tvlp);
                    myTextViews[i] = tv_who_attend;
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    final View myView_inflat = inflater_new.inflate(R.layout.row_who_should_attend, null);
                    tv_who_attend = (TextView) myView_inflat.findViewById(R.id.tv_who_attend);

                    if (i == 0) {
                        tv_who_attend.setText(whoshouldattend.get(i));
                    } else if (i == 1) {
                        tv_who_attend.setText(whoshouldattend.get(i));
                    } else if (i == 2) {
                        tv_who_attend.setText(whoshouldattend.get(i));
                    } else {
                        int count = whoshouldattend.size() - 3;
                        tv_who_attend.setText("+" + count
                                + " more");
                    }

                    lv_who_attend.addView(tv_who_attend);
                    LinearLayout.LayoutParams tvlp = (LinearLayout.LayoutParams) tv_who_attend.getLayoutParams();
                    tvlp.topMargin = 5;
                    tvlp.bottomMargin = 5;
                    tvlp.leftMargin = 20;
                    tv_who_attend.setLayoutParams(tvlp);
                    myTextViews[i] = tv_who_attend;

                }
            }
        }

        lv_who_attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(WebinarDetailsActivity.this, ActivityWhoYouAre.class);
                i.putExtra(getResources().getString(R.string.pass_who_you_are_list_review_question), webinarid);
                i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                i.putStringArrayListExtra(getResources().getString(R.string.pass_who_you_are_list), whoshouldattend);
                startActivity(i);
                finish();

            }
        });
    }

    private void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            if (arrayListhandout.size() > 0) {
                DownloadHandouts(arrayListhandout);
            } else {
                Constant.toast(this, getResources().getString(R.string.str_download_link_not_found));
            }
        }

    }

    public void ShowAdapter() {
        if (arrayliattimezones.size() > 0) {
            //Getting the instance of Spinner and applying OnItemSelectedListener on it

            //Creating the ArrayAdapter instance having the user type list

            binding.spinner.setVisibility(View.VISIBLE);
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ivback:
                Log.e("*+*+*", "click ivBack");
                backpress();
                break;

            case R.id.relImgBack:
                backpress();
                Log.e("*+*+*", "click relImgBack");
                break;

            case R.id.relDetails:
                Log.e("*+*+*", "Clicked On relDetails");
                if (isExpandDetails) {
                    collapseAllComponents();
                } else {

                    isExpandDetails = true;
                    isExpandDescription = false;
                    isExpandOverviewOfTopics = false;
                    isExpandWhoShouldAttend = false;
                    isExpandPresenter = false;
                    isExpandCompany = false;
                    isExpandTestimonials = false;
                    isExpandOthers = false;
                    binding.relDetailsContent.setVisibility(View.VISIBLE);
                    binding.relDescriptionContent.setVisibility(View.GONE);
                    binding.relOverviewOfTopicsContent.setVisibility(View.GONE);
                    binding.relWhoShouldAttendContent.setVisibility(View.GONE);
                    binding.relPresenterContent.setVisibility(View.GONE);
                    binding.relCompanyContent.setVisibility(View.GONE);
                    binding.relTestimonialsContent.setVisibility(View.GONE);
                    binding.relOthersContent.setVisibility(View.GONE);

                    binding.imgArrowDetails.setBackgroundResource(R.drawable.ic_arrow_up_svg);
                    binding.imgArrowDescription.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowOverviewOfTopics.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowWhoShouldAttend.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowPresenter.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowCompany.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowTestimonials.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowOthers.setBackgroundResource(R.drawable.ic_arrow_down_svg);

//                    binding.scrollViewDetails.smoothScrollTo(0, binding.relDetails.getTop());
                }
                break;

            case R.id.relDescription:
                Log.e("*+*+*", "Clicked On relDescription");
                if (isExpandDescription) {
                    collapseAllComponents();
                } else {

                    isExpandDescription = true;
                    isExpandDetails = false;
                    isExpandOverviewOfTopics = false;
                    isExpandWhoShouldAttend = false;
                    isExpandPresenter = false;
                    isExpandCompany = false;
                    isExpandTestimonials = false;
                    isExpandOthers = false;
                    binding.relDetailsContent.setVisibility(View.GONE);
                    binding.relDescriptionContent.setVisibility(View.VISIBLE);
                    binding.relOverviewOfTopicsContent.setVisibility(View.GONE);
                    binding.relWhoShouldAttendContent.setVisibility(View.GONE);
                    binding.relPresenterContent.setVisibility(View.GONE);
                    binding.relCompanyContent.setVisibility(View.GONE);
                    binding.relTestimonialsContent.setVisibility(View.GONE);
                    binding.relOthersContent.setVisibility(View.GONE);

                    binding.imgArrowDetails.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowDescription.setBackgroundResource(R.drawable.ic_arrow_up_svg);
                    binding.imgArrowOverviewOfTopics.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowWhoShouldAttend.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowPresenter.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowCompany.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowTestimonials.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowOthers.setBackgroundResource(R.drawable.ic_arrow_down_svg);

//                    binding.scrollViewDetails.smoothScrollTo(0, binding.relDescription.getTop());
                }
                break;

            case R.id.relOverviewOfTopics:
                if (isExpandOverviewOfTopics) {
                    collapseAllComponents();
                } else {

                    isExpandOverviewOfTopics = true;
                    isExpandDetails = false;
                    isExpandDescription = false;
                    isExpandWhoShouldAttend = false;
                    isExpandPresenter = false;
                    isExpandCompany = false;
                    isExpandTestimonials = false;
                    isExpandOthers = false;
                    binding.relDetailsContent.setVisibility(View.GONE);
                    binding.relDescriptionContent.setVisibility(View.GONE);
                    binding.relOverviewOfTopicsContent.setVisibility(View.VISIBLE);
                    binding.relWhoShouldAttendContent.setVisibility(View.GONE);
                    binding.relPresenterContent.setVisibility(View.GONE);
                    binding.relCompanyContent.setVisibility(View.GONE);
                    binding.relTestimonialsContent.setVisibility(View.GONE);
                    binding.relOthersContent.setVisibility(View.GONE);

                    binding.imgArrowDetails.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowDescription.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowOverviewOfTopics.setBackgroundResource(R.drawable.ic_arrow_up_svg);
                    binding.imgArrowWhoShouldAttend.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowPresenter.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowCompany.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowTestimonials.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowOthers.setBackgroundResource(R.drawable.ic_arrow_down_svg);

//                    binding.scrollViewDetails.smoothScrollTo(0, binding.relOverviewOfTopics.getTop());
                }
                break;

            case R.id.relWhoShouldAttend:
                if (isExpandWhoShouldAttend) {
                    collapseAllComponents();
                } else {

                    isExpandWhoShouldAttend = true;
                    isExpandDetails = false;
                    isExpandDescription = false;
                    isExpandOverviewOfTopics = false;
                    isExpandPresenter = false;
                    isExpandCompany = false;
                    isExpandTestimonials = false;
                    isExpandOthers = false;
                    binding.relDetailsContent.setVisibility(View.GONE);
                    binding.relDescriptionContent.setVisibility(View.GONE);
                    binding.relOverviewOfTopicsContent.setVisibility(View.GONE);
                    binding.relWhoShouldAttendContent.setVisibility(View.VISIBLE);
                    binding.relPresenterContent.setVisibility(View.GONE);
                    binding.relCompanyContent.setVisibility(View.GONE);
                    binding.relTestimonialsContent.setVisibility(View.GONE);
                    binding.relOthersContent.setVisibility(View.GONE);

                    binding.imgArrowDetails.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowDescription.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowOverviewOfTopics.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowWhoShouldAttend.setBackgroundResource(R.drawable.ic_arrow_up_svg);
                    binding.imgArrowPresenter.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowCompany.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowTestimonials.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowOthers.setBackgroundResource(R.drawable.ic_arrow_down_svg);

//                    binding.scrollViewDetails.smoothScrollTo(0, binding.relWhoShouldAttend.getTop());
                }
                break;

            case R.id.relPresenter:
                Log.e("*+*+*", "Clicked On relPresenter");
                if (isExpandPresenter) {
                    collapseAllComponents();
                } else {

                    isExpandPresenter = true;
                    isExpandDetails = false;
                    isExpandDescription = false;
                    isExpandOverviewOfTopics = false;
                    isExpandWhoShouldAttend = false;
                    isExpandCompany = false;
                    isExpandTestimonials = false;
                    isExpandOthers = false;
                    binding.relDetailsContent.setVisibility(View.GONE);
                    binding.relDescriptionContent.setVisibility(View.GONE);
                    binding.relOverviewOfTopicsContent.setVisibility(View.GONE);
                    binding.relWhoShouldAttendContent.setVisibility(View.GONE);
                    binding.relPresenterContent.setVisibility(View.VISIBLE);
                    binding.relCompanyContent.setVisibility(View.GONE);
                    binding.relTestimonialsContent.setVisibility(View.GONE);
                    binding.relOthersContent.setVisibility(View.GONE);

                    binding.imgArrowDetails.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowDescription.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowOverviewOfTopics.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowWhoShouldAttend.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowPresenter.setBackgroundResource(R.drawable.ic_arrow_up_svg);
                    binding.imgArrowCompany.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowTestimonials.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowOthers.setBackgroundResource(R.drawable.ic_arrow_down_svg);

//                    binding.scrollViewDetails.smoothScrollTo(0, binding.relPresenter.getTop());
                }
                break;

            case R.id.relCompany:
                Log.e("*+*+*", "Clicked On relCompany");
                if (isExpandCompany) {
                    collapseAllComponents();
                } else {

                    isExpandCompany = true;
                    isExpandDetails = false;
                    isExpandDescription = false;
                    isExpandOverviewOfTopics = false;
                    isExpandWhoShouldAttend = false;
                    isExpandPresenter = false;
                    isExpandTestimonials = false;
                    isExpandOthers = false;
                    binding.relDetailsContent.setVisibility(View.GONE);
                    binding.relDescriptionContent.setVisibility(View.GONE);
                    binding.relOverviewOfTopicsContent.setVisibility(View.GONE);
                    binding.relWhoShouldAttendContent.setVisibility(View.GONE);
                    binding.relPresenterContent.setVisibility(View.GONE);
                    binding.relCompanyContent.setVisibility(View.VISIBLE);
                    binding.relTestimonialsContent.setVisibility(View.GONE);
                    binding.relOthersContent.setVisibility(View.GONE);

                    binding.imgArrowDetails.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowDescription.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowOverviewOfTopics.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowWhoShouldAttend.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowPresenter.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowCompany.setBackgroundResource(R.drawable.ic_arrow_up_svg);
                    binding.imgArrowTestimonials.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowOthers.setBackgroundResource(R.drawable.ic_arrow_down_svg);

//                    binding.scrollViewDetails.smoothScrollTo(0, binding.relCompany.getTop());
                }
                break;

            case R.id.relTestimonials:
                Log.e("*+*+*", "Clicked On relTestimonials");
                if (isExpandTestimonials) {
                    collapseAllComponents();
                } else {

                    isExpandTestimonials = true;
                    isExpandDetails = false;
                    isExpandDescription = false;
                    isExpandOverviewOfTopics = false;
                    isExpandWhoShouldAttend = false;
                    isExpandPresenter = false;
                    isExpandCompany = false;
                    isExpandOthers = false;
                    binding.relDetailsContent.setVisibility(View.GONE);
                    binding.relDescriptionContent.setVisibility(View.GONE);
                    binding.relOverviewOfTopicsContent.setVisibility(View.GONE);
                    binding.relWhoShouldAttendContent.setVisibility(View.GONE);
                    binding.relPresenterContent.setVisibility(View.GONE);
                    binding.relCompanyContent.setVisibility(View.GONE);
                    binding.relTestimonialsContent.setVisibility(View.VISIBLE);
                    binding.relOthersContent.setVisibility(View.GONE);

                    binding.imgArrowDetails.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowDescription.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowOverviewOfTopics.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowWhoShouldAttend.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowPresenter.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowCompany.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowTestimonials.setBackgroundResource(R.drawable.ic_arrow_up_svg);
                    binding.imgArrowOthers.setBackgroundResource(R.drawable.ic_arrow_down_svg);

//                    binding.scrollViewDetails.smoothScrollTo(0, binding.relTestimonials.getTop());
                }
                break;

            case R.id.relOthers:
                Log.e("*+*+*", "Clicked On relOthers");
                if (isExpandOthers) {
                    collapseAllComponents();
                } else {

                    isExpandOthers = true;
                    isExpandDetails = false;
                    isExpandDescription = false;
                    isExpandOverviewOfTopics = false;
                    isExpandWhoShouldAttend = false;
                    isExpandPresenter = false;
                    isExpandCompany = false;
                    isExpandTestimonials = false;
                    binding.relDetailsContent.setVisibility(View.GONE);
                    binding.relDescriptionContent.setVisibility(View.GONE);
                    binding.relOverviewOfTopicsContent.setVisibility(View.GONE);
                    binding.relWhoShouldAttendContent.setVisibility(View.GONE);
                    binding.relPresenterContent.setVisibility(View.GONE);
                    binding.relCompanyContent.setVisibility(View.GONE);
                    binding.relTestimonialsContent.setVisibility(View.GONE);
                    binding.relOthersContent.setVisibility(View.VISIBLE);

                    binding.imgArrowDetails.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowDescription.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowOverviewOfTopics.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowWhoShouldAttend.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowPresenter.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowCompany.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowTestimonials.setBackgroundResource(R.drawable.ic_arrow_down_svg);
                    binding.imgArrowOthers.setBackgroundResource(R.drawable.ic_arrow_up_svg);

//                    binding.scrollViewDetails.smoothScrollTo(0, binding.relOthers.getTop());
                }
                break;

            case R.id.linPopupReview:
                // Do nothing..
                break;

            case R.id.relPopupSubmitReview:
                strReview = binding.edtReview.getText().toString();

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
//                        GetSubmitReviewAnswer(0, 3, "Test Review testing here..", 163);
                    } else {
                        Snackbar.make(binding.relView, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.relChecked_yes:
                binding.relCheckedYes.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_checkbox_checked));
                binding.relCheckedNo.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_checkbox_unchecked));
                isLikeToKnowMore = true;
                is_like = 1;
                break;

            case R.id.relChecked_no:
                binding.relCheckedNo.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_checkbox_checked));
                binding.relCheckedYes.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_checkbox_unchecked));
                isLikeToKnowMore = true;
                is_like = 0;
                break;

            case R.id.tv_yes:
                binding.relCheckedYes.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_checkbox_checked));
                binding.relCheckedNo.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_checkbox_unchecked));
                isLikeToKnowMore = true;
                is_like = 1;
                break;

            case R.id.tv_no:
                binding.relCheckedNo.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_checkbox_checked));
                binding.relCheckedYes.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_checkbox_unchecked));
                isLikeToKnowMore = true;
                is_like = 0;
                break;

            case R.id.txtPopupReviewCancel:
                binding.relPopupReview.setVisibility(View.GONE);
                break;

            case R.id.iv_one:
                binding.ivOne.setImageResource(R.mipmap.add_review_star_hover);
                binding.ivTwo.setImageResource(R.mipmap.add_review_star);
                binding.ivThree.setImageResource(R.mipmap.add_review_star);
                binding.ivFour.setImageResource(R.mipmap.add_review_star);
                binding.ivFive.setImageResource(R.mipmap.add_review_star);

                binding.ivOne.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                binding.ivTwo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                binding.ivThree.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                binding.ivFour.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                binding.ivFive.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));

                rating = 1;
                break;

            case R.id.iv_two:
                binding.ivOne.setImageResource(R.mipmap.add_review_star_hover);
                binding.ivTwo.setImageResource(R.mipmap.add_review_star_hover);
                binding.ivThree.setImageResource(R.mipmap.add_review_star);
                binding.ivFour.setImageResource(R.mipmap.add_review_star);
                binding.ivFive.setImageResource(R.mipmap.add_review_star);

                binding.ivOne.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                binding.ivTwo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                binding.ivThree.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                binding.ivFour.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                binding.ivFive.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));

                rating = 2;
                break;

            case R.id.iv_three:
                binding.ivOne.setImageResource(R.mipmap.add_review_star_hover);
                binding.ivTwo.setImageResource(R.mipmap.add_review_star_hover);
                binding.ivThree.setImageResource(R.mipmap.add_review_star_hover);
                binding.ivFour.setImageResource(R.mipmap.add_review_star);
                binding.ivFive.setImageResource(R.mipmap.add_review_star);

                binding.ivOne.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                binding.ivTwo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                binding.ivThree.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                binding.ivFour.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                binding.ivFive.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));

                rating = 3;
                break;

            case R.id.iv_four:
                binding.ivOne.setImageResource(R.mipmap.add_review_star_hover);
                binding.ivTwo.setImageResource(R.mipmap.add_review_star_hover);
                binding.ivThree.setImageResource(R.mipmap.add_review_star_hover);
                binding.ivFour.setImageResource(R.mipmap.add_review_star_hover);
                binding.ivFive.setImageResource(R.mipmap.add_review_star);

                binding.ivOne.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                binding.ivTwo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                binding.ivThree.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                binding.ivFour.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                binding.ivFive.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));

                rating = 4;
                break;

            case R.id.iv_five:
                binding.ivOne.setImageResource(R.mipmap.add_review_star_hover);
                binding.ivTwo.setImageResource(R.mipmap.add_review_star_hover);
                binding.ivThree.setImageResource(R.mipmap.add_review_star_hover);
                binding.ivFour.setImageResource(R.mipmap.add_review_star_hover);
                binding.ivFive.setImageResource(R.mipmap.add_review_star_hover);

                binding.ivOne.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                binding.ivTwo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                binding.ivThree.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                binding.ivFour.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                binding.ivFive.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));

                rating = 5;
                break;
        }
    }

    private void collapseAllComponents() {
//        isexpandContentOpen = false;
        isExpandDetails = false;
        isExpandDescription = false;
        isExpandOverviewOfTopics = false;
        isExpandWhoShouldAttend = false;
        isExpandPresenter = false;
        isExpandCompany = false;
        isExpandTestimonials = false;
        isExpandOthers = false;

        binding.relDetailsContent.setVisibility(View.GONE);
        binding.relDescriptionContent.setVisibility(View.GONE);
        binding.relOverviewOfTopicsContent.setVisibility(View.GONE);
        binding.relWhoShouldAttendContent.setVisibility(View.GONE);
        binding.relPresenterContent.setVisibility(View.GONE);
        binding.relCompanyContent.setVisibility(View.GONE);
        binding.relTestimonialsContent.setVisibility(View.GONE);
        binding.relOthersContent.setVisibility(View.GONE);

        binding.imgArrowDetails.setBackgroundResource(R.drawable.ic_arrow_down_svg);
        binding.imgArrowDescription.setBackgroundResource(R.drawable.ic_arrow_down_svg);
        binding.imgArrowOverviewOfTopics.setBackgroundResource(R.drawable.ic_arrow_down_svg);
        binding.imgArrowWhoShouldAttend.setBackgroundResource(R.drawable.ic_arrow_down_svg);
        binding.imgArrowPresenter.setBackgroundResource(R.drawable.ic_arrow_down_svg);
        binding.imgArrowCompany.setBackgroundResource(R.drawable.ic_arrow_down_svg);
        binding.imgArrowTestimonials.setBackgroundResource(R.drawable.ic_arrow_down_svg);
        binding.imgArrowOthers.setBackgroundResource(R.drawable.ic_arrow_down_svg);
    }

    private void backpress() {

        if (isFromSpeakerCompanyProfile) {
            finish();
        } else {
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
                    Log.e("*+*+*","screen details on backpress is : 5");
                    finish();
                }

            }
        }
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
        if (webinar_type.equalsIgnoreCase(getResources().getString(R.string.str_self_study_on_demand))) {
            if (!webinar_status.equalsIgnoreCase(getResources().getString(R.string.str_webinar_status_register))) {
                if (webinar_status.equalsIgnoreCase(getResources().getString(R.string.str_webinar_status_watchnow)) ||
                        webinar_status.equalsIgnoreCase(getResources().getString(R.string.str_webinar_status_resume_watching))) {
                    if (!isAnswered) {
                        Constant.webinar_id = "" + webinarid;
//                        adapter.addFragment(new ReviewQuestionsFragment(), getResources().getString(R.string.str_review_question_lable));
                    }
                }
            }
        }
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
        if (webinar_type.equalsIgnoreCase(getResources().getString(R.string.str_self_study_on_demand))) {
            if (!webinar_status.equalsIgnoreCase(getResources().getString(R.string.str_webinar_status_register))) {
                if (webinar_status.equalsIgnoreCase(getResources().getString(R.string.str_webinar_status_watchnow)) ||
                        webinar_status.equalsIgnoreCase(getResources().getString(R.string.str_webinar_status_resume_watching))) {
                    if (!isAnswered) {
                        Constant.webinar_id = "" + webinarid;
//                        adapter.addFragment(new ReviewQuestionsFragment(), getResources().getString(R.string.str_review_question_lable));
                    }
                }
            }
        }
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

    private void showPopupReview() {
        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_new);

        Log.e("*+*+*", "Called method for showing popup from the MainAvtivity..");
        binding.relPopupReview.setVisibility(View.VISIBLE);
        binding.linPopupReview.startAnimation(slide_up);

        binding.txtReviewQuestion.setText(getResources().getString(R.string.str_title_question_rating_popup) + " " + WebinarDetailsActivity.getInstance().aboutpresenterCompanyName + "?");
    }

}
