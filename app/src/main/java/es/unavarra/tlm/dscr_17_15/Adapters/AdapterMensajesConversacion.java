package es.unavarra.tlm.dscr_17_15.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import es.unavarra.tlm.dscr_17_15.Objects.Message;
import es.unavarra.tlm.dscr_17_15.R;



import static android.graphics.Color.rgb;

/**
 * Created by ibai on 10/19/17.
 */

public class AdapterMensajesConversacion extends BaseAdapter {

    List<Message> messages;
    LayoutInflater inflater;
    Context context;

    public AdapterMensajesConversacion(Context context, List<Message> messages) {
        this.messages = messages;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Message getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AuxMensaje auxMensaje;
        SharedPreferences settings = context.getSharedPreferences("Config", 0);
        String emailUsuario = settings.getString("email", "");
        Message message = getItem(i);

        if (message.getUser().getEmail().equals(emailUsuario)){
            view = inflater.inflate(R.layout.message_list_item_right, viewGroup, false);
            auxMensaje = new AuxMensaje(view);
            //view.setTag(auxMensaje);
        }else{
            view = inflater.inflate(R.layout.message_list_item_left, viewGroup, false);
            auxMensaje = new AuxMensaje(view);
            //view.setTag(auxMensaje);
        }


        SimpleDateFormat formatoFecha = new SimpleDateFormat("HH:mm");

        auxMensaje.message.setText(message.getText());
        auxMensaje.fechaMessage.setText(formatoFecha.format(message.getReceived_at()));

        return view;
    }

    private class AuxMensaje {
        TextView message, fechaMessage;

        public AuxMensaje(View view) {
            message = view.findViewById(R.id.TextoMensaje);
            fechaMessage = view.findViewById(R.id.FechaMensaje);
        }
    }
}
