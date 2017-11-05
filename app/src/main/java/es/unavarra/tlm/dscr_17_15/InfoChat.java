package es.unavarra.tlm.dscr_17_15;

/**
 * Created by ibai on 11/2/17.
 */

public class InfoChat {

    String email;
    String fecha;

    public InfoChat(String email, String fecha) {
        this.email = email;
        this.fecha = fecha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
