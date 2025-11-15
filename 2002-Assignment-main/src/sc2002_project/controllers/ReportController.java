package sc2002_project.controllers;

import sc2002_project.entities.InternshipOpportunity;
import sc2002_project.db.OpportunityDB;
import sc2002_project.entities.ReportCriteria;
import sc2002_project.db.UserDB;

import java.util.List;
import java.util.stream.Collectors;

public class ReportController {
    private final OpportunityDB oppDB;
    private final UserDB userDB;
    private ReportCriteria criteria;

    public ReportController(OpportunityDB oppDB, UserDB userDB){
        this.oppDB = oppDB;
        this.userDB = userDB;
    }

    public List<InternshipOpportunity> generate(String userId, ReportCriteria criteria) {
        List<InternshipOpportunity> all = oppDB.getAll();
        if (userDB.findStaff(userId) != null) {
            return all.stream()
                    .filter(opp -> criteria.getOppStatus() == null
                            || opp.getStatus() == criteria.getOppStatus())
                    .filter(opp -> criteria.getMinApproved() == 0
                            || opp.getApprovedCount() >= criteria.getMinApproved())
                    .filter(opp -> criteria.getPreferredMajor() == null
                            || (opp.getPreferredMajor() != null
                            && equalsIgnoreCaseSafe(criteria.getPreferredMajor(), opp.getPreferredMajor())))
                    .filter(o -> criteria.getLevel() == null
                            || o.getLevel() == criteria.getLevel())
                    .filter(o -> criteria.getCompanyName() == null
                            || equalsIgnoreCaseSafe(criteria.getCompanyName(), o.getCompanyName()))
                    .collect(Collectors.toList());
        } else {
            System.out.println("Error: User not authorised to generate a report");
            return null;
        }
    }

    private boolean equalsIgnoreCaseSafe(String a, String b) { return a != null && b != null && a.equalsIgnoreCase(b); }

    public ReportCriteria updateFilters(ReportCriteria criteria) {
        criteria.updateFilters();
        return criteria;
    }
}

