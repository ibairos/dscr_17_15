package es.unavarra.tlm.dscr_17_15.EventListeners;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import es.unavarra.tlm.dscr_17_15.Adapters.AdapterMensajesConversacion;
import es.unavarra.tlm.dscr_17_15.Pantallas.PantallaConversacion;
import es.unavarra.tlm.dscr_17_15.R;

import static android.view.View.GONE;

/**
 * Created by ibai on 11/21/17.
 */

public class MostrarLayoutBuscarEnChat implements View.OnClickListener {

    Activity activity;
    boolean esconder;

    public MostrarLayoutBuscarEnChat(Activity activity, boolean esconder){
        this.activity = activity;
        this.esconder = esconder;
    }

    @Override
    public void onClick(View view) {
        if (esconder){
            activity.findViewById(R.id.LayoutBuscarEnChat).setVisibility(GONE);
            activity.findViewById(R.id.ImagenBuscarEnChat).setVisibility(View.VISIBLE);
            activity.findViewById(R.id.ImagenBuscarEnChat2).setVisibility(GONE);
            ListView listaMensajes = activity.findViewById(R.id.ListViewConversacion);
            AdapterMensajesConversacion adapterMensajesConversacion = new AdapterMensajesConversacion(activity, PantallaConversacion.messages);
            listaMensajes.setAdapter(adapterMensajesConversacion);
            adapterMensajesConversacion.notifyDataSetChanged();
        }else{
            activity.findViewById(R.id.LayoutBuscarEnChat).setVisibility(View.VISIBLE);
            activity.findViewById(R.id.ImagenBuscarEnChat).setVisibility(View.GONE);
            activity.findViewById(R.id.ImagenBuscarEnChat2).setVisibility(View.VISIBLE);
        }
    }
}
