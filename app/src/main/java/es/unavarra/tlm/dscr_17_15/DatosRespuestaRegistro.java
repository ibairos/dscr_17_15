package es.unavarra.tlm.dscr_17_15;

/**
 * Created by ibai on 10/31/17.
 */

public class DatosRespuestaRegistro {

    private String session, user;

    public DatosRespuestaRegistro(String session, String user) {

        this.session = session;
        this.user = user;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
