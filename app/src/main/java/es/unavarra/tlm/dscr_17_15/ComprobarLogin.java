package es.unavarra.tlm.dscr_17_15;

import android.app.Activity;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ibai on 9/28/17.
 */

public class ComprobarLogin implements View.OnClickListener {

    EditText email;
    EditText password;
    Activity activity;
    ClasePeticionesRest clasePeticionesRest = new ClasePeticionesRest(activity);

    public ComprobarLogin(EditText email, EditText password, Activity activity){
        this.email = email;
        this.password = password;
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {

        Context context = view.getContext();

        if (!isEmailValid(email.getText().toString())){
            CharSequence texto = "Email en formato incorrecto";
            Toast.makeText(context, texto, Toast.LENGTH_SHORT).show();
        }else if (password.getText().toString().equals("")){
            CharSequence texto = "Password vacía";
            Toast.makeText(context, texto, Toast.LENGTH_SHORT).show();
        }else{
            clasePeticionesRest.LoginUsuario(new DatosLogin(email.getText().toString(), password.getText().toString()));
        }
    }


    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }
}
