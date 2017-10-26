package es.unavarra.tlm.dscr_17_15;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by ibai on 10/19/17.
 */

public class Adapter extends BaseAdapter {

    ArrayList<Log> logs = new ArrayList<Log>();
    LayoutInflater inflater;
    Context context;

    public Adapter(Context context, ArrayList<Log> logs) {
        this.logs = logs;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return logs.size();
    }

    @Override
    public Log getItem(int i) {
        return logs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AuxLog auxLog;

        if (view == null) {
            view = inflater.inflate(R.layout.layout_list_item, viewGroup, false);
            auxLog = new AuxLog(view);
            view.setTag(auxLog);
        } else {
            auxLog = (AuxLog) view.getTag();
        }

        Log log = getItem(i);

        SimpleDateFormat formatoFecha = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

        auxLog.fechaLogin.setText(formatoFecha.format(log.getAccess()));
        auxLog.mailLogin.setText(log.getMail());
        if (log.getValid()){
            auxLog.iconoLogin.setImageResource(R.drawable.puntoverde);
        }else{
            auxLog.iconoLogin.setImageResource(R.drawable.puntorojo);
        }

        return view;
    }

    private class AuxLog {
        TextView fechaLogin, mailLogin;
        ImageView iconoLogin;

        public AuxLog(View view) {
            fechaLogin = view.findViewById(R.id.fechaLogin);
            mailLogin = view.findViewById(R.id.mailLogin);
            iconoLogin = view.findViewById(R.id.iconoLogin);
        }
    }
}
