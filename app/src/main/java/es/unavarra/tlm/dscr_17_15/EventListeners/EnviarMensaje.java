package es.unavarra.tlm.dscr_17_15.EventListeners;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.unavarra.tlm.dscr_17_15.REST.ClasePeticionesRest;
import es.unavarra.tlm.dscr_17_15.Objects.Chat;
import es.unavarra.tlm.dscr_17_15.Objects.DatosEnviarMensaje;

/**
 * Created by ibai on 11/6/17.
 */

public class EnviarMensaje implements View.OnClickListener {

    EditText textoMensaje;
    Activity activity;
    Chat chat;

    public EnviarMensaje(EditText textoMensaje, Activity activity, Chat chat){
        this.textoMensaje = textoMensaje;
        this.activity = activity;
        this.chat = chat;
    }

    @Override
    public void onClick(View view) {

        SimpleDateFormat formatoFecha = new SimpleDateFormat("Y-m-d H:m:s");
        (new ClasePeticionesRest()).EnviarMensaje(activity, new DatosEnviarMensaje(textoMensaje.getText().toString(), formatoFecha.format(Calendar.getInstance().getTime())), chat);
        textoMensaje.setText("");
    }
}
