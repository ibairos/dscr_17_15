package es.unavarra.tlm.dscr_17_15.EventListeners;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import es.unavarra.tlm.dscr_17_15.REST.ClasePeticionesRest;
import es.unavarra.tlm.dscr_17_15.Objects.Chat;
import es.unavarra.tlm.dscr_17_15.Objects.DatosInvitarChat;

/**
 * Created by ibai on 11/5/17.
 */

public class InvitarChat implements View.OnClickListener {

    EditText cuadroInvite;
    Activity activity;
    ClasePeticionesRest clasePeticionesRest = new ClasePeticionesRest();
    ArrayList<Chat> myList;

    public InvitarChat(EditText cuadroInvite, ArrayList<Chat> myList, Activity activity){
        this.cuadroInvite = cuadroInvite;
        this.activity = activity;
        this.myList = myList;
    }

    @Override
    public void onClick(View view) {
        clasePeticionesRest.InvitarChat(new DatosInvitarChat(cuadroInvite.getText().toString()), myList, activity);
    }
}
