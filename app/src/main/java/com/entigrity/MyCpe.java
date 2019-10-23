package com.entigrity;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class MyCpe extends Application {

    private static MyCpe smycpe;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        smycpe = this;
    }

    public static MyCpe getMyCpe() {
        return smycpe;
    }

    public Context getContext() {
        return smycpe.getContext();
    }
}
