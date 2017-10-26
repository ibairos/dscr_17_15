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
    EditText contraseña;
    Activity activity;

    public ComprobarLogin(EditText email, EditText contraseña, Activity activity){
        this.email = email;
        this.contraseña = contraseña;
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {

        Context context = view.getContext();
        int duration = Toast.LENGTH_SHORT;

        if (!isEmailValid(email.getText().toString())){
            CharSequence texto = "Email en formato incorrecto";
            Toast toast = Toast.makeText(context, texto, duration);
            toast.show();
        }else if (!contraseña.getText().toString().equals("")){

            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(activity, "db");
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            DaoSession daoSession = daoMaster.newSession();
            LogDao logDao = daoSession.getLogDao();

            if (!contraseña.getText().toString().equals("dscr")){
                CharSequence texto = "Contraseña incorrecta";
                Toast toast = Toast.makeText(context, texto, duration);
                toast.show();
                Log log = new Log();
                log.setAccess(new Date());
                log.setMail(email.getText().toString());
                log.setValid(false);
                logDao.insert(log);
            }else{
                CharSequence texto = "Bienvenido "+email.getText().toString();
                Toast toast = Toast.makeText(context, texto, duration);
                toast.show();
                guardarEmail(email.getText().toString(), context);
                Log log = new Log();
                log.setAccess(new Date());
                log.setMail(email.getText().toString());
                log.setValid(true);
                logDao.insert(log);
            }

        }else{
            CharSequence texto = "Contraseña vacía";
            Toast toast = Toast.makeText(context, texto, duration);
            toast.show();
        }
    }

    public void guardarEmail(String emailSP, Context contexto){

        SharedPreferences settings = contexto.getSharedPreferences("Config", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("email", emailSP);
        editor.commit();
        activity.finish();
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
