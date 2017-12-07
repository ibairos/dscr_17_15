package es.unavarra.tlm.dscr_17_15.REST;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import es.unavarra.tlm.dscr_17_15.Adapters.AdapterMensajesConversacion;
import es.unavarra.tlm.dscr_17_15.Adapters.AdapterChatList;
import es.unavarra.tlm.dscr_17_15.EventListeners.ChatListClickListener;
import es.unavarra.tlm.dscr_17_15.Objects.Chat;
import es.unavarra.tlm.dscr_17_15.Objects.DaoMaster;
import es.unavarra.tlm.dscr_17_15.Objects.DaoSession;
import es.unavarra.tlm.dscr_17_15.Objects.DatosCambiarNombre;
import es.unavarra.tlm.dscr_17_15.Objects.DatosCambiarPassword;
import es.unavarra.tlm.dscr_17_15.Objects.DatosEnviarFirebaseToken;
import es.unavarra.tlm.dscr_17_15.Objects.DatosEnviarMensaje;
import es.unavarra.tlm.dscr_17_15.Objects.DatosInvitarChat;
import es.unavarra.tlm.dscr_17_15.Objects.DatosLogin;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRegistro;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRespuestaCambiarNombre;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRespuestaCogerUsuario;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRespuestaEnviarMensaje;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRespuestaListChats;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRespuestaListMensajes;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRespuestaRegistro;
import es.unavarra.tlm.dscr_17_15.Objects.Error;
import es.unavarra.tlm.dscr_17_15.Objects.InformacionListChat;
import es.unavarra.tlm.dscr_17_15.Objects.Message;
import es.unavarra.tlm.dscr_17_15.Objects.PushMessage;
import es.unavarra.tlm.dscr_17_15.Objects.SeenMessages;
import es.unavarra.tlm.dscr_17_15.Objects.SeenMessagesDao;
import es.unavarra.tlm.dscr_17_15.Objects.User;
import es.unavarra.tlm.dscr_17_15.Pantallas.PantallaConversacion;
import es.unavarra.tlm.dscr_17_15.Pantallas.PantallaInicio;
import es.unavarra.tlm.dscr_17_15.Pantallas.PantallaMiPerfil;
import es.unavarra.tlm.dscr_17_15.Pantallas.PantallaOtroPerfil;
import es.unavarra.tlm.dscr_17_15.Pantallas.PantallaUsuarioLogueado;
import es.unavarra.tlm.dscr_17_15.R;

/**
 * Created by dscr25 on 26/10/17.
 */

public class ClasePeticionesRest {

    public AsyncHttpClient asyncClient = new AsyncHttpClient();
    public SyncHttpClient syncClient = new SyncHttpClient();


