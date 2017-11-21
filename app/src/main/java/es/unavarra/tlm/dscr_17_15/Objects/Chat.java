package es.unavarra.tlm.dscr_17_15.Objects;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by ibai on 11/5/17.
 */

public class Chat{

    int id;
    int participants;
    Date created_at;
    User[] users;

    public Chat(int id, int participants, Date created_at, User[] users) {
        this.id = id;
        this.participants = participants;
        this.created_at = created_at;
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }
}