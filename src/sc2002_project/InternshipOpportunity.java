package sc2002_project;

public class InternshipOpportunity {
	 	private int appId;
	    private String title;
	    private String description;
	    private InternshipLevel level;
	    private String preferredMajor;
	    private String openingDate;
	    private String closingDate;
	    private OpportunityStatus status;
	    private boolean visible;
	    private String companyName;
	    private CompanyRep representativeInCharge; //assume will use CompanyRep from Company class
	    private int slots; // <= 10
	    private int approvedCount;
	    
	    //filler start
	    enum OpportunityStatus {
	       Pending,
	       Approved,
	       Rejected,
	       Filled
	    }
	    enum InternshipLevel {
	        Basic,
	        Intermediate,
	        Advanced
	    }
	    //filler end
	    
	    public InternshipOpportunity(int appId, String title, InternshipLevel level, 
                String closingDate, String companyName) {
	    this.appId = appId;
        this.title = title;
        this.level = level;
        this.closingDate = closingDate;
        this.companyName = companyName;
        this.status = OpportunityStatus.Pending; 
        this.visible = true;
        this.slots = 10; 
        this.approvedCount = 0;
	    }
	    
	    //accessors
	    public int getAppId() { return appId; } 
	    public String getTitle() { return title; }
	    public String getDescription() { return description; }
	    public InternshipLevel getLevel() { return level; }
	    public String getPreferredMajor() { return preferredMajor; }
	    public String getOpeningDate() { return openingDate; }
	    public String getClosingDate() { return closingDate; }
	    public OpportunityStatus getStatus() { return status; }
	    public boolean isVisible() { return visible; } 
	    public String getCompanyName() { return companyName; }
	    public CompanyRep getRepresentativeInCharge() { return representativeInCharge; }
	    public int getSlots() { return slots; }
	    public int getApprovedCount() { return approvedCount; }
	    
	    //mutators
	    public void setAppId(int appId) { 
	        this.appId = appId; 
	    }
	    
	    public void setDescription(String description) { 
	        this.description = description; 
	    }
	    public void setLevel(InternshipLevel level) { 
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
	    public void setStatus(OpportunityStatus status) { 
	        this.status = status; 
	    }
	    public void setVisible(boolean visible) { 
	        this.visible = visible; 
	    }
	    public void setCompanyName(String companyName) { 
	        this.companyName = companyName; 
	    }
	    public void setSlots(int slots) { 
	        this.slots = slots; 
	    }
	    public void setApprovedCount(int approvedCount) { 
	        this.approvedCount = approvedCount; 
	    }
	    
	    
	    //methods
	    public boolean isOpen(Student student) //student class
	    {
	    	
	    	return this.status == OpportunityStatus.Approved && remainingSlots() > 0;
	    }
	    
	    
	    public void updateStatus(OpportunityStatus newStatus) {
	    	this.status = newStatus;
	    }
	    
	    public int remainingSlots() {
	    	int remaining=this.slots-this.approvedCount;
	    		return remaining;
	    	}
	    	
	    }
}
