import java.util.*;

public class RegistrationController {
    private FileImport importer;
    private UserDB userDB;
    
    public RegistrationController() {
        this.importer = new FileImport();
        this.userDB = new UserDB();
    }
    
    // import students from csv
    public void importStudents(String path) {
        System.out.println("Importing students from: " + path);
        
        List<Student> students = importer.readStudents(path);
        
        for (Student student : students) {
            userDB.createStudent(student);
        }
        
        System.out.println("Successfully imported " + students.size() + " students");
    }
    
    // import staff from csv
    public void importStaff(String path) {
        System.out.println("Importing staff from: " + path);
        
        List<CareerCentreStaff> staff = importer.readStaff(path);
        
        for (CareerCentreStaff staffMember : staff) {
            userDB.createStaff(staffMember);
        }
        
        System.out.println("Successfully imported " + staff.size() + " staff");
    }
    
    // register company rep
    public void registerRep(CompanyRep details) {
        if (details != null) {
            userDB.createRep(details);
            System.out.println("Company rep registered: " + details.getName());
        }
    }
    
    public FileImport getImporter() {
        return importer;
    }
    
    public UserDB getUserDB() {
        return userDB;
    }
}

