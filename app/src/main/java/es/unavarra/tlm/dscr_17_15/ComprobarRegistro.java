package es.unavarra.tlm.dscr_17_15;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by dscr25 on 26/10/17.
 */

public class ComprobarRegistro implements View.OnClickListener {

    EditText email, contraseña;
    Activity actual;

    public ComprobarRegistro(EditText nombre, EditText email, EditText contraseña, Activity actual){
        this.email = email;
        this.contraseña = contraseña;
        this.actual = actual;
    }

    @Override
    public void onClick(View view) {

    }
}
