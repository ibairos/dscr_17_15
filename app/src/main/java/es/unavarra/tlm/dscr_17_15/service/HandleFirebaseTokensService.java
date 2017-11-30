package es.unavarra.tlm.dscr_17_15.service;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import es.unavarra.tlm.dscr_17_15.Objects.DatosEnviarFirebaseToken;
import es.unavarra.tlm.dscr_17_15.REST.ClasePeticionesRest;

/**
 * Created by ibai on 11/23/17.
 */

public class HandleFirebaseTokensService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();

        guardarFirebaseToken(token);
        (new ClasePeticionesRest()).enviarFirebaseToken(this, new DatosEnviarFirebaseToken(token));
    }

    public void guardarFirebaseToken(String token){

        SharedPreferences settings = getSharedPreferences("Config", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("firebase_token", token);

        editor.commit();

        Log.e("PUSH", "****TOKEN_GOOGLE****: "+token);

    }
}