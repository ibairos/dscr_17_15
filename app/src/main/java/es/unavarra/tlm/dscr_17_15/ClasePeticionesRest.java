package es.unavarra.tlm.dscr_17_15;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import es.unavarra.tlm.dscr_17_15.Objects.Chat;
import es.unavarra.tlm.dscr_17_15.Objects.DatosInvitarChat;
import es.unavarra.tlm.dscr_17_15.Objects.DatosLogin;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRegistro;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRespuestaInvitarChat;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRespuestaRegistro;

/**
 * Created by dscr25 on 26/10/17.
 */

public class ClasePeticionesRest {

    public AsyncHttpClient client = new AsyncHttpClient();

    public void RegistrarUsuario(DatosRegistro datosRegistro, Activity activity){

        Gson gson = new Gson();

        try {
            client.post(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/auth/register", new StringEntity(gson.toJson(datosRegistro)), "application/json", new RespuestaRegistro(activity));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    public class RespuestaRegistro extends AsyncHttpResponseHandler{

        Activity activity;

        public RespuestaRegistro(Activity activity){
            this.activity = activity;
        }
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Gson gson = new Gson();
            DatosRespuestaRegistro datosRespuestaRegistro = gson.fromJson(new String(responseBody), DatosRespuestaRegistro.class);

            guardarUsuarioYSesion(datosRespuestaRegistro, activity.getApplicationContext());
            Intent intent = new Intent(activity.getApplicationContext(), UsuarioLogueado.class);
            activity.getApplicationContext().startActivity(intent);
            activity.finish();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            CharSequence texto = "ERROR: " + statusCode;
            Toast.makeText(activity.getApplicationContext(), texto, Toast.LENGTH_SHORT).show();
            for (int x = 0; x < headers.length; x++) {
                android.util.Log.e("HEADER " + x, headers[x] + "");
            }
            android.util.Log.e("RESPONSE", new String(responseBody));
        }
    }

    public void LoginUsuario(DatosLogin datosLogin, Activity activity){

        Gson gson = new Gson();

        try {
            client.post(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/auth/login", new StringEntity(gson.toJson(datosLogin)), "application/json", new RespuestaLogin(activity));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public class RespuestaLogin extends AsyncHttpResponseHandler{

        Activity activity;

        public RespuestaLogin(Activity activity){
            this.activity = activity;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Gson gson = new Gson();
            android.util.Log.e("JSON", new String(responseBody));
            DatosRespuestaRegistro datosRespuestaRegistro = gson.fromJson(new String(responseBody), DatosRespuestaRegistro.class);

            guardarUsuarioYSesion(datosRespuestaRegistro, activity.getApplicationContext());
            Intent intent = new Intent(activity.getApplicationContext(), UsuarioLogueado.class);
            activity.getApplicationContext().startActivity(intent);
            activity.finish();

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            CharSequence texto = "ERROR: " + statusCode;
            Toast.makeText(activity.getApplicationContext(), texto, Toast.LENGTH_SHORT).show();
            for (int x = 0; x < headers.length; x++) {
                android.util.Log.e("HEADER " + x, headers[x] + "");
            }
        }
    }

    public void InvitarChat(DatosInvitarChat datosInvitarChat, ArrayList<InfoChat> myList, Activity activity){

        Gson gson = new Gson();

        try {
            client.post(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/chat/invite", new StringEntity(gson.toJson(datosInvitarChat)), "application/json", new RespuestaInvitarChat(myList, activity));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public class RespuestaInvitarChat extends AsyncHttpResponseHandler{

        Activity activity;
        ArrayList<InfoChat> myList;

        public RespuestaInvitarChat(ArrayList<InfoChat> myList, Activity activity){
            this.activity = activity;
            this.myList = myList;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Gson gson = new Gson();
            android.util.Log.e("JSON", new String(responseBody));
            DatosRespuestaInvitarChat datosRespuestaInvitarChat = gson.fromJson(new String(responseBody), DatosRespuestaInvitarChat.class);

            dibujarChat(datosRespuestaInvitarChat.getChat(), myList, activity);

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            CharSequence texto = "ERROR: " + statusCode;
            Toast.makeText(activity.getApplicationContext(), texto, Toast.LENGTH_SHORT).show();
            for (int x = 0; x < headers.length; x++) {
                android.util.Log.e("HEADER " + x, headers[x] + "");
            }
        }
    }

    public void guardarUsuarioYSesion(DatosRespuestaRegistro datosRespuestaRegistro, Context context){

        SharedPreferences settings = context.getSharedPreferences("Config", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("id", datosRespuestaRegistro.getUser().getId());
        editor.putString("name", datosRespuestaRegistro.getUser().getName());
        editor.putString("email", datosRespuestaRegistro.getUser().getEmail());
        editor.putString("token", datosRespuestaRegistro.getSession().getToken());
        editor.putString("valid_until", datosRespuestaRegistro.getSession().getValid_until().toString());
        editor.commit();

    }

    public void dibujarChat(Chat chat, ArrayList<InfoChat> myList, Activity activity){

        ListView listaChats = activity.findViewById(R.id.ListViewChats);
        myList.add(new InfoChat(chat.getUsers()[0].getEmail(), chat.getCreated_at().toString()));

        AdapterUsuarioLogueado adapterUsuarioLogueado = new AdapterUsuarioLogueado(activity.getApplicationContext(), myList);
        listaChats.setAdapter(adapterUsuarioLogueado);
        adapterUsuarioLogueado.notifyDataSetChanged();


    }


}
