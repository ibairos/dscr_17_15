package es.unavarra.tlm.dscr_17_15;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import es.unavarra.tlm.dscr_17_15.Objects.Chat;

/**
 * Created by ibai on 10/19/17.
 */

public class AdapterUsuarioLogueado extends BaseAdapter {

    List<Chat> chats;
    LayoutInflater inflater;
    Context context;

    public AdapterUsuarioLogueado(Context context, List<Chat> chats) {
        this.chats = chats;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return chats.size();
    }

    @Override
    public Chat getItem(int i) {
        return chats.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AuxChat auxChat;

        if (view == null) {
            view = inflater.inflate(R.layout.usuario_logueado_list_item, viewGroup, false);
            auxChat = new AuxChat(view);
            view.setTag(auxChat);
            Log.e("IT", "1");
        } else {
            Log.e("IT", "2");
            auxChat = (AuxChat) view.getTag();
        }


        Chat chat = getItem(i);
        Log.e("chat "+i, (new Gson()).toJson(chat));

        SimpleDateFormat formatoFecha = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

        //auxInfoChat.fechaLogin.setText(formatoFecha.format(infoChat.getAccess()));
        auxChat.mailChat.setText(chat.getUsers()[0].getName());
        auxChat.fechaChat.setText(formatoFecha.format(chat.getCreated_at()));

        return view;
    }

    private class AuxChat {
        TextView mailChat, fechaChat;

        public AuxChat(View view) {
            mailChat = view.findViewById(R.id.MailInfoChat);
            fechaChat = view.findViewById(R.id.FechaInfoChat);
        }
    }
}
