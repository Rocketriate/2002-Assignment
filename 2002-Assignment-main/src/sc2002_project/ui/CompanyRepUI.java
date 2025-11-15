package sc2002_project.ui;

import sc2002_project.controllers.ApplicationController;
import sc2002_project.controllers.InternshipController;
import sc2002_project.entities.*;

import java.util.Scanner;

public class CompanyRepUI {
    private String activeId;
    private final InternshipController internshipController;
    private final ApplicationController applicationController;
    private final Scanner sc = new Scanner(System.in);

    public CompanyRepUI(InternshipController internshipController, ApplicationController applicationController) {
        this.activeId = null;
        this.internshipController = internshipController;
        this.applicationController = applicationController;
    }

    public void runMenu(CompanyRep user, LoginUI loginUI) {
        System.out.println("Welcome, " + user.getName());
        activeId = user.getUserID();
        System.out.println("What would you like to do today?");
        System.out.println("1. View all created internship opportunities");
        System.out.println("2. Create new internship opportunity");
        System.out.println("3. View ongoing applications");
        System.out.println("4. Internship application decision");
        System.out.println("5. Change visibility of approved opportunities");
        System.out.println("6. Change account password");
        System.out.println("7. Logout");
        boolean operation = true;
        while (operation) {
            System.out.println("Choose an option:");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    internshipController.printOppList(activeId);
                    break;
                case "2":
                    internshipController.createOpp(activeId);
                    break;
                case "3":
                    this.appView(user);
                    break;
                case "4":
                    applicationController.decide(activeId);
                    break;
                case "5":
                    internshipController.toggleVisible(activeId);
                    break;
                case "6":
                    loginUI.changePassword();
                case "7":
                    operation = false;
                    break;
                default:
                    System.out.println("Error: Invalid choice. Please choose an option from 1-7");
                    break;
            }
        }
        activeId = null;
        System.out.println("Thank you for using the internship placement system");
        loginUI.logout();
        loginUI.start();
    }

    public void appView(CompanyRep rep) {
        System.out.println("Please enter the ID of the internship opportunity you wish to check");
        int oppId = sc.nextInt();
        InternshipOpportunity targetOpp = internshipController.findOpp(oppId);
        if (targetOpp != null) {
            System.out.println("Applications under internship opportunity " + targetOpp.getTitle() + ":" );
            if (targetOpp.getRepresentativeInCharge() == rep) {
                System.out.println("Reference ID " + "Applied on " + "Status");
                for (Integer appId : targetOpp.getAppList()) {
                    Application app = applicationController.findApp(appId);
                    System.out.print(app.getAppId());
                    System.out.print(app.getAppliedOn());
                    System.out.println(app.getStatus());
                }
            } else {
                System.out.println("Error: User does not have permission to view applications for this internship opportunity");
            }
        } else {
            System.out.println("Error: Internship opportunity under ID " + oppId + " does not exist");
        }
    }


}
