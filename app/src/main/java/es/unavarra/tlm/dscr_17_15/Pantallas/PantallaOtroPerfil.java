package es.unavarra.tlm.dscr_17_15.Pantallas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import es.unavarra.tlm.dscr_17_15.Objects.User;
import es.unavarra.tlm.dscr_17_15.R;

public class PantallaOtroPerfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_otro_perfil);

        Bundle bundle = getIntent().getExtras();

        User user = (new Gson()).fromJson(bundle.getString("User"), User.class);

        ((TextView)findViewById(R.id.TextViewOtherMail)).setText(user.getEmail());
        ((TextView)findViewById(R.id.TextViewOtherName)).setText(user.getName());
    }
}
