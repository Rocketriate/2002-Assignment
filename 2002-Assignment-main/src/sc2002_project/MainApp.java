package sc2002_project;

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


        UserDB userDB = new UserDB();
        OpportunityDB oppDB = new OpportunityDB();
        ApplicationDB appDB = new ApplicationDB();
        CompanyDB companyDB = new CompanyDB();

        FileImport fileImporter = new FileImport(userDB, companyDB);

        System.out.println("--------------------------------------------------");



        LoginController loginController = new LoginController(userDB);
        InternshipController internshipController = new InternshipController(oppDB, appDB, userDB);
        ApplicationController applicationController = new ApplicationController(oppDB, appDB, userDB);
        ApprovalController approvalController = new ApprovalController(userDB, oppDB, appDB);
        ReportController reportController = new ReportController(oppDB, userDB);



        StudentUI studentUI = new StudentUI(internshipController, applicationController);
        CompanyRepUI companyRepUI = new CompanyRepUI(internshipController, applicationController);


        StaffUI staffUI = new StaffUI(approvalController, reportController);


        LoginUI loginUI = new LoginUI(loginController, studentUI, companyRepUI, staffUI);

        fileImporter.importAllUsers();

        System.out.println("System Ready. Launching Main Menu...");
        loginUI.start();
    }
}