package es.unavarra.tlm.dscr_17_15;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class UsuarioLogueado extends AppCompatActivity {

    ArrayList<InfoChat> myList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_logueado);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListView listaDeChats = (ListView)findViewById(R.id.ListViewChats);
        EditText cuadroInvite = (EditText)findViewById(R.id.CuadroInvitarChat);
        Button botonInvitarChat = (Button)findViewById(R.id.BotonInvitarChat);

        botonInvitarChat.setOnClickListener(new InvitarChat(cuadroInvite, myList, this));

        llenarListaDeMierda();

        listaDeChats.setAdapter(new AdapterUsuarioLogueado(getApplicationContext(), myList));

        listaDeChats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                /*Intent i = new Intent(getApplicationContext(), InfoAccess.class);
                i.putExtra("posicion", position);
                startActivity(i);*/
                Toast.makeText(getApplicationContext(), "Chat Nº "+position, Toast.LENGTH_SHORT);
            }
        });

    }

    public void llenarListaDeMierda(){

        for (int x = 0; x < 15; x++){
            myList.add(new InfoChat("email"+x, "fecha"+x));
        }

    }
}
