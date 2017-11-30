package es.unavarra.tlm.dscr_17_15.Objects;

/**
 * Created by ibai on 11/8/17.
 */

public class Error {

    String description;

    public Error(){

    }

    public Error(String description) {
        this.description = description;
    }

    public String getMessage() {
        return description;
    }

    public void setMessage(String description) {
        this.description = description;
    }
}
