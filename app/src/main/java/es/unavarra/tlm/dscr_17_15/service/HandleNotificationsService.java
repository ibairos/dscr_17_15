package es.unavarra.tlm.dscr_17_15.service;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import es.unavarra.tlm.dscr_17_15.Objects.Chat;
import es.unavarra.tlm.dscr_17_15.Objects.PushMessage;
import es.unavarra.tlm.dscr_17_15.Pantallas.PantallaConversacion;
import es.unavarra.tlm.dscr_17_15.Pantallas.PantallaInicio;
import es.unavarra.tlm.dscr_17_15.R;
import es.unavarra.tlm.dscr_17_15.REST.ClasePeticionesRest;

/**
 * Created by ibai on 11/23/17.
 */

public class HandleNotificationsService extends FirebaseMessagingService {

    ClasePeticionesRest clasePeticionesRest = new ClasePeticionesRest();


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        
        Gson gson = new Gson();

        PushMessage mensaje = gson.fromJson(gson.toJson(remoteMessage.getData()), PushMessage.class);
        clasePeticionesRest.cogerInfoChat(this, mensaje);

    }

    public void runningServices(){
        ActivityManager am = (ActivityManager)this.getSystemService(Activity.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> rs = am.getRunningServices(10000);
        String message = null;

        ArrayList<ActivityManager.RunningServiceInfo> runningServiceInfo = new ArrayList<>();
        for (int i = 0; i < rs.size(); i++) {
            ActivityManager.RunningServiceInfo rsi = rs.get(i);
            //Log.i("Service", "Process " + rsi.process + " with component " + rsi.service.getClassName());
            if (rsi.process.equals(getPackageName())){
                runningServiceInfo.add(rsi);
            }
        }

        for (int x = 0; x < runningServiceInfo.size(); x++){
            Log.d("PUSH", "INFO: " + new Gson().toJson(runningServiceInfo.get(x)));
        }


    }

}
