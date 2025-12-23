public interface ApplicationService {
    void searchApplication();
    void filterApplications();
    void saveApplication(Application application);
    void editApplication(Application application);
    void removeApplication(Application application);
}