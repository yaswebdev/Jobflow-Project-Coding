public interface AchievementService {
    void addAchievement(Users user, Achievement achievement);
    void checkAndCreateAchievement(Users user);
}