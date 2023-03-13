package sample;

//==========================Objekat da bi prikazao gresku korisniku pri loginu=========================

public class ConObjekat {
    private User user;
    private Exception exception;

    public ConObjekat(User user, Exception exception) {
        this.user = user;
        this.exception = exception;
    }

    public ConObjekat(User user) {
        this.user = user;
    }

    public ConObjekat() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
