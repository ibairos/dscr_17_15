package es.unavarra.tlm.dscr_17_15.Pantallas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import es.unavarra.tlm.dscr_17_15.Objects.Chat;
import es.unavarra.tlm.dscr_17_15.R;

public class PantallaInfoChat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_info_chat);

        Bundle bundle = getIntent().getExtras();
        Gson gson = new Gson();
        Chat chat = gson.fromJson(bundle.getString("Chat"), Chat.class);

        ((TextView)findViewById(R.id.fechaInfoChat)).setText(chat.getCreated_at().toString());
        ((TextView)findViewById(R.id.usuario1InfoChat)).setText(chat.getUsers()[0].getEmail());
        ((TextView)findViewById(R.id.usuario2InfoChat)).setText(chat.getUsers()[1].getEmail());



    }
}
