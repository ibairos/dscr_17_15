package es.unavarra.tlm.dscr_17_15.EventListeners;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import es.unavarra.tlm.dscr_17_15.Adapters.AdapterChatList;
import es.unavarra.tlm.dscr_17_15.Objects.InformacionListChat;
import es.unavarra.tlm.dscr_17_15.Pantallas.PantallaUsuarioLogueado;
import es.unavarra.tlm.dscr_17_15.R;

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

            AdapterChatList adapterChatList = new AdapterChatList(activity, auxChatList);
            listaChats.setAdapter(adapterChatList);
            listaChats.setOnItemClickListener(new ChatListClickListener(auxChatList, activity));
            adapterChatList.notifyDataSetChanged();

        }else{
            AdapterChatList adapterChatList = new AdapterChatList(activity, PantallaUsuarioLogueado.myList);
            listaChats.setAdapter(adapterChatList);
            listaChats.setOnItemClickListener(new ChatListClickListener(PantallaUsuarioLogueado.myList, activity));
            adapterChatList.notifyDataSetChanged();
        }

    }
}
