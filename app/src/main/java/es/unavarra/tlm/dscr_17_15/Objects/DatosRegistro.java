package es.unavarra.tlm.dscr_17_15.Objects;

/**
 * Created by ibai on 10/31/17.
 */

public class DatosRegistro{

    private String email, password, name;

    public DatosRegistro(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
