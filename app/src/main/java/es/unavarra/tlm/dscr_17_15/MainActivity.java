package es.unavarra.tlm.dscr_17_15;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.*;
import android.widget.ImageView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.botonRegistro).setOnClickListener(new ManejadorOnClick(this, Registro.class));
        findViewById(R.id.botonEntrar).setOnClickListener(new ManejadorOnClick(this, Entrar.class));
        findViewById(R.id.botonAccesos).setOnClickListener(new ManejadorOnClick(this, ListadoAccesos.class));
    }

    @Override
    protected void onResume() {
        super.onResume();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        LogDao logDao = daoSession.getLogDao();
        long valid = logDao.queryBuilder().where(LogDao.Properties.Valid.eq(true)).count();
        //List l = logDao.queryBuilder().where(LogDao.Properties.Valid.eq(true)).list();

        long notValid = logDao.queryBuilder().where(LogDao.Properties.Valid.eq(false)).count();

        ImageView puntoLog = (ImageView)findViewById(R.id.puntoLog);
        if (valid > notValid){
            puntoLog.setImageResource(R.drawable.puntoverde);
        }else if (valid < notValid){
            puntoLog.setImageResource(R.drawable.puntorojo);
        }else{
            puntoLog.setImageResource(R.drawable.puntoblanco);
        }

    }
}
