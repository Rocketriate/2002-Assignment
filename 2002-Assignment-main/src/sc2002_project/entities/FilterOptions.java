package sc2002_project.entities;

import enums.*;

import java.util.Scanner;

public class FilterOptions {

    private String userId;
	private OPPORTUNITY_STATUS status;
	private String preferredMajor;
	private INTERNSHIP_LEVEL level;
	private String closingDate;
	private String companyName;
    private boolean visible;
    private final Scanner sc = new Scanner(System.in);
	
	public FilterOptions(String userId) {
        this.userId = userId;
        setStatus(OPPORTUNITY_STATUS.Approved);
        this.visible = true;
    }
	
	// accessors
	public OPPORTUNITY_STATUS getStatus() {return status;}
	public String getPreferredMajor() {return preferredMajor;}
	public INTERNSHIP_LEVEL getLevel() {return level;}
	public String getClosingDate() {return closingDate;}
	public String getCompanyName() {return companyName;}

	// mutators
	public void setStatus(OPPORTUNITY_STATUS status) {
	        this.status = status;
	}
	public void setPreferredMajor(String preferredMajor) {
        this.preferredMajor = preferredMajor;
    }
	public void setInternshipLevel(INTERNSHIP_LEVEL level) {
	        this.level = level;
	}
	public void setClosingDate(String closingDate) {
	        this.closingDate = closingDate;
	}
	public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
	
	//methods
	public boolean matches(InternshipOpportunity opportunity) {
		if (this.status!=null) { 
			return this.status.equals(opportunity.getStatus());
		}
		if (this.preferredMajor!=null) {
			return this.preferredMajor.equalsIgnoreCase(opportunity.getPreferredMajor());
		}
		if (this.level!=null) {
			return this.level.equals(opportunity.getLevel());
		}
		if (this.closingDate!=null) {
			return this.closingDate.equals(opportunity.getClosingDate());
			}
		if (this.companyName!=null) {
            return this.companyName.equals(opportunity.getCompanyName());
		}
		return true;
	}

    public void updateFilters() {
        System.out.println("Filter Options:");
        System.out.println("1: Internship Level");
        System.out.println("2: Company Name");
        System.out.println("3: Preferred Major");
        String choice = sc.nextLine().trim();

        switch (choice) {
            case "1":
                System.out.println("Set visible level:");
                System.out.println("1: Basic");
                System.out.println("2: Intermediate");
                System.out.println("3: Advanced");
                String option = sc.nextLine().trim();

                switch (option) {
                    case "1":
                        this.setInternshipLevel(INTERNSHIP_LEVEL.Basic);
                        break;
                    case "2":
                        this.setInternshipLevel(INTERNSHIP_LEVEL.Intermediate);
                        break;
                    case "3":
                        this.setInternshipLevel(INTERNSHIP_LEVEL.Advanced);
                        break;
                }
            case "2":
                System.out.println("Input desired company name (Case sensitive):");
                String name = sc.nextLine().trim();
                this.setCompanyName(name);
            case "3":
                System.out.println("Preferred major by internship opportunity:");
                System.out.println("1: Data Science & AI");
                System.out.println("2: Computer Science");
                System.out.println("3: Information Engineering & Media");
                System.out.println("4: Computer Engineering");
                String major = sc.nextLine().trim();

                switch (major) {
                    case "1":
                        this.setPreferredMajor("Data Science & AI");
                        break;
                    case "2":
                        this.setPreferredMajor("Computer Science");
                        break;
                    case "3":
                        this.setPreferredMajor("Information Engineering & Media");
                        break;
                    case "4":
                        this.setPreferredMajor("Computer Engineering");
                        break;
                }
        }
    }
}
