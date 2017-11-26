package es.unavarra.tlm.dscr_17_15.service;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by ibai on 11/23/17.
 */

public class HandleFirebaseTokensService extends FirebaseInstanceIdService {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("etiqueta", "CREADO FIIS");
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();

        //sendRegistrationToServer(token);
        guardarFirebaseToken(token);
    }

    public void guardarFirebaseToken(String token){

        SharedPreferences settings = getSharedPreferences("Config", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("firebase_token", token);

        editor.commit();

        Log.e("etiqueta", "TOKEN_GOOGLE: "+token);

    }
}