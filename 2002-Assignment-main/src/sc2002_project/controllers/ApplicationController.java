package sc2002_project.controllers;

import enums.*;
import sc2002_project.db.ApplicationDB;
import sc2002_project.db.OpportunityDB;
import sc2002_project.db.UserDB;
import sc2002_project.entities.*;

import java.util.*;
public class ApplicationController {
    private final OpportunityDB oppDB;
    private final ApplicationDB appDB;
    private final UserDB userDB;
    private InternshipOpportunity currentOpp;
    private Application currentApp;

    private final Scanner sc = new Scanner(System.in);

    public ApplicationController(OpportunityDB oppDB, ApplicationDB appDB, UserDB userDB) {
        this.oppDB = oppDB;
        this.appDB = appDB;
        this.userDB = userDB;
    }

    private int readIntInput(String prompt) {
        boolean firstAttempt = true;
        while (true) {
            System.out.print(prompt);
            String input = sc.next().trim();
            if (input.equalsIgnoreCase("CANCEL")) {
                return -1; // Use -1 as a sentinel value for cancellation
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                if (!firstAttempt) {
                    System.out.println("Invalid input. Please enter a valid number or type 'cancel'.");
                }
                firstAttempt = false;
            }
        }
    }

    public void createApp(String studentId) {
        if (userDB.findStudent(studentId) instanceof Student student) {
            System.out.println("Please enter the ID of the internship opportunity you wish to apply for");
            int oppId = readIntInput("Enter reference ID: ");
            if (oppId == -1) {
                System.out.println("Application process cancelled");
                return;
            }
            if (oppDB.findOpp(oppId) instanceof InternshipOpportunity opp
                    && opp.getStatus() == OPPORTUNITY_STATUS.Approved
                    && studentQualified(student, opp)) {
                if (student.numActiveApps() <= 2) {
                    Random num = new Random();
                    int appId = num.nextInt(100);
                    while (appDB.findApp(appId) != null) {
                        appId = num.nextInt(100);
                    }
                    currentApp = new Application(studentId, oppId, appId);
                    appDB.addApp(currentApp);
                    student.apply(appId);
                    opp.addAppId(appId);
                    System.out.println("Application for " + oppId + " created successfully");
                } else {
                    System.out.println("Error: Maximum active applications reached, please request to withdraw an existing application before trying again");
                }
            } else {
                System.out.println("Error: Internship opportunity is not applicable for current user, or does not exist");
            }
        } else {
            System.out.println("Error: User does not have permission to apply for an internship opportunity");
        }

    }

    public boolean studentQualified(Student student, InternshipOpportunity opp) {
        if (student.getYearOfStudy() <= 2) {
            return opp.getLevel() == INTERNSHIP_LEVEL.Basic;
        } else {
            return true;
        }
    }

    public void decide(String repId) {
        if (userDB.findRep(repId) instanceof CompanyRep rep) {
            System.out.println("Please enter the ID of the application you wish to make a decision on");
            int appId = readIntInput("Enter reference ID: ");
            if (appId == -1) {
                System.out.println("Application process cancelled");
                return;
            }
            currentApp = appDB.findApp(appId);
            if (currentApp != null) {
                if (rep.getActiveOpps().contains(currentApp.getOppId())) {
                    currentApp.printApp();
                    currentOpp = oppDB.findOpp(currentApp.getOppId());
                    System.out.println("Do you want to accept this application, belonging to " + currentOpp.getTitle() + "? (Y/N)");
                    String choice = sc.next();
                    if (choice.equalsIgnoreCase("y")) {
                        currentApp.markSuccessful();
                        System.out.println("Application accepted by company representative " + rep.getName());
                    } else if (choice.equalsIgnoreCase("n")) {
                        currentApp.markUnsuccessful();
                        System.out.println("Application rejected by company representative " + rep.getName());
                    } else {
                        System.out.println("Invalid choice, operation cancelled");
                    }
                } else {
                System.out.println("Error: Please confirm the ID of the application you wish to make a decision on");
                }
            } else {
            System.out.println("Error: Invalid user, unable to review internship applications");
            }
        }
    }

