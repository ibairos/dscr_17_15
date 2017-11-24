package es.unavarra.tlm.dscr_17_15.REST;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

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
import es.unavarra.tlm.dscr_17_15.Objects.DatosEnviarMensaje;
import es.unavarra.tlm.dscr_17_15.Objects.DatosInvitarChat;
import es.unavarra.tlm.dscr_17_15.Objects.DatosLogin;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRegistro;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRespuestaCogerUsuario;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRespuestaEnviarMensaje;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRespuestaListChats;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRespuestaListMensajes;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRespuestaRegistro;
import es.unavarra.tlm.dscr_17_15.Objects.Error;
import es.unavarra.tlm.dscr_17_15.Objects.InformacionListChat;
import es.unavarra.tlm.dscr_17_15.Objects.Message;
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

        client.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        try {
            client.post(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/chat/invite", new StringEntity(gson.toJson(datosInvitarChat)), "application/json", new RespuestaInvitarChat(activity));
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

            //Log.d("etiqueta", new String(responseBody));

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

    public void EnviarMensaje(Activity activity, DatosEnviarMensaje datosEnviarMensaje, Chat chat){

        Gson gson = new Gson();
        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);

        client.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        client.post(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/chat/"+chat.getId()+"/message", new StringEntity(gson.toJson(datosEnviarMensaje), "UTF-8"), "application/json", new RespuestaEnviarMensaje(activity, chat));

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

            dibujarMensajes(activity, new DatosRespuestaListMensajes(datosRespuestaEnviarMensaje.getMessages().size(), datosRespuestaEnviarMensaje.getMessages()), chat.getId());

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            if (statusCode == 403){
                toastLargo(activity, "Sesion expirada");
                CerrarSesion(activity);
            }
            Log.d("etiqueta", "ERROR: " + new String(responseBody));
            Gson gson = new Gson();
            Error mensajeError = gson.fromJson(new String(responseBody), Error.class);

            toastCorto(activity, mensajeError.getMessage());
        }
    }

    public void ListMensajes(Activity activity, Chat chat){

        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);

        client.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        client.get(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/chat/"+chat.getId()+"/messages", new RespuestaListMensajes(activity, chat.getId()));

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
            DatosRespuestaListMensajes datosRespuestaListMensajes = gson.fromJson(new String(responseBody), DatosRespuestaListMensajes.class);

            dibujarMensajes(activity, datosRespuestaListMensajes, chatId);

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

        client.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        client.get(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/chat/"+chat.getId()+"/exit", new RespuestaBorrarConversacion(activity));

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
                for (int x = 0; x < headers.length; x++) {
                    android.util.Log.e("HEADER " + x, headers[x] + "");
                }
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

        client.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        client.get(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/auth/logout", new RespuestaCerrarSesion(activity));

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
                for (int x = 0; x < headers.length; x++) {
                    android.util.Log.e("HEADER " + x, headers[x] + "");
                }
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
            client.addHeader("X-AUTH-TOKEN", token);
            client.get(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/chat/" + datosRespuestaListChats.getChats().get(x).getId() + "/messages", new RespuestaCogerUltimosMensajes(activity, datosRespuestaListChats.getChats().get(x)));
        }

        //dibujarChats(datosRespuestaListChats, activity);
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

            Message ultimoMensaje;
            if (datosRespuestaListMensajes.getMessages().size() != 0){
                ultimoMensaje = datosRespuestaListMensajes.getMessages().get(datosRespuestaListMensajes.getMessages().size()-1);
            }else{
                ultimoMensaje = null;
            }

            int unreadMessages = datosRespuestaListMensajes.getCount() - numeroMensajesVistos(chat.getId(), activity);

            dibujarChat(activity, chat, ultimoMensaje, unreadMessages);

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

        client.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        client.get(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/profile/me", new RespuestaCogerUsuario(activity));

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

        client.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        client.get(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/profile/"+user.getId(), new RespuestaCogerOtroUsuario(activity));

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

        client.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        try {
            client.post(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/profile/me", new StringEntity(gson.toJson(datosCambiarNombre)), "application/json", new RespuestaCambiarNombre(activity));
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
            User user = gson.fromJson(new String(responseBody), User.class);

            actualizarPerfil(activity, user);

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

        client.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        client.delete(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/profile", new RespuestaEliminarUsuario(activity));
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

        client.addHeader("X-AUTH-TOKEN", settings.getString("token", ""));
        try {
            client.post(activity.getApplicationContext(), "https://api.messenger.tatai.es/v2/profile/me/password", new StringEntity(gson.toJson(datosCambiarPassword)), "application/json", new RespuestaCambiarPassword(activity));
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




    public void guardarUsuarioYSesion(DatosRespuestaRegistro datosRespuestaRegistro, Context context){

        SharedPreferences settings = context.getSharedPreferences("Config", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("id", datosRespuestaRegistro.getUser().getId());
        editor.putString("name", datosRespuestaRegistro.getUser().getName());
        editor.putString("email", datosRespuestaRegistro.getUser().getEmail());
        editor.putString("token", datosRespuestaRegistro.getSession().getToken());
        editor.putBoolean("sesion", true);
        editor.putLong("valid_until", datosRespuestaRegistro.getSession().getValid_until().getTime());
        editor.commit();

    }

    public void dibujarChat(Activity activity, Chat chat, Message ultimoMensaje, int unreadMessages){

        ListView listaChats = activity.findViewById(R.id.ListViewChats);

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
        //listaMensajes.setOnItemClickListener(new ChatListClickListener(myList, activity));
        adapterMensajesConversacion.notifyDataSetChanged();

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

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(activity, "db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        SeenMessagesDao seenMessagesDao = daoSession.getSeenMessagesDao();

        List<SeenMessages> seenMessagesList = seenMessagesDao.queryBuilder().where(SeenMessagesDao.Properties.IdChat.eq(idChat)).list();
        if (seenMessagesList.size() == 0){
            return 0;
        }else{
            if (seenMessagesList.size() == 1){
                SeenMessages aux = seenMessagesList.get(0);
                return aux.getSeenMessages();
            }
            return 0;
        }

    }

    public void ordenarLista(){

        ArrayList<InformacionListChat> lista = new ArrayList();
        boolean z;

        if (PantallaUsuarioLogueado.myList.size() != 0) {
            lista.add(PantallaUsuarioLogueado.myList.get(0));
            for (int x = 1; x < PantallaUsuarioLogueado.myList.size(); x++) {
                z = false;
                for (int y = 0; y < lista.size(); y++) {
                    if (PantallaUsuarioLogueado.myList.get(x).getUltimoMensaje().getReceived_at().after(lista.get(y).getUltimoMensaje().getReceived_at())) {
                        lista.add(y, PantallaUsuarioLogueado.myList.get(x));
                        z = true;
                        break;
                    }
                }
                if (z == false){
                    lista.add(PantallaUsuarioLogueado.myList.get(x));
                }
            }
        }
        PantallaUsuarioLogueado.myList.clear();
        PantallaUsuarioLogueado.myList = lista;

    }

    public static void borrarSharedPreferences(Context context){
        SharedPreferences settings = context.getSharedPreferences("Config", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.putBoolean("sesion", false);
        editor.commit();
    }

    public static boolean tokenExpirado(Activity activity){

        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("Config", 0);

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

    public static void toastCorto(Activity activity, String text){
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
    }

    public static void toastLargo(Activity activity, String text){
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
    }

    public static void mostrarSnackbar(View view, String texto){
        Snackbar.make(view, texto, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

}
