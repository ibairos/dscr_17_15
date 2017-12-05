package es.unavarra.tlm.dscr_17_15.Pantallas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import es.unavarra.tlm.dscr_17_15.EventListeners.CambiarNombre;
import es.unavarra.tlm.dscr_17_15.EventListeners.EliminarUsuario;
import es.unavarra.tlm.dscr_17_15.EventListeners.IrACambiarPassword;
import es.unavarra.tlm.dscr_17_15.EventListeners.MostrarLayoutCambiarNombre;
import es.unavarra.tlm.dscr_17_15.Objects.User;
import es.unavarra.tlm.dscr_17_15.R;

public class PantallaMiPerfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_mi_perfil);

        Bundle bundle = getIntent().getExtras();

        User user = (new Gson()).fromJson(bundle.getString("User"), User.class);

        //Log.d("USER", new Gson().toJson(user));

        ((TextView)findViewById(R.id.TextViewMiEmail)).setText(user.getEmail());
        ((TextView)findViewById(R.id.TextViewMiNombre)).setText(user.getName());
        ((EditText)findViewById(R.id.EditTextCambiarNombre)).setText(user.getName());

        findViewById(R.id.BotonCambiarNombre1).setOnClickListener(new MostrarLayoutCambiarNombre(this, false));
        findViewById(R.id.BotonCambiarNombre2).setOnClickListener(new MostrarLayoutCambiarNombre(this, true));

        findViewById(R.id.BotonCambiarNombre).setOnClickListener(new CambiarNombre(this, (EditText)findViewById(R.id.EditTextCambiarNombre)));

        findViewById(R.id.BotonIrACambiarPassword).setOnClickListener(new IrACambiarPassword(this));
        findViewById(R.id.BotonEliminarCuenta).setOnClickListener(new EliminarUsuario(this));

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
