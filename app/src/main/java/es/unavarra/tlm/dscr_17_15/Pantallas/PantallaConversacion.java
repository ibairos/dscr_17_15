package es.unavarra.tlm.dscr_17_15.Pantallas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import es.unavarra.tlm.dscr_17_15.REST.ClasePeticionesRest;
import es.unavarra.tlm.dscr_17_15.EventListeners.EnviarMensaje;
import es.unavarra.tlm.dscr_17_15.Objects.Chat;
import es.unavarra.tlm.dscr_17_15.R;

public class PantallaConversacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversacion);

        Chat chat = (new Gson()).fromJson(getIntent().getExtras().getString("chat"), Chat.class);

        ((TextView)findViewById(R.id.NombreConversacion)).setText(chat.getUsers()[0].getName());

        (findViewById(R.id.BotonEnviarMensaje)).setOnClickListener(new EnviarMensaje((EditText) findViewById(R.id.TextoMensaje), this, chat));

        (new ClasePeticionesRest()).ListMensajes(this, chat);


    }
}
