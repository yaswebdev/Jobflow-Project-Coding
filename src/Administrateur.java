public class Administrateur extends Users {

    public Administrateur(int userId, String username, String password, String email) {
        super(userId, username, password, email);
    }

    void manageUserAccount() {
        System.out.println("Managing user accounts...");
    }

    void viewSystemWideAnalytics() {
        System.out.println("Viewing system-wide analytics...");
    }

    void viewAllUsers() {
        System.out.println("Viewing all users...");
    }

    void configureApplicationSettings() {
        System.out.println("Configuring application settings...");
    }

    void getAdminInfo() {
        System.out.println("Admin ID: " + getUserId());
        System.out.println("Admin Username: " + getUsername());
        System.out.println("Admin Email: " + getEmail());
    }
}
