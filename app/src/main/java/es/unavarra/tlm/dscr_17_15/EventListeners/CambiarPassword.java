package es.unavarra.tlm.dscr_17_15.EventListeners;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import es.unavarra.tlm.dscr_17_15.Objects.DatosCambiarPassword;
import es.unavarra.tlm.dscr_17_15.REST.ClasePeticionesRest;

/**
 * Created by ibai on 11/21/17.
 */

public class CambiarPassword implements View.OnClickListener {

    Activity activity;
    EditText password1, password2;

    public CambiarPassword(Activity activity, EditText password1, EditText password2) {
        this.activity = activity;
        this.password1 = password1;
        this.password2 = password2;
    }

    @Override
    public void onClick(View view) {
        if (password1.getText().toString().equals(password2.getText().toString())) {
            if (!password1.getText().toString().equals("")) {
                (new ClasePeticionesRest()).cambiarPassword(activity, new DatosCambiarPassword(password1.getText().toString()));
            }else{
                ClasePeticionesRest.toastCorto(activity, "Password empty");
            }
        }else{
            ClasePeticionesRest.toastCorto(activity, "Different passwords");
        }
    }
}
