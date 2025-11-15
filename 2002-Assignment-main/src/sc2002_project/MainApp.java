package sc2002_project;
// --- Database Imports ---
import sc2002_project.db.UserDB;
import sc2002_project.db.OpportunityDB;
import sc2002_project.db.ApplicationDB;
import sc2002_project.db.CompanyDB;

// --- Controller Imports ---
import sc2002_project.controllers.LoginController;
import sc2002_project.controllers.ApplicationController;
import sc2002_project.controllers.InternshipController;
import sc2002_project.controllers.ApprovalController;
import sc2002_project.controllers.ReportController;

// --- UI Imports ---
import sc2002_project.ui.LoginUI;
import sc2002_project.ui.StudentUI;
import sc2002_project.ui.CompanyRepUI;
import sc2002_project.ui.StaffUI;

// --- Entity Imports (for demo data) ---
import sc2002_project.entities.Student;
import sc2002_project.entities.CareerCentreStaff;
import sc2002_project.entities.FileImport;




public class MainApp {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  SC2002 INTERNSHIP PLACEMENT SYSTEM INITIALIZING");
        System.out.println("==================================================");

        // --- 1. INITIALIZE DATA MANAGERS (DBs) ---
        // These are the core data stores.
        UserDB userDB = new UserDB();
        OpportunityDB oppDB = new OpportunityDB();
        ApplicationDB appDB = new ApplicationDB();
        CompanyDB companyDB = new CompanyDB(); // Manages Company integrity

        FileImport fileImporter = new FileImport(userDB, companyDB); // Assuming FileImport requires DBs

        System.out.println("--------------------------------------------------");


        // --- 4. INITIALIZE CONTROLLERS (Wiring logic to data) ---
        LoginController loginController = new LoginController(userDB);
        InternshipController internshipController = new InternshipController(oppDB, userDB);
        ApplicationController applicationController = new ApplicationController(oppDB, appDB, userDB);
        ApprovalController approvalController = new ApprovalController(userDB, oppDB, appDB);
        ReportController reportController = new ReportController(oppDB, userDB);


        // --- 5. INITIALIZE UIs (Wiring UIs to controllers) ---
        // Note: UIs are initialized top-down, but the main LoginUI is the core driver.

        // UIs for specific roles
        StudentUI studentUI = new StudentUI(internshipController, applicationController);
        CompanyRepUI companyRepUI = new CompanyRepUI(internshipController, applicationController); // Placeholder

        // StaffUI needs both approval and reporting logic
        StaffUI staffUI = new StaffUI(approvalController, reportController);

        // LoginUI needs all role UIs and the LoginController to direct traffic
        LoginUI loginUI = new LoginUI(loginController, studentUI, companyRepUI, staffUI);

        fileImporter.importAllUsers();
        // --- 6. START APPLICATION ---
        System.out.println("System Ready. Launching Main Menu...");
        loginUI.start();
    }
}