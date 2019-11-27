package com.entigrity.webinarDetail;

import android.Manifest;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.entigrity.R;
import com.entigrity.activity.ActivityWhoYouAre;
import com.entigrity.activity.PdfViewActivity;
import com.entigrity.activity.WebinarDetailsActivity;
import com.entigrity.databinding.FragmentDetailsBinding;
import com.entigrity.utility.Constant;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class DetailsFragment extends Fragment {

    FragmentDetailsBinding binding;
    View view;
    LayoutInflater inflater_new;
    TextView tv_who_attend;
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 123;
    public static final int PERMISSIONS_MULTIPLE_REQUEST_KEYTERMS = 1234;
    private DownloadManager downloadManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, null, false);

        inflater_new = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);

        getActivity().registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


        if (!WebinarDetailsActivity.getInstance().Cost.equalsIgnoreCase("")) {
            binding.tvCost.setText("$" + WebinarDetailsActivity.getInstance().Cost);
        } else {
            binding.tvCost.setText("FREE");
        }

        //Constant.Log("key_terms", "++++" + WebinarDetailsActivity.getInstance().keyterms);

        if (!WebinarDetailsActivity.getInstance().credit.equalsIgnoreCase("")) {
            binding.tvCredit.setText("" + WebinarDetailsActivity.getInstance().credit);
        }


        if (!WebinarDetailsActivity.getInstance().keyterms.equalsIgnoreCase("")) {
            binding.lvKeyTerms.setVisibility(View.VISIBLE);
            binding.viewkeyterms.setVisibility(View.VISIBLE);
        } else {
            binding.lvKeyTerms.setVisibility(View.GONE);
            binding.viewkeyterms.setVisibility(View.GONE);
        }


        if (!WebinarDetailsActivity.getInstance().cecredit.equalsIgnoreCase("")) {
            binding.lvCeCredit.setVisibility(View.VISIBLE);
            binding.viewCeId.setVisibility(View.VISIBLE);
            binding.tvCeCredit.setText("" + WebinarDetailsActivity.getInstance().cecredit);

        } else {
            binding.lvCeCredit.setVisibility(View.GONE);
            binding.viewCeId.setVisibility(View.GONE);
        }


        if (!WebinarDetailsActivity.getInstance().course_id.equalsIgnoreCase("")) {
            binding.lvCourseId.setVisibility(View.VISIBLE);
            binding.viewIrsCourseId.setVisibility(View.VISIBLE);
            binding.tvCouseId.setText(WebinarDetailsActivity.getInstance().course_id);
        } else {
            binding.lvCourseId.setVisibility(View.GONE);
            binding.viewIrsCourseId.setVisibility(View.GONE);
        }

        if (!WebinarDetailsActivity.getInstance().ctec_course_id.equalsIgnoreCase("")) {
            binding.lvCtecCourseId.setVisibility(View.VISIBLE);
            binding.viewCtec.setVisibility(View.VISIBLE);
            binding.tvCtecCourseId.setText(WebinarDetailsActivity.getInstance().ctec_course_id);
        } else {
            binding.lvCtecCourseId.setVisibility(View.GONE);
            binding.viewCtec.setVisibility(View.GONE);
        }


        if (!WebinarDetailsActivity.getInstance().recorded_date.equalsIgnoreCase("")) {

            binding.lvRecordedDate.setVisibility(View.VISIBLE);
            binding.viewRecordedDate.setVisibility(View.VISIBLE);
            binding.tvRecordedDate.setText(WebinarDetailsActivity.getInstance().recorded_date);

        } else {
            binding.lvRecordedDate.setVisibility(View.GONE);
            binding.viewRecordedDate.setVisibility(View.GONE);
        }


        if (!WebinarDetailsActivity.getInstance().published_date.equalsIgnoreCase("")) {
            binding.lvPublishedDate.setVisibility(View.VISIBLE);
            binding.viewPublishedDate.setVisibility(View.VISIBLE);
            binding.tvPublishedDate.setText(WebinarDetailsActivity.getInstance().published_date);
        } else {
            binding.lvPublishedDate.setVisibility(View.GONE);
            binding.viewPublishedDate.setVisibility(View.GONE);
        }

        if (!WebinarDetailsActivity.getInstance().instructional_document.equalsIgnoreCase("")) {
            binding.viewInstructions.setVisibility(View.VISIBLE);
            binding.lvInstructions.setVisibility(View.VISIBLE);
        } else {
            binding.viewInstructions.setVisibility(View.GONE);
            binding.lvInstructions.setVisibility(View.GONE);
        }

        if (WebinarDetailsActivity.getInstance().duration != 0) {

            String result = formatHoursAndMinutes(WebinarDetailsActivity.getInstance().duration);

            StringTokenizer tokens = new StringTokenizer(result, ":");
            String hour = tokens.nextToken();// this will contain year
            String min = tokens.nextToken();//this will contain month

            Constant.Log("hour", "hour" + hour);


            if (min.equalsIgnoreCase("00")) {

                if (Integer.parseInt(hour) > 1) {

                    binding.tvDuration.setText(hour + " " + getResources().getString(R.string.str_hours));

                } else {
                    binding.tvDuration.setText(hour + " " + getResources().getString(R.string.str_hour));
                }

            } else {
                if (hour.equalsIgnoreCase("0")) {
                    binding.tvDuration.setText(min +
                            " " + getResources().getString(R.string.str_min));
                } else {

                    if (Integer.parseInt(hour) > 1) {

                        binding.tvDuration.setText(hour + " " + getResources().getString(R.string.str_hours) + " " + min +
                                " " + getResources().getString(R.string.str_min));
                    } else {
                        binding.tvDuration.setText(hour + " " + getResources().getString(R.string.str_hour) + " " + min +
                                " " + getResources().getString(R.string.str_min));

                    }


                }


            }


        }


        if (!WebinarDetailsActivity.getInstance().subject_area.equalsIgnoreCase("")) {
            binding.tvSubjectarea.setText("" + WebinarDetailsActivity.getInstance().subject_area);
        }


        if (!WebinarDetailsActivity.getInstance().course_level.equalsIgnoreCase("")) {
            binding.tvCourseLevel.setText("" + WebinarDetailsActivity.getInstance().course_level);
        }


        if (!WebinarDetailsActivity.getInstance().instructional_method.equalsIgnoreCase("")) {
            binding.tvInstructionalmethod.setText("" + WebinarDetailsActivity.getInstance().instructional_method);
        }


        if (!WebinarDetailsActivity.getInstance().prerequisite.equalsIgnoreCase("")) {
            binding.tvPrerequisite.setText("" + WebinarDetailsActivity.getInstance().prerequisite);
        } else {
            binding.tvPrerequisite.setText("None");
        }


        if (!WebinarDetailsActivity.getInstance().advancePreparation.equalsIgnoreCase("")) {
            binding.tvAdvancePreparation.setText("" + WebinarDetailsActivity.getInstance().advancePreparation);
        } else {
            binding.tvAdvancePreparation.setText("None");
        }


        if (WebinarDetailsActivity.getInstance().whoshouldattend.size() > 0) {
            final LinearLayout[] myview = new LinearLayout[WebinarDetailsActivity.getInstance().whoshouldattend.size()];

            final TextView[] myTextViews = new TextView[WebinarDetailsActivity.getInstance().whoshouldattend.size()]; // create an empty array;

            if (WebinarDetailsActivity.getInstance().whoshouldattend.size() == 1) {
                final View myView_inflat = inflater_new.inflate(R.layout.row_who_should_attend, null);
                tv_who_attend = (TextView) myView_inflat.findViewById(R.id.tv_who_attend);
                tv_who_attend.setText(WebinarDetailsActivity.getInstance().whoshouldattend.get(0));
                binding.lvWhoAttend.addView(tv_who_attend);
            } else if (WebinarDetailsActivity.getInstance().whoshouldattend.size() == 2) {
                for (int i = 0; i < 2; i++) {
                    final View myView_inflat = inflater_new.inflate(R.layout.row_who_should_attend, null);
                    tv_who_attend = (TextView) myView_inflat.findViewById(R.id.tv_who_attend);

                    if (i == 0) {
                        tv_who_attend.setText(WebinarDetailsActivity.getInstance().whoshouldattend.get(i));
                    } else {
                        int count = WebinarDetailsActivity.getInstance().whoshouldattend.size() - 1;
                        tv_who_attend.setText("+" + count
                                + " more");
                    }

                    binding.lvWhoAttend.addView(tv_who_attend);
                    LinearLayout.LayoutParams tvlp = (LinearLayout.LayoutParams) tv_who_attend.getLayoutParams();
                    tvlp.topMargin = 5;
                    tvlp.bottomMargin = 5;
                    tv_who_attend.setLayoutParams(tvlp);
                    myTextViews[i] = tv_who_attend;


                }

            } else if (WebinarDetailsActivity.getInstance().whoshouldattend.size() == 3) {

                for (int i = 0; i < 3; i++) {
                    final View myView_inflat = inflater_new.inflate(R.layout.row_who_should_attend, null);
                    tv_who_attend = (TextView) myView_inflat.findViewById(R.id.tv_who_attend);

                    if (i == 0) {
                        tv_who_attend.setText(WebinarDetailsActivity.getInstance().whoshouldattend.get(i));
                    } else if (i == 1) {
                        tv_who_attend.setText(WebinarDetailsActivity.getInstance().whoshouldattend.get(i));
                    } else {
                        int count = WebinarDetailsActivity.getInstance().whoshouldattend.size() - 2;
                        tv_who_attend.setText("+" + count
                                + " more");
                    }

                    binding.lvWhoAttend.addView(tv_who_attend);
                    LinearLayout.LayoutParams tvlp = (LinearLayout.LayoutParams) tv_who_attend.getLayoutParams();
                    tvlp.topMargin = 5;
                    tvlp.bottomMargin = 5;
                    tv_who_attend.setLayoutParams(tvlp);
                    myTextViews[i] = tv_who_attend;


                }
            } else {


                for (int i = 0; i < 4; i++) {
                    final View myView_inflat = inflater_new.inflate(R.layout.row_who_should_attend, null);
                    tv_who_attend = (TextView) myView_inflat.findViewById(R.id.tv_who_attend);

                    if (i == 0) {
                        tv_who_attend.setText(WebinarDetailsActivity.getInstance().whoshouldattend.get(i));
                    } else if (i == 1) {
                        tv_who_attend.setText(WebinarDetailsActivity.getInstance().whoshouldattend.get(i));
                    } else if (i == 2) {
                        tv_who_attend.setText(WebinarDetailsActivity.getInstance().whoshouldattend.get(i));
                    } else {
                        int count = WebinarDetailsActivity.getInstance().whoshouldattend.size() - 3;
                        tv_who_attend.setText("+" + count
                                + " more");
                    }

                    binding.lvWhoAttend.addView(tv_who_attend);
                    LinearLayout.LayoutParams tvlp = (LinearLayout.LayoutParams) tv_who_attend.getLayoutParams();
                    tvlp.topMargin = 5;
                    tvlp.bottomMargin = 5;
                    tv_who_attend.setLayoutParams(tvlp);
                    myTextViews[i] = tv_who_attend;

                }
            }
        }


        binding.lvWhoAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), ActivityWhoYouAre.class);
                i.putExtra(getResources().getString(R.string.pass_who_you_are_list_review_question),
                        WebinarDetailsActivity.getInstance().webinarid);
                i.putExtra(getResources().getString(R.string.pass_webinar_type),
                        WebinarDetailsActivity.getInstance().webinar_type);
                i.putStringArrayListExtra(getResources().getString(R.string.pass_who_you_are_list), WebinarDetailsActivity.getInstance().whoshouldattend);
                startActivity(i);
                getActivity().finish();

            }
        });


        binding.tvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                checkAndroidVersion();
            }
        });

        binding.tvDownloadKeyTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                checkAndroidVersion_keyterms();
                if (!WebinarDetailsActivity.getInstance().keyterms.equalsIgnoreCase("")) {
                    Intent i = new Intent(getActivity(), PdfViewActivity.class);
                    i.putExtra(getActivity().getResources().getString(R.string.str_document_link),
                            WebinarDetailsActivity.getInstance().keyterms);
                    i.putExtra(getActivity().getResources().getString(R.string.str_pdf_view_titile), getActivity().getString(R.string.str_keyterms));
                    i.putExtra(getResources().getString(R.string.pass_who_you_are_list_review_question),
                            WebinarDetailsActivity.getInstance().webinarid);
                    i.putExtra(getResources().getString(R.string.pass_webinar_type), WebinarDetailsActivity.getInstance()
                            .webinar_type);
                    getActivity().startActivity(i);
                    getActivity().finish();
                } else {
                    Constant.toast(getActivity(), getResources().getString(R.string.str_key_terms_link_not_found));
                }


            }
        });

        binding.tvDownloadInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!WebinarDetailsActivity.getInstance().instructional_document.equalsIgnoreCase("")) {
                    Intent i = new Intent(getActivity(), PdfViewActivity.class);
                    i.putExtra(getActivity().getResources().getString(R.string.str_document_link),
                            WebinarDetailsActivity.getInstance().instructional_document);
                    i.putExtra(getActivity().getResources().getString(R.string.str_pdf_view_titile), getActivity().getString(R.string.str_instructional_document));
                    i.putExtra(getResources().getString(R.string.pass_who_you_are_list_review_question),
                            WebinarDetailsActivity.getInstance().webinarid);
                    i.putExtra(getResources().getString(R.string.pass_webinar_type), WebinarDetailsActivity.getInstance()
                            .webinar_type);
                    getActivity().startActivity(i);
                    getActivity().finish();
                } else {
                    Constant.toast(getActivity(), getResources().getString(R.string.str_instruction_doc_not_found));
                }
            }
        });


        return view = binding.getRoot();
    }

    BroadcastReceiver onComplete = new BroadcastReceiver() {

        public void onReceive(Context ctxt, Intent intent) {


            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);


            WebinarDetailsActivity.getInstance().list.remove(referenceId);


            if (WebinarDetailsActivity.getInstance().list.isEmpty()) {


                Toast.makeText(getActivity(), "Download complete", Toast.LENGTH_LONG).show();
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getActivity())
                                .setSmallIcon(R.mipmap.app_icon)
                                .setContentTitle("Document")
                                .setContentText("MYCpe");


                NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(455, mBuilder.build());


            }

        }
    };


    @Override
    public void onDetach() {
        super.onDetach();

        getActivity().unregisterReceiver(onComplete);
    }

    private void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            if (WebinarDetailsActivity.getInstance().arrayListhandout.size() > 0) {
                DownloadHandouts(WebinarDetailsActivity.getInstance().arrayListhandout);
            } else {
                Constant.toast(getActivity(), getResources().getString(R.string.str_download_link_not_found));
            }

        }

    }

    private void checkAndroidVersion_keyterms() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission_keyTerms();
        } else {
            if (WebinarDetailsActivity.getInstance().keyterms.equalsIgnoreCase("")) {
                DownloadKeyterms(WebinarDetailsActivity.getInstance().keyterms);
            } else {
                Constant.toast(getActivity(), getResources().getString(R.string.str_key_terms_link_not_found));
            }

        }

    }


    public void DownloadHandouts(ArrayList<String> arrayListhandout) {
        WebinarDetailsActivity.getInstance().list.clear();

        for (int i = 0; i < arrayListhandout.size(); i++) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(arrayListhandout.get(i)));
            String extension = arrayListhandout.get(i).substring(arrayListhandout.get(i).lastIndexOf('.') + 1).trim();
            request.setAllowedOverRoaming(false);
            request.setTitle("Downloading Handouts");
            request.setVisibleInDownloadsUi(true);

            Log.e("handout", "arrayListhandout" + arrayListhandout.get(i));
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/MyCpe/" + "/" + "Webinar_handouts" + i + "." + extension);

            WebinarDetailsActivity.getInstance().refid = downloadManager.enqueue(request);
        }

        WebinarDetailsActivity.getInstance().list.add(WebinarDetailsActivity.getInstance().refid);


    }

    public void DownloadKeyterms(String key_terms) {
        WebinarDetailsActivity.getInstance().list.clear();

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(key_terms));
        String extension = key_terms.substring(key_terms.lastIndexOf('.') + 1).trim();
        request.setAllowedOverRoaming(false);
        request.setTitle("Downloading Keyterms");
        request.setVisibleInDownloadsUi(true);

        Log.e("Keyterms", "Keyterms" + key_terms);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/MyCpe/" + "/" + "Webinar_handouts" + "." + extension);

        WebinarDetailsActivity.getInstance().refid = downloadManager.enqueue(request);

        WebinarDetailsActivity.getInstance().list.add(WebinarDetailsActivity.getInstance().refid);


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

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

            if (WebinarDetailsActivity.getInstance().arrayListhandout.size() > 0) {

                DownloadHandouts(WebinarDetailsActivity.getInstance().arrayListhandout);

            } else {
                Constant.toast(getActivity(), getResources().getString(R.string.str_download_link_not_found));
            }

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermission_keyTerms() {

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                requestPermissions(
                        new String[]{Manifest.permission
                                .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSIONS_MULTIPLE_REQUEST_KEYTERMS);
            } else {
                requestPermissions(
                        new String[]{Manifest.permission
                                .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSIONS_MULTIPLE_REQUEST_KEYTERMS);
            }
        } else {
            // write your logic code if permission already granted
            if (!WebinarDetailsActivity.getInstance().keyterms.equalsIgnoreCase("")) {
                DownloadKeyterms(WebinarDetailsActivity.getInstance().keyterms);
            } else {
                Constant.toast(getActivity(), getResources().getString(R.string.str_key_terms_link_not_found));
            }


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
                        if (WebinarDetailsActivity.getInstance().arrayListhandout.size() > 0) {

                            DownloadHandouts(WebinarDetailsActivity.getInstance().arrayListhandout);

                        } else {
                            Constant.toast(getActivity(), getResources().getString(R.string.str_download_link_not_found));
                        }
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
                            Intent intent = new Intent();
                            intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
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
            case PERMISSIONS_MULTIPLE_REQUEST_KEYTERMS:
                if (grantResults.length > 0) {
                    boolean writePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (writePermission && readExternalFile) {
                        // write your logic here
                        if (!WebinarDetailsActivity.getInstance().keyterms.equalsIgnoreCase("")) {
                            DownloadKeyterms(WebinarDetailsActivity.getInstance().keyterms);
                        } else {
                            Constant.toast(getActivity(), getResources().getString(R.string.str_key_terms_link_not_found));
                        }
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
                            Intent intent = new Intent();
                            intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        } else {
                            requestPermissions(
                                    new String[]{Manifest.permission
                                            .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    PERMISSIONS_MULTIPLE_REQUEST_KEYTERMS);
                        }
                    }
                }
                break;

        }
    }

    public String formatHoursAndMinutes(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        return (totalMinutes / 60) + ":" + minutes;
    }
}
