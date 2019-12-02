package com.myCPE.fcm;

import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * This will use for generating the device token while app will open
 */
public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    public FirebaseInstanceIDService() {

    }


    @Override
    public void onTokenRefresh() {


    }
}