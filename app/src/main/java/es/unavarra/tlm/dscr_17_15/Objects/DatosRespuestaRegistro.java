package es.unavarra.tlm.dscr_17_15.Objects;

import java.util.Date;

/**
 * Created by ibai on 10/31/17.
 */

public class DatosRespuestaRegistro {

    Session session;
    User user;

    public DatosRespuestaRegistro(Session session, User user) {
        this.session = session;
        this.user = user;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session sesion) {
        this.session = sesion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
