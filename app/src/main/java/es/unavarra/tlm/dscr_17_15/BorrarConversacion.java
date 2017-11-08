package es.unavarra.tlm.dscr_17_15;

import android.app.Activity;
import android.view.View;

import es.unavarra.tlm.dscr_17_15.Objects.Chat;

/**
 * Created by ibai on 11/8/17.
 */

public class BorrarConversacion implements View.OnClickListener {

    Activity activity;
    Chat chat;

    public BorrarConversacion(Activity activity, Chat chat){
        this.activity = activity;
        this.chat = chat;
    }

    @Override
    public void onClick(View view) {
        new ClasePeticionesRest().BorrarConversacion(activity, chat);
    }
}
