package es.unavarra.tlm.dscr_17_15.service;

import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

/**
 * Created by ibai on 11/23/17.
 */

public class HandleNotificationsService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Toast.makeText(getApplicationContext(), (new Gson()).toJson(remoteMessage), Toast.LENGTH_LONG);
        startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));

    }
}
