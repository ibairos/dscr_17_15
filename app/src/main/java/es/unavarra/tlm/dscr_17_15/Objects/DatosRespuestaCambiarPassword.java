package es.unavarra.tlm.dscr_17_15.Objects;

/**
 * Created by ibai on 11/21/17.
 */

public class DatosRespuestaCambiarPassword {

    Session Session;
    User user;

    public DatosRespuestaCambiarPassword(es.unavarra.tlm.dscr_17_15.Objects.Session session, User user) {
        Session = session;
        this.user = user;
    }

    public es.unavarra.tlm.dscr_17_15.Objects.Session getSession() {
        return Session;
    }

    public void setSession(es.unavarra.tlm.dscr_17_15.Objects.Session session) {
        Session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
