package sc2002_project.entities;

import enums.*;

import java.time.LocalDate;
import java.util.Scanner;

public class FilterOptions {
    private OPPORTUNITY_STATUS status;
    private String preferredMajor;
    private INTERNSHIP_LEVEL preferredLevel;
    private INTERNSHIP_LEVEL maxLevel;
    private LocalDate closingDate;
    private String companyName;
    private User currentUser;
    private int minApproved;
    private final Scanner sc = new Scanner(System.in);

    public FilterOptions(Student student) {
        this.setStatus(OPPORTUNITY_STATUS.Approved);
        if (student.getYearOfStudy() <= 2) {
            this.maxLevel = INTERNSHIP_LEVEL.Basic;
        } else {
            this.maxLevel = INTERNSHIP_LEVEL.Advanced;
        }
        this.currentUser = student;
        this.minApproved = 0;
    }

    public FilterOptions(CareerCentreStaff staff) {
        this.maxLevel = INTERNSHIP_LEVEL.Advanced;
        this.status = null;
        this.currentUser = staff;
        this.minApproved = 0;
    }

    public FilterOptions(CompanyRep rep) {
        this.maxLevel = INTERNSHIP_LEVEL.Advanced;
        this.status = null;
        this.currentUser = rep;
        this.minApproved = 0;
    }

    // accessors
    public OPPORTUNITY_STATUS getStatus() {
        return status;
    }

    public String getPreferredMajor() {
        return preferredMajor;
    }

    public INTERNSHIP_LEVEL getPreferredLevel() {
        return preferredLevel;
    }

    public INTERNSHIP_LEVEL getMaxLevel() {
        return maxLevel;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getMinApproved() {
        return minApproved;
    }

    // mutators
    public void setStatus(OPPORTUNITY_STATUS status) {
        this.status = status;
    }

    public void setPreferredMajor(String preferredMajor) {
        this.preferredMajor = preferredMajor;
    }

    public void setInternshipLevel(INTERNSHIP_LEVEL level) {
        this.preferredLevel = level;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    //methods
    public boolean matches(InternshipOpportunity opportunity) {
        boolean check = true;
        if (this.status != null) {
            if (!this.status.equals(opportunity.getStatus())) {
                return false;
            };
        }
        if (this.preferredMajor != null) {
            if (!this.preferredMajor.equals(opportunity.getPreferredMajor())) {
                return false;
            };
        }
        if (this.preferredLevel != null) {
            if (!this.preferredLevel.equals(opportunity.getLevel())) {
                return false;
            };
        }
        if (this.closingDate != null) {
            if (this.closingDate != opportunity.getClosingDate()) {
                return false;
            };
        }
        if (this.companyName != null) {
            if (!this.companyName.equals(opportunity.getCompanyName())) {
                return false;
            };
        }
        if (this.maxLevel == INTERNSHIP_LEVEL.Basic) {
            if (!this.maxLevel.equals(opportunity.getLevel())) {
                return false;
            };
        }
        if (this.currentUser instanceof Student) {
            if (!opportunity.isVisible()) {
                return false;
            };
        }
        if (this.minApproved != 0) {
            if (opportunity.getApprovedCount() < this.minApproved) {
                return false;
            }
        }

        return true;
    }

    public void updateFilters() {
        boolean operation = true;
        while (operation) {
            System.out.println("Filter Options:");
            System.out.println("1: Internship Level");
            System.out.println("2: Company Name");
            System.out.println("3: Preferred Major");
            System.out.println("4. Opportunity Status (CC Staff and Company Reps Only)");
            System.out.println("5. Minimum number approved (CC Staff and Company Reps Only)");
            System.out.println("6. Finish changing filters");
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
                            if (this.maxLevel == INTERNSHIP_LEVEL.Advanced) {
                                this.setInternshipLevel(INTERNSHIP_LEVEL.Intermediate);
                            } else {
                                System.out.println("Error: User does not have permission to view internships of intermediate and advanced levels");
                            }
                            break;
                        case "3":
                            if (this.maxLevel == INTERNSHIP_LEVEL.Advanced) {
                                this.setInternshipLevel(INTERNSHIP_LEVEL.Advanced);
                            } else {
                                System.out.println("Error: User does not have permission to view internships of intermediate and advanced levels");
                            }
                            break;
                    }
                    break;
                case "2":
                    System.out.println("Input desired company name (Case sensitive):");
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
                            break;
                        case "2":
                            this.setPreferredMajor("Computer Science");
                            break;
                        case "3":
                            this.setPreferredMajor("Information Engineering & Media");
                            break;
                        case "4":
                            this.setPreferredMajor("Computer Engineering");
                            break;
                    }
                    break;
                case "4":
                    if (currentUser instanceof Student) {
                        System.out.println("Error: User not authorised to filter by this parameter");
                    } else {
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
                    }
                case "5":
                    if (currentUser instanceof Student) {
                        System.out.println("Error: User not authorised to filter by this parameter");
                    } else {
                        System.out.println("Specify minimum number of approved internship students");
                        this.minApproved = sc.nextInt();
                        System.out.println("Minimum number of approved internship students set to " + minApproved);
                    }
                    break;
                case "6":
                    System.out.println("Filters have been updated.");
                    operation = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter an option from '1' to '6'.");
                    break;
            }
        }
    }
}
