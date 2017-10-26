package es.unavarra.tlm.dscr_17_15;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by dscr25 on 26/10/17.
 */

public class ClasePeticionesRest {

    public AsyncHttpClient client = new AsyncHttpClient();
    Context context;

    public ClasePeticionesRest(Context context){
        this.context = context;
    }

    public void comprobarUsuarioRegistro(String usuario){
        //client.post(context, "http://api.messenger.tatai.es/v2/welcome")
        //client.post(this,​ ​ "http://api.messenger.tatai.es/welcome",​ ​ new StringEntity("{\"name\":\"Fran\"}"),​ ​ "application/json",​ ​ new WelcomeResponseHandler(this));
    }


}
