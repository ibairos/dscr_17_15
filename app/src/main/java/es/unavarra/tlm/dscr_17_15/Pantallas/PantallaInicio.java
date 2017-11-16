package es.unavarra.tlm.dscr_17_15.Pantallas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import es.unavarra.tlm.dscr_17_15.REST.ClasePeticionesRest;
import es.unavarra.tlm.dscr_17_15.EventListeners.ManejadorOnClick;
import es.unavarra.tlm.dscr_17_15.R;

public class PantallaInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.botonRegistro).setOnClickListener(new ManejadorOnClick(this, PantallaRegistro.class));
        findViewById(R.id.botonEntrar).setOnClickListener(new ManejadorOnClick(this, PantallaLogin.class));

        if (ClasePeticionesRest.sesionAbierta(this)) {
            if (ClasePeticionesRest.tokenExpirado(this)) {
                Toast.makeText(this, "Sesión caducada", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, PantallaUsuarioLogueado.class);
                startActivity(intent);
                finish();
            }
        }
        ClasePeticionesRest.borrarSharedPreferences(this);

    }


}
