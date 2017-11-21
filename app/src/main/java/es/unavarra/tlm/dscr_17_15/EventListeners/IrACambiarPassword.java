package es.unavarra.tlm.dscr_17_15.EventListeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import es.unavarra.tlm.dscr_17_15.Pantallas.PantallaCambiarPassword;

/**
 * Created by ibai on 11/21/17.
 */

public class IrACambiarPassword implements View.OnClickListener {

    Activity activity;

    public IrACambiarPassword(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(activity, PantallaCambiarPassword.class);
        activity.startActivity(intent);
    }
}
