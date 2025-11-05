package ec.edu.monster.auth;

public class AuthService {
    private static final String USER = "MONSTER";
    private static final String PASSWORD = "MONSTER9";
    public boolean login(String user, String pass) {
        return USER.equals(user) && PASSWORD.equals(pass);
    }
}
