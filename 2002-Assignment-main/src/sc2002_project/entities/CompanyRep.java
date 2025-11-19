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

    public void removeOpp(int oppId) {
        if (allOpps.contains(oppId)) {
            allOpps.remove(oppId);
        }
    }

    public void oppApproved(int oppId) { activeOpps.add((Integer) oppId); }

    public int numActiveOpps() { return activeOpps.size(); }

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