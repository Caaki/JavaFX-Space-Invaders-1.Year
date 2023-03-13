package sample;

import java.util.List;

public class ConList {

    private List<User> users;
    private Exception exception;

    public ConList(List<User> users, Exception exception) {
        this.users = users;
        this.exception = exception;
    }

    public ConList() {
    }

    public ConList(List<User> users) {
        this.users = users;
    }

    public ConList(Exception exception) {
        this.exception = exception;
    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
