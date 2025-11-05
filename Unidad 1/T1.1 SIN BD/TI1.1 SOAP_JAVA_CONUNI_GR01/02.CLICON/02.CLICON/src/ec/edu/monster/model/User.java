package ec.edu.monster.model;

public class User {
    private String username;
    private String password;
    private boolean authenticated;

    public User(String u, String p) { this.username = u; this.password = p; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public boolean isAuthenticated() { return authenticated; }
    public void setAuthenticated(boolean a) { this.authenticated = a; }
}
