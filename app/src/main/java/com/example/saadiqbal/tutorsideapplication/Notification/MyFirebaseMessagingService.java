package com.example.saadiqbal.tutorsideapplication.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.saadiqbal.tutorsideapplication.MainScreen;
import com.example.saadiqbal.tutorsideapplication.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import org.json.JSONException;
import org.json.JSONObject;


public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = "FCM Service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.

        Log.d(TAG, "From: " + remoteMessage.getData().toString());
        try {
            JSONObject notificationObject = new JSONObject(remoteMessage.getData().toString());

         addNotification(notificationObject.getJSONObject("data"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

       // Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification());

    }

   private void addNotification(JSONObject notificationObject) throws JSONException {

       String phoneNo = notificationObject.getString("phoneNo");
       String requestID =notificationObject.getString("reqId");


        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("" + notificationObject.getString("title"))
                        .setContentText("" + notificationObject.getString("message"));
       Intent notificationIntent = new Intent(this, MainScreen.class);
       notificationIntent.putExtra("title",notificationObject.getString("title"));
       notificationIntent.putExtra("phoneNo",notificationObject.getString("phoneNo"));
       notificationIntent.putExtra("type",notificationObject.getString("type"));
       notificationIntent.putExtra("reqId",notificationObject.getString("reqId"));



       PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
               PendingIntent.FLAG_UPDATE_CURRENT);
    builder.setContentIntent(contentIntent);
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
//        builder.setColor(Integer.parseInt(remoteMessage.getNotification().getColor()));
        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

       //tutor phone no
       // request id
       //  ye values intent ma pass hong
       //

      /* String latitude = notificationObject.getString("latitude");
        String longitude = notificationObject.getString("longitude");
        String title = notificationObject.getString("title");
    *//*    String address = notificationObject.getString("address");
        String jobId = notificationObject.getString("jobId");
        String type = notificationObject.getString("type");
*//*

        Intent notificationIntent = new Intent(this, NavigationDrawer.class);
       *//* notificationIntent.putExtra("latitude", latitude);
        notificationIntent.putExtra("longitude", longitude);
        notificationIntent.putExtra("title", title);
        notificationIntent.putExtra("address", address);
        notificationIntent.putExtra("type", type);
        notificationIntent.putExtra("jobId", jobId);*//*
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(notificationIntent);
*/
       // Log.d(TAG, "Body" + latitude + "" + longitude + "" + title + "" + address + "" + type);


        /*updateMyActivity(getApplicationContext(),latitude,longitude,title,address,type,jobId);
*/

    }
/*
    static void updateMyActivity(Context context, String latitude, String longitude, String title, String address, String type, String jobId) {
        Intent notificationIntent = new Intent("com.google.android.c2dm.intent.RECEIVE");
        //put whatever data you want to send, if any
        notificationIntent.putExtra("latitude", latitude);
        notificationIntent.putExtra("longitude", longitude);
        notificationIntent.putExtra("title", title);
        notificationIntent.putExtra("address", address);
        notificationIntent.putExtra("type", type);
        notificationIntent.putExtra("jobId", jobId);
        //send broadcast
        context.sendBroadcast(notificationIntent);
    } */
}
