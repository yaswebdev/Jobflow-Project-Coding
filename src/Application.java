import java.util.Date;

public class Application {

    private int applicationId;
    private String position;
    private String company;
    private AppStatus status;
    private Date dateApplied;

    public Application(int applicationId, String position, String company, AppStatus status, Date dateApplied) {
        this.applicationId = applicationId;
        this.position = position;
        this.company = company;
        this.status = status;
        this.dateApplied = dateApplied;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public String getPosition() {
        return position;
    }

    public String getCompany() {
        return company;
    }

    public AppStatus getStatus() {
        return status;
    }

    public Date getDateApplied() {
        return dateApplied;
    }
    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setStatus(AppStatus status) {
        this.status = status;
    }

    public void setDateApplied(Date dateApplied) {
        this.dateApplied = dateApplied;
    }

    void getApplicationInfo() {
        System.out.println("Application ID: " + applicationId);
        System.out.println("Position: " + position);
        System.out.println("Company: " + company);
        System.out.println("Status: " + status);
        System.out.println("Date Applied: " + dateApplied);
    }

    void viewApplication(){

    }
}
