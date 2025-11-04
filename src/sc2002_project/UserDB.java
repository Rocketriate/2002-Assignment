import java.util.*;

public class UserDB {
    private List<Student> students;
    private List<CompanyRep> companyReps;
    private List<CareerCentreStaff> staff;
    
    public UserDB() {
        this.students = new ArrayList<>();
        this.companyReps = new ArrayList<>();
        this.staff = new ArrayList<>();
    }
    
    public void createStudent(Object details) {
        if (details instanceof Student) {
            students.add((Student) details);
            System.out.println("Student created: " + ((Student) details).getName());
        }
    }
    
    public void createStaff(Object details) {
        if (details instanceof CareerCentreStaff) {
            staff.add((CareerCentreStaff) details);
            System.out.println("Staff created: " + ((CareerCentreStaff) details).getName());
        }
    }
    
    public void createRep(Object details) {
        if (details instanceof CompanyRep) {
            companyReps.add((CompanyRep) details);
            System.out.println("Company Rep created: " + ((CompanyRep) details).getName());
        }
    }
    
    public List<Student> getStudents() {
        return students;
    }
    
    public List<CompanyRep> getCompanyReps() {
        return companyReps;
    }
    
    public List<CareerCentreStaff> getStaff() {
        return staff;
    }
}



