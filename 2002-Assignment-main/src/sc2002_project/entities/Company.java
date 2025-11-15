package sc2002_project.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Company in the Internship Placement Management System.
 * A company can have multiple company representatives and internship opportunities.
 * 
 * @author Your Name
 * @version 1.0
 */
public class Company {
    // Private attributes
    private int companyId;
    private String name;
    private List<CompanyRep> reps;
    // Commented out temporarily until InternshipOpportunity class is created
    private List<InternshipOpportunity> opps;
    
    /**
     * Constructor to create a new Company
     * @param name The name of the company
     */
    public Company(int companyId, String name) {
        this.companyId = companyId;
        this.name = name;
        // Initialize empty lists for representatives
        this.reps = new ArrayList<>();
        // this.opportunities = new ArrayList<>();
    }
    
    /**
     * Adds a company representative to this company
     * @param rep The CompanyRep to add
     */
    public void addRep(CompanyRep rep) {
        if (!reps.contains(rep)) {
            reps.add(rep);
            System.out.println("Representative " + rep.getName() + " added to " + this.name);
        } else {
            System.out.println("Representative already exists in this company.");
        }
    }
    
    /**
     * Removes a company representative from this company
     * @param rep The CompanyRep to remove
     */
    public void removeRep(CompanyRep rep) {
        if (reps.remove(rep)) {
            System.out.println("Representative " + rep.getName() + " removed from " + this.name);
        } else {
            System.out.println("Representative not found in this company.");
        }
    }

    public void addOpportunity(InternshipOpportunity opportunity) {
        if (!opps.contains(opportunity)) {
            opps.add(opportunity);
            System.out.println("Internship opportunity added: " + opportunity.getTitle());
        } else {
            System.out.println("Opportunity already exists.");
        }
    }
    
    public void removeOpportunity(InternshipOpportunity opportunity) {
        if (opps.remove(opportunity)) {
            System.out.println("Opportunity removed: " + opportunity.getTitle());
        } else {
            System.out.println("Opportunity not found.");
        }
    }

    
    /**
     * Gets the company name
     * @return The name of the company
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the company name
     * @param name The new company name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the list of company representatives
     * @return List of CompanyRep objects
     */
    public List<CompanyRep> getReps() {
        return reps;
    }
    
    /* COMMENTED OUT - Uncomment when InternshipOpportunity class is ready
    public List<InternshipOpportunity> getOpps() {
        return opportunities;
    }
    */
    
    /**
     * Displays all representatives of this company
     */
    public void displayRepresentatives() {
        System.out.println("\n=== Representatives of " + this.name + " ===");
        if (reps.isEmpty()) {
            System.out.println("No representatives registered.");
        } else {
            for (int i = 0; i < reps.size(); i++) {
                CompanyRep rep = reps.get(i);
                System.out.println((i + 1) + ". " + rep.getName() + 
                                 " (" + rep.getPosition() + ")");
            }
        }
    }

    public void displayOpportunities() {
        System.out.println("\n=== Opportunities at " + this.name + " ===");
        if (opps.isEmpty()) {
            System.out.println("No opportunities available.");
        } else {
            for (int i = 0; i < opps.size(); i++) {
                InternshipOpportunity opp = opps.get(i);
                System.out.println((i + 1) + ". " + opp.getTitle() + 
                                 " - Level: " + opp.getLevel());
            }
        }
    }
    
    /**
     * Returns a string representation of the company
     * @return String containing company information
     */
    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", representatives=" + reps.size() +
                ", opportunities=" + opps.size() +
                '}';
    }
}