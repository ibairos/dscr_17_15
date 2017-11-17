package es.unavarra.tlm.dscr_17_15.EventListeners;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by ibai on 11/17/17.
 */

public class EsconderLayoutBuscarChat implements View.OnClickListener {

    Activity activity;
    LinearLayout layoutBuscarChat;
    android.support.design.widget.FloatingActionButton botonFlotanteBuscarChat, botonFlotanteBuscarChatX;
    boolean esconder;

    public EsconderLayoutBuscarChat(Activity activity, LinearLayout layoutBuscarChat,
                                    android.support.design.widget.FloatingActionButton botonFlotanteBuscarChat,
                                    android.support.design.widget.FloatingActionButton botonFlotanteBuscarChatX, boolean esconder) {
        this.activity = activity;
        this.layoutBuscarChat = layoutBuscarChat;
        this.botonFlotanteBuscarChat = botonFlotanteBuscarChat;
        this.botonFlotanteBuscarChatX = botonFlotanteBuscarChatX;
        this.esconder = esconder;
    }

    @Override
    public void onClick(View view) {
        if (esconder){
            layoutBuscarChat.setVisibility(View.GONE);
            botonFlotanteBuscarChat.setVisibility(View.VISIBLE);
            botonFlotanteBuscarChatX.setVisibility(View.GONE);
        }else{
            layoutBuscarChat.setVisibility(View.VISIBLE);
            botonFlotanteBuscarChat.setVisibility(View.GONE);
            botonFlotanteBuscarChatX.setVisibility(View.VISIBLE);
        }
    }
}
