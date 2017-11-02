package es.unavarra.tlm.dscr_17_15;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListadoAccesos extends AppCompatActivity {

    ArrayList<Log> myList = new ArrayList<Log>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_accesos);

        ListView listView = (ListView)findViewById(R.id.ListViewAccesos);

        llenarLista();

        listView.setAdapter(new Adapter(getApplicationContext(), myList));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(getApplicationContext(), InfoAccess.class);
                i.putExtra("posicion", position);
                startActivity(i);
            }
        });

    }

    public void llenarLista(){

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        LogDao logDao = daoSession.getLogDao();

        List l = logDao.queryBuilder().orderDesc(LogDao.Properties.Access).list();
        for (int x = 0; x < l.size(); x++){
            myList.add((Log)l.get(x));
        }

    }
}
