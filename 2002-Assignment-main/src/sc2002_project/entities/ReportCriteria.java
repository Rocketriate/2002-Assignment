package sc2002_project.entities;

import enums.*;

import java.util.Scanner;

public class ReportCriteria extends FilterOptions {
    private String userId;
    private OPPORTUNITY_STATUS oppStatus;  // approved, rejected, pending, filled (Q: what abt empty?)
    private String preferredMajor; // csc
    private INTERNSHIP_LEVEL level; // basic, intermediate, advanced
    private String companyName;
    private int minApproved;
    private final Scanner sc = new Scanner(System.in);

    public ReportCriteria(String userId) {
        super(userId);
        this.oppStatus = null;
        this.minApproved = 0;
    }

    public OPPORTUNITY_STATUS getOppStatus() { return oppStatus; }

    public int getMinApproved() { return minApproved; }

    public void updateFilters() {
        boolean operation = true;
        while (operation) {
            System.out.println("Report Criteria (No filters by default):");
            System.out.println("1: Internship Level");
            System.out.println("2: Company Name");
            System.out.println("3: Preferred Major");
            System.out.println("4: Opportunity Status");
            System.out.println("5: Minimum number approved");
            System.out.println("6. Finalise and generate report");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("Set visible level:");
                    System.out.println("1: Basic");
                    System.out.println("2: Intermediate");
                    System.out.println("3: Advanced");
                    String option = sc.nextLine().trim();

                    switch (option) {
                        case "1":
                            this.setInternshipLevel(INTERNSHIP_LEVEL.Basic);
                            break;
                        case "2":
                            this.setInternshipLevel(INTERNSHIP_LEVEL.Intermediate);
                            break;
                        case "3":
                            this.setInternshipLevel(INTERNSHIP_LEVEL.Advanced);
                            break;
                    }
                    break;
                case "2":
                    System.out.println("Input desired company name:");
                    String name = sc.nextLine().trim();
                    this.setCompanyName(name);
                    break;
                case "3":
                    System.out.println("Preferred major by internship opportunity:");
                    System.out.println("1: Data Science & AI");
                    System.out.println("2: Computer Science");
                    System.out.println("3: Information Engineering & Media");
                    System.out.println("4: Computer Engineering");
                    String major = sc.nextLine().trim();

                    switch (major) {
                        case "1":
                            this.setPreferredMajor("Data Science & AI");
                            System.out.println("Preferred major set to Data Science & AI");
                            break;
                        case "2":
                            this.setPreferredMajor("Computer Science");
                            System.out.println("Preferred major set to Computer Science");
                            break;
                        case "3":
                            this.setPreferredMajor("Information Engineering & Media");
                            System.out.println("Preferred major set to Information Engineering & Media");
                            break;
                        case "4":
                            this.setPreferredMajor("Computer Engineering");
                            System.out.println("Preferred major set to Computer Engineering");
                            break;
                        default:
                            System.out.println("Error: Invalid selection. Operation cancelled");
                            break;
                    }
                    break;
                case "4":
                    System.out.println("Set opportunity status:");
                    System.out.println("1. Approved");
                    System.out.println("2. Pending");
                    System.out.println("3. Rejected");
                    System.out.println("4. Filled");
                    String status = sc.nextLine().trim();

                    switch (status) {
                        case "1":
                            this.setStatus(OPPORTUNITY_STATUS.Approved);
                            System.out.println("Filtering opportunity status set to approved");
                            break;
                        case "2":
                            this.setStatus(OPPORTUNITY_STATUS.Pending);
                            System.out.println("Filtering opportunity status set to pending");
                            break;
                        case "3":
                            this.setStatus(OPPORTUNITY_STATUS.Rejected);
                            System.out.println("Filtering opportunity status set to rejected");
                            break;
                        case "4":
                            this.setStatus(OPPORTUNITY_STATUS.Filled);
                            System.out.println("Filtering opportunity status set to filled");
                            break;
                        default:
                            System.out.println("Error: Invalid selection. Operation cancelled");
                            break;
                    }
                    break;
                case "5":
                    System.out.println("Specify minimum number of finalised internship students");
                    this.minApproved = sc.nextInt();
                    System.out.println("Minimum number of finalised internship students set to " + minApproved );
                    break;
                case "6":
                    System.out.println("Report criteria finalised. Generating report...");
                    operation = false;
                    break;
            }
        }
    }
}
