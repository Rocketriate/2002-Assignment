package sc2002_project.entities;

import enums.*;

import java.util.List;
import java.util.ArrayList;

public class CompanyRep extends User {
    private String position;
    private Company company;
    private String department;
    private REP_STATUS approved;
    private String email;
    private List<Integer> activeOpps;
    private List<Integer> allOpps;

    public CompanyRep(String userID, String name, String password, String email,
                      String position, Company company, String department) {
        super(userID, name, password, email);
        this.position = position;
        this.company = company;
        this.department = department;
        this.approved = REP_STATUS.Pending; // Needs approval from Career Center Staff
        this.activeOpps = new ArrayList<>();
        this.allOpps = new ArrayList<>();
    }
    public void createOpp(int oppId) { allOpps.add(oppId); }

    public void removeOpp(int oppId) {
        if (allOpps.contains(oppId)) {
            allOpps.remove(oppId);
        }
    }

    public void oppApproved(int oppId) { activeOpps.add(oppId); }

    public int numActiveOpps() { return activeOpps.size(); }

    public String getPosition() {
        return position;
    }

    public List<Integer> getActiveOpps() { return activeOpps; }

    public List<Integer> getAllOpps() { return allOpps; }

    public Company getCompany() {
        return company;
    }

    public String getDepartment() {
        return department;
    }

    public REP_STATUS isApproved() {
        return approved;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setApproved(boolean approved) {
        if (approved) {
            this.approved = REP_STATUS.Approved;
        } else {
            this.approved = REP_STATUS.Rejected;
        }
    }

    public void printDetails() {
        System.out.println("-----------------------------------------");
        System.out.println("Rep ID:           " + this.getUserID());
        System.out.println("Name:             " + this.getName());
        System.out.println("Email:            " + this.getEmail());
        System.out.println("Position:         " + this.getPosition());
        System.out.println("Company:          " + this.getCompany().getName());
        System.out.println("Active Apps:      " + this.isApproved());
        System.out.println("-----------------------------------------");
    }

}