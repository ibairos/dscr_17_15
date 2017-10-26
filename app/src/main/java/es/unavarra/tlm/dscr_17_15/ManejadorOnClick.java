package es.unavarra.tlm.dscr_17_15;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * Created by ibai on 9/28/17.
 */

public class ManejadorOnClick implements View.OnClickListener {

    Activity actual;
    Class destino;

    public ManejadorOnClick(Activity actual, Class destino){
        this.actual = actual;
        this.destino = destino;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(actual, destino);
        actual.startActivity(intent);
    }
}