    public void RegistrarUsuario(DatosRegistro datosRegistro, Activity activity){

        Gson gson = new Gson();

        try {
            asyncClient.post(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/auth/register", new StringEntity(gson.toJson(datosRegistro)), "application/json", new RespuestaRegistro(activity));
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
            Intent intent = new Intent(activity.getApplicationContext(), PantallaUsuarioLogueado.class);
            activity.getApplicationContext().startActivity(intent);
            activity.finish();

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Gson gson = new Gson();
            Error mensajeError = gson.fromJson(new String(responseBody), Error.class);

            toastCorto(activity, mensajeError.getMessage());
        }
    }

    public void LoginUsuario(DatosLogin datosLogin, Activity activity){

        Gson gson = new Gson();

        try {
            asyncClient.post(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/auth/login", new StringEntity(gson.toJson(datosLogin)), "application/json", new RespuestaLogin(activity));
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
            DatosRespuestaRegistro datosRespuestaRegistro = gson.fromJson(new String(responseBody), DatosRespuestaRegistro.class);

            guardarUsuarioYSesion(datosRespuestaRegistro, activity.getApplicationContext());
            Intent intent = new Intent(activity.getApplicationContext(), PantallaUsuarioLogueado.class);
            activity.getApplicationContext().startActivity(intent);
            activity.finish();

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Gson gson = new Gson();
            Error mensajeError = gson.fromJson(new String(responseBody), Error.class);

            toastCorto(activity, mensajeError.getMessage());
        }
    }

    public void InvitarChat(DatosInvitarChat datosInvitarChat, Activity activity){

        Gson gson = new Gson();
        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);

        asyncClient.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        try {
            asyncClient.post(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/chat/invite", new StringEntity(gson.toJson(datosInvitarChat)), "application/json", new RespuestaInvitarChat(activity));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public class RespuestaInvitarChat extends AsyncHttpResponseHandler{

        Activity activity;

        public RespuestaInvitarChat(Activity activity){
            this.activity = activity;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            ListChats(activity);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            if (statusCode == 403){
                toastLargo(activity, "Sesion expirada");
                CerrarSesion(activity);
            }
            Gson gson = new Gson();
            Error mensajeError = gson.fromJson(new String(responseBody), Error.class);

            toastCorto(activity, mensajeError.getMessage());
        }
    }

    public void ListChats(Activity activity){

        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);

        asyncClient.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        asyncClient.get(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/chats", new RespuestaListChats(activity));

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

            PantallaUsuarioLogueado.myList.clear();
            cogerUltimosMensajes(activity, datosRespuestaListChats);

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            if (statusCode == 403){
                toastLargo(activity, "Sesion expirada");
                CerrarSesion(activity);
            }
            Gson gson = new Gson();
            Error mensajeError = gson.fromJson(new String(responseBody), Error.class);

            toastCorto(activity, mensajeError.getMessage());
        }
    }

    public void ListChatsSync(Activity activity){

        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);

        syncClient.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        syncClient.get(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/chats", new RespuestaListChatsSync(activity));

    }

    public class RespuestaListChatsSync extends AsyncHttpResponseHandler{

        Activity activity;

        public RespuestaListChatsSync(Activity activity){
            this.activity = activity;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Gson gson = new Gson();
            DatosRespuestaListChats datosRespuestaListChats = gson.fromJson(new String(responseBody), DatosRespuestaListChats.class);

            PantallaUsuarioLogueado.myList.clear();
            cogerUltimosMensajesSync(activity, datosRespuestaListChats);

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            if (statusCode == 403){
                toastLargo(activity, "Sesion expirada");
                CerrarSesion(activity);
            }
            Gson gson = new Gson();
            Error mensajeError = gson.fromJson(new String(responseBody), Error.class);

            toastCorto(activity, mensajeError.getMessage());
        }
    }

    public void EnviarMensaje(Activity activity, DatosEnviarMensaje datosEnviarMensaje, Chat chat){

        Gson gson = new Gson();
        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);

        asyncClient.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        asyncClient.post(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/chat/"+chat.getId()+"/message", new StringEntity(gson.toJson(datosEnviarMensaje), "UTF-8"), "application/json", new RespuestaEnviarMensaje(activity, chat));

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
            DatosRespuestaEnviarMensaje datosRespuestaEnviarMensaje = gson.fromJson(new String(responseBody), DatosRespuestaEnviarMensaje.class);

            //dibujarMensajes(activity, new DatosRespuestaListMensajes(datosRespuestaEnviarMensaje.getMessages().size(), datosRespuestaEnviarMensaje.getMessages()), chat.getId());
            ListMensajes(activity, chat);

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            if (statusCode == 403){
                toastLargo(activity, "Sesion expirada");
                CerrarSesion(activity);
            }
            Gson gson = new Gson();
            Error mensajeError = gson.fromJson(new String(responseBody), Error.class);

            toastCorto(activity, mensajeError.getMessage());
        }
    }

    public void ListMensajes(Activity activity, Chat chat){

        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);

        asyncClient.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        asyncClient.get(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/chat/"+chat.getId()+"/messages", new RespuestaListMensajes(activity, chat.getId()));

    }

    public void ListMensajesSync(Activity activity, Chat chat){

        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);

        syncClient.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        syncClient.get(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/chat/"+chat.getId()+"/messages", new RespuestaListMensajes(activity, chat.getId()));

    }

    public class RespuestaListMensajes extends AsyncHttpResponseHandler{

        Activity activity;
        int chatId;

        public RespuestaListMensajes(Activity activity, int chatId){
            this.activity = activity;
            this.chatId = chatId;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Gson gson = new Gson();
            final DatosRespuestaListMensajes datosRespuestaListMensajes = gson.fromJson(new String(responseBody), DatosRespuestaListMensajes.class);

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dibujarMensajes(activity, datosRespuestaListMensajes, chatId);
                }
            });

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            if (statusCode == 403){
                toastLargo(activity, "Sesion expirada");
                CerrarSesion(activity);
            }
            Gson gson = new Gson();
            Error mensajeError = gson.fromJson(new String(responseBody), Error.class);

            toastCorto(activity, mensajeError.getMessage());
        }
    }

    public void BorrarConversacion(Activity activity, Chat chat){

        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);

        asyncClient.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        asyncClient.get(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/chat/"+chat.getId()+"/exit", new RespuestaBorrarConversacion(activity));

    }

    public class RespuestaBorrarConversacion extends AsyncHttpResponseHandler{

        Activity activity;

        public RespuestaBorrarConversacion(Activity activity){
            this.activity = activity;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            String response = new String(responseBody);

            if (response.equals("[]")){
                ListChats(activity);
            }else{
                toastCorto(activity, "Error al borrar la conversación");
            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            if (statusCode == 403){
                toastLargo(activity, "Sesion expirada");
                CerrarSesion(activity);
            }
            Gson gson = new Gson();
            Error mensajeError = gson.fromJson(new String(responseBody), Error.class);

            toastCorto(activity, mensajeError.getMessage());
        }
    }

    public void CerrarSesion(Activity activity){

        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);

        asyncClient.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        asyncClient.get(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/auth/logout", new RespuestaCerrarSesion(activity));

    }

    public class RespuestaCerrarSesion extends AsyncHttpResponseHandler{

        Activity activity;

        public RespuestaCerrarSesion(Activity activity){
            this.activity = activity;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            String response = new String(responseBody);

            if (response.equals("[]")){
                borrarSharedPreferences(activity.getApplicationContext());
                Intent intent = new Intent(activity, PantallaInicio.class);
                activity.startActivity(intent);
                activity.finish();
            }else{
                toastCorto(activity, "Error al cerrar la sesión");
            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            if (statusCode == 403){
                toastLargo(activity, "Sesion expirada");
                CerrarSesion(activity);
            }
            Gson gson = new Gson();
            Error mensajeError = gson.fromJson(new String(responseBody), Error.class);

            toastCorto(activity, mensajeError.getMessage());
        }
    }

    public void cogerUltimosMensajes(Activity activity, DatosRespuestaListChats datosRespuestaListChats){

        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);
        String token = settings.getString("token", "");

        for (int x = 0; x < datosRespuestaListChats.getCount(); x++) {
            asyncClient.addHeader("X-AUTH-TOKEN", token);
            asyncClient.get(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/chat/" + datosRespuestaListChats.getChats().get(x).getId() + "/messages", new RespuestaCogerUltimosMensajes(activity, datosRespuestaListChats.getChats().get(x)));
        }

        //dibujarChats(datosRespuestaListChats, activity);
    }

    public void cogerUltimosMensajesSync(Activity activity, DatosRespuestaListChats datosRespuestaListChats){

        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);
        String token = settings.getString("token", "");

        for (int x = 0; x < datosRespuestaListChats.getCount(); x++) {
            syncClient.addHeader("X-AUTH-TOKEN", token);
            syncClient.get(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/chat/" + datosRespuestaListChats.getChats().get(x).getId() + "/messages", new RespuestaCogerUltimosMensajes(activity, datosRespuestaListChats.getChats().get(x)));
        }

    }

    public class RespuestaCogerUltimosMensajes extends AsyncHttpResponseHandler{

        Activity activity;
        Chat chat;

        public RespuestaCogerUltimosMensajes(Activity activity, Chat chat){
            this.activity = activity;
            this.chat = chat;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Gson gson = new Gson();
            DatosRespuestaListMensajes datosRespuestaListMensajes = gson.fromJson(new String(responseBody), DatosRespuestaListMensajes.class);

            final Message ultimoMensaje;
            if (datosRespuestaListMensajes.getMessages().size() != 0){
                ultimoMensaje = datosRespuestaListMensajes.getMessages().get(datosRespuestaListMensajes.getMessages().size()-1);
            }else{
                ultimoMensaje = null;
            }

            final int unreadMessages = datosRespuestaListMensajes.getCount() - numeroMensajesVistos(chat.getId(), activity);

            activity.runOnUiThread(new Runnable() {
                @Override public void run() {
                    dibujarChat(activity, chat, ultimoMensaje, unreadMessages);
                }
            });

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            if (statusCode == 403){
                toastLargo(activity, "Sesion expirada");
                CerrarSesion(activity);
            }
            Gson gson = new Gson();
            Error mensajeError = gson.fromJson(new String(responseBody), Error.class);

            toastCorto(activity, mensajeError.getMessage());
        }
    }

    public void cogerUsuario(Activity activity){
        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);

        asyncClient.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        asyncClient.get(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/profile/me", new RespuestaCogerUsuario(activity));

    }

    public class RespuestaCogerUsuario extends AsyncHttpResponseHandler{

        Activity activity;

        public RespuestaCogerUsuario(Activity activity){
            this.activity = activity;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Gson gson = new Gson();
            DatosRespuestaCogerUsuario datosRespuestaCogerUsuario = gson.fromJson(new String(responseBody), DatosRespuestaCogerUsuario.class);

            Intent intent = new Intent(activity, PantallaMiPerfil.class);
            intent.putExtra("User", gson.toJson(datosRespuestaCogerUsuario.getUser()));
            activity.startActivity(intent);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            if (statusCode == 403){
                toastLargo(activity, "Sesion expirada");
                CerrarSesion(activity);
            }
            Gson gson = new Gson();
            Error mensajeError = gson.fromJson(new String(responseBody), Error.class);

            toastCorto(activity, mensajeError.getMessage());
        }
    }

    public void cogerOtroUsuario(Activity activity, User user){
        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);

        asyncClient.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        asyncClient.get(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/profile/"+user.getId(), new RespuestaCogerOtroUsuario(activity));

    }

    public class RespuestaCogerOtroUsuario extends AsyncHttpResponseHandler{

        Activity activity;

        public RespuestaCogerOtroUsuario(Activity activity){
            this.activity = activity;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Gson gson = new Gson();
            User user = gson.fromJson(new String(responseBody), User.class);

            Intent intent = new Intent(activity, PantallaOtroPerfil.class);
            intent.putExtra("User", gson.toJson(user));
            activity.startActivity(intent);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            if (statusCode == 403){
                toastLargo(activity, "Sesion expirada");
                CerrarSesion(activity);
            }
            Gson gson = new Gson();
            Error mensajeError = gson.fromJson(new String(responseBody), Error.class);

            toastCorto(activity, mensajeError.getMessage());
        }
    }

    public void cambiarNombre(Activity activity, DatosCambiarNombre datosCambiarNombre){

        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);
        Gson gson = new Gson();

        asyncClient.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        try {
            asyncClient.post(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/profile/me", new StringEntity(gson.toJson(datosCambiarNombre)), "application/json", new RespuestaCambiarNombre(activity));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public class RespuestaCambiarNombre extends AsyncHttpResponseHandler{

        Activity activity;

        public RespuestaCambiarNombre(Activity activity){
            this.activity = activity;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Gson gson = new Gson();
            DatosRespuestaCambiarNombre datosRespuestaCambiarNombre = gson.fromJson(new String(responseBody), DatosRespuestaCambiarNombre.class);

            actualizarPerfil(activity, datosRespuestaCambiarNombre.getUser());

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            if (statusCode == 403){
                toastLargo(activity, "Sesion expirada");
                CerrarSesion(activity);
            }
            Gson gson = new Gson();
            Error mensajeError = gson.fromJson(new String(responseBody), Error.class);

            toastCorto(activity, mensajeError.getMessage());
        }
    }

    public void eliminarUsuario(Activity activity){
        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);

        asyncClient.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        asyncClient.delete(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/profile", new RespuestaEliminarUsuario(activity));
    }

    public class RespuestaEliminarUsuario extends AsyncHttpResponseHandler{

        Activity activity;

        public RespuestaEliminarUsuario(Activity activity){
            this.activity = activity;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            borrarSharedPreferences(activity);
            Intent intent = new Intent(activity, PantallaInicio.class);
            activity.startActivity(intent);
            activity.finish();

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            if (statusCode == 403){
                toastLargo(activity, "Sesion expirada");
                CerrarSesion(activity);
            }
            Gson gson = new Gson();
            Error mensajeError = gson.fromJson(new String(responseBody), Error.class);

            toastCorto(activity, mensajeError.getMessage());
        }
    }

    public void cambiarPassword(Activity activity, DatosCambiarPassword datosCambiarPassword){

        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);
        Gson gson = new Gson();

        asyncClient.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        try {
            asyncClient.post(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/profile/me/password", new StringEntity(gson.toJson(datosCambiarPassword)), "application/json", new RespuestaCambiarPassword(activity));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public class RespuestaCambiarPassword extends AsyncHttpResponseHandler{

        Activity activity;

        public RespuestaCambiarPassword(Activity activity){
            this.activity = activity;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Gson gson = new Gson();
            DatosRespuestaRegistro datosRespuestaRegistro = gson.fromJson(new String(responseBody), DatosRespuestaRegistro.class);

            guardarUsuarioYSesion(datosRespuestaRegistro, activity);
            activity.finish();

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            if (statusCode == 403){
                toastLargo(activity, "Sesion expirada");
                CerrarSesion(activity);
            }
            Gson gson = new Gson();
            Error mensajeError = gson.fromJson(new String(responseBody), Error.class);

            toastCorto(activity, mensajeError.getMessage());
        }
    }

    public void enviarFirebaseToken(Context context, DatosEnviarFirebaseToken datosEnviarFirebaseToken){

        SharedPreferences settings = context.getSharedPreferences("Config", 0);
        Gson gson = new Gson();
        String token = settings.getString("token", "");

        if (!token.equals("")) {
            syncClient.addHeader("X-AUTH-TOKEN", token);
            try {
                syncClient.post(context, "https://api.messenger.tatai.es/v2/notification/token", new StringEntity(gson.toJson(datosEnviarFirebaseToken)), "application/json", new RespuestaEnviarFirebaseToken(context));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }

    public class RespuestaEnviarFirebaseToken extends AsyncHttpResponseHandler{

        Context context;

        public RespuestaEnviarFirebaseToken(Context context){
            this.context = context;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            //toastLargo(context, "TOKEN REFRESHED");
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            if (statusCode == 403){
                toastLargo(context, "Sesion expirada");
                borrarSharedPreferences(context);
                //CerrarSesion(context);
            }
            Gson gson = new Gson();
            Error mensajeError = gson.fromJson(new String(responseBody), Error.class);

            toastCorto(context, mensajeError.getMessage());
        }
    }

    public void cogerInfoChat(Service service, PushMessage pushMessage){

        SharedPreferences settings = service.getApplicationContext().getSharedPreferences("Config", 0);
        String token = settings.getString("token", "");

        if (!(token.equals("") || tokenExpirado(service))) {
            syncClient.addHeader("X-AUTH-TOKEN", token);
            syncClient.get(service, "https://api.messenger.tatai.es/v2/chats", new RespuestaCogerInfoChat(service, pushMessage));
        }

    }

    public class RespuestaCogerInfoChat extends AsyncHttpResponseHandler{

        Service service;
        PushMessage pushMessage;

        public RespuestaCogerInfoChat(Service service, PushMessage pushMessage){
            this.service = service;
            this.pushMessage = pushMessage;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Gson gson = new Gson();
            DatosRespuestaListChats datosRespuestaListChats = gson.fromJson(new String(responseBody), DatosRespuestaListChats.class);
            Chat chat = null;
            for (int x = 0; x < datosRespuestaListChats.getChats().size(); x++){
                if (datosRespuestaListChats.getChats().get(x).getId() == Integer.parseInt(pushMessage.getChat())){
                    chat = datosRespuestaListChats.getChats().get(x);
                    break;
                }
            }

            if (PantallaInicio.appIsInForeground) {
                if (PantallaInicio.nameOfActivityInForeground.equals("Pantallas.PantallaConversacion")){
                    if (PantallaConversacion.idChat == chat.getId()){
                        ListMensajesSync(PantallaInicio.activityInForeground, chat);
                    }
                }else if (PantallaInicio.nameOfActivityInForeground.equals("Pantallas.PantallaUsuarioLogueado")){
                    ListChatsSync(PantallaInicio.activityInForeground);
                }
            }else{
                sendNotification(pushMessage, chat, service);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            if (statusCode == 403){
                toastLargo(service, "Sesion expirada");
                borrarSharedPreferences(service);
                //CerrarSesion(context);
            }
            Gson gson = new Gson();
            Error mensajeError = gson.fromJson(new String(responseBody), Error.class);

            toastCorto(service, mensajeError.getMessage());
        }
    }






    public void guardarUsuarioYSesion(DatosRespuestaRegistro datosRespuestaRegistro, Context context){

        SharedPreferences settings = context.getSharedPreferences("Config", 0);
        SharedPreferences.Editor editor = settings.edit();
        String token = FirebaseInstanceId.getInstance().getToken();

        editor.putString("firebase_token", token);
        editor.putString("token", datosRespuestaRegistro.getSession().getToken());
        editor.putInt("id", datosRespuestaRegistro.getUser().getId());
        editor.putString("name", datosRespuestaRegistro.getUser().getName());
        editor.putString("email", datosRespuestaRegistro.getUser().getEmail());
        editor.putBoolean("sesion", true);
        editor.putLong("valid_until", datosRespuestaRegistro.getSession().getValid_until().getTime());
        editor.commit();
        new EnviarFirebaseTokenAsync(context, new DatosEnviarFirebaseToken(token)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    public void dibujarChat(Activity activity, Chat chat, Message ultimoMensaje, int unreadMessages){

        ListView listaChats = PantallaUsuarioLogueado.listView;

        PantallaUsuarioLogueado.myList.add(new InformacionListChat(chat, ultimoMensaje, unreadMessages));

        ordenarLista();

        AdapterChatList adapterChatList = new AdapterChatList(activity, PantallaUsuarioLogueado.myList);
        listaChats.setAdapter(adapterChatList);

        listaChats.setOnItemClickListener(new ChatListClickListener(PantallaUsuarioLogueado.myList, activity));
        adapterChatList.notifyDataSetChanged();

    }

    public void dibujarMensajes(Activity activity, DatosRespuestaListMensajes datosRespuestaListMensajes, int idChat){

        ListView listaMensajes = activity.findViewById(R.id.ListViewConversacion);

        PantallaConversacion.messages = datosRespuestaListMensajes.getMessages();

        AdapterMensajesConversacion adapterMensajesConversacion = new AdapterMensajesConversacion(activity.getApplicationContext(), PantallaConversacion.messages);
        listaMensajes.setAdapter(adapterMensajesConversacion);
        adapterMensajesConversacion.notifyDataSetChanged();

        listaMensajes.setSelection(PantallaConversacion.messages.size()-1);

        PantallaConversacion.mensajesVistos(activity, idChat);

    }

    public void actualizarPerfil(Activity activity, User user){

        ((TextView)activity.findViewById(R.id.TextViewMiEmail)).setText(user.getEmail());
        ((TextView)activity.findViewById(R.id.TextViewMiNombre)).setText(user.getName());
        ((EditText)activity.findViewById(R.id.EditTextCambiarNombre)).setText(user.getName());
        activity.findViewById(R.id.LayoutCambiarNombre1).setVisibility(View.VISIBLE);
        activity.findViewById(R.id.LayoutCambiarNombre2).setVisibility(View.GONE);

    }

    public int numeroMensajesVistos(long idChat, Activity activity){

        try {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(activity, "db");
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            DaoSession daoSession = daoMaster.newSession();
            SeenMessagesDao seenMessagesDao = daoSession.getSeenMessagesDao();

            List<SeenMessages> seenMessagesList = seenMessagesDao.queryBuilder().where(SeenMessagesDao.Properties.IdChat.eq(idChat)).list();
            if (seenMessagesList.size() == 0) {
                return 0;
            } else {
                if (seenMessagesList.size() == 1) {
                    SeenMessages aux = seenMessagesList.get(0);
                    return aux.getSeenMessages();
                }
                return 0;
            }
        }catch (Exception e){
            return 0;
        }

    }

    public void ordenarLista(){

        ArrayList<InformacionListChat> lista = new ArrayList();
        boolean z;

        lista.add(PantallaUsuarioLogueado.myList.get(0));
        for (int x = 1; x < PantallaUsuarioLogueado.myList.size(); x++) {
            z = false;
            if (PantallaUsuarioLogueado.myList.get(x).getUltimoMensaje() != null) {
                for (int y = 0; y < lista.size(); y++) {
                    if (lista.get(y).getUltimoMensaje() != null) {
                        if (PantallaUsuarioLogueado.myList.get(x).getUltimoMensaje().getReceived_at().after(lista.get(y).getUltimoMensaje().getReceived_at())) {
                            lista.add(y, PantallaUsuarioLogueado.myList.get(x));
                            z = true;
                            break;
                        }
                    }else{
                        lista.add(y, PantallaUsuarioLogueado.myList.get(x));
                        z = true;
                        break;
                    }
                }
            }
            if (z == false){
                lista.add(PantallaUsuarioLogueado.myList.get(x));
            }
        }

        PantallaUsuarioLogueado.myList.clear();
        PantallaUsuarioLogueado.myList = lista;

    }

    public static void borrarSharedPreferences(Context context){
        SharedPreferences settings = context.getSharedPreferences("Config", 0);
        SharedPreferences.Editor editor = settings.edit();
        String token_google = settings.getString("firebase_token", "");
        editor.clear();
        editor.putString("firebase_token", token_google);
        editor.putBoolean("sesion", false);
        editor.commit();
    }

    public void sendNotification(PushMessage message, Chat chat, Service service) {
        Intent intent = new Intent(service, PantallaConversacion.class);
        intent.putExtra("chat", new Gson().toJson(chat));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(service, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(service)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(message.getSender())
                .setContentText(message.getMessage())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    public class EnviarFirebaseTokenAsync extends AsyncTask{

        Context context;
        DatosEnviarFirebaseToken datosEnviarFirebaseToken;

        public EnviarFirebaseTokenAsync(Context context, DatosEnviarFirebaseToken datosEnviarFirebaseToken) {
            this.context = context;
            this.datosEnviarFirebaseToken = datosEnviarFirebaseToken;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            enviarFirebaseToken(context, datosEnviarFirebaseToken);
            return null;
        }
    }

    public static boolean tokenExpirado(Context context){

        SharedPreferences settings = context.getSharedPreferences("Config", 0);

        Date now = Calendar.getInstance().getTime();
        long valid_until_long = settings.getLong("valid_until", 0);
        Date valid_until_date = new Date(valid_until_long);

        if (now.before(valid_until_date)) {
            return false;
        } else {
            return true;
        }

    }

    public static boolean sesionAbierta(Activity activity){

        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);
        return settings.getBoolean("sesion", false);

    }

    public static void toastCorto(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void toastLargo(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void mostrarSnackbar(View view, String texto){
        Snackbar.make(view, texto, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

}
