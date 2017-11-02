package es.unavarra.tlm.dscr_17_15;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
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

        Gson gson = new Gson();

        try {
            client.post(context, "https://api.messenger.tatai.es/v2/auth/register", new StringEntity(gson.toJson(datosRegistro)), "application/json", new RespuestaRegistro(context));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    public class RespuestaRegistro extends AsyncHttpResponseHandler{

        Context context;

        public RespuestaRegistro(Context context){
            this.context = context;
        }
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Gson gson = new Gson();
            DatosRespuestaRegistro datosRespuestaRegistro = gson.fromJson(new String(responseBody), DatosRespuestaRegistro.class);

            /*CharSequence texto = "Tu usuario es: " + datosRespuestaRegistro.getUser().toString() + "\n " +
                    "y tu sesion es: " + datosRespuestaRegistro.getSession().toString();
            Toast.makeText(context, texto, Toast.LENGTH_SHORT).show();*/

            guardarUsuarioYSesion(datosRespuestaRegistro);
            Intent intent = new Intent(context, UsuarioLogueado.class);
            context.startActivity(intent);
            activity.finish();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            CharSequence texto = "ERROR: " + statusCode;
            Toast.makeText(context, texto, Toast.LENGTH_SHORT).show();
            for (int x = 0; x < headers.length; x++) {
                android.util.Log.e("HEADER " + x, headers[x] + "");
            }
            android.util.Log.e("RESPONSE", new String(responseBody));
        }
    }

    public void LoginUsuario(DatosLogin datosLogin){

        Gson gson = new Gson();

        try {
            client.post(context, "https://api.messenger.tatai.es/v2/auth/login", new StringEntity(gson.toJson(datosLogin)), "application/json", new RespuestaLogin(context));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public class RespuestaLogin extends AsyncHttpResponseHandler{

        Context context;

        public RespuestaLogin(Context context){
            this.context = context;
        }
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Gson gson = new Gson();
            android.util.Log.e("JSON", new String(responseBody));
            DatosRespuestaRegistro datosRespuestaRegistro = gson.fromJson(new String(responseBody), DatosRespuestaRegistro.class);

            /*CharSequence texto = "Tu usuario es: " + datosRespuestaRegistro.getUser().toString() + "\n " +
                    "y tu sesion es: " + datosRespuestaRegistro.getSession().toString();
            Toast.makeText(context, texto, Toast.LENGTH_SHORT).show();*/

            guardarUsuarioYSesion(datosRespuestaRegistro);
            Intent intent = new Intent(context, UsuarioLogueado.class);
            context.startActivity(intent);
            activity.finish();

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

    public void guardarUsuarioYSesion(DatosRespuestaRegistro datosRespuestaRegistro){

        SharedPreferences settings = context.getSharedPreferences("Config", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("id", datosRespuestaRegistro.getUser().getId());
        editor.putString("name", datosRespuestaRegistro.getUser().getName());
        editor.putString("email", datosRespuestaRegistro.getUser().getEmail());
        editor.putString("token", datosRespuestaRegistro.getSession().getToken());
        editor.putString("valid_until", datosRespuestaRegistro.getSession().getValid_until().toString());
        editor.commit();

    }


}
