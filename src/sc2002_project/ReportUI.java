import java.util.List;
import java.util.Scanner;

public class ReportUI {
    private final ReportController reportController;
    private final Scanner sc = new Scanner(System.in);

    public ReportUI(ReportController reportController){
        this.reportController = reportController;
    }

    public void generateReport(){
        OpportunityStatus oppStatus = null;
        ApplicationStatus appStatus = null;
        String major = null;
        Level level = null;
        String company = null;

        /*System.out.print("Filter by Opportunity status? (y/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.print("  Enter status (PENDING/APPROVED/REJECTED/FILLED): ");
            status = sc.nextLine().trim();
        }

        System.out.print("Filter by Application status? (y/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.print("  Enter status (PENDING/SUCCESSFUL/UNSUCCESSFUL): ");
            status = sc.nextLine().trim();
        }

        System.out.print("Filter by major? (y/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.print("  Enter major (e.g. CSC): ");
            major = sc.nextLine().trim();
        }

        System.out.print("Filter by level? (y/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.print("  Enter level (BASIC/INTERMEDIATE/ADVANCED): ");
            level = sc.nextLine().trim();
        }

        System.out.print("Filter by company? (y/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.print("  Enter company: ");
            company = sc.nextLine().trim();
        }
        */

         // build criteria using whatever the user actually filled in
        
        java.util.Set<String> validKeys = new java.util.LinkedHashSet<>(
            java.util.List.of("oppstatus", "appstatus", "level", "major", "company")
        );

        while (true) {
            System.out.println("\nWhat do you want to filter by?");
            System.out.println(" - Type one of: oppstatus, appstatus, level, major, company");
            System.out.println(" - Or type: all, none, done");
            System.out.print("> ");

            String choice = sc.nextLine().trim().toLowerCase();

            if (choice.isBlank()) { System.out.println("  Invalid input. Try again."); continue; }
            if (choice.equals("none")) break;   // no filters
            if (choice.equals("done")) break;   // finish with whatever is set

            if (choice.equals("all")) {
                if (oppStatus != null && !askYesNo("oppstatus is " + oppStatus + ". Change it? (y/n): ")) { /* keep */ }
                else oppStatus = askOpportunityStatus();

                if (appStatus != null && !askYesNo("appstatus is " + appStatus + ". Change it? (y/n): ")) { /* keep */ }
                else appStatus = askApplicationStatus();

                if (level != null && !askYesNo("level is " + level + ". Change it? (y/n): ")) { /* keep */ }
                else level = askLevel();

                if (major != null && !askYesNo("major is '" + major + "'. Change it? (y/n): ")) { /* keep */ }
                else major = askTextNonBlankOrSkip("  Enter major (e.g. CSC) [blank = skip]: ");

                if (company != null && !askYesNo("company is '" + company + "'. Change it? (y/n): ")) { /* keep */ }
                else company = askTextNonBlankOrSkip("  Enter company [blank = skip]: ");

                showSelected(oppStatus, appStatus, level, major, company);
                break;
            }

            if (!validKeys.contains(choice)) {
                System.out.println("  Invalid filter name. Allowed: oppstatus, appstatus, level, major, company, or 'all/none/done'.");
                continue;
            }

            switch (choice) {
                case "oppstatus" -> oppStatus = askOpportunityStatus();
                case "appstatus" -> appStatus = askApplicationStatus();
                case "level"     -> level = askLevel();
                case "major"     -> major = askTextNonBlankOrSkip("  Enter major (e.g. CSC) [blank = skip]: ");
                case "company"   -> company = askTextNonBlankOrSkip("  Enter company [blank = skip]: ");
            }

            System.out.println("  Added filter. Type more, or 'done' to finish.");
        }
        
        
        
        ReportCriteria criteria = new ReportCriteria(oppStatus, appStatus, major, level, company);
        List<InternshipOpportunity> result = reportController.generate(criteria);
        print(result);
    }

   private void print(List<InternshipOpportunity> list) {
        System.out.println("\n=== Internship Opportunities Report ===");
        if (list.isEmpty()) {
            System.out.println("No opportunities found.\n");
            return;
        }
        System.out.println("ID | Title | Company | Level | OppStatus | AppStatus");
        for (InternshipOpportunity opp : list) {
            System.out.printf("%s | %s | %s | %s | %s | %s%n",
                    opp.getId(),
                    opp.getTitle(),
                    opp.getCompanyName(),
                    opp.getLevel(),                // Level enum
                    opp.getOpportunityStatus(),    // OpportunityStatus enum
                    opp.getApplicationStatus());   // ApplicationStatus enum
        }
        System.out.println("Total: " + list.size());
    }


    private String askTextNonBlankOrSkip(String prompt) {
    System.out.print(prompt);
    String s = sc.nextLine();
    if (s == null || s.isBlank()) return null; // skip
    return s.trim();
}

    // shows current selections each time you loop
    private void showSelected(OpportunityStatus oppStatus, ApplicationStatus appStatus,
                            Level level, String major, String company) {
        System.out.println("\nCurrent filters:");
        System.out.println("  oppstatus : " + (oppStatus == null ? "-" : oppStatus));
        System.out.println("  appstatus : " + (appStatus == null ? "-" : appStatus));
        System.out.println("  level     : " + (level == null ? "-" : level));
        System.out.println("  major     : " + (major == null ? "-" : major));
        System.out.println("  company   : " + (company == null ? "-" : company));
    }

    // simple yes/no prompt (loops until y or n)
    private boolean askYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim().toLowerCase();
            if (s.equals("y")) return true;
            if (s.equals("n")) return false;
            System.out.println("  Please type y or n.");
        }
    }



    private OpportunityStatus askOpportunityStatus() {
        while (true) {
            System.out.print("  Enter OPPORTUNITY status (PENDING/APPROVED/REJECTED/FILLED): ");
            String s = sc.nextLine().trim().toUpperCase();
            try { return OpportunityStatus.valueOf(s); }
            catch (Exception e) { System.out.println("  Invalid. Try again."); }
        }
    }

    private ApplicationStatus askApplicationStatus() {
        while (true) {
            System.out.print("  Enter APPLICATION status (PENDING/SUCCESSFUL/UNSUCCESSFUL): ");
            String s = sc.nextLine().trim().toUpperCase();
            try { return ApplicationStatus.valueOf(s); }
            catch (Exception e) { System.out.println("  Invalid. Try again."); }
        }
    }

    private Level askLevel() {
        while (true) {
            System.out.print("  Enter level (BASIC/INTERMEDIATE/ADVANCED): ");
            String s = sc.nextLine().trim().toUpperCase();
            try { return Level.valueOf(s); }
            catch (Exception e) { System.out.println("  Invalid. Try again."); }
        }
    }

}
