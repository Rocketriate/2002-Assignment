package sc2002_project.entities;

import enums.*;

import java.util.List;

public class InternshipOpportunity {
	 	private List<Integer> apps;
        private int oppId;
	    private String title;
	    private String description;
	    private INTERNSHIP_LEVEL level;
	    private String preferredMajor;
	    private String openingDate;
	    private String closingDate;
	    private OPPORTUNITY_STATUS status;
	    private boolean visible;
	    private Company company;
	    private CompanyRep representativeInCharge; //assume will use 0CompanyRep from Company class
	    private int slots; // <= 10
	    private int approvedCount;
	    
	    public InternshipOpportunity(int oppId, String title, INTERNSHIP_LEVEL level,
                String closingDate, Company company) {
            this.oppId = oppId;
            this.title = title;
            this.level = level;
            this.closingDate = closingDate;
            this.company = company;
            this.status = OPPORTUNITY_STATUS.Pending;
            this.visible = false;
            this.slots = 10;
            this.approvedCount = 0;
	    }
	    
	    //accessors
        public int getOppId() { return oppId; }
	    public List<Integer> getAppList() { return apps; }
	    public String getTitle() { return title; }
	    public String getDescription() { return description; }
	    public INTERNSHIP_LEVEL getLevel() { return level; }
	    public String getPreferredMajor() { return preferredMajor; }
	    public String getOpeningDate() { return openingDate; }
	    public String getClosingDate() { return closingDate; }
	    public OPPORTUNITY_STATUS getStatus() { return status; }
	    public boolean isVisible() { return visible; } 
	    public String getCompanyName() { return company.getName(); }
	    public CompanyRep getRepresentativeInCharge() { return representativeInCharge; }
	    public int getSlots() { return slots; }
	    public int getApprovedCount() { return approvedCount; }
	    
	    //mutators
	    public void addAppId(int appId) { this.apps.add(appId); }
	    
	    public void setDescription(String description) { 
	        this.description = description; 
	    }
	    public void setLevel(INTERNSHIP_LEVEL level) {
	        this.level = level; 
	    }
	    public void setPreferredMajor(String preferredMajor) { 
	        this.preferredMajor = preferredMajor; 
	    }
	    public void setOpeningDate(String openingDate) { 
	        this.openingDate = openingDate; 
	    }
	    public void setClosingDate(String closingDate) { 
	        this.closingDate = closingDate; 
	    }
	    public void setStatus(OPPORTUNITY_STATUS status) {
	        this.status = status; 
	    }
	    public void setVisible(boolean visible) {
            if (this.status == OPPORTUNITY_STATUS.Approved) {
                this.visible = visible;
            } else {
                System.out.println("Error: Internship opportunity cannot be displayed to students if it is not approved");
            }
        }
	    public void setCompany(Company companyName) {
	        this.company = companyName;
	    }
	    public void setSlots(int slots) { 
	        this.slots = slots; 
	    }
	    public void setApprovedCount(int approvedCount) { 
	        this.approvedCount = approvedCount; 
	    }
	    
	    
	    //methods


	    public boolean isOpen(Student student) {
            return this.status == OPPORTUNITY_STATUS.Approved && remainingSlots() > 0;
	    }
	    
	    
	    public void updateStatus(OPPORTUNITY_STATUS newStatus) {
	    	this.status = newStatus;
	    }
	    
	    public int remainingSlots() {
	    	return this.slots-this.approvedCount;
        }

    public void printDetails() {

        System.out.println("Opportunity Title: " + this.getTitle());
        System.out.println("Company: " + this.getCompanyName());
        System.out.println("Status: " + this.getStatus());

        String desc = this.getDescription();
        if (desc != null && !desc.trim().isEmpty()) {
            System.out.println("Description: " + desc);
        } else {
            System.out.println("Description: (Not provided)");
        }

        // Check and print Preferred Major
        String major = this.getPreferredMajor();
        if (major != null && !major.trim().isEmpty()) {
            System.out.println("Preferred Major: " + major);
        } else {
            System.out.println("Preferred Major: (Any major accepted)");
        }

        System.out.println("Level: " + this.getLevel());
        System.out.println("Closing Date: " + this.getClosingDate());
        System.out.println("Available Slots: " + this.remainingSlots());

    }
}

