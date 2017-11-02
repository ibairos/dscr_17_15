package es.unavarra.tlm.dscr_17_15;

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

    public class Session{

        private String token;
        private Date valid_until;

        public Session(String token, Date valid_until) {
            this.token = token;
            this.valid_until = valid_until;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Date getValid_until() {
            return valid_until;
        }

        public void setValid_until(Date valid_until) {
            this.valid_until = valid_until;
        }

    }

    public class User{

        int id;
        String name, email;

        public User(int id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        public int getId() {

            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }

}
