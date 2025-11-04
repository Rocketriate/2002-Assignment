package sc2002_project;

import java.io.*;
import java.util.*;

public class FileImport {
    private List<Student> students;
    private List<CareerCentreStaff> staff;
    
    public FileImport() {
        this.students = new ArrayList<>();
        this.staff = new ArrayList<>();
    }
    
    // read students from csv file
    public List<Student> readStudents(String csv) {
        List<Student> studentList = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(csv))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    Student student = new Student(
                        parts[0].trim(), 
                        parts[1].trim(), 
                        parts[2].trim()
                    );
                    studentList.add(student);
                }
            }
            
            this.students = studentList;
            System.out.println("Read " + studentList.size() + " students from file");
            
        } catch (IOException e) {
            System.out.println("Error reading students file: " + e.getMessage());
        }
        
        return studentList;
    }
    
    // read staff from csv file
    public List<CareerCentreStaff> readStaff(String csv) {
        List<CareerCentreStaff> staffList = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(csv))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    CareerCentreStaff staff = new CareerCentreStaff(
                        parts[0].trim(), 
                        parts[1].trim(), 
                        parts[2].trim()
                    );
                    staffList.add(staff);
                }
            }
            
            this.staff = staffList;
            System.out.println("Read " + staffList.size() + " staff from file");
            
        } catch (IOException e) {
            System.out.println("Error reading staff file: " + e.getMessage());
        }
        
        return staffList;
    }
    
    public List<Student> getStudents() {
        return students;
    }
    
    public List<CareerCentreStaff> getStaff() {
        return staff;
    }
}

