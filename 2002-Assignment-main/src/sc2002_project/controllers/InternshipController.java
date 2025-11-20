package sc2002_project.controllers;

import enums.INTERNSHIP_LEVEL;
import enums.OPPORTUNITY_STATUS;
import enums.REP_STATUS;
import sc2002_project.db.ApplicationDB;
import sc2002_project.db.OpportunityDB;
import sc2002_project.db.UserDB;
import sc2002_project.entities.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class InternshipController {
    private final OpportunityDB oppDB;
    private final ApplicationDB appDB;
    private final UserDB userDB;
    private final Scanner sc = new Scanner(System.in);

    public InternshipController(OpportunityDB oppDB, ApplicationDB appDB, UserDB userDB) {
        this.oppDB = oppDB;
        this.appDB = appDB;
        this.userDB = userDB;
    }

    public void createOpp(String repId) {
        if (!(userDB.findRep(repId) instanceof CompanyRep rep)) {
            System.out.println("Error: User not found or is not a company representative.");
            return;
        }
        if (rep.isApproved() != REP_STATUS.Approved) {
            System.out.println("Error: Representative is not approved to create opportunities.");
            return;
        }
        if (rep.numActiveOpps() > 4) { // Logic check: max 5 active opportunities
            System.out.println("Error: Maximum number of active internship opportunities reached.");
            return;
        }

        try {
            Random num = new Random();
            int oppId;
            do {
                oppId = num.nextInt(100);
            } while (oppDB.findOpp(oppId) != null);

            System.out.println("--- Internship Creation Interface ---");

            System.out.println("Enter title of internship:");
            String title = sc.nextLine().trim();
            while(title.isEmpty()) {
                System.out.println("Title cannot be empty. Please enter title:");
                title = sc.nextLine().trim();
            }

            INTERNSHIP_LEVEL level = null;
            while (level == null) {
                System.out.println("Input internship level (1: Basic, 2: Intermediate, 3: Advanced):");
                String choice = sc.nextLine().trim();
                switch (choice) {
                    case "1" -> level = INTERNSHIP_LEVEL.Basic;
                    case "2" -> level = INTERNSHIP_LEVEL.Intermediate;
                    case "3" -> level = INTERNSHIP_LEVEL.Advanced;
                    default -> System.out.println("Invalid choice. Please input '1', '2', or '3'.");
                }
            }

            System.out.println("Input closing date of applications (YYYY-MM-DD):");
            LocalDate closingDate = readDateInput("Date in YYYY-MM-DD format:");

            InternshipOpportunity newOpp = new InternshipOpportunity(oppId, title, level, closingDate, rep.getCompany(), rep.getUserID());
            System.out.println("Input opening date of applications? (YYYY-MM-DD, Leave blank for today's date)");
            newOpp.setOpeningDate(sc.nextLine().trim());
            oppDB.addOpp(newOpp);
            rep.createOpp(oppId);

            String major = null;
            while (true) {
                System.out.println("Indicate a preferred major? (Y/N):");
                String choice = sc.nextLine().trim().toUpperCase();
                if (choice.equals("N")) {
                    System.out.println("No preferred major set.");
                    break;
                } else if (choice.equals("Y")) {
                    System.out.println("Select Preferred Major:");
                    System.out.println("1: Data Science & AI");
                    System.out.println("2: Computer Science");
                    System.out.println("3: Information Engineering & Media");
                    System.out.println("4: Computer Engineering");

                    String majorChoice = sc.nextLine().trim();
                    switch (majorChoice) {
                        case "1" -> major = "Data Science & AI";
                        case "2" -> major = "Computer Science";
                        case "3" -> major = "Information Engineering & Media";
                        case "4" -> major = "Computer Engineering";
                        default -> System.out.println("Invalid selection. Please try again.");
                    }
                    if (major != null) {
                        newOpp.setPreferredMajor(major);
                        break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                }
            }

            System.out.println("Enter a short description (Optional, press Enter to skip):");
            String description = sc.nextLine().trim();
            if (!description.isEmpty()) {
                newOpp.setDescription(description);
            }

            System.out.println("Internship Opportunity '" + title + "' created successfully (ID: " + oppId + ").");

        } catch (Exception e) {
            System.out.println("An unexpected error occurred during creation: " + e.getMessage());
        }
    }

    private void checkOpp(int oppId) {
        InternshipOpportunity opp = oppDB.findOpp(oppId);
        if (opp != null) {
            opp.printDetails();
        } else {
            System.out.println("Opportunity ID " + oppId + " not found.");
        }
    }

    public void toggleVisible(String repId) {
        if (userDB.findRep(repId) instanceof CompanyRep rep && rep.isApproved() == REP_STATUS.Approved) {
            System.out.println("Please enter the ID of the internship opportunity you wish to change the visibility of");
            int oppId = readIntInput("Enter reference ID: ");
            if (rep.getActiveOpps().contains(oppId)) {
                InternshipOpportunity opp = oppDB.findOpp(oppId);
                opp.setVisible(!opp.isVisible());
                System.out.println("Visibility of internship opportunity of ID " + oppId + " has set to " + opp.isVisible());
            }
        } else {
            System.out.println("Error: Internship opportunity either does not exist, or cannot be visible in the first place");
        }
    }

    public List<InternshipOpportunity> listVisible(String userId, FilterOptions filter) {
        if (userDB.findStudent(userId) != null) {
            return oppDB.filterOpp(filter);
        } else {
            System.out.println("Error: User is not authorised to view internships");
            return new ArrayList<>();
        }
    }
    public void filterView(String activeId, FilterOptions currentFilter) {
        if (!this.listVisible(activeId, currentFilter).isEmpty()) {
            System.out.println("Filtered Internship Opportunities:");
            for (InternshipOpportunity opp : this.listVisible(activeId, currentFilter)) {
                opp.printDetails();
            }
        } else {
            System.out.println("Error: No opportunities under filter settings found");
        }
    }

    public void checkCreatedOpps(String repId) {
        if (userDB.findRep(repId) instanceof CompanyRep rep) {

            System.out.println("--- Your Created Opportunities ---");
             List<Integer> opps = rep.getAllOpps();
             if (opps.isEmpty()) {
                 System.out.println("No opportunities created yet.");
             } else {
                 for (Integer id : opps) {
                     checkOpp(id);
                 }
             }
        } else {
            System.out.println("Error: User is not authorized.");
        }
    }

    private LocalDate readDateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("none")) {
                return null;
            }

            try {
                // Attempt to parse the input string into a LocalDate object
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                // Catch the specific exception thrown if the format is wrong
                System.out.println("Error: Invalid date format. Please use YYYY-MM-DD (e.g., 2026-06-15) or type 'none'.");
            }
        }
    }

    public void appView(String repId) {
        System.out.println("Please enter the ID of the internship opportunity you wish to view.");
        int oppId = readIntInput("Enter reference ID:");
        if (oppId == -1) {
            System.out.println("Operation cancelled.");
            return;
        }

        InternshipOpportunity targetOpp = oppDB.findOpp(oppId);
        if (targetOpp != null) {
            User user = userDB.findRep(repId);
            if (user instanceof CompanyRep rep && targetOpp.getCompanyName().equals(rep.getCompany().getName())) {

                System.out.println("Applications for: " + targetOpp.getTitle());
                List<Integer> appList = targetOpp.getAppList();

                if (appList == null || appList.isEmpty()) {
                    System.out.println("No applications have been received for this internship opportunity.");
                } else {
                    for (Integer appId : appList) {
                        Application app = appDB.findApp(appId);
                        if (app != null && userDB.findStudent(app.getUserId()) instanceof Student applicant) {
                           app.printApp();
                           applicant.printDetails();

                        }
                    }
                }
            } else {
                System.out.println("Error: You do not have permission to view applications for this opportunity.");
            }
        } else {
            System.out.println("Error: Internship opportunity under ID " + oppId + " does not exist.");
        }
    }

    public void editOpp(String repId) {
        if (userDB.findRep(repId) instanceof CompanyRep rep) {
            System.out.println("Please enter the ID of the opportunity you wish to edit.");
            int oppId = readIntInput("Enter reference ID:");
            if (oppId == -1) {
                System.out.println("Operation cancelled.");
                return;
            }
            InternshipOpportunity targetOpp = oppDB.findOpp(oppId);
            if (targetOpp != null && targetOpp.getStatus() == OPPORTUNITY_STATUS.Pending) {
                if (targetOpp.getRepresentativeInCharge().equals(repId)) {
                    checkOpp(oppId);
                    boolean operation = true;
                    while (operation) {
                        System.out.println("What do you wish to change? (Note: Edits are only possible before the internship opportunity is approved by staff.)");
                        System.out.println("1. Title");
                        System.out.println("2. Description");
                        System.out.println("3. Internship level");
                        System.out.println("4. Preferred Major");
                        System.out.println("5. Closing Date");
                        System.out.println("6. Delete this internship opportunity");
                        System.out.println("7. Finish editing internship opportunity");
                        String choice = sc.nextLine().trim();
                        switch (choice) {
                            case "1":
                                System.out.println("Enter your new title: ");
                                String title = sc.nextLine().trim();
                                while (title.isEmpty()) {
                                    System.out.println("Title cannot be empty. Please enter title: ");
                                    title = sc.nextLine().trim();
                                }
                                targetOpp.setTitle(title);
                                break;
                            case "2":
                                System.out.println("Enter your new description: ");
                                String description = sc.nextLine().trim();
                                while (description.isEmpty()) {
                                    System.out.println("Description cannot be empty. Please enter description: ");
                                    description = sc.nextLine().trim();
                                }
                                targetOpp.setDescription(description);
                                break;
                            case "3":
                                INTERNSHIP_LEVEL level = null;
                                while (level == null) {
                                    System.out.println("Input internship level (1: Basic, 2: Intermediate, 3: Advanced):");
                                    String levelChoice = sc.nextLine().trim();
                                    switch (levelChoice) {
                                        case "1" -> level = INTERNSHIP_LEVEL.Basic;
                                        case "2" -> level = INTERNSHIP_LEVEL.Intermediate;
                                        case "3" -> level = INTERNSHIP_LEVEL.Advanced;
                                        default -> System.out.println("Invalid choice. Please input '1', '2', or '3'.");
                                    }
                                }
                                targetOpp.setLevel(level);
                                break;
                            case "4":
                                String major = "0";
                                while (major.equals("0")) {
                                    System.out.println("Select Preferred Major:");
                                    System.out.println("1: Data Science & AI");
                                    System.out.println("2: Computer Science");
                                    System.out.println("3: Information Engineering & Media");
                                    System.out.println("4: Computer Engineering");
                                    String majorChoice = sc.nextLine().trim();
                                    switch (majorChoice) {
                                        case "1" -> major = "Data Science & AI";
                                        case "2" -> major = "Computer Science";
                                        case "3" -> major = "Information Engineering & Media";
                                        case "4" -> major = "Computer Engineering";
                                        default -> System.out.println("Invalid selection. Please try again.");
                                    }
                                }
                                targetOpp.setPreferredMajor(major);
                                break;
                            case "5":
                                System.out.println("Input closing date of applications (YYYY-MM-DD):");
                                LocalDate closingDate = readDateInput("Date in YYYY-MM-DD format:");
                                targetOpp.setClosingDate(closingDate);
                                break;
                            case "6":
                                targetOpp.printDetails();
                                System.out.println("Are you sure you want to delete this internship opportunity?");
                                while (true) {
                                    String deleteChoice = sc.nextLine().trim().toUpperCase();
                                    if (deleteChoice.equals("N")) {
                                        System.out.println("Internship opportunity was not deleted.");
                                        break;
                                    } else if (deleteChoice.equals("Y")) {
                                        oppDB.removeOpp(targetOpp.getOppId());
                                        rep.removeOpp((targetOpp.getOppId()));
                                        System.out.println("Internship opportunity with title " + targetOpp.getTitle() + " has been deleted.");
                                        break;
                                    } else {
                                        System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                                    }
                                }
                                break;
                            case "7":
                                System.out.println("Internship application has been updated as follows: ");
                                targetOpp.printDetails();
                                operation = false;
                                break;
                            default:
                                System.out.println("Invalid choice. Please enter a number from '1' to '7'.");
                                break;
                            }
                        }
                    } else {
                        System.out.println("Error: User is not in charge of this internship opportunity.");
                        }
                } else {
                    System.out.println("Error: Internship opportunity does not exist, or cannot be edited.");
                    }
            } else {
            System.out.println("Error: User does not have permission to edit opportunities.");
        }

    }

    private int readIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("CANCEL")) {
                return -1; // Use -1 as a sentinel value for cancellation
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number or type 'cancel'.");
            }
        }
    }
}