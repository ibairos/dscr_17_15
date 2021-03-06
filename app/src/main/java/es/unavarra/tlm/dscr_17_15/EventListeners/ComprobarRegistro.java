package es.unavarra.tlm.dscr_17_15.EventListeners;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import es.unavarra.tlm.dscr_17_15.REST.ClasePeticionesRest;
import es.unavarra.tlm.dscr_17_15.Objects.DatosRegistro;

/**
 * Created by ibai on 9/28/17.
 */

public class ComprobarRegistro implements View.OnClickListener {

    EditText nombre, email, password1, password2;
    Activity activity;
    ClasePeticionesRest clasePeticionesRest;

    public ComprobarRegistro(EditText nombre, EditText email, EditText password1, EditText password2, Activity activity){
        this.nombre = nombre;
        this.email = email;
        this.password1 = password1;
        this.password2 = password2;
        this.activity = activity;
        clasePeticionesRest = new ClasePeticionesRest();
    }

    @Override
    public void onClick(View view) {

        String textoEmail = email.getText().toString();
        String textoPassword1 = password1.getText().toString();
        String textoPassword2 = password2.getText().toString();

        Context context = view.getContext();

        if (textoPassword1.equals(textoPassword2)) {

            if (textoEmail.equals("") && textoPassword1.equals("")) {
                CharSequence texto = "No has introducido ni email ni contraseña";
                Toast.makeText(context, texto, Toast.LENGTH_SHORT).show();
            } else {
                if (textoEmail.equals("")) {
                    CharSequence texto = "No has introducido email";
                    Toast.makeText(context, texto, Toast.LENGTH_SHORT).show();
                } else if (textoPassword1.equals("")) {
                    CharSequence texto = "No has introducido contraseña";
                    Toast.makeText(context, texto, Toast.LENGTH_SHORT).show();
                } else {
                    DatosRegistro datosRegistro = new DatosRegistro(textoEmail, textoPassword1, nombre.getText().toString());
                    clasePeticionesRest.RegistrarUsuario(datosRegistro, activity);
                }
            }
        }else{
            CharSequence texto = "Passwords diferentes";
            Toast.makeText(context, texto, Toast.LENGTH_SHORT).show();
        }

    }

}
