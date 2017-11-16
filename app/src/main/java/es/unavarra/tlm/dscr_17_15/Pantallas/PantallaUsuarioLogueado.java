package es.unavarra.tlm.dscr_17_15.Pantallas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import es.unavarra.tlm.dscr_17_15.EventListeners.CerrarSesion;
import es.unavarra.tlm.dscr_17_15.Objects.InformacionListChat;
import es.unavarra.tlm.dscr_17_15.REST.ClasePeticionesRest;
import es.unavarra.tlm.dscr_17_15.EventListeners.InvitarChat;
import es.unavarra.tlm.dscr_17_15.Objects.Chat;
import es.unavarra.tlm.dscr_17_15.R;

public class PantallaUsuarioLogueado extends AppCompatActivity {

    public static ArrayList<InformacionListChat> myList = new ArrayList<>();
    ClasePeticionesRest clasePeticionesRest = new ClasePeticionesRest();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myList.clear();
        setContentView(R.layout.activity_usuario_logueado);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myList.clear();
        //ListView listaDeChats = (ListView)findViewById(R.id.ListViewChats);
        EditText cuadroInvite = (EditText)findViewById(R.id.CuadroInvitarChat);
        Button botonInvitarChat = (Button)findViewById(R.id.BotonInvitarChat);
        View botonCerrarSesion = findViewById(R.id.BotonCerrarSesion);

        clasePeticionesRest.ListChats(this);

        botonCerrarSesion.setOnClickListener(new CerrarSesion(this));
        botonInvitarChat.setOnClickListener(new InvitarChat(cuadroInvite, this));

    }

}
