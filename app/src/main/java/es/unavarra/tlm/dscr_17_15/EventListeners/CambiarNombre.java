package es.unavarra.tlm.dscr_17_15.EventListeners;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import es.unavarra.tlm.dscr_17_15.Objects.DatosCambiarNombre;
import es.unavarra.tlm.dscr_17_15.REST.ClasePeticionesRest;

/**
 * Created by ibai on 11/21/17.
 */

public class CambiarNombre implements View.OnClickListener {

    Activity activity;
    EditText editText;


    public CambiarNombre(Activity activity, EditText editText) {
        this.activity = activity;
        this.editText = editText;
    }

    @Override
    public void onClick(View view) {
        (new ClasePeticionesRest()).cambiarNombre(activity, new DatosCambiarNombre(editText.getText().toString()));
    }
}
