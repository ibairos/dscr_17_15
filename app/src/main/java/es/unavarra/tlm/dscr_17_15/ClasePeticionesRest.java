package es.unavarra.tlm.dscr_17_15;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by dscr25 on 26/10/17.
 */

public class ClasePeticionesRest {

    public AsyncHttpClient client = new AsyncHttpClient();
    Context context;

    public ClasePeticionesRest(Context context){
        this.context = context;
    }

    public void RegistrarUsuario(DatosRegistro datosRegistro){

        /*
        Gson gson = new Gson();
        String objetoGSON = gson.toJson(datosRegistro);
        */
        RequestParams requestParams = new RequestParams(datosRegistro);

        client.post(context, "http://api.messenger.tatai.es//v2/auth/register", requestParams, new RespuestaRegistro(context));

    }

    public class RespuestaRegistro extends AsyncHttpResponseHandler{

        Context context;

        public RespuestaRegistro(Context context){
            this.context = context;
        }
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Gson gson = new Gson();
            String datosRespuesta = gson.toJson(responseBody);
            DatosRespuestaRegistro datosRespuestaRegistro = gson.fromJson(datosRespuesta, DatosRespuestaRegistro.class);

            CharSequence texto = "Tu usuario es: " + datosRespuestaRegistro.getUser() + "\n " +
                    "y tu sesion es: " + datosRespuestaRegistro.getSession();
            Toast.makeText(context, texto, Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    }


}
