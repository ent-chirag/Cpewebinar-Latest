package com.myCPE;

import android.app.Application;
import android.content.Context;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

//import com.crashlytics.android.Crashlytics;

//import io.fabric.sdk.android.Fabric;

public class MyCpe extends Application {

    private static MyCpe smycpe;

    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
        smycpe = this;
        FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();
        crashlytics.checkForUnsentReports();
    }

    public static MyCpe getMyCpe() {
        return smycpe;
    }

    public Context getContext() {
        return smycpe.getContext();
    }
}
