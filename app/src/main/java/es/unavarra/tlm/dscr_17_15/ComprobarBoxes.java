package es.unavarra.tlm.dscr_17_15;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ibai on 9/28/17.
 */

public class ComprobarBoxes implements View.OnClickListener {

    EditText email, contraseña;
    Activity actual;

    public ComprobarBoxes(EditText email, EditText contraseña, Activity actual){
        this.email = email;
        this.contraseña = contraseña;
        this.actual = actual;
    }

    @Override
    public void onClick(View view) {

        String textoEmail = email.getText().toString();
        String textoContraseña = contraseña.getText().toString();

        //Log.e("Texto Boxes", "Email="+textoEmail+"   Contraseña="+textoContraseña);
        Context context = view.getContext();
        int duration = Toast.LENGTH_SHORT;

        if (textoEmail.equals("") && textoContraseña.equals("")){
            CharSequence texto = "No has introducido ni email ni contraseña";
            Toast toast = Toast.makeText(context, texto, duration);
            toast.show();
        }else{
            if (textoEmail.equals("")){
                CharSequence texto = "No has introducido email";
                Toast toast = Toast.makeText(context, texto, duration);
                toast.show();
            }else if (textoContraseña.equals("")){
                CharSequence texto = "No has introducido contraseña";
                Toast toast = Toast.makeText(context, texto, duration);
                toast.show();
            }else{
                CharSequence texto = "Tu cuenta "+textoEmail+" se ha creado correctamente";
                Toast toast = Toast.makeText(context, texto, duration);
                toast.show();
                actual.finish();
            }
        }

    }
}
