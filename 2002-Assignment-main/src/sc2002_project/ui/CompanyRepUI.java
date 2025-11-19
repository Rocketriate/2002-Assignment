package sc2002_project.ui;

import sc2002_project.controllers.ApplicationController;
import sc2002_project.controllers.InternshipController;
import sc2002_project.entities.*;

import java.util.Scanner;

public class CompanyRepUI {
    private String activeId;
    private FilterOptions currentFilter;
    private final InternshipController internshipController;
    private final ApplicationController applicationController;
    private final Scanner sc = new Scanner(System.in);

    public CompanyRepUI(InternshipController internshipController, ApplicationController applicationController) {
        this.activeId = null;
        this.currentFilter = null;
        this.internshipController = internshipController;
        this.applicationController = applicationController;
    }

    public void runMenu(CompanyRep user, LoginUI loginUI) {
        System.out.println("Welcome, " + user.getName());
        activeId = user.getUserID();

        boolean operation = true;
        while (operation) {
            System.out.println("What would you like to do today?");
            System.out.println("1. View all created internship opportunities");
            System.out.println("2. Create new internship opportunity");
            System.out.println("3. View ongoing applications");
            System.out.println("4. Internship application decision");
            System.out.println("5. Change visibility of approved opportunities");
            System.out.println("6. View filtered internship opportunities");
            System.out.println("7. Update viewing filters");
            System.out.println("8. Edit internship opportunities");
            System.out.println("9. Change account password");
            System.out.println("10. Logout");
            System.out.println("Choose an option:");
            String choice = sc.nextLine().trim();
            currentFilter = new FilterOptions(user);
            switch (choice) {
                case "1":
                    internshipController.checkCreatedOpps(activeId);
                    break;
                case "2":
                    internshipController.createOpp(activeId);
                    break;
                case "3":
                    internshipController.appView(activeId);
                    break;
                case "4":
                    applicationController.decide(activeId);
                    break;
                case "5":
                    internshipController.toggleVisible(activeId);
                    break;
                case "6":
                    internshipController.filterView(activeId, currentFilter);
                    break;
                case "7":
                    currentFilter.updateFilters();
                    break;
                case "8":
                    internshipController.editOpp(activeId);
                    break;
                case "9":
                    loginUI.changePassword();
                    break;
                case "10":
                    operation = false;
                    break;
                default:
                    System.out.println("Error: Invalid choice. Please choose an option from '1' to '10'");
                    break;
            }
        }
        activeId = null;
        currentFilter = null;
        System.out.println("Thank you for using the internship placement system");
        loginUI.logout();
        loginUI.start();
    }
}
