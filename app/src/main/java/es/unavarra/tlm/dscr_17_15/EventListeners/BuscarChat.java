package es.unavarra.tlm.dscr_17_15.EventListeners;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import es.unavarra.tlm.dscr_17_15.Adapters.AdapterUsuarioLogueado;
import es.unavarra.tlm.dscr_17_15.Objects.InformacionListChat;
import es.unavarra.tlm.dscr_17_15.Pantallas.PantallaUsuarioLogueado;
import es.unavarra.tlm.dscr_17_15.R;
import es.unavarra.tlm.dscr_17_15.REST.ClasePeticionesRest;

/**
 * Created by ibai on 11/16/17.
 */

public class BuscarChat implements View.OnClickListener {

    EditText cuadroBuscarChat;
    Activity activity;

    public BuscarChat(Activity activity, EditText cuadroBuscarChat){
        this.cuadroBuscarChat = cuadroBuscarChat;
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {

        ListView listaChats = activity.findViewById(R.id.ListViewChats);
        List<InformacionListChat> chatList = PantallaUsuarioLogueado.myList;
        ArrayList<InformacionListChat> auxChatList = new ArrayList<>();
        String textoCuadroBuscarChat = cuadroBuscarChat.getText().toString();

        if (!textoCuadroBuscarChat.equals("")) {
            for (int x = 0; x < chatList.size(); x++) {
                for (int y = 0; y < chatList.get(x).getChat().getUsers().length; y++) {
                    if (chatList.get(x).getChat().getUsers()[y].getEmail().equals(textoCuadroBuscarChat)) {
                        auxChatList.add(chatList.get(x));
                    }
                }
            }

            AdapterUsuarioLogueado adapterUsuarioLogueado = new AdapterUsuarioLogueado(activity, auxChatList);
            listaChats.setAdapter(adapterUsuarioLogueado);
            listaChats.setOnItemClickListener(new ChatListClickListener(auxChatList, activity));
            adapterUsuarioLogueado.notifyDataSetChanged();

        }else{
            AdapterUsuarioLogueado adapterUsuarioLogueado = new AdapterUsuarioLogueado(activity, PantallaUsuarioLogueado.myList);
            listaChats.setAdapter(adapterUsuarioLogueado);
            listaChats.setOnItemClickListener(new ChatListClickListener(PantallaUsuarioLogueado.myList, activity));
            adapterUsuarioLogueado.notifyDataSetChanged();
        }

    }
}
