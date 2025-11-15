package sc2002_project.entities;

import sc2002_project.db.CompanyDB;
import sc2002_project.db.UserDB;

import java.io.*;
import sc2002_project.entities.CareerCentreStaff;
import sc2002_project.entities.Student;
import sc2002_project.entities.CompanyRep;

public class FileImport {

    // --- Predetermined File Paths ---
    private static final String STUDENT_FILE = "data/students.csv";
    private static final String STAFF_FILE = "data/staff.csv";
    private static final String REP_FILE = "data/company_reps.csv";
    // ----------------------------------

    private final UserDB userDB;
    private final CompanyDB companyDB;

    // Constructor MUST now accept the shared database instances
    public FileImport(UserDB userDB, CompanyDB companyDB) {
        this.userDB = userDB;
        this.companyDB = companyDB;
    }

    /**
     * Imports all three user types from predetermined CSV files.
     */
    public void importAllUsers() {
        System.out.println("--- Starting Non-Interactive CSV Import ---");
        readStudents(STUDENT_FILE);
        readStaff(STAFF_FILE);
        readReps(REP_FILE);
        System.out.println("--- CSV Import Complete ---");
    }

    private void readStudents(String csv) {
        try (BufferedReader reader = new BufferedReader(new FileReader(csv))) {
            String line;
            int studentCount = 0;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { isFirstLine = false; continue; }

                String[] parts = line.split(",");
                String defaultPass = "password";
                if (parts.length >= 5) {
                    Student student = new Student(
                            parts[0].trim(),
                            parts[1].trim(),
                            defaultPass,
                            parts[4].trim(),
                            Integer.parseInt(parts[3].trim()),
                            parts[2].trim()
                    );
                    userDB.addStudent(student);
                    studentCount++;
                }
            }
            System.out.println("Read " + studentCount + " students from " + csv);

        } catch (IOException e) {
            System.out.println("Warning: Could not find or read student file at " + csv);
        } catch (NumberFormatException e) {
            System.out.println("Error: Failed to parse integer field in student file.");
        }
    }

    private void readStaff(String csv) {
        try (BufferedReader reader = new BufferedReader(new FileReader(csv))) {
            String line;
            int staffCount = 0;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { isFirstLine = false; continue; }

                String[] parts = line.split(",");
                String defaultPass = "password";
                if (parts.length >= 5) {
                    CareerCentreStaff staff = new CareerCentreStaff(
                            parts[0].trim(),
                            parts[1].trim(),
                            defaultPass,
                            parts[4].trim(),
                            parts[3].trim()
                    );
                    userDB.addStaff(staff);
                    staffCount++;
                }
            }
            System.out.println("Read " + staffCount + " staff from " + csv);

        } catch (IOException e) {
            System.out.println("Error: Could not find or read staff file at " + csv);
        }
    }

    public void readReps(String csv) {
        try (BufferedReader reader = new BufferedReader(new FileReader(csv))) {
            String line;
            int repCount = 0;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { isFirstLine = false; continue; }

                String[] parts = line.split(",");
                String defaultPass = "password";
                if (parts.length >= 7) {
                    String companyName = parts[2].trim();
                    // Crucial step: Use CompanyDB to find/create the standardized Company entity
                    Company newCompany = companyDB.createOrGetCompany(companyName);

                    CompanyRep rep = new CompanyRep(
                            parts[0].trim(),
                            parts[1].trim(),
                            defaultPass,
                            parts[5].trim(),
                            parts[4].trim(),
                            newCompany,
                            parts[3].trim()
                    );
                    userDB.addRep(rep);
                    newCompany.addRep(rep); // Assuming this method exists on the Company entity
                    repCount++;
                }
            }
            System.out.println("Read " + repCount + " representatives from " + csv);

        } catch (IOException e) {
            System.out.println("Error: Could not find or read representative file at " + csv);
        }
    }
}