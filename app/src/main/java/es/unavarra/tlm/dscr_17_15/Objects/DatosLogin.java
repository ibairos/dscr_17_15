package es.unavarra.tlm.dscr_17_15.Objects;

/**
 * Created by ibai on 11/1/17.
 */

public class DatosLogin {

    private String email, password;

    public DatosLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
