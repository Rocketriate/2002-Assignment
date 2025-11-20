package sc2002_project.ui;

import sc2002_project.controllers.LoginController;
import sc2002_project.entities.CareerCentreStaff;
import sc2002_project.entities.CompanyRep;
import sc2002_project.entities.Student;
import sc2002_project.entities.User;

import java.util.Scanner;

public class LoginUI {
    private final LoginController loginController;
    private final StudentUI studentUI;
    private final CompanyRepUI companyRepUI;
    private final StaffUI staffUI;
    private final Scanner sc = new Scanner(System.in);

 /* private so that only this class(LoginUI can directly access this fields) */


    public LoginUI(LoginController loginController, StudentUI studentUI, CompanyRepUI companyRepUI, StaffUI staffUI){
        this.loginController = loginController;
        this.studentUI =  studentUI;
        this.companyRepUI = companyRepUI;
        this.staffUI = staffUI;
    }

    public void start(){
        while (true){
            System.out.println("========================================");
            System.out.println("   INTERNSHIP PLACEMENT SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine().trim();
            switch(choice){
                case "1":
                    attemptLogin();
                    break;
                case "2":
                    System.out.println("Goodbye.");
                    return;
                    // return will cause programme to exit start() method > programme ends
                default:
                    System.out.println("Invalid choice.\n");  
                    break;
            }
        }
    }

    private void attemptLogin(){
        System.out.print("Enter User ID: ");
        String id = sc.nextLine().trim();

        System.out.print("Enter Password: ");
        String pass = sc.nextLine().trim();

        int result = loginController.authenticate(id,pass);
        switch (result){
            case 1:
                System.out.println("\nLogin failed. Invalid ID.");
                return;
            case 2:
                System.out.println("\nLogin failed. Incorrect password.");
                return;
            case 3:
                System.out.println("\nLogin failed. Your account isn't approved.");
                return;
            default:
                User loggedIn = loginController.getCurrentUser();
                System.out.println("\nLogin successful. Welcome, " + loggedIn.getName() + "!\n");
                directUserType(loggedIn);
                logout();
        }

       

    }

    public void directUserType(User current) {
        //if no current user
        switch (current) {
            case null -> {
                System.out.println("Internal error: No active user.\n");
                return;
            }
            case Student student -> studentUI.runMenu(student, this);

            case CompanyRep rep -> companyRepUI.runMenu(rep, this);

            case CareerCentreStaff staff -> staffUI.runMenu(staff, this);
            default -> System.out.println("Unknown user type.\n");
        }
    }

    public void changePassword() {
        loginController.requestChangePassword();
    }
    public void logout(){
        loginController.logout();
        System.out.println("\nYou have been logged out.\n");
    }

}
