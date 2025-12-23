import java.util.ArrayList;
import java.util.List;

public class Users {
    private int userId;
    private String username;
    private String password;
    private String email;
    private List<Achievement> achievements;
    private List<Application> applications;

    public Users(int userId, String username, String password, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.achievements = new ArrayList<>();
        this.applications = new ArrayList<>();
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public List<Achievement> getAchievements() { return achievements; }
    public List<Application> getApplications() { return applications; }

    public void setUserId(int userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }

    public void addAchievement(Achievement achievement) {
        this.achievements.add(achievement);
    }

    public void addApplication(Application application) {
        this.applications.add(application);
    }

    void viewDashboard() {
        System.out.println("=== Dashboard for " + username + " ===");
        getUserInfo();
    }

    void viewPersonalAnalytics() {
        System.out.println("Total Applications: " + applications.size());
        System.out.println("Total Achievements: " + achievements.size());
    }

    void viewAchievementList() {
        System.out.println("\n=== Achievements ===");
        for (Achievement achievement : achievements) {
            achievement.getAchievementInfo();
            System.out.println("---");
        }
    }

    void getUserInfo() {
        System.out.println("User ID: " + userId);
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
    }
}
