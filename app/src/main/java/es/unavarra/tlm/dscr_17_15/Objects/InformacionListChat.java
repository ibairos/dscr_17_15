package es.unavarra.tlm.dscr_17_15.Objects;

/**
 * Created by ibai on 11/16/17.
 */

public class InformacionListChat {

    Chat chat;
    Message ultimoMensaje;
    int unreadMessages;

    public InformacionListChat(Chat chat, Message ultimoMensaje, int unreadMessages) {
        this.chat = chat;
        this.ultimoMensaje = ultimoMensaje;
        this.unreadMessages = unreadMessages;
    }

    public int getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(int unreadMessages) {
        this.unreadMessages = unreadMessages;
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
