package es.unavarra.tlm.dscr_17_15.Pantallas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import es.unavarra.tlm.dscr_17_15.REST.ClasePeticionesRest;
import es.unavarra.tlm.dscr_17_15.EventListeners.ManejadorOnClick;
import es.unavarra.tlm.dscr_17_15.R;

public class PantallaInicio extends AppCompatActivity {

    public static boolean appIsInForeground;
    public static String nameOfActivityInForeground;
    public static Activity activityInForeground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);

        findViewById(R.id.botonRegistro).setOnClickListener(new ManejadorOnClick(this, PantallaRegistro.class));
        findViewById(R.id.botonEntrar).setOnClickListener(new ManejadorOnClick(this, PantallaLogin.class));

        SharedPreferences settings = getSharedPreferences("Config", 0);
        Log.e("PUSH", "1- " + settings.getString("firebase_token", "EMPTY"));
        //Log.e("etiqueta", "2- "+FirebaseInstanceId.getInstance().getToken());

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

    @Override
    protected void onResume() {
        super.onResume();
        appIsInForeground = true;
        nameOfActivityInForeground = getLocalClassName();
        activityInForeground = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        appIsInForeground = false;
        nameOfActivityInForeground = null;
        activityInForeground = null;
    }

}
