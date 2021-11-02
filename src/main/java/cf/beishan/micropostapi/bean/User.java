package cf.beishan.micropostapi.bean;

import java.util.Date;

public class User {

    private int id;
    private String email;
    private String username;
    private String password;
    private String avatarPath;
    private boolean state;
    private Date crtTime;

    public User() {
    }

    public User(int id, String email, String username, String password, String avatarPath, boolean state, Date crtTime) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.avatarPath = avatarPath;
        this.state = state;
        this.crtTime = crtTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", state=" + state +
                ", crtTime=" + crtTime +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }
}
