package sc2002_project.entities;

public abstract class User {
    // Private attributes - encapsulation principle
    private String userID;
    private String name;
    private String password;
    private String email;
    private boolean loggedIn;
    
    /**
     * Constructor to create a new User
     * @param userID The unique identifier for the user
     * @param name The name of the user
     * @param password The password for authentication
     */
    public User(String userID, String name, String password, String email) {
        this.userID = userID;
        this.name = name;
        this.password = password;
        this.loggedIn = false;
    }
    
    /**
     * Authenticates the user with the provided password
     * @param inputPassword The password to verify
     * @return true if password matches, false otherwise
     */
    public boolean login(String inputPassword) {
        if (this.password.equals(inputPassword)) {
            this.loggedIn = true;
            System.out.println("Login successful! Welcome, " + this.name);
            return true;
        } else {
            System.out.println("Login failed! Incorrect password.");
            return false;
        }
    }
    
    /**
     * Logs out the current user
     */
    public void logout() {
        this.loggedIn = false;
        System.out.println(this.name + " has been logged out.");
    }
    
    /**
     * Changes the user's password
     * @param oldPassword The current password for verification
     * @param newPassword The new password to set
     * @return true if password was changed successfully, false otherwise
     */
    public void changePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
            System.out.println("Password changed successfully!");
            // Force re-login after password change
            this.loggedIn = false;
        } else {
            System.out.println("Password change failed! Current password is incorrect.");
        }
    }
    
    // Getter methods (accessors)
    /**
     * Gets the user ID
     * @return The user's unique identifier
     */
    public String getUserID() {
        return userID;
    }
    
    /**
     * Gets the user's name
     * @return The user's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the user's password
     * @return The user's password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Checks if the user is currently logged in
     * @return true if logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    // Setter methods (mutators)
    /**
     * Sets the user ID
     * @param userID The new user ID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }
    
    /**
     * Sets the user's name
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the logged in status
     * @param loggedIn The login status
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    
    /**
     * Returns a string representation of the user
     * @return String containing user information
     */
    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", name='" + name + '\'' +
                ", loggedIn=" + loggedIn +
                '}';
    }
}