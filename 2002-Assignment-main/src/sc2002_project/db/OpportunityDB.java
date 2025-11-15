package sc2002_project.db;

import enums.OPPORTUNITY_STATUS;
import sc2002_project.entities.FilterOptions;
import sc2002_project.entities.InternshipOpportunity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpportunityDB {
    private Map<Integer, InternshipOpportunity> oppDB;
    public OpportunityDB() { this.oppDB = new HashMap<>(); }
    public void addOpp(InternshipOpportunity opp) {
        if (opp != null) {
            this.oppDB.put(opp.getOppId(), opp);
            System.out.println("Added: " + opp.getAppList());
        }
    }
    public void removeOpp(int id) {
            InternshipOpportunity removedOpp = this.oppDB.remove(id);
            if (removedOpp != null) {
                System.out.println("Removed " + id + " Successfully");
            } else {
                System.out.println("Unable to find application");
            }
        }

    public List<InternshipOpportunity> getAll() {
        List<InternshipOpportunity> all = new ArrayList<>();
        for (InternshipOpportunity opp:this.oppDB.values()) {
            all.add(opp);
        }
        return all;
    }

    public List<InternshipOpportunity> filterOpp(FilterOptions filter) {
        List<InternshipOpportunity> filterList = new ArrayList<>();
        for (InternshipOpportunity opp:this.oppDB.values()) {
            if (filter.matches(opp)) {
                filterList.add(opp);
            }
        }
        return filterList;
    }

    public List<InternshipOpportunity> pendingOpp() {
        List<InternshipOpportunity> pendingList = new ArrayList<>();
        for (InternshipOpportunity opp:this.oppDB.values()) {
            if (opp.getStatus() == OPPORTUNITY_STATUS.Pending) {
                pendingList.add(opp);
            }
        }
        return pendingList;
    }
    public InternshipOpportunity findOpp(int id) {
        if (this.oppDB.get(id) != null) {
            return this.oppDB.get(id);
        } else {
            System.out.println("Error: No internship opportunity under ID " + id + " was found");
            return null;
        }
    }
    }


