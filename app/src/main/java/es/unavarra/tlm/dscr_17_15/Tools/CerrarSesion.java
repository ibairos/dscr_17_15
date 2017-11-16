package es.unavarra.tlm.dscr_17_15.Tools;

import android.app.Activity;
import android.view.View;

import es.unavarra.tlm.dscr_17_15.REST.ClasePeticionesRest;

/**
 * Created by ibai on 11/8/17.
 */

public class CerrarSesion implements View.OnClickListener {

    Activity activity;

    public CerrarSesion(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        new ClasePeticionesRest().CerrarSesion(activity);
    }

}
