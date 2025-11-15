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
    private List<Application> withdrawalList = new ArrayList<>();
    private final Scanner sc = new Scanner(System.in);

    public ApplicationController(OpportunityDB oppDB, ApplicationDB appDB, UserDB userDB) {
        this.oppDB = oppDB;
        this.appDB = appDB;
        this.userDB = userDB;
    }

    public void createApp(String studentId) {
        if (userDB.findStudent(studentId) instanceof Student student) {
            System.out.println("Please enter the ID of the internship opportunity you wish to apply for");
            int oppId = sc.nextInt();
            if (oppDB.findOpp(oppId) instanceof InternshipOpportunity opp && opp.getStatus() == OPPORTUNITY_STATUS.Approved) {
                if (student.numActiveApps() <= 2) {
                    Random num = new Random();
                    int appId = num.nextInt(100);
                    while (appDB.findApp(appId) != null) {
                        appId = num.nextInt(100);
                    }
                    Application app = new Application(studentId, oppId, appId);
                    appDB.addApp(app);
                    student.apply(app.getAppId());
                    System.out.println("Application for " + oppId + " created successfully");
                } else {
                    System.out.println("Error: Maximum active applications reached, please request to withdraw a previous application before trying again");
                }
            } else {
                System.out.println("Error: Internship opportunity is not open for applications, or does not exist");
            }
        } else {
            System.out.println("Error: User does not have permission to apply for an internship opportunity");
        }

    }

    public Application findApp(int appId) {
        return appDB.findApp(appId);
    }

    public void decide(String repId) {
        if (userDB.findRep(repId) instanceof CompanyRep rep) {
            System.out.println("Please enter the ID of the application you wish to make a decision on");
            int appId = sc.nextInt();
            Application app = appDB.findApp(appId);
            if (app != null) {
                if (rep.getActiveOpps().contains(app.getOppId())) {
                    System.out.println("Do you want to accept this application, belonging to " + oppDB.findOpp(app.getOppId()).getTitle() + " ?");
                    app.markSuccessful();
                    System.out.println("Application accepted by company representative " + rep.getName());
                } else {
                    System.out.println("Application rejected by company representative " + rep.getName());
                }
            } else {
                System.out.println("Error: Please confirm the ID of the application you wish to make a decision on");
            }
        } else {
            System.out.println("Error: Invalid user, unable to review internship applications");
        }
    }


    public void accept(String studentId) {
        if (userDB.findStudent(studentId) instanceof Student student) {
            System.out.println("Please enter the ID of the application you wish to accept");
            int appId = sc.nextInt();
            if (this.checkApp(appId) && student.getActiveApps().contains(appId)) {
                System.out.println("Are you sure you want to accept this internship opportunity? (Y/N)");
                String choice = sc.next();
                if (choice.equalsIgnoreCase("y")) {
                    student.acceptSuccess(appId);
                    this.findApp(appId).studentAccepts();
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
        int appId = sc.nextInt();
        if (this.checkApp(appId)) {
            System.out.println("Are you sure you want to withdraw this internship application? (Y/N)");
            String choice = sc.next();
            if (choice.equalsIgnoreCase("y")) {
                User user = userDB.findStudent(studentId);
                Application app = appDB.findApp(appId);
                if (user instanceof Student requester && app != null) {
                    if (app.getWithdrawStatus() == WITHDRAWAL_STATUS.Active) {
                        app.requestWithdraw();
                        withdrawalList.add(app);
                        System.out.println("Request for application withdrawal successfully submitted");
                    } else {
                        System.out.println("Error: Application has already been withdrawn, or a withdrawal request already exists");
                    }
                } else {
                    System.out.println("Error: Invalid user or application does not exist");
                }
            }
        }
    }


    public void appView(String studentId) {
        if (userDB.findStudent(studentId) instanceof Student student) {
            if (student.numActiveApps() != 0) {
                System.out.println("Reference ID " + "Applied on " + "Status");
                for (int appId : student.getActiveApps()) {
                    Application app = this.findApp(appId);
                    System.out.print(app.getAppId());
                    System.out.print(app.getAppliedOn());
                    System.out.println(app.getStatus());
                }
            } else {
                System.out.println("Error: No ongoing applications found");
            }
        } else {
            System.out.println("Error: User does not have permission to view student application list");
        }
    }



    public boolean checkApp(int appId) {
        if (this.findApp(appId) instanceof Application currentApp) {
            System.out.println(currentApp.toString());
            return true;
        } else {
            System.out.println("Error: No application under ID " + appId);
            return false;
        }
    }
}
