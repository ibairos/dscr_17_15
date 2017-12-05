package es.unavarra.tlm.dscr_17_15.Pantallas;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import es.unavarra.tlm.dscr_17_15.EventListeners.BuscarEnChat;
import es.unavarra.tlm.dscr_17_15.EventListeners.IrAOtroPerfil;
import es.unavarra.tlm.dscr_17_15.EventListeners.MostrarLayoutBuscarEnChat;
import es.unavarra.tlm.dscr_17_15.Objects.DaoMaster;
import es.unavarra.tlm.dscr_17_15.Objects.DaoSession;
import es.unavarra.tlm.dscr_17_15.Objects.Message;
import es.unavarra.tlm.dscr_17_15.Objects.SeenMessages;
import es.unavarra.tlm.dscr_17_15.Objects.SeenMessagesDao;
import es.unavarra.tlm.dscr_17_15.Objects.User;
import es.unavarra.tlm.dscr_17_15.REST.ClasePeticionesRest;
import es.unavarra.tlm.dscr_17_15.EventListeners.EnviarMensaje;
import es.unavarra.tlm.dscr_17_15.Objects.Chat;
import es.unavarra.tlm.dscr_17_15.R;

public class PantallaConversacion extends AppCompatActivity {

    public static List<Message> messages = new ArrayList<>();
    public static int idChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_conversacion);

        Chat chat = (new Gson()).fromJson(getIntent().getExtras().getString("chat"), Chat.class);

        idChat = chat.getId();

        SharedPreferences settings = getSharedPreferences("Config", 0);
        String miEmail = settings.getString("email", "");
        User otroUsuario = null;
        for (int x = 0; x < chat.getUsers().length; x++){
            if (!chat.getUsers()[x].getEmail().equals(miEmail)){
                otroUsuario = chat.getUsers()[x];
            }
        }

        ((TextView)findViewById(R.id.NombreConversacion)).setText(otroUsuario.getName());

        (findViewById(R.id.BotonEnviarMensaje)).setOnClickListener(new EnviarMensaje((EditText) findViewById(R.id.TextoMensaje), this, chat));
        (findViewById(R.id.ImagenBuscarEnChat)).setOnClickListener(new MostrarLayoutBuscarEnChat(this, false));
        (findViewById(R.id.ImagenBuscarEnChat2)).setOnClickListener(new MostrarLayoutBuscarEnChat(this, true));
        (findViewById(R.id.BotonBuscarEnChat)).setOnClickListener(new BuscarEnChat(this, (EditText) findViewById(R.id.TextoBuscarEnChat)));
        (findViewById(R.id.LayoutOtroPerfil)).setOnClickListener(new IrAOtroPerfil(this, otroUsuario));

        (new ClasePeticionesRest()).ListMensajes(this, chat);

    }


    @Override
    protected void onResume() {
        super.onResume();
        PantallaInicio.appIsInForeground = true;
        PantallaInicio.nameOfActivityInForeground = getLocalClassName();
        PantallaInicio.activityInForeground = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        PantallaInicio.appIsInForeground = false;
        PantallaInicio.nameOfActivityInForeground = null;
        PantallaInicio.activityInForeground = null;
    }

    public static void mensajesVistos(Activity activity, int idChat){

        int countSeenMessages = PantallaConversacion.messages.size();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(activity, "db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        SeenMessagesDao seenMessagesDao = daoSession.getSeenMessagesDao();

        List<SeenMessages> seenMessagesList = seenMessagesDao.queryBuilder().where(SeenMessagesDao.Properties.IdChat.eq(idChat)).list();
        if (seenMessagesList.size() == 0){
            SeenMessages auxSeenMessages = new SeenMessages();
            auxSeenMessages.setIdChat(idChat);
            auxSeenMessages.setSeenMessages(countSeenMessages);
            seenMessagesDao.insert(auxSeenMessages);
        }else{
            if (seenMessagesList.size() == 1){
                SeenMessages aux = seenMessagesList.get(0);
                aux.setSeenMessages(countSeenMessages);
                daoSession.getSeenMessagesDao().update(aux);
            }
        }

    }

}
