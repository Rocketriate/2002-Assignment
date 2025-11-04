package sc2002_project;

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
        System.out.print("Enter sc2002_project.LoginUI.User ID: ");
        String id = sc.nextLine().trim();

        System.out.print("Enter Password: ");
        String pass = sc.nextLine().trim();

        /*sc2002_project.LoginUI.User loggedIn = loginController.authenticate(id,pass);
          sc2002_project.LoginUI.User is an object type
         * use the loginController class method to determine if the password and userid is authenthic
         * if it is authenthic then return smt, if not then return null
         */

        // if unsuccessful
        /*if (loggedIn == null){
            System.out.println("\nLogin failed.");
            System.out.println("Either ID/password is wrong, or (for Company Reps) your account isn't approved yet.\n");
            return;
        }

        // if successful
        System.out.println("\nLogin successful. Welcome, " + loggedIn.getName() + "!\n");
        directUserType();
        // direct user to the ui according to what they are */

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
                directUserType();
                logout();
        }

       

    }

    public void directUserType(){
        User current = loginController.getCurrentUser();
        //if no current user
        if (current == null){
            System.out.println("Internal error: no active user.\n");
            return;
        }

        //else
        if (current instanceof Student){
            studentUI.runMenu((Student) current, loginController);
        }
        else if (current instanceof CompanyRep){
            companyRepUI.runMenu((CompanyRep) current, loginController);
        }
        else if (current instanceof CareerCentreStaff) {
        staffUI.runMenu((CareerCentreStaff) current, loginController);
        }
        else {
        System.out.println("Unknown user type.\n");
        } 

        /*switch (current){
            case Student s -> studentUI.runMenu(s, loginController);
            case CompanyRep c -> companyRepUI.runMenu(c, loginController);
            case CareerCentreStaff st -> staffUI.runMenu(st, loginController);
        } */
    }

    public void logout(){
        loginController.logout();
        System.out.println("\nYou have been logged out.\n");
    }


    public static class User {
    }
}
