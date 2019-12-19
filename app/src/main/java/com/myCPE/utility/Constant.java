package com.myCPE.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.adapter.rxjava.HttpException;


public class Constant {
    public static boolean developer_mode = true;
    public static String device_type = "A";  //1 for android
    public static String failure_message = "";
    public static int status_code = 0;
    public static ArrayList<Integer> arraylistselectedvalue = new ArrayList<Integer>();

    public static String watchedDuration = "";



    public static List<Boolean> arrsavebooleanstate = new ArrayList();
    public  static List<String> arraysavefilter = new ArrayList<String>();
    public static  List<String> arraypricefilter = new ArrayList<String>();
    //for date home filter
    public static ArrayList<String> arraylistHomeDateFilter = new ArrayList<>();





    //Global Search


    public static String webinartype = "live";
    public static String SubjectAreaFilter = "";
    public static String search = "";
    public static String price_filter = "";
    public static String date_filter = "";
    public static int is_cpd = 0;
    public static boolean isCpdSelected = false;
    public static boolean isWebinarCPD = false;
    public static String cpdCredit = "";


    //for sign up proffesional credential
    public static ArrayList<Integer> arraylistselectedproffesionalcredentialID = new ArrayList<>();
    public static ArrayList<String> arraylistselectedproffesionalcredential = new ArrayList<String>();
    public static HashMap<String, Boolean> hashmap_professional_credential = new HashMap<String, Boolean>();


    //for home filter subject area
    public static ArrayList<Integer> arraylistselectedsubjectareahomeID = new ArrayList<>();
    public static HashMap<String, Boolean> hashmap_subject_home_area = new HashMap<String, Boolean>();


    //for sign up additional qualification
    public static ArrayList<Integer> arraylistselectedadditionalqualificationID = new ArrayList<>();
    public static ArrayList<String> arraylistselectedadditionalqualification = new ArrayList<String>();
    public static HashMap<String, Boolean> hashmap_additional_qualification = new HashMap<String, Boolean>();


    public static ArrayList<Integer> arraylistselectedtopicsofinterest = new ArrayList<Integer>();
    public static boolean checkmywebinardotstatusset = false;
    public static boolean isdataupdate = false;
    public static ArrayList<String> arraylistselectedreviewanswerreview = new ArrayList<String>();
    public static ArrayList<Integer> arraylistselectedquestionreview = new ArrayList<Integer>();
    public static HashMap<String, Boolean> hashmap_asnwer_review_question = new HashMap<String, Boolean>();
    public static HashMap<String, String> hashmap_asnwer_string_review_question = new HashMap<String, String>();
    public static HashMap<String, String> hashmap_answer_string_final_question = new HashMap<String, String>();

    public static boolean isClickedSubmit = false;
    public static boolean isAllAnswerTrue = false;

    public static String is_from = "";


    public static ArrayList<String> arraylistselectedanswerfinal = new ArrayList<String>();
    public static ArrayList<Integer> arraylistselectedquestionfinal = new ArrayList<Integer>();


    public static boolean isNetworkAvailable(Context con) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) con
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission") NetworkInfo activeNetworkInfo = connectivityManager
                    .getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<String> listOfPattern() {
        ArrayList<String> listOfPattern = new ArrayList<String>();

        String ptVisa = "^4[0-9]$";

        listOfPattern.add(ptVisa);

        String ptMasterCard = "^5[1-5]$";

        listOfPattern.add(ptMasterCard);

        String ptDiscover = "^6(?:011|5[0-9]{2})$";

        listOfPattern.add(ptDiscover);

        String ptAmeExp = "^3[47]$";

        listOfPattern.add(ptAmeExp);

        return listOfPattern;
    }


    public static boolean isValidEmailId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public static boolean isValidCanadianZipCode (String zipcode) {

        return  Pattern.compile("^(?!.*[DFIOQUdfioqu])[A-VXYa-vxy][0-9][A-Za-z] ?[0-9][A-Za-z][0-9]$")
//        return  Pattern.compile("^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] [ ][0-9][A-Z][0-9]$")
                .matcher(zipcode).matches();
    }


    public static void setLightStatusBar(Activity activity) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = activity.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;   // add LIGHT_STATUS_BAR to flag
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(Color.LTGRAY); // optional
        }

    }


    public static boolean isValidPassword(String password) {
        return Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$").matcher(password).matches();

    }


    public static String GetReturnResponse(Context context, Throwable e) {


        try {
            HttpException error = (HttpException) e;

            Constant.Log("status code", "+++++" + error.response().code());

            status_code = error.response().code();

            try {
                JSONObject jsonObject = new JSONObject(error.response().errorBody().string());

                Constant.Log("object", "+++++" + jsonObject);

                //   Constant.Log("error", "+++++" + error.response().errorBody().string());

                String success = jsonObject.getString("success");
                failure_message = jsonObject.getString("message");


            } catch (JSONException e2) {
                e2.printStackTrace();
            }


        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return failure_message;


    }


    public static String Trim(String inputvalue) {

        String inputreturn = inputvalue.trim();
        return inputreturn;

    }


    public static String GetDeviceid(Context context) {
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id;
    }


    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public static void Log(String text, String text2) {
        if (developer_mode == true) {
            try {
                Log.e(text, text2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void ShowPopUp(String message, Context context) {

        final AlertDialog alertDialog = new AlertDialog.Builder(
                context).create();

        alertDialog.setMessage(message);
        // Setting OK Button

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                alertDialog.dismiss();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static String capitalize(String capString) {
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }


}
