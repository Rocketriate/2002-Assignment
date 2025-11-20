package sc2002_project.entities;
import sc2002_project.util.HashingUtility;
public abstract class User {
    private final String userID;
    private final String name;
    private String hashedPassword;
    private String salt;
    private final String email;



    public User(String userID, String name, String password, String email) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.salt = HashingUtility.getSalt();
        this.hashedPassword = HashingUtility.hashPassword(password, this.salt);
    }


    public boolean login(String inputPassword) {
        if (HashingUtility.verifyPassword(inputPassword, hashedPassword, salt)) {
            return true;
        } else {
            return false;
        }
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (HashingUtility.verifyPassword(oldPassword, hashedPassword, salt)) {
            this.salt = HashingUtility.getSalt();
            this.hashedPassword = HashingUtility.hashPassword(newPassword, salt);
            System.out.println("Password changed successfully!");
        } else {
            System.out.println("Password change failed! Current password is incorrect.");
        }
    }

    // Getter methods (accessors)

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() { return email; }

}