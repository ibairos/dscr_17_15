package es.unavarra.tlm.dscr_17_15;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);

        findViewById(R.id.entrar).setOnClickListener(new ComprobarLogin((EditText)findViewById(R.id.textoEmailEntrar), (EditText)findViewById(R.id.textoContraseñaEntrar), this));


    }
}
