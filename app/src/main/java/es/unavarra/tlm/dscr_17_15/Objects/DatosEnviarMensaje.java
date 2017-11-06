package es.unavarra.tlm.dscr_17_15.Objects;

import java.util.Date;

/**
 * Created by ibai on 11/6/17.
 */

public class DatosEnviarMensaje {

    String text;
    String created_at;

    public DatosEnviarMensaje(String text, String created_at) {
        this.text = text;
        this.created_at = created_at;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
