package sc2002_project.entities;

import enums.*;

import java.util.List;
import java.util.ArrayList;

public class CompanyRep extends User {
    // Private attributes specific to CompanyRep
    private String position;
    private Company company;
    private String department;
    private REP_STATUS approved;
    private String email;
    private List<Integer> activeOpps;
    private List<Integer> allOpps;

    /**
     * Constructor to create a new Company Representative
     * @param userID The unique identifier (company email)
     * @param name The name of the representative
     * @param password The password for authentication
     * @param position The job position of the representative
     * @param company The name of the company
     * @param department The department of the representative
     */
    public CompanyRep(String userID, String name, String password, String email,
                      String position, Company company, String department) {
        // Call parent class (User) constructor
        super(userID, name, password, email);
        this.position = position;
        this.company = company;
        this.department = department;
        this.approved = REP_STATUS.Pending; // Needs approval from Career Center Staff
        this.activeOpps = new ArrayList<>();
        this.allOpps = new ArrayList<>();
    }
    public void createOpp(int oppId) { allOpps.add((Integer) oppId); }

    public void oppApproved(int oppId) { activeOpps.add((Integer) oppId); }

    public int numActiveOpps() { return activeOpps.size(); }


     /* Registers the company representative's account
     * Note: Account must be approved by Career Center Staff before login
     */
    /* public void registerCompany() {
        System.out.println("Registration request submitted for " + getName());
        System.out.println("Company: " + this.company);
        System.out.println("Awaiting approval from Career Center Staff...");
    }
    
    /* COMMENTED OUT - Uncomment when InternshipOpportunity class is ready
    public boolean createOpportunity(InternshipOpportunity opportunity) {
        if (!this.approved) {
            System.out.println("Cannot create opportunity. Account not yet approved.");
            return false;
        }
        
        if (!isLoggedIn()) {
            System.out.println("You must be logged in to create opportunities.");
            return false;
        }
        
        System.out.println("Internship opportunity created: " + opportunity.getTitle());
        System.out.println("Status: Pending approval from Career Center Staff");
        return true;
    }
    
    public void toggleVisibility(InternshipOpportunity opportunity, boolean visible) {
        if (!isLoggedIn()) {
            System.out.println("You must be logged in to toggle visibility.");
            return;
        }
        
        opportunity.setVisible(visible);
        String status = visible ? "visible" : "hidden";
        System.out.println("Opportunity '" + opportunity.getTitle() + 
                         "' is now " + status);
    }
    */
    
    /* COMMENTED OUT - Uncomment when Application class is ready
    public void reviewApplications(List<Application> applications) {
        if (!isLoggedIn()) {
            System.out.println("You must be logged in to review applications.");
            return;
        }
        
        System.out.println("\n=== Reviewing Applications ===");
        for (Application app : applications) {
            System.out.println("Student: " + app.getStudentID());
            System.out.println("Status: " + app.getStatus());
            System.out.println("---");
        }
    }
    
    public void approveApplication(Application application) {
        if (!isLoggedIn()) {
            System.out.println("You must be logged in to approve applications.");
            return;
        }
        
        application.markSuccessful();
        System.out.println("Application approved for student: " + 
                         application.getStudentID());
    }
    
    public void rejectApplication(Application application) {
        if (!isLoggedIn()) {
            System.out.println("You must be logged in to reject applications.");
            return;
        }
        
        application.markUnsuccessful();
        System.out.println("Application rejected for student: " + 
                         application.getStudentID());
    }
    
    public void updateDecisionApplication(Application application, boolean decision) {
        if (decision) {
            approveApplication(application);
        } else {
            rejectApplication(application);
        }
    }
    */
    
    // Getter methods
    /**
     * Gets the position of the representative
     * @return The job position
     */
    public String getPosition() {
        return position;
    }

    public List<Integer> getActiveOpps() { return activeOpps; }

    public List<Integer> getAllOpps() { return allOpps; }
    /**
     * Gets the company name
     * @return The name of the company
     */
    public Company getCompany() {
        return company;
    }
    
    /**
     * Gets the department
     * @return The department name
     */
    public String getDepartment() {
        return department;
    }
    
    /**
     * Checks if the representative is approved
     * @return true if approved, false otherwise
     */
    public REP_STATUS isApproved() {
        return approved;
    }
    
    // Setter methods
    /**
     * Sets the position
     * @param position The new position
     */
    public void setPosition(String position) {
        this.position = position;
    }
    
    /**
     * Sets the company name
     * @param company The new company name
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Sets the department
     * @param department The new department
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Sets the approval status
     * @param approved The approval status
     */
    public void setApproved(boolean approved) {
        if (approved) {
            this.approved = REP_STATUS.Approved;
        } else {
            this.approved = REP_STATUS.Rejected;
        }
    }
    
    /**
     * Returns a string representation of the company representative
     * @return String containing representative information
     */
    @Override
    public String toString() {
        return "CompanyRep{" +
                "userID='" + getUserID() + '\'' +
                ", name='" + getName() + '\'' +
                ", position='" + position + '\'' +
                ", company='" + company.getName() + '\'' +
                ", department='" + department + '\'' +
                ", approved=" + approved +
                '}';
    }
}