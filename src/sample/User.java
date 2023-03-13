package sample;

public class User {

    private String username;
    private String password;
    private double score;
    private double highScore;
    private int id;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.score = 0;
        this.highScore = 0;

    }

    public User(String username, String password, double score, double highScore, int id) {
        this.username = username;
        this.password = password;
        this.score = score;
        this.highScore = highScore;
        this.id = id;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
        if (score > highScore) {
            this.highScore = score;
        }
    }

    public double getHighScore() {
        return highScore;
    }

    public void setHighScore(double highScore) {
        this.highScore = highScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return username + " - "+highScore;
    }
}