package es.unavarra.tlm.dscr_17_15;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by ibai on 10/19/17.
 */

public class AdapterUsuarioLogueado extends BaseAdapter {

    ArrayList<InfoChat> infoChats = new ArrayList<>();
    LayoutInflater inflater;
    Context context;

    public AdapterUsuarioLogueado(Context context, ArrayList<InfoChat> infoChats) {
        this.infoChats = infoChats;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return infoChats.size();
    }

    @Override
    public InfoChat getItem(int i) {
        return infoChats.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AuxInfoChat auxInfoChat;

        if (view == null) {
            view = inflater.inflate(R.layout.usuario_logueado_list_item, viewGroup, false);
            auxInfoChat = new AuxInfoChat(view);
            view.setTag(auxInfoChat);
        } else {
            auxInfoChat = (AuxInfoChat) view.getTag();
        }

        //Log log = getItem(i);
        InfoChat infoChat = getItem(i);

        //SimpleDateFormat formatoFecha = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

        //auxInfoChat.fechaLogin.setText(formatoFecha.format(infoChat.getAccess()));
        auxInfoChat.mailInfoChat.setText(infoChat.getEmail());
        auxInfoChat.fechaInfoChat.setText(infoChat.getFecha());

        return view;
    }

    private class AuxInfoChat {
        TextView mailInfoChat, fechaInfoChat;

        public AuxInfoChat(View view) {
            mailInfoChat = view.findViewById(R.id.MailInfoChat);
            fechaInfoChat = view.findViewById(R.id.FechaInfoChat);
        }
    }
}
