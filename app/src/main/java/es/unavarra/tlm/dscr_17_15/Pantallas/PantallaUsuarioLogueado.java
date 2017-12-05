package es.unavarra.tlm.dscr_17_15.Pantallas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import es.unavarra.tlm.dscr_17_15.EventListeners.BuscarChat;
import es.unavarra.tlm.dscr_17_15.EventListeners.CerrarSesion;
import es.unavarra.tlm.dscr_17_15.EventListeners.IrAMiPerfil;
import es.unavarra.tlm.dscr_17_15.EventListeners.MostrarLayoutBuscarChat;
import es.unavarra.tlm.dscr_17_15.Objects.InformacionListChat;
import es.unavarra.tlm.dscr_17_15.REST.ClasePeticionesRest;
import es.unavarra.tlm.dscr_17_15.EventListeners.InvitarChat;
import es.unavarra.tlm.dscr_17_15.R;

public class PantallaUsuarioLogueado extends AppCompatActivity {

    public static ListView listView;
    public static ArrayList<InformacionListChat> myList = new ArrayList<>();
    ClasePeticionesRest clasePeticionesRest = new ClasePeticionesRest();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myList.clear();
        setContentView(R.layout.activity_pantalla_usuario_logueado);
        (findViewById(R.id.ImagenMiPerfil)).setOnClickListener(new IrAMiPerfil(this));
        listView = findViewById(R.id.ListViewChats);

        SharedPreferences settings = getSharedPreferences("Config", 0);
        Log.e("PUSH", "2- " + settings.getString("firebase_token", "EMPTY"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ClasePeticionesRest.tokenExpirado(this) || !ClasePeticionesRest.sesionAbierta(this)){
            ClasePeticionesRest.borrarSharedPreferences(this);
            Intent intent = new Intent(this, PantallaInicio.class);
            startActivity(intent);
        }
        myList.clear();
        //ListView listaDeChats = (ListView)findViewById(R.id.ListViewChats);
        EditText cuadroInvite = (EditText)findViewById(R.id.CuadroInvitarChat);
        Button botonInvitarChat = (Button)findViewById(R.id.BotonInvitarChat);
        View botonCerrarSesion = findViewById(R.id.BotonCerrarSesion);
        EditText cuadroBuscarChat = (EditText)findViewById(R.id.CuadroBuscarChat);
        Button botonBuscarChat = (Button)findViewById(R.id.BotonBuscarChat);
        android.support.design.widget.FloatingActionButton botonFlotanteBuscarChat = (android.support.design.widget.FloatingActionButton)findViewById(R.id.BotonFlotanteBuscarChat);
        android.support.design.widget.FloatingActionButton botonFlotanteBuscarChatX = (android.support.design.widget.FloatingActionButton)findViewById(R.id.BotonFlotanteBuscarChatX);
        LinearLayout layoutBuscarChat = (LinearLayout)findViewById(R.id.LayoutBuscarChat);

        clasePeticionesRest.ListChats(this);

        botonCerrarSesion.setOnClickListener(new CerrarSesion(this));
        botonInvitarChat.setOnClickListener(new InvitarChat(cuadroInvite, this));
        botonBuscarChat.setOnClickListener(new BuscarChat(this, cuadroBuscarChat));
        botonFlotanteBuscarChat.setOnClickListener(new MostrarLayoutBuscarChat(this, layoutBuscarChat, botonFlotanteBuscarChat, botonFlotanteBuscarChatX, false));
        botonFlotanteBuscarChatX.setOnClickListener(new MostrarLayoutBuscarChat(this, layoutBuscarChat, botonFlotanteBuscarChat, botonFlotanteBuscarChatX, true));

        PantallaInicio.appIsInForeground = true;
        PantallaInicio.nameOfActivityInForeground = getLocalClassName();
        PantallaInicio.activityInForeground = this;

    }

    @Override
    protected void onPause() {
        super.onPause();
        PantallaInicio.appIsInForeground = false;
        PantallaInicio.nameOfActivityInForeground = null;
        PantallaInicio.activityInForeground = null;
    }

}
