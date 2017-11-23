package es.unavarra.tlm.dscr_17_15.EventListeners;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import es.unavarra.tlm.dscr_17_15.Adapters.AdapterMensajesConversacion;
import es.unavarra.tlm.dscr_17_15.Objects.Message;
import es.unavarra.tlm.dscr_17_15.Pantallas.PantallaConversacion;
import es.unavarra.tlm.dscr_17_15.R;

/**
 * Created by ibai on 11/21/17.
 */

public class BuscarEnChat implements View.OnClickListener{

    Activity activity;
    EditText textoBuscarEnChat;

    public BuscarEnChat(Activity activity, EditText textoBuscarEnChat){
        this.activity = activity;
        this.textoBuscarEnChat = textoBuscarEnChat;
    }

    @Override
    public void onClick(View view) {

        ListView listaMensajes = activity.findViewById(R.id.ListViewConversacion);
        List<Message> messages = PantallaConversacion.messages;
        ArrayList<Message> auxMessages = new ArrayList<>();
        String textoCuadroBuscarEnChat = textoBuscarEnChat.getText().toString();

        if (!textoCuadroBuscarEnChat.equals("")) {
            for (int x = 0; x < messages.size(); x++) {
                if (messages.get(x).getText().equals(textoCuadroBuscarEnChat)) {
                    auxMessages.add(messages.get(x));
                }
            }

            AdapterMensajesConversacion adapterMensajesConversacion = new AdapterMensajesConversacion(activity, auxMessages);
            listaMensajes.setAdapter(adapterMensajesConversacion);
            //listaMensajes.setOnItemClickListener(new ChatListClickListener(auxMessages, activity));
            adapterMensajesConversacion.notifyDataSetChanged();

        }else{
            AdapterMensajesConversacion adapterMensajesConversacion = new AdapterMensajesConversacion(activity, PantallaConversacion.messages);
            listaMensajes.setAdapter(adapterMensajesConversacion);
            //listaMensajes.setOnItemClickListener(new ChatListClickListener(PantallaConversacion.messages, activity));
            adapterMensajesConversacion.notifyDataSetChanged();
        }

    }
}