    public void accept(String studentId) {
        if (userDB.findStudent(studentId) instanceof Student student) {
            System.out.println("Please enter the ID of the application you wish to accept");
            int appId = readIntInput("Enter reference ID: ");
            if (appId == -1) {
                System.out.println("Operation cancelled.");
                return;
            }
            if (this.checkApp(appId) && student.getActiveApps().contains(appId)) {
                currentApp = appDB.findApp(appId);
                if (currentApp.getStatus() != APPLICATION_STATUS.Successful) {
                    System.out.println("Error: Application is not open for acceptance");
                    return;
                }
                currentOpp = oppDB.findOpp(currentApp.getOppId());
                System.out.println("Are you sure you want to accept this internship opportunity? (Y/N)");
                String choice = sc.next();
                if (choice.equalsIgnoreCase("y")) {
                    if (currentOpp.getStatus()==OPPORTUNITY_STATUS.Approved) {
                        student.acceptSuccess(appId);
                        currentApp.studentAccepts();
                        currentOpp.addApproved();
                        for (Integer apps:student.getActiveApps()) {
                            if (apps != appId) {
                                currentApp = appDB.findApp(apps);
                                currentApp.approveWithdraw();
                                student.markWithdraw(apps);
                            }
                        }
                    } else {
                        System.out.println("Error: Internship opportunity is closed as it is filled");
                    }
                } else if (choice.equalsIgnoreCase("n")) {
                    System.out.println("Internship opportunity was not accepted, it may not be open if you change your mind later");
                } else {
                    System.out.println("Invalid choice, operation cancelled");
                }
            } else {
                System.out.println("Error: Please confirm the application ID for the opportunity you wish to accept");
            }
        } else {
            System.out.println("Error: User is not authorised to accept internship offers");
        }
    }

    public void requestWithdraw(String studentId) {
        System.out.println("Please specify the ID of the application you wish to withdraw");
        int appId = readIntInput("Enter reference ID: ");
        if (appId == -1) {
            System.out.println("Application withdrawal process cancelled.");
            return;
        }
        if (this.checkApp(appId)) {
            System.out.println("Are you sure you want to request to withdraw this internship application? (Y/N)");
            String choice = sc.next();
            if (choice.equalsIgnoreCase("y")) {
                Student student = (Student) userDB.findStudent(studentId);
                Application app = appDB.findApp(appId);
                if (student.getActiveApps().contains(appId)) {
                    if (app.getWithdrawStatus() == WITHDRAWAL_STATUS.Active) {
                        app.requestWithdraw();
                        System.out.println("Request for application withdrawal successfully submitted");
                    } else {
                        System.out.println("Error: Application has already been withdrawn, or a withdrawal request already exists");
                    }
                } else {
                    System.out.println("Error: User does not have permission to withdraw this application");
                }
            } else if (choice.equalsIgnoreCase(("n"))) {
                System.out.println("Withdrawal request was not submitted");
            } else {
                System.out.println("Invalid choice, operation was cancelled");
            }
        } else {
            System.out.println("Error: This application does not exist");
        }
    }

    public void appView(String studentId) {
        if (userDB.findStudent(studentId) instanceof Student student) {
            if (student.numActiveApps() != 0) {
                for (int appId : student.getActiveApps()) {
                    Application app = appDB.findApp(appId);
                    app.printApp();
                }
            } else {
                System.out.println("Error: No ongoing applications found");
            }
        } else {
            System.out.println("Error: User does not have permission to view student application list");
        }
    }

    public boolean checkApp(int appId) {
        if (appDB.findApp(appId) instanceof Application app) {
            app.printApp();
            return true;
        } else {
            return false;
        }
    }
}
