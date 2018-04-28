package com.example.saadiqbal.tutorsideapplication.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.saadiqbal.tutorsideapplication.ChatActivity;
import com.example.saadiqbal.tutorsideapplication.MainHomeScreenTutor;
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

       /* String phoneNo = notificationObject.getString("phoneNo");
        String requestID = notificationObject.getString("reqId");*/


        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("" + notificationObject.getString("title"))
                        .setContentText("" + notificationObject.getString("message"));

        Log.d("Form 1",notificationObject.getString("type"));

        switch (notificationObject.getString("type")){
            case "Tution ":

                Intent notificationIntent = new Intent(this, MainScreen.class);
                notificationIntent.putExtra("title", notificationObject.getString("title"));
                notificationIntent.putExtra("phoneNo", notificationObject.getString("phoneNo"));
                notificationIntent.putExtra("type", notificationObject.getString("type"));
                notificationIntent.putExtra("reqId", notificationObject.getString("reqId"));
                notificationIntent.putExtra("latitude", notificationObject.getString("latitude"));
                notificationIntent.putExtra("longitude", notificationObject.getString("longitude"));


                PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(contentIntent);
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0, builder.build());


                break;
            case "FinishTution":
                Intent notificationIntents = new Intent(this, MainHomeScreenTutor.class);
                notificationIntents.putExtra("title", notificationObject.getString("title"));
                notificationIntents.putExtra("type", notificationObject.getString("type"));
                notificationIntents.putExtra("reqId", notificationObject.getString("reqId"));
                notificationIntents.putExtra("message", notificationObject.getString("message"));


                PendingIntent contentIntents = PendingIntent.getActivity(this, 1, notificationIntents,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(contentIntents);
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

                NotificationManager managers = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                managers.notify(1, builder.build());
                break;
            case "Message":
               /* Intent notificationIntentss = new Intent(this, ChatActivity.class);
                notificationIntentss.putExtra("title", notificationObject.getString("title"));
                notificationIntentss.putExtra("type", notificationObject.getString("type"));
                notificationIntentss.putExtra("chatId", notificationObject.getString("chatId"));
                notificationIntentss.putExtra("message", notificationObject.getString("message"));
                notificationIntentss.putExtra("tutname", notificationObject.getString("tutname"));
                notificationIntentss.putExtra("createdat", notificationObject.getString("createdat"));
                notificationIntentss.putExtra("phoneNo", notificationObject.getString("phoneNo"));
                PendingIntent contentIntentss = PendingIntent.getActivity(this, 2, notificationIntentss,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(contentIntentss);
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

                NotificationManager managerss = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                managerss.notify(2, builder.build());*/


                Intent notificationIntentss = new Intent(this, ChatActivity.class);
                notificationIntentss.putExtra("title", notificationObject.getString("title"));
                notificationIntentss.putExtra("type", notificationObject.getString("type"));
                notificationIntentss.putExtra("chatId", notificationObject.getString("chatId"));
                notificationIntentss.putExtra("message", notificationObject.getString("message"));
                notificationIntentss.putExtra("tutname", notificationObject.getString("tutname"));
                notificationIntentss.putExtra("createdat", notificationObject.getString("createdat"));
                notificationIntentss.putExtra("phoneNo", notificationObject.getString("phoneNo"));
                notificationIntentss.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                startActivity(notificationIntentss);

                break;

        }
    }
}
