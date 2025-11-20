package sc2002_project.controllers;

import enums.REP_STATUS;
import sc2002_project.entities.CompanyRep;
import sc2002_project.entities.User;
import sc2002_project.db.UserDB;

import java.util.Scanner;

public class LoginController {
    private final UserDB userDB;
    private User currentUser;
    private final Scanner sc = new Scanner(System.in);

    public LoginController(UserDB userDB){
        this.userDB = userDB;
        this.currentUser = null;
    }

    public int authenticate(String id, String pass){
        
        User user = userDB.findUser(id);
        // if no such user exists
        if (user == null){
            return 1;
        }
        // if user exists, need check if the password matches 
        if (!user.login(pass)) {
            return 2;
        }
        //need check companyreps approved
        if (user instanceof CompanyRep rep){
            if (rep.isApproved() != REP_STATUS.Approved){
                return 3;
            }
        }
        // if everything okay, then can succesfully log in
        this.currentUser = user;
        return 0;
    }

    public User getCurrentUser(){
        return currentUser;
    }


    public void requestChangePassword(){
        User requester = this.getCurrentUser();
        System.out.println("Enter your old password:");
        String oldPass = sc.nextLine().trim();
        System.out.println("Enter your new password");
        String newPass = sc.nextLine().trim();
        System.out.println("Confirm your new password");
        String confirmPass = sc.nextLine().trim();
        if (newPass.isEmpty() || !newPass.equals(confirmPass)){
            System.out.println("Invalid user or password. Please check again");
            return;
        }
        requester.changePassword(oldPass, newPass);
    }

    public void logout(){
        currentUser = null;
    } 

    // logout() is supposed to clear who is logged  in 



    
}
