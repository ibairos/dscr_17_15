package es.unavarra.tlm.dscr_17_15.Pantallas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import es.unavarra.tlm.dscr_17_15.EventListeners.ComprobarRegistro;
import es.unavarra.tlm.dscr_17_15.R;

public class PantallaRegistro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registro);
        findViewById(R.id.crearCuenta).setOnClickListener(new ComprobarRegistro((EditText)findViewById(R.id.textoNombreRegistro), (EditText)findViewById(R.id.textoEmailRegistro), (EditText)findViewById(R.id.textoPasswordRegistro), (EditText)findViewById(R.id.textoPasswordRegistro2), this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences settings = getApplicationContext().getSharedPreferences("Config", 0);
        boolean sesion = settings.getBoolean("sesion", false);
        if (!sesion){
            Intent intent = new Intent(this, PantallaInicio.class);
            startActivity(intent);
        }
    }
}
