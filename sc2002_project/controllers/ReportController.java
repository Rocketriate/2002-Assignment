package sc2002_project.controllers;

import sc2002_project.entities.InternshipOpportunity;
import sc2002_project.db.OpportunityDB;
import sc2002_project.entities.ReportCriteria;
import sc2002_project.db.UserDB;

import java.util.Comparator;
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
            System.out.println("Generating filtered internship opportunity report...");
            return all.stream()
                    .filter(opp -> criteria.getStatus() == null
                            || opp.getStatus() == criteria.getStatus())
                    .filter(opp -> criteria.getMinApproved() == 0
                            || opp.getApprovedCount() >= criteria.getMinApproved())
                    .filter(opp -> criteria.getPreferredMajor() == null
                            || (opp.getPreferredMajor() != null
                            && equalsIgnoreCaseSafe(criteria.getPreferredMajor(), opp.getPreferredMajor())))
                    .filter(o -> criteria.getPreferredLevel() == null
                            || o.getLevel() == criteria.getPreferredLevel())
                    .filter(o -> criteria.getCompanyName() == null
                            || equalsIgnoreCaseSafe(criteria.getCompanyName(), o.getCompanyName()))
                    .sorted(Comparator.comparing((InternshipOpportunity::getTitle), String.CASE_INSENSITIVE_ORDER))
                    .collect(Collectors.toList());
        } else {
            System.out.println("Error: User not authorised to generate a report");
            return null;
        }
    }

    private boolean equalsIgnoreCaseSafe(String a, String b) { return a != null && a.equalsIgnoreCase(b); }

    public ReportCriteria updateFilters(ReportCriteria criteria) {
        criteria.updateFilters();
        return criteria;
    }
    public void printReport(List<InternshipOpportunity> list) {
        System.out.println("\n=== Internship Opportunities Staff Report ===");
        if (list.isEmpty()) {
            System.out.println("No opportunities found matching the specified criteria.\n");
            return;
        }

        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.printf("%-4s | %-30s | %-15s | %-12s | %-12s | %-10s%n",
                "ID", "Title", "Company", "Level", "OppStatus", "Approved");
        System.out.println("--------------------------------------------------------------------------------------------------");

        for (InternshipOpportunity opp : list) {
            System.out.printf("%-4d | %-30s | %-15s | %-12s | %-12s | %-10d%n",
                    opp.getOppId(),
                    opp.getTitle(),
                    opp.getCompanyName(),
                    opp.getLevel(),
                    opp.getStatus(),
                    opp.getApprovedCount());
        }
        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.println("Total Opportunities in Report: " + list.size());
        System.out.println("Report completed.");
    }
}

