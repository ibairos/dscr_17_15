package es.unavarra.tlm.dscr_17_15;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

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
