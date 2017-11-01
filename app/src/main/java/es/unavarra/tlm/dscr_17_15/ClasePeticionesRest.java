package es.unavarra.tlm.dscr_17_15;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.*;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by dscr25 on 26/10/17.
 */

public class ClasePeticionesRest {

    public AsyncHttpClient client = new AsyncHttpClient();
    Context context;
    Activity activity;

    public ClasePeticionesRest(Activity activity){
        this.context = activity;
        this.activity = activity;
    }

    public void RegistrarUsuario(DatosRegistro datosRegistro){

        HashMap<String, String> mapa = crearMapadesdeObjeto(datosRegistro);

        RequestParams requestParams = new RequestParams(mapa);

        client.post(context, "https://api.messenger.tatai.es/v2/auth/register", requestParams, new RespuestaRegistro(context));

    }

    public class RespuestaRegistro extends AsyncHttpResponseHandler{

        Context context;

        public RespuestaRegistro(Context context){
            this.context = context;
        }
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Gson gson = new Gson();
            String datosRespuesta = gson.toJson(responseBody);
            DatosRespuestaRegistro datosRespuestaRegistro = gson.fromJson(datosRespuesta, DatosRespuestaRegistro.class);

            CharSequence texto = "Tu usuario es: " + datosRespuestaRegistro.getUser() + "\n " +
                    "y tu sesion es: " + datosRespuestaRegistro.getSession();
            Toast.makeText(context, texto, Toast.LENGTH_SHORT).show();

            guardarUsuarioYSesion(datosRespuestaRegistro.getSession(), datosRespuestaRegistro.getUser());

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            CharSequence texto = "ERROR: " + statusCode;
            Toast.makeText(context, texto, Toast.LENGTH_SHORT).show();
            for (int x = 0; x < headers.length; x++) {
                android.util.Log.e("HEADER " + x, headers[x] + "");
            }
        }
    }

    public void LoginUsuario(DatosLogin datosLogin){

        HashMap<String, String> mapa = crearMapadesdeObjeto(datosLogin);

        RequestParams requestParams = new RequestParams(mapa);

        client.post(context, "https://api.messenger.tatai.es/v2/auth/login", requestParams, new RespuestaLogin(context));

    }

    public class RespuestaLogin extends AsyncHttpResponseHandler{

        Context context;

        public RespuestaLogin(Context context){
            this.context = context;
        }
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Gson gson = new Gson();
            String datosRespuesta = gson.toJson(responseBody);
            DatosRespuestaRegistro datosRespuestaRegistro = gson.fromJson(datosRespuesta, DatosRespuestaRegistro.class);

            CharSequence texto = "Tu usuario es: " + datosRespuestaRegistro.getUser() + "\n " +
                    "y tu sesion es: " + datosRespuestaRegistro.getSession();
            Toast.makeText(context, texto, Toast.LENGTH_SHORT).show();

            guardarUsuarioYSesion(datosRespuestaRegistro.getSession(), datosRespuestaRegistro.getUser());

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            CharSequence texto = "ERROR: " + statusCode;
            Toast.makeText(context, texto, Toast.LENGTH_SHORT).show();
            for (int x = 0; x < headers.length; x++) {
                android.util.Log.e("HEADER " + x, headers[x] + "");
            }
        }
    }











    public HashMap crearMapadesdeObjeto(Object objeto){

        HashMap<String, String> mapa = new HashMap<>();
        Gson gson = new Gson();
        String objetoGSON = gson.toJson(objeto);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(objetoGSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<?> keys = jsonObject.keys();

        while (keys.hasNext()){
            String key = (String)keys.next();
            String value = null;
            try {
                value = jsonObject.getString(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mapa.put(key, value);
        }

        return mapa;
    }

    public void guardarUsuarioYSesion(String session, String user){

        SharedPreferences settings = context.getSharedPreferences("Config", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("session", session);
        editor.putString("user", user);
        editor.commit();

    }


}
