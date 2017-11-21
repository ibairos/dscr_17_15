package es.unavarra.tlm.dscr_17_15.EventListeners;

import android.app.Activity;
import android.view.View;

import es.unavarra.tlm.dscr_17_15.R;

/**
 * Created by ibai on 11/21/17.
 */

public class MostrarLayoutCambiarNombre implements View.OnClickListener {

    Activity activity;
    boolean esconder;

    public MostrarLayoutCambiarNombre(Activity activity, boolean esconder) {
        this.activity = activity;
        this.esconder = esconder;
    }

    @Override
    public void onClick(View view) {
        if (esconder){
            activity.findViewById(R.id.LayoutCambiarNombre1).setVisibility(View.VISIBLE);
            activity.findViewById(R.id.LayoutCambiarNombre2).setVisibility(View.GONE);
        }else {
            activity.findViewById(R.id.LayoutCambiarNombre1).setVisibility(View.GONE);
            activity.findViewById(R.id.LayoutCambiarNombre2).setVisibility(View.VISIBLE);
        }
    }
}
