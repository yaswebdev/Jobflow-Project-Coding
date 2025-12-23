public interface AuthService {
    boolean login(String username, String password);
    void logout();
    void forgotPassword(String email);
    void createAccount(String username, String password, String email);
}
