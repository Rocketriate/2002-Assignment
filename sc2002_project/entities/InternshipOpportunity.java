package sc2002_project.entities;

import enums.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
public class InternshipOpportunity {
	 	private final List<Integer> apps;
        private final int oppId;
	    private String title;
	    private String description;
	    private INTERNSHIP_LEVEL level;
	    private String preferredMajor;
	    private LocalDate openingDate;
	    private LocalDate closingDate;
	    private OPPORTUNITY_STATUS status;
	    private boolean visible;
	    private Company company;
	    private final String representativeInCharge;
	    private final int slots;
	    private int approvedCount;
	    
	    public InternshipOpportunity(int oppId, String title, INTERNSHIP_LEVEL level,
                LocalDate closingDate, Company company, String repId) {
            this.oppId = oppId;
            this.title = title;
            this.level = level;
            this.openingDate = LocalDate.now();
            this.closingDate = closingDate;
            this.company = company;
            this.representativeInCharge = repId;
            this.status = OPPORTUNITY_STATUS.Pending;
            this.visible = false;
            this.slots = 10;
            this.approvedCount = 0;
            this.apps = new ArrayList<>();
	    }
	    
	    //accessors
        public int getOppId() { return oppId; }
	    public List<Integer> getAppList() { return apps; }
	    public String getTitle() { return title; }
	    public String getDescription() { return description; }
	    public INTERNSHIP_LEVEL getLevel() { return level; }
	    public String getPreferredMajor() { return preferredMajor; }
	    public LocalDate getOpeningDate() { return openingDate; }
	    public LocalDate getClosingDate() { return closingDate; }
	    public OPPORTUNITY_STATUS getStatus() { return status; }
	    public boolean isVisible() { return visible; } 
	    public String getCompanyName() { return company.getName(); }
	    public String getRepresentativeInCharge() { return representativeInCharge; }
	    public int getSlots() { return slots; }
	    public int getApprovedCount() { return approvedCount; }
	    
	    //mutators
	    public void addAppId(int appId) { this.apps.add(appId); }
	    public void setTitle(String title) { this.title = title; }
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
            if (openingDate == null || openingDate.trim().isEmpty()) {
                this.openingDate = LocalDate.now();
            } else {
                this.openingDate = LocalDate.parse(openingDate);
            }
	    }
	    public void setClosingDate(String closingDate) {
            if (closingDate == null || closingDate.trim().isEmpty()) {
                this.closingDate = LocalDate.parse("2099-01-01");
            } else {
                this.closingDate = LocalDate.parse(closingDate);
            }
	    }

        public void setClosingDate(LocalDate closingDate) { this.closingDate = closingDate; }
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

        public void updateStatus() {
            if (remainingSlots() == 0) {
                this.status = OPPORTUNITY_STATUS.Filled;
            } else {
                this.status = OPPORTUNITY_STATUS.Approved;
            }
        }
	    public void addApproved() {
	        ++this.approvedCount;
            this.updateStatus();
	    }
        public void removeApproved() {
            --this.approvedCount;
            this.updateStatus();
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
        System.out.println("Reference ID: " + this.getOppId());
        System.out.println("Company: " + this.getCompanyName());
        System.out.println("Status: " + this.getStatus());

        String desc = this.getDescription();
        if (desc != null && !desc.trim().isEmpty()) {
            System.out.println("Description: " + desc);
        } else {
            System.out.println("Description: (Not provided)");
        }

        String major = this.getPreferredMajor();
        if (major != null && !major.trim().isEmpty()) {
            System.out.println("Preferred Major: " + major);
        } else {
            System.out.println("Preferred Major: (Any major accepted)");
        }

        System.out.println("Level: " + this.getLevel());
        System.out.println("Opening Date: " + this.getOpeningDate());
        System.out.println("Closing Date: " + this.getClosingDate());
        System.out.println("Available Slots: " + this.remainingSlots());

    }
}

