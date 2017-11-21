package es.unavarra.tlm.dscr_17_15.EventListeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.google.gson.Gson;

import es.unavarra.tlm.dscr_17_15.Objects.User;
import es.unavarra.tlm.dscr_17_15.Pantallas.PantallaOtroPerfil;

/**
 * Created by ibai on 11/21/17.
 */

public class IrAOtroPerfil implements View.OnClickListener {

    Activity activity;
    User user;


    public IrAOtroPerfil(Activity activity, User user) {
        this.activity = activity;
        this.user = user;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(activity, PantallaOtroPerfil.class);
        intent.putExtra("User", (new Gson()).toJson(user));
        activity.startActivity(intent);
    }
}
