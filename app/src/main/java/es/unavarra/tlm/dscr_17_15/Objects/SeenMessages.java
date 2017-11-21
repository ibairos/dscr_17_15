package es.unavarra.tlm.dscr_17_15.Objects;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ibai on 11/21/17.
 */

@Entity
public class SeenMessages {

    @Id
    int idChat;

    int seenMessages;



    @Generated(hash = 1597454704)
    public SeenMessages(int idChat, int seenMessages) {
        this.idChat = idChat;
        this.seenMessages = seenMessages;
    }

    @Generated(hash = 1778919020)
    public SeenMessages() {
    }



    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public int getSeenMessages() {
        return seenMessages;
    }

    public void setSeenMessages(int seenMessages) {
        this.seenMessages = seenMessages;
    }

}
