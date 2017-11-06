package es.unavarra.tlm.dscr_17_15;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
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
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import es.unavarra.tlm.dscr_17_15.Objects.Chat;
import es.unavarra.tlm.dscr_17_15.Objects.DatosEnviarMensaje;
import es.unavarra.tlm.dscr_17_15.Objects.DatosInvitarChat;
import es.unavarra.tlm.dscr_17_15.Objects.DatosLogin;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRegistro;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRespuestaEnviarMensaje;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRespuestaInvitarChat;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRespuestaListChats;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRespuestaListMensajes;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRespuestaRegistro;
import es.unavarra.tlm.dscr_17_15.Objects.Message;

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

    public void InvitarChat(DatosInvitarChat datosInvitarChat, List<Chat> myList, Activity activity){

        Gson gson = new Gson();
        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);

        try {
            client.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
            client.post(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/chat/invite", new StringEntity(gson.toJson(datosInvitarChat)), "application/json", new RespuestaInvitarChat(myList, activity));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public class RespuestaInvitarChat extends AsyncHttpResponseHandler{

        Activity activity;
        List<Chat> myList;

        public RespuestaInvitarChat(List<Chat> myList, Activity activity){
            this.activity = activity;
            this.myList = myList;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Gson gson = new Gson();
            android.util.Log.e("JSON", new String(responseBody));
            //DatosRespuestaInvitarChat datosRespuestaInvitarChat = gson.fromJson(new String(responseBody), DatosRespuestaInvitarChat.class);

            ListChats(activity);

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

    public void ListChats(Activity activity){

        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);

        client.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        client.get(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/chats", new RespuestaListChats(activity));

    }

    public class RespuestaListChats extends AsyncHttpResponseHandler{

        Activity activity;

        public RespuestaListChats(Activity activity){
            this.activity = activity;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Gson gson = new Gson();
            DatosRespuestaListChats datosRespuestaListChats = gson.fromJson(new String(responseBody), DatosRespuestaListChats.class);
            android.util.Log.e("JSON", gson.toJson(datosRespuestaListChats));

            dibujarChats(datosRespuestaListChats, activity);

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

    public void EnviarMensaje(Activity activity, DatosEnviarMensaje datosEnviarMensaje, Chat chat){

        Gson gson = new Gson();
        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);

        try {
            client.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
            client.post(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/chat/"+chat.getId()+"/message", new StringEntity(gson.toJson(datosEnviarMensaje)), "application/json", new RespuestaEnviarMensaje(activity, chat));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public class RespuestaEnviarMensaje extends AsyncHttpResponseHandler{

        Activity activity;
        Chat chat;

        public RespuestaEnviarMensaje(Activity activity, Chat chat){
            this.activity = activity;
            this.chat = chat;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Gson gson = new Gson();
            //android.util.Log.e("JSON", new String(responseBody));
            DatosRespuestaEnviarMensaje datosRespuestaEnviarMensaje = gson.fromJson(new String(responseBody), DatosRespuestaEnviarMensaje.class);

            ListMensajes(activity, chat);
            //actualizarMensajes(activity, datosRespuestaEnviarMensaje);

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            CharSequence texto = "ERRORR: " + statusCode;
            Log.e("RESPUESTA", new String(responseBody));
            Toast.makeText(activity.getApplicationContext(), texto, Toast.LENGTH_SHORT).show();
            for (int x = 0; x < headers.length; x++) {
                android.util.Log.e("HEADER " + x, headers[x] + "");
            }
        }
    }

    public void ListMensajes(Activity activity, Chat chat){

        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);

        client.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        client.get(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/chat/"+chat.getId()+"/messages", new RespuestaListMensajes(activity));

    }

    public class RespuestaListMensajes extends AsyncHttpResponseHandler{

        Activity activity;

        public RespuestaListMensajes(Activity activity){
            this.activity = activity;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Gson gson = new Gson();
            DatosRespuestaListMensajes datosRespuestaListMensajes = gson.fromJson(new String(responseBody), DatosRespuestaListMensajes.class);

            dibujarMensajes(activity, datosRespuestaListMensajes);

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

    public void dibujarChats(DatosRespuestaListChats datosRespuestaListChats, Activity activity){

        ListView listaChats = activity.findViewById(R.id.ListViewChats);

        List<Chat> myList = new ArrayList<>();
        for (int x = 0; x < datosRespuestaListChats.getChats().size(); x++){
            myList.add(datosRespuestaListChats.getChats().get(x));
            Log.d("CUENTA", x+"");
        }
        Log.d("CUENTA", "SIZE = "+myList.size());

        AdapterUsuarioLogueado adapterUsuarioLogueado = new AdapterUsuarioLogueado(activity.getApplicationContext(), myList);
        listaChats.setAdapter(adapterUsuarioLogueado);
        listaChats.setOnItemClickListener(new ChatListClickListener(myList, activity));
        adapterUsuarioLogueado.notifyDataSetChanged();


    }

    public void actualizarMensajes(Activity activity, DatosRespuestaEnviarMensaje datosRespuestaEnviarMensaje){

        ListView listaMensajes = activity.findViewById(R.id.ListViewConversacion);

        List<Message> myList = datosRespuestaEnviarMensaje.getMessages();

        AdapterMensajesConversacion adapterMensajesConversacion = new AdapterMensajesConversacion(activity.getApplicationContext(), myList);
        listaMensajes.setAdapter(adapterMensajesConversacion);
        //listaMensajes.setOnItemClickListener(new ChatListClickListener(myList, activity));
        adapterMensajesConversacion.notifyDataSetChanged();

    }

    public void dibujarMensajes(Activity activity, DatosRespuestaListMensajes datosRespuestaListMensajes){

        ListView listaMensajes = activity.findViewById(R.id.ListViewConversacion);

        List<Message> myList = datosRespuestaListMensajes.getMessages();

        AdapterMensajesConversacion adapterMensajesConversacion = new AdapterMensajesConversacion(activity.getApplicationContext(), myList);
        listaMensajes.setAdapter(adapterMensajesConversacion);
        //listaMensajes.setOnItemClickListener(new ChatListClickListener(myList, activity));
        adapterMensajesConversacion.notifyDataSetChanged();

    }


}
