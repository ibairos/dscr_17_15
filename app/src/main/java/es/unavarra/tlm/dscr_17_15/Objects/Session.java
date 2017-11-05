package es.unavarra.tlm.dscr_17_15.Objects;

import java.util.Date;

/**
 * Created by ibai on 11/5/17.
 */

public class Session{

    private String token;
    private Date valid_until;

    public Session(String token, Date valid_until) {
        this.token = token;
        this.valid_until = valid_until;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getValid_until() {
        return valid_until;
    }

    public void setValid_until(Date valid_until) {
        this.valid_until = valid_until;
    }

}
