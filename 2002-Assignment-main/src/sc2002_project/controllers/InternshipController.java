package sc2002_project.controllers;

import enums.INTERNSHIP_LEVEL;
import enums.REP_STATUS;
import sc2002_project.db.OpportunityDB;
import sc2002_project.db.UserDB;
import sc2002_project.entities.CompanyRep;
import sc2002_project.entities.FilterOptions;
import sc2002_project.entities.InternshipOpportunity;

import java.util.*;

public class InternshipController {
    private final OpportunityDB oppDB;
    private final UserDB userDB;
    private final Scanner sc = new Scanner(System.in);

    public InternshipController(OpportunityDB oppDB, UserDB userDB) {
        this.oppDB = oppDB;
        this.userDB = userDB;
    }

    public void createOpp(String repId) {
        if (userDB.findRep(repId) instanceof CompanyRep rep && rep.isApproved() == REP_STATUS.Approved) {
            if (rep.numActiveOpps() <=4) {
                Random num = new Random();
                int oppId = num.nextInt(100);
                while (oppDB.findOpp(oppId) != null) {
                    oppId = num.nextInt(100);
                }
                System.out.println("Internship creation interface:");
                System.out.println("Enter title of internship:");
                String title = sc.nextLine().trim();
                System.out.println("Input internship level:");
                System.out.println("1: Basic");
                System.out.println("2: Intermediate");
                System.out.println("3: Advanced");
                boolean levelChoice = true;
                while (levelChoice) {
                    String choice = sc.nextLine().trim();
                    INTERNSHIP_LEVEL level = null;
                    switch (choice) {
                        case "1":
                            level = INTERNSHIP_LEVEL.Basic;
                            levelChoice = false;
                            break;
                        case "2":
                            level = INTERNSHIP_LEVEL.Intermediate;
                            levelChoice = false;
                            break;
                        case "3":
                            level = INTERNSHIP_LEVEL.Advanced;
                            levelChoice = false;
                            break;
                        default:
                            System.out.println("Invalid choice, input level again:");
                            break;
                    }

                    System.out.println("Input closing date of applications (YYYY-MM-DD)");
                    String closingDate = sc.nextLine().trim();
                    InternshipOpportunity newOpp = new InternshipOpportunity(oppId, title, level, closingDate, rep.getCompany());
                    oppDB.addOpp(newOpp);
                    rep.createOpp(oppId);
                    System.out.println("Indicate a preferred major for applicants? (Optional, Y/N):");
                    choice = sc.nextLine().trim();
                    boolean operation = true;
                    while (operation) {
                        if (choice.equalsIgnoreCase("y")) {
                            System.out.println("Preferred major by internship opportunity:");
                            System.out.println("1: Data Science & AI");
                            System.out.println("2: Computer Science");
                            System.out.println("3: Information Engineering & Media");
                            System.out.println("4: Computer Engineering");
                            String major = sc.nextLine().trim();

                            switch (major) {
                                case "1":
                                    newOpp.setPreferredMajor("Data Science & AI");
                                    operation = false;
                                    break;
                                case "2":
                                    newOpp.setPreferredMajor("Computer Science");
                                    operation = false;
                                    break;
                                case "3":
                                    newOpp.setPreferredMajor("Information Engineering & Media");
                                    operation = false;
                                    break;
                                case "4":
                                    newOpp.setPreferredMajor("Computer Engineering");
                                    operation = false;
                                    break;
                            }
                        } else if (choice.equalsIgnoreCase("n")) {
                            System.out.println("No preferred major will be set for the opportunity");
                            operation = false;
                            break;
                        } else {
                            System.out.println("Error: Invalid choice, please try again");
                            operation = false;
                        }
                    }

                    System.out.println("Enter a short description of the internship (Optional):");
                    newOpp.setDescription(sc.nextLine().trim());
                }
            } else {
                    System.out.println("Error: Maximum number of active internship opportunities reached");
                }
        } else {
            System.out.println("Error: User is not authorised to create an internship opportunity");
        }
    }

    public InternshipOpportunity findOpp(int oppId) { return oppDB.findOpp(oppId); }

    public void toggleVisible (String repId) {
        if (userDB.findRep(repId) instanceof CompanyRep rep && rep.isApproved() == REP_STATUS.Approved) {
            System.out.println("Please enter the ID of the internship opportunity you wish to change the visibility of");
            int oppId = sc.nextInt();
            if (rep.getActiveOpps().contains(oppId)) {
                InternshipOpportunity opp = oppDB.findOpp(oppId);
                opp.setVisible(!opp.isVisible());
                System.out.println("Visibility of internship opportunity of ID " + oppId + " has set to " + opp.isVisible());
                }
            } else {
            System.out.println("Error: Internship opportunity either does not exist, or cannot be visible in the first place");
            }
        }

    public List<InternshipOpportunity> listVisible (String userId, FilterOptions filter) {
        if (userDB.findStudent(userId) != null) {
            return oppDB.filterOpp(filter);
        } else {
            System.out.println("Error: User is not authorised to view internships");
            return null;
        }
    }

    public boolean checkOpp(int oppId) {
        if (this.findOpp(oppId) instanceof InternshipOpportunity currentOpp) {
            currentOpp.printDetails();
            return true;
        } else {
            System.out.println("Error: Internship opportunity under ID " + oppId + " does not exist");
            return false;
        }
    }
    public void filterView(String activeId, FilterOptions currentFilter) {
        if (this.listVisible(activeId, currentFilter) != null) {
            System.out.println("Reference ID " + "Title " + "Company Name");
            for (InternshipOpportunity opp:this.listVisible(activeId, currentFilter)) {
                System.out.println(opp.getOppId() + opp.getTitle() + opp.getCompanyName());
            }
        } else {
            System.out.println("Error: No opportunities under filter settings found");
        }
    }

    public void printOppList(String repId) {
        if (userDB.findRep(repId) instanceof CompanyRep rep) {
            if (!rep.getAllOpps().isEmpty()) {
                for (Integer oppId : rep.getAllOpps()) {
                    this.checkOpp(oppId);
                }
            } else {
                System.out.println("Error: User has no internship opportunities registered");
            }
        } else {
            System.out.println("Error: User does not have permission to check created internship opportunities");
        }

    };

}
