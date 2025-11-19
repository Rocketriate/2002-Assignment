package sc2002_project.db;

import sc2002_project.entities.CareerCentreStaff;
import sc2002_project.entities.CompanyRep;
import sc2002_project.entities.Student;
import sc2002_project.entities.User;

import java.util.*;
import java.util.function.Predicate;

public class UserDB {
    private final Map<String, User> userDB;
    
    public UserDB() {
        this.userDB = new HashMap<>();
    }
    
    public void addStudent(Student student) {
        if (student != null) {
            userDB.put(student.getUserID(), student);
            System.out.println("Student created: " + student.getName());
        } else {
            System.out.println("Error: Student object does not exist");
        }
    }
    
    public void addStaff(CareerCentreStaff staff) {
        if (staff != null) {
            userDB.put(staff.getUserID(), staff);
            System.out.println("Staff created: " + staff.getName());
        } else {
            System.out.println("Error: CareerCentreStaff object does not exist");
        }
    }
    public void addRep(CompanyRep rep) {
        if (rep != null) {
            userDB.put(rep.getUserID(), rep);
            System.out.println("Company Rep created: " + rep.getName());
        } else {
            System.out.println("Error: CompanyRep object does not exist");
        }
    }
    public User findStudent(String id) {
            User identity = this.userDB.get(id);
            if (identity != null && identity.getClass() == Student.class) {
                return this.userDB.get(id);
            } else {
                System.out.println("Error: No student under ID " + id + " was found");
                return null;
            }
        }
    
    public User findRep(String id) {
        User identity = this.userDB.get(id);
        if (identity != null && identity.getClass() == CompanyRep.class) {
            return this.userDB.get(id);
        } else {
            System.out.println("Error: No company representative under ID " + id + " was found");
            return null;
        }
    }
    
    public User findStaff(String id) {
        User identity = this.userDB.get(id);
        if (identity != null && identity.getClass() == CareerCentreStaff.class) {
            return this.userDB.get(id);
        } else {
            System.out.println("Error: No career centre staff under ID " + id + " was found");
            return null;
        }
    }

    public User findUser(String id) { return this.userDB.get(id); }

    public <T extends User> List<T> filterUser(Class<T> target, Predicate<T> criteria) {
        List<T> filterList = new ArrayList<>();
        for (User user:this.userDB.values()) {
            if (target.isInstance(user)) {
                T actualTarget = target.cast(user);
                if (criteria.test(actualTarget)) {
                    filterList.add(actualTarget);
                }
            }
        }
        return filterList;
    }
}



