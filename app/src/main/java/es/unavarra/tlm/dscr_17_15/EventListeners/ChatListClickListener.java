package es.unavarra.tlm.dscr_17_15.EventListeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;

import java.util.List;

import es.unavarra.tlm.dscr_17_15.Objects.Chat;
import es.unavarra.tlm.dscr_17_15.Pantallas.Conversacion;

/**
 * Created by ibai on 11/5/17.
 */

public class ChatListClickListener implements AdapterView.OnItemClickListener {

    List<Chat> chats;
    Activity activity;

    public ChatListClickListener(List<Chat> chats, Activity activity){
        this.chats = chats;
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent intent = new Intent(activity, Conversacion.class);
        intent.putExtra("chat", (new Gson()).toJson(chats.get(i)));
        activity.startActivity(intent);

    }
}
