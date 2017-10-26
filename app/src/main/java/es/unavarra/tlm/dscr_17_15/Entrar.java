package es.unavarra.tlm.dscr_17_15;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Entrar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);
        final SharedPreferences settings = getSharedPreferences("Config", 0);
        String email = settings.getString("email", "");

        if (email.equals("")){
            findViewById(R.id.entrar).setOnClickListener(new ComprobarLogin((EditText)findViewById(R.id.textoEmailEntrar), (EditText)findViewById(R.id.textoContraseñaEntrar), this));
        }else{
            findViewById(R.id.textoEmailEntrar).setVisibility(View.INVISIBLE);
            findViewById(R.id.textoContraseñaEntrar).setVisibility(View.INVISIBLE);
            ((TextView)findViewById(R.id.mensajeHola)).setText("Hola " + email);
            findViewById(R.id.mensajeHola).setVisibility(View.VISIBLE);
            ((Button)findViewById(R.id.entrar)).setText(R.string.salir);
            findViewById(R.id.entrar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("email", "");
                    editor.commit();
                    finish();
                }
            });
        }

    }
}
