package es.unavarra.tlm.dscr_17_15.Objects;

/**
 * Created by ibai on 11/26/17.
 */

public class DatosRespuestaCambiarNombre {

    User user;

    public DatosRespuestaCambiarNombre(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
