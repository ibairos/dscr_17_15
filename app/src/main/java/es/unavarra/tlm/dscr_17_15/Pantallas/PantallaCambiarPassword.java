package es.unavarra.tlm.dscr_17_15.Pantallas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import es.unavarra.tlm.dscr_17_15.EventListeners.CambiarPassword;
import es.unavarra.tlm.dscr_17_15.R;

public class PantallaCambiarPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_cambiar_password);


        findViewById(R.id.BotonCambiarPassword).setOnClickListener(new CambiarPassword(this, (EditText) findViewById(R.id.TextoCambiarPassword1), (EditText) findViewById(R.id.TextoCambiarPassword2)));

    }


    @Override
    protected void onResume() {
        super.onResume();
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
