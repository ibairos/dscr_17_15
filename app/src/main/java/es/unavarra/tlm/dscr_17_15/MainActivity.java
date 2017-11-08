package es.unavarra.tlm.dscr_17_15;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getApplicationContext().getSharedPreferences("Config", 0);

        if (settings.getBoolean("sesion", false)){
            Date now = Calendar.getInstance().getTime();
            long valid_until_long = settings.getLong("valid_until", 0);
            Date valid_until_date = new Date(valid_until_long);

            if (now.before(valid_until_date)){
                Intent intent = new Intent(this, UsuarioLogueado.class);
                startActivity(intent);
                finish();
            }else{
                Log.d("xyz", "NOW: " + now.toString());
                Log.d("xyz", "VALID_UNTIL: " + valid_until_date.toString());
            }
            findViewById(R.id.botonRegistro).setOnClickListener(new ManejadorOnClick(this, Registro.class));
            findViewById(R.id.botonEntrar).setOnClickListener(new ManejadorOnClick(this, Login.class));

        }else{
            ClasePeticionesRest.borrarSharedPreferences(this);
            findViewById(R.id.botonRegistro).setOnClickListener(new ManejadorOnClick(this, Registro.class));
            findViewById(R.id.botonEntrar).setOnClickListener(new ManejadorOnClick(this, Login.class));
        }


    }


}
