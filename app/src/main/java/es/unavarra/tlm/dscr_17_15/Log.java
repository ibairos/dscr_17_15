package es.unavarra.tlm.dscr_17_15;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by ibai on 10/5/17.
 */

@Entity
public class Log {

    @Id
    private Date access;

    private String mail;
    private boolean valid;

    @Generated(hash = 1364647056)
    public Log() {
    }

    @Generated(hash = 372032334)
    public Log(Date access, String mail, boolean valid) {
        this.access = access;
        this.mail = mail;
        this.valid = valid;
    }

    public Date getAccess() {
        return this.access;
    }

    public void setAccess(Date access) {
        this.access = access;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean getValid() {
        return this.valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    
}
