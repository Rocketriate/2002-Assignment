package sc2002_project.controllers;

import enums.*;
import sc2002_project.db.ApplicationDB;
import sc2002_project.db.OpportunityDB;
import sc2002_project.db.UserDB;
import sc2002_project.entities.Application;
import sc2002_project.entities.CompanyRep;
import sc2002_project.entities.InternshipOpportunity;

import java.util.*;
import java.util.function.Predicate;

public class ApprovalController {
    private final UserDB userDB;
    private final OpportunityDB oppDB;
    private final ApplicationDB appDB;
    private final Scanner sc = new Scanner(System.in);

    public ApprovalController(UserDB userDB, OpportunityDB oppDB, ApplicationDB appDB) {
        this.userDB = userDB;
        this.oppDB = oppDB;
        this.appDB = appDB;
    }

    public void viewPendingRep(String staffId) {
        if (userDB.findStaff(staffId) != null) {
            System.out.println("\n--- Pending Company Representative Applications ---");
            Class<CompanyRep> targetType = CompanyRep.class;
            Predicate<CompanyRep> pendingRep = rep -> rep.isApproved() == REP_STATUS.Pending;
            List<CompanyRep> pendingList = userDB.filterUser(targetType, pendingRep);
            if (pendingList.isEmpty()) {
                System.out.println("Error: No company representatives waiting for approval");
            } else {
                pendingList.forEach(rep -> System.out.println(rep.toString()));
            }
        } else {
            System.out.println("Error: User is not authorised to view pending company representatives");
        }
    }
    public void viewPendingWithdraw(String staffId) {
        if (userDB.findStaff(staffId) != null) {
            System.out.println("\n--- Pending Student Withdrawal Requests ---");
            Predicate<Application> pendingApp = app -> app.getWithdrawStatus() == WITHDRAWAL_STATUS.Pending;
            List<Application> pendingList = appDB.filterApp(pendingApp);
            if (pendingList.isEmpty()) {
                System.out.println("Error: No pending withdrawal requests found");
            } else {
                for(Application app:pendingList) {
                    System.out.println("Application ID: " + app.getAppId());
                    System.out.println(app.toString());
                }
            }
        } else {
            System.out.println("Error: User is not authorised to view pending withdrawal requests");
        }
    }

    public void viewPendingOpp(String staffId) {
        if (userDB.findStaff(staffId) != null) {
            System.out.println("\n--- Pending Internship Opportunities ---");
            List<InternshipOpportunity> pendingList = oppDB.pendingOpp();
            if (pendingList.isEmpty()) {
                System.out.println("Error: No pending withdrawal requests found");
            } else {
                for(InternshipOpportunity opp:pendingList) {
                    System.out.println("Internship opportunity ID: " + opp.getOppId());
                    opp.printDetails();
                }
            }
        } else {
            System.out.println("Error: User is not authorised to view pending withdrawal requests");
        }
    }



    public void approveRep(String staffId) {
        if (userDB.findStaff(staffId) != null) {
            System.out.println("Please specify the ID that belongs to the company representative");
            String repId = sc.nextLine().trim();
            if (userDB.findRep(repId) instanceof CompanyRep rep) {
                System.out.println("Do you want to approve this representative? (Y/N)");
                String choice = sc.next();
                if (choice.equalsIgnoreCase("y")) {
                    rep.setApproved(true);
                    System.out.println("Company representative " + rep.getName() + " was approved");
                } else if (choice.equalsIgnoreCase("n")) {
                    rep.setApproved(false);
                    System.out.println("Company representative " + rep.getName() + " was rejected");
                } else {
                    System.out.println("Invalid choice, operation cancelled");
                }
            } else {
                System.out.println("Error: Please confirm the ID of the company representative you wish to approve");
            }
        } else {
            System.out.println("Error: User is not authorised to approve internship opportunities");
        }
    }

    public void approveOpp(String staffId) {
        if (userDB.findStaff(staffId) != null) {
            System.out.println("Please specify the application ID that belongs to the internship opportunity");
            int oppId = sc.nextInt();
            if (this.checkOpp(oppId)) {
                InternshipOpportunity targetOpp = oppDB.findOpp(oppId);
                System.out.println("Do you want to approve this internship opportunity? (Y/N)");
                String choice = sc.next();
                if (choice.equalsIgnoreCase("y")) {
                    targetOpp.setStatus(OPPORTUNITY_STATUS.Approved);
                    CompanyRep rep = targetOpp.getRepresentativeInCharge();
                    rep.oppApproved(oppId);
                    System.out.println("Internship opportunity was approved");
                } else if (choice.equalsIgnoreCase("n")) {
                    targetOpp.setStatus(OPPORTUNITY_STATUS.Rejected);
                    System.out.println("Internship opportunity was rejected");
                } else {
                    System.out.println("Invalid choice, operation cancelled");
                }
            } else {
                System.out.println("Error: Please confirm the ID of the internship opportunity you wish to approve");
            }
        } else {
            System.out.println("Error: User is not authorised to approve internship opportunities");
        }
    }

    public void approveWithdraw(String staffId) {
        if (userDB.findStaff(staffId) != null) {
            System.out.println("Please specify the application ID of the withdrawal request");
            int appId = sc.nextInt();
            if (this.checkApp(appId)) {
                Application targetApp = appDB.findApp(appId);
                System.out.println("Do you want to approve this withdrawal request? (Y/N)");
                String choice = sc.next();
                if (choice.equalsIgnoreCase("y")) {
                    targetApp.approveWithdraw();
                    System.out.println("Application was withdrawn");
                } else if (choice.equalsIgnoreCase("n")) {
                    targetApp.rejectWithdraw();
                    System.out.println("Application will stay active");
                } else {
                    System.out.println("Invalid choice, operation cancelled");
                }
            } else {
                System.out.println("Error: Please confirm the application ID of the withdrawal request you wish to approve");
            }
        } else {
            System.out.println("Error: User is not authorised to approve application withdrawals");
        }
    }

    public boolean checkOpp(int oppId) {
        if (oppDB.findOpp(oppId) instanceof InternshipOpportunity currentOpp) {
            currentOpp.printDetails();
            return true;
        } else {
            System.out.println("Error: Internship opportunity under ID " + oppId + " does not exist");
            return false;
        }
    }

    public boolean checkApp(int appId) {
        if (appDB.findApp(appId) instanceof Application currentApp) {
            System.out.println(currentApp.toString());
            return true;
        } else {
            System.out.println("Error: No application under ID " + appId);
            return false;
        }
    }
}
