package es.unavarra.tlm.dscr_17_15.Objects;

import java.util.List;

/**
 * Created by ibai on 11/6/17.
 */

public class DatosRespuestaListMensajes {

    int count;
    List<Message> messages;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
