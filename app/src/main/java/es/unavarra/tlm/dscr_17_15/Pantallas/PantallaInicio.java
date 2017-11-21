package es.unavarra.tlm.dscr_17_15.Pantallas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import es.unavarra.tlm.dscr_17_15.REST.ClasePeticionesRest;
import es.unavarra.tlm.dscr_17_15.EventListeners.ManejadorOnClick;
import es.unavarra.tlm.dscr_17_15.R;

public class PantallaInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);

        findViewById(R.id.botonRegistro).setOnClickListener(new ManejadorOnClick(this, PantallaRegistro.class));
        findViewById(R.id.botonEntrar).setOnClickListener(new ManejadorOnClick(this, PantallaLogin.class));

        if (ClasePeticionesRest.sesionAbierta(this)) {
            if (ClasePeticionesRest.tokenExpirado(this)) {
                Toast.makeText(this, "Sesión caducada", Toast.LENGTH_SHORT).show();
                ClasePeticionesRest.borrarSharedPreferences(this);
            } else {
                Intent intent = new Intent(this, PantallaUsuarioLogueado.class);
                startActivity(intent);
                finish();
            }
        }

    }


}
