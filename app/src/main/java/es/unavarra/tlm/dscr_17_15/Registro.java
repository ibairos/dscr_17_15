package es.unavarra.tlm.dscr_17_15;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        findViewById(R.id.crearCuenta).setOnClickListener(new ComprobarBoxes((EditText)findViewById(R.id.textoEmailRegistro), (EditText)findViewById(R.id.textoContraseñaRegistro), this));
    }
}
