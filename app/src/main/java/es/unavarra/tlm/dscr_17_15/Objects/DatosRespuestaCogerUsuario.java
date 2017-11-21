package es.unavarra.tlm.dscr_17_15.Objects;

/**
 * Created by ibai on 11/21/17.
 */

public class DatosRespuestaCogerUsuario {

    User user;

    public DatosRespuestaCogerUsuario(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
