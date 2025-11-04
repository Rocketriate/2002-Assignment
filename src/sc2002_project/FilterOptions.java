package sc2002_project;

public class FilterOptions {

	private OpportunityStatus status;
	private String preferredMajor;
	private InternshipLevel level;
	private String closingDate;
	private String companyName;
	private String sortOrder = "ALPHA";
	
	// filler start
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
	// filler end
	    
	public FilterOptions() {}
	
	// accessors
	public OpportunityStatus getStatus() {return status;}
	public String getPreferredMajor() {return preferredMajor;}
	public InternshipLevel getLevel() {return level;}
	public String getClosingDate() {return closingDate;}
	public String getCompanyName() {return companyName;}
	public String getSortOrder() {return sortOrder;}

	// mutators
	public void setStatus(OpportunityStatus status) {
	        this.status = status;
	}
	public void setPreferredMajor(String preferredMajor) {
        this.preferredMajor = preferredMajor;
    }
	public void setInternshipLevel(InternshipLevel level) {
	        this.level = level;
	}
	public void setClosingDate(String closingDate) {
	        this.closingDate = closingDate;
	}
	public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
	public void setSortOrder(String sortOrder) {
	        this.sortOrder = sortOrder;
	}
	
	//methods
	public boolean matches(InternshipOpportunity opportunity) {
		if (this.status!=null) { 
				if(!this.status.equals(opportunity.getStatus())) return false;
		}
		if (this.preferredMajor!=null) {
				if (!this.preferredMajor.equalsIgnoreCase(opportunity.getPreferredMajor())) return false;
		}
		if (this.level!=null){
				if(!this.level.equals(opportunity.getLevel()))return false;
		}
		if (this.closingDate!=null) {
			if(!this.closingDate.equals(opportunity.getClosingDate()))return false;
			}
		if (this.companyName!=null) {
			if (!this.companyName.equals(opportunity.getCompanyName()))return false;
		}
		return true;
	}
	
	
	
}
