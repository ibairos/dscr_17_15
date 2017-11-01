package es.unavarra.tlm.dscr_17_15;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.botonRegistro).setOnClickListener(new ManejadorOnClick(this, Registro.class));
        findViewById(R.id.botonEntrar).setOnClickListener(new ManejadorOnClick(this, Login.class));

    }









    @Override
    protected void onResume() {
        super.onResume();

    }
}
