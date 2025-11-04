package sc2002_project;

public class LoginController {
    private final UserDB userDB;
    private User currentUser;

    public LoginController(UserDB userDB){
        this.userDB = userDB;
        this.currentUser = null;
    }

    public int authenticate(String id, String pass){
        
        User user = userDB.getUserById(id);
        // if no such user exists
        if (user == null){
            return 1;
        }
        // if user exists, need check if the password matches 
        if (!user.checkPass(pass)){
            return 2;
        }
        //need check companyreps approved
        if (user instanceof CompanyRep){
            CompanyRep rep = (CompanyRep) user;
            //IMPORTANT: NEED ENSURE THIS METHOD IS DEFINED 
            if (!rep.isApproved()){
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


    public void changePassword(User user, String newPass){
        if(user == null || newPass == null || newPass.isEmpty()){
            System.out.println("Invalid user or password.");
            return;
        }
        user.changePassword(newPass);
        /* there is changePW method in sc2002_project.LoginUI.User class
         * calls the object(sc2002_project.LoginUI.User) to change password
         * in user class, pasword is private so only user class can change it
         * 
         * controller: decide if pw can change
         * user: actually applies it 
         */

        System.out.println("Password changed successfully.");               
    }

    public void logout(){
        currentUser = null;
    } 

    // logout() is supposed to clear who is logged  in 



    
}
