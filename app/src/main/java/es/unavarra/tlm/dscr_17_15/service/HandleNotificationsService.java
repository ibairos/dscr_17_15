package es.unavarra.tlm.dscr_17_15.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import es.unavarra.tlm.dscr_17_15.Pantallas.PantallaInicio;
import es.unavarra.tlm.dscr_17_15.R;

/**
 * Created by ibai on 11/23/17.
 */

public class HandleNotificationsService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Toast.makeText(getApplicationContext(), (new Gson()).toJson(remoteMessage), Toast.LENGTH_LONG);
        Log.e("PUSH", "MENSAJE: "+(new Gson()).toJson(remoteMessage));
        //startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
        sendNotification(remoteMessage.getNotification().getBody());

    }


    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, PantallaInicio.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Firebase Push Notification")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }


}
