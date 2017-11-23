package es.unavarra.tlm.dscr_17_15.Objects;

/**
 * Created by ibai on 11/8/17.
 */

public class Error {

    String message;

    public Error(){

    }

    public Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
