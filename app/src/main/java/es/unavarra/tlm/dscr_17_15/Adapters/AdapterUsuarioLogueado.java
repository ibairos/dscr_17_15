package es.unavarra.tlm.dscr_17_15.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;

import es.unavarra.tlm.dscr_17_15.EventListeners.BorrarConversacion;
import es.unavarra.tlm.dscr_17_15.Objects.Chat;
import es.unavarra.tlm.dscr_17_15.Objects.InformacionListChat;
import es.unavarra.tlm.dscr_17_15.R;

/**
 * Created by ibai on 10/19/17.
 */

public class AdapterUsuarioLogueado extends BaseAdapter {

    List<InformacionListChat> chats;
    LayoutInflater inflater;
    Activity activity;

    public AdapterUsuarioLogueado(Activity activity, List<InformacionListChat> chats) {
        this.chats = chats;
        this.activity = activity;
        inflater = LayoutInflater.from(this.activity);
    }

    @Override
    public int getCount() {
        return chats.size();
    }

    @Override
    public InformacionListChat getItem(int i) {
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
            view = inflater.inflate(R.layout.chats_list_item, viewGroup, false);
            auxChat = new AuxChat(view);
            view.setTag(auxChat);
            Log.e("IT", "1");
        } else {
            Log.e("IT", "2");
            auxChat = (AuxChat) view.getTag();
        }


        Chat chat = getItem(i).getChat();
        Log.e("chat "+i, (new Gson()).toJson(chat));

        SimpleDateFormat formatoFecha = new SimpleDateFormat("MM-dd");

        auxChat.mailChat.setText(chat.getUsers()[0].getName());
        auxChat.fechaChat.setText(formatoFecha.format(chat.getCreated_at()));
        if (getItem(i).getUltimoMensaje() != null){
            auxChat.ultimoMensaje.setText(getItem(i).getUltimoMensaje().getText());
        }
        auxChat.delete.setImageResource(R.drawable.trash);
        auxChat.delete.setOnClickListener(new BorrarConversacion(activity, chat));

        return view;
    }

    private class AuxChat {
        TextView mailChat, fechaChat, ultimoMensaje;
        ImageView delete;

        public AuxChat(View view) {
            mailChat = view.findViewById(R.id.MailInfoChat);
            fechaChat = view.findViewById(R.id.FechaInfoChat);
            ultimoMensaje = view.findViewById(R.id.UltimoMensaje);
            delete = view.findViewById(R.id.BotonBorrarConversacion);
        }
    }
}
