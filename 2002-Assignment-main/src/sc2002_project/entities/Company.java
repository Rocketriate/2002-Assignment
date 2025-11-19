package sc2002_project.entities;

import java.util.ArrayList;
import java.util.List;


public class Company {
    // Private attributes
    private int companyId;
    private final String name;
    private List<CompanyRep> reps;

    public Company(int companyId, String name) {
        this.companyId = companyId;
        this.name = name;
        this.reps = new ArrayList<>();
    }

    public void addRep(CompanyRep rep) {
        if (!reps.contains(rep)) {
            reps.add(rep);
            System.out.println("Representative " + rep.getName() + " added to " + this.name);
        } else {
            System.out.println("Representative already exists in this company.");
        }
    }

    public void removeRep(CompanyRep rep) {
        if (reps.remove(rep)) {
            System.out.println("Representative " + rep.getName() + " removed from " + this.name);
        } else {
            System.out.println("Representative not found in this company.");
        }
    }

    public String getName() {
        return name;
    }

    public List<CompanyRep> getReps() {
        return reps;
    }

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

}