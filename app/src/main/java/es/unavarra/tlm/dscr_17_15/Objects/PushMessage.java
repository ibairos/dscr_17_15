package es.unavarra.tlm.dscr_17_15.Objects;

/**
 * Created by ibai on 11/30/17.
 */

public class PushMessage {

    String sender, chat, type, when, sender_id, message;

    public PushMessage(String sender, String chat, String type, String when, String sender_id, String message) {
        this.sender = sender;
        this.chat = chat;
        this.type = type;
        this.when = when;
        this.sender_id = sender_id;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
