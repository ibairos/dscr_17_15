package es.unavarra.tlm.dscr_17_15;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class InfoAccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_access);

        Bundle bundle = getIntent().getExtras();
        int posicion = bundle.getInt("posicion");

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        LogDao logDao = daoSession.getLogDao();

        List l = logDao.queryBuilder().orderDesc(LogDao.Properties.Access).list();
        Log log = (Log)l.get(posicion);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

        ((TextView)findViewById(R.id.InfoAccesoEmail)).setText(log.getMail());
        ((TextView)findViewById(R.id.InfoAccesoFecha)).setText(formatoFecha.format(log.getAccess()));
        if (log.getValid()){
            ((TextView)findViewById(R.id.InfoAccesoValid)).setText("Sí");
        }else{
            ((TextView)findViewById(R.id.InfoAccesoValid)).setText("No");
        }


    }
}
