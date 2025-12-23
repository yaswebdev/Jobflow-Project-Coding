// java
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final List<Users> users = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static int nextUserId = 5;
    private static int nextAppId = 5;
    private static int nextAchievementId = 2;

    public static void main(String[] args) {
        // Load persisted users if exists
        List<Users> loaded = Storage.loadUsers();
        if (!loaded.isEmpty()) {
            users.addAll(loaded);
            // adjust next ids based on loaded data (simple heuristic)
            for (Users u : users) {
                if (u.getUserId() >= nextUserId) nextUserId = u.getUserId() + 1;
                for (Application a : u.getApplications()) {
                    if (a.getApplicationId() >= nextAppId) nextAppId = a.getApplicationId() + 1;
                }
                for (Achievement ach : u.getAchievements()) {
                    if (ach.getAchievementId() >= nextAchievementId) nextAchievementId = ach.getAchievementId() + 1;
                }
            }
        } else {
            // Seed admin and some users
            users.add(new Administrateur(0, "admin", "admin123", "admin@example.com"));
            users.add(new Users(1, "Yassine", "someone123", "yassine.rami@gmail.com"));
            users.add(new Users(2, "Zakaria", "ziko123", "zakaria.rami@gmail.com"));
            users.add(new Users(3, "Othman", "aatar123", "othman.aatar@gmail.com"));
            users.add(new Users(4, "Zebo", "zebo123", "betsaheel.zebo@gmail.com"));
            Storage.saveUsers(users);
        }

        mainLoop();
    }

    private static void mainLoop() {
        while (true) {
            System.out.println("\n=== Welcome ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    Users logged = login();
                    if (logged != null) {
                        if (logged instanceof Administrateur) adminMenu((Administrateur) logged);
                        else userMenu(logged);
                    }
                    break;
                case "2":
                    register();
                    break;
                case "3":
                    Storage.saveUsers(users); // ensure saved on exit
                    System.out.println("Goodbye.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static Users login() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        for (Users u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                System.out.println("Logged in as " + u.getUsername());
                return u;
            }
        }
        System.out.println("Invalid credentials.");
        return null;
    }

    private static void register() {
        System.out.print("Choose username: ");
        String username = scanner.nextLine().trim();
        for (Users u : users) {
            if (u.getUsername().equals(username)) {
                System.out.println("Username already taken.");
                return;
            }
        }
        System.out.print("Choose password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        Users newUser = new Users(nextUserId++, username, password, email);
        users.add(newUser);
        Storage.saveUsers(users);
        System.out.println("Registered. You can now login.");
    }

    private static void userMenu(Users user) {
        while (true) {
            System.out.println("\n=== User Menu (" + user.getUsername() + ") ===");
            System.out.println("1. Create application");
            System.out.println("2. Show applications");
            System.out.println("3. Show achievements");
            System.out.println("4. Settings");
            System.out.println("5. Logout");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    createApplicationFor(user);
                    break;
                case "2":
                    showApplications(user);
                    break;
                case "3":
                    showAchievements(user);
                    break;
                case "4":
                    if (settings(user)) return; // settings may logout
                    break;
                case "5":
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void adminMenu(Administrateur admin) {
        while (true) {
            System.out.println("\n=== Admin Menu (" + admin.getUsername() + ") ===");
            System.out.println("1. Create application for a user");
            System.out.println("2. Show all applications");
            System.out.println("3. Show all achievements");
            System.out.println("4. Manage user accounts (admin action)");
            System.out.println("5. View system-wide analytics (admin action)");
            System.out.println("6. View all users");
            System.out.println("7. Configure application settings (admin action)");
            System.out.println("8. Settings");
            System.out.println("9. Logout");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    createApplicationForUser(admin);
                    break;
                case "2":
                    showAllApplications();
                    break;
                case "3":
                    showAllAchievements();
                    break;
                case "4":
                    admin.manageUserAccount();
                    break;
                case "5":
                    admin.viewSystemWideAnalytics();
                    break;
                case "6":
                    viewAllUsers();
                    break;
                case "7":
                    admin.configureApplicationSettings();
                    break;
                case "8":
                    if (settings(admin)) return;
                    break;
                case "9":
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void createApplicationFor(Users user) {
        System.out.print("Position: ");
        String position = scanner.nextLine().trim();
        System.out.print("Company: ");
        String company = scanner.nextLine().trim();
        Application app = new Application(nextAppId++, position, company, AppStatus.APPLIED, new Date());
        user.addApplication(app);
        System.out.println("Application created with ID " + app.getApplicationId());

        // Simple achievement: add "First Application" if user had no achievements before
        if (user.getAchievements().isEmpty()) {
            Achievement ach = new Achievement(nextAchievementId++, "First Application", "Applied to first job", new Date());
            user.addAchievement(ach);
            System.out.println("Achievement earned: " + ach.getTitle());
        }
        Storage.saveUsers(users);
    }

    // Admin-specific: create an application for another user (not admin)
    private static void createApplicationForUser(Administrateur admin) {
        System.out.println("\nSelect a user to create an application for:");
        for (Users u : users) {
            String marker = (u.getUserId() == admin.getUserId()) ? " (admin)" : "";
            System.out.println(u.getUserId() + ". " + u.getUsername() + marker);
        }
        System.out.print("Enter user ID: ");
        String input = scanner.nextLine().trim();
        int id;
        try {
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
            return;
        }

        Users target = null;
        for (Users u : users) {
            if (u.getUserId() == id) {
                target = u;
                break;
            }
        }

        if (target == null) {
            System.out.println("User not found.");
            return;
        }
        if (target.getUserId() == admin.getUserId()) {
            System.out.println("Admins cannot create applications for themselves via admin actions.");
            return;
        }

        System.out.print("Position: ");
        String position = scanner.nextLine().trim();
        System.out.print("Company: ");
        String company = scanner.nextLine().trim();

        Application app = new Application(nextAppId++, position, company, AppStatus.APPLIED, new Date());
        target.addApplication(app);
        System.out.println("Application created for " + target.getUsername() + " with ID " + app.getApplicationId());

        if (target.getAchievements().isEmpty()) {
            Achievement ach = new Achievement(nextAchievementId++, "First Application", "Applied to first job", new Date());
            target.addAchievement(ach);
            System.out.println("Achievement earned for " + target.getUsername() + ": " + ach.getTitle());
        }
        Storage.saveUsers(users);
    }

    private static void showApplications(Users user) {
        List<Application> apps = user.getApplications();
        if (apps.isEmpty()) {
            System.out.println("No applications.");
            return;
        }
        System.out.println("\n=== Applications ===");
        for (Application app : apps) {
            app.getApplicationInfo();
            System.out.println("---");
        }
    }

    // Admin: show applications of all users
    private static void showAllApplications() {
        boolean any = false;
        System.out.println("\n=== All Applications ===");
        for (Users u : users) {
            List<Application> apps = u.getApplications();
            if (!apps.isEmpty()) {
                any = true;
                System.out.println("User: " + u.getUsername());
                for (Application app : apps) {
                    app.getApplicationInfo();
                    System.out.println("---");
                }
            }
        }
        if (!any) {
            System.out.println("No applications in the system.");
        }
    }

    private static void showAchievements(Users user) {
        List<Achievement> achs = user.getAchievements();
        if (achs.isEmpty()) {
            System.out.println("No achievements.");
            return;
        }
        System.out.println("\n=== Achievements ===");
        for (Achievement a : achs) {
            a.getAchievementInfo();
            System.out.println("---");
        }
    }

    // Admin: show achievements of all users
    private static void showAllAchievements() {
        boolean any = false;
        System.out.println("\n=== All Achievements ===");
        for (Users u : users) {
            List<Achievement> achs = u.getAchievements();
            if (!achs.isEmpty()) {
                any = true;
                System.out.println("User: " + u.getUsername());
                for (Achievement a : achs) {
                    a.getAchievementInfo();
                    System.out.println("---");
                }
            }
        }
        if (!any) {
            System.out.println("No achievements in the system.");
        }
    }

    private static boolean settings(Users user) {
        while (true) {
            System.out.println("\n=== Settings ===");
            System.out.println("1. Change password");
            System.out.println("2. Logout");
            System.out.println("3. Back");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("New password: ");
                    String newPass = scanner.nextLine().trim();
                    user.setPassword(newPass);
                    Storage.saveUsers(users);
                    System.out.println("Password updated.");
                    break;
                case "2":
                    System.out.println("Logged out.");
                    return true;
                case "3":
                    return false;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void viewAllUsers() {
        System.out.println("\n=== All Users ===");
        for (Users u : users) {
            u.getUserInfo();
            System.out.println("---");
        }
    }
}
