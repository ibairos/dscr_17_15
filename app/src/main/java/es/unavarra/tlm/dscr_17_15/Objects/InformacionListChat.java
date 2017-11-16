package es.unavarra.tlm.dscr_17_15.Objects;

/**
 * Created by ibai on 11/16/17.
 */

public class InformacionListChat {

    Chat chat;
    Message ultimoMensaje;

    public InformacionListChat(Chat chat, Message ultimoMensaje) {
        this.chat = chat;
        this.ultimoMensaje = ultimoMensaje;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Message getUltimoMensaje() {
        return ultimoMensaje;
    }

    public void setUltimoMensaje(Message ultimoMensaje) {
        this.ultimoMensaje = ultimoMensaje;
    }
}
