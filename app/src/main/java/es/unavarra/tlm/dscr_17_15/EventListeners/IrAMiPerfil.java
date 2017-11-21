package es.unavarra.tlm.dscr_17_15.EventListeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import es.unavarra.tlm.dscr_17_15.REST.ClasePeticionesRest;

/**
 * Created by ibai on 11/21/17.
 */

public class IrAMiPerfil implements View.OnClickListener {

    Activity activity;

    public IrAMiPerfil(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        (new ClasePeticionesRest()).cogerUsuario(activity);
    }
}
