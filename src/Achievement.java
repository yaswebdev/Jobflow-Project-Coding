import java.util.Date;

public class Achievement {
    private int achievementId;
    private String title;
    private String description;
    private Date dateAchieved;

    public Achievement(int achievementId, String title, String description, Date dateAchieved) {
        this.achievementId = achievementId;
        this.title = title;
        this.description = description;
        this.dateAchieved = dateAchieved;
    }

    public int getAchievementId() {
        return achievementId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateAchieved() {
        return dateAchieved;
    }
    public void setAchievementId(int achievementId) {
        this.achievementId = achievementId;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDateAchieved(Date dateAchieved) {
        this.dateAchieved = dateAchieved;
    }

    void getAchievementInfo() {
        System.out.println("Achievement ID: " + achievementId);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Date Achieved: " + dateAchieved);
    }

    void viewAchievement() {

    }
}
