package com.myCPE.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.activity.SplashActivity;
import com.myCPE.utility.AppSettings;
import com.myCPE.utility.Constant;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;


/**
 * This will use for the receiving data from the notification
 */
public class ReceiveFirebaseMessagingService extends FirebaseMessagingService {

    Context context;
    private String CHANNEL_ID = "myCPE";
    private static final String TAG = ReceiveFirebaseMessagingService.class.getSimpleName();
    private String title, message, webinar_type = "";
    private int flag = 0, webinar_id = 0;

    public ReceiveFirebaseMessagingService() {
    }


    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        String deviceToken = token;
        AppSettings.set_device_token(ReceiveFirebaseMessagingService.this, deviceToken);
        Log.e("Refreshed token:", deviceToken);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        context = this;

        if (remoteMessage.getData() != null) {

            Log.e("remotemessage", "++++++" + remoteMessage.getData());

            if (remoteMessage.getData().size() > 0) {

                System.out.println("+++++ remoteMessage:" + remoteMessage.getData().get("flag"));
                System.out.println("+++++ remoteMessage:" + remoteMessage.getData().get("text"));
                System.out.println("+++++ remoteMessage:" + remoteMessage.getData().get("title"));
                System.out.println("+++++ remoteMessage:" + remoteMessage.getData().get("webinar_type"));
                System.out.println("+++++ remoteMessage:" + remoteMessage.getData().get("webinar_id"));

                flag = Integer.parseInt(remoteMessage.getData().get("flag"));
                message = remoteMessage.getData().get("text");
                title = remoteMessage.getData().get("title");
                webinar_type = remoteMessage.getData().get("webinar_type");
                webinar_id = Integer.parseInt(remoteMessage.getData().get("webinar_id"));


            }
            Constant.Log("type_and_id", "+++" + webinar_type + "  " + webinar_id);

            if (!AppSettings.get_login_token(ReceiveFirebaseMessagingService.this).isEmpty()) {
                Intent intent = new Intent(this, SplashActivity.class);
                intent.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                intent.putExtra(getResources().getString(R.string.pass_webinar_id), webinar_id);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                final int not_nu = generateRandom();

                PendingIntent pendingIntent = PendingIntent.getActivity(this, not_nu, intent, PendingIntent.FLAG_ONE_SHOT);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.notification_app_icon)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(title))
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                                R.mipmap.notification_app_icon))
                        .setContentTitle(title)
                        .setColor(getResources().getColor(R.color.webinar_status))
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)// Set the intent that will fire when the user taps the notification
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(not_nu, mBuilder.build());

                createNotificationChannel();
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                final int not_nu = generateRandom();

                PendingIntent pendingIntent = PendingIntent.getActivity(this, not_nu, intent, PendingIntent.FLAG_ONE_SHOT);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.notification_app_icon)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(title))
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                                R.mipmap.notification_app_icon))
                        .setContentTitle(title)
                        .setColor(getResources().getColor(R.color.webinar_status))
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)// Set the intent that will fire when the user taps the notification
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(not_nu, mBuilder.build());

                createNotificationChannel();
            }


        }


    }


    public int generateRandom() {
        Random random = new Random();
        return random.nextInt(9999 - 1000) + 1000;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}