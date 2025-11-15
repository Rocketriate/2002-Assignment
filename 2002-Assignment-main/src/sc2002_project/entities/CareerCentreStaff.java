package sc2002_project.entities;

public class CareerCentreStaff extends User {
    private final String staffDepartment;
    public CareerCentreStaff(String userId, String name, String password, String email, String staffDepartment) {
        super(userId, name, password, email);
        this.staffDepartment = staffDepartment;
    }
    public String getStaffDepartment() {
        return staffDepartment;
    }
}
