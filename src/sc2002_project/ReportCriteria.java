
public class ReportCriteria {
    private OpportunityStatus oppStatus;  // approved, rejected, pending, filled (Q: what abt empty?)
    private ApplicationStatus appStatus;
    private String preferredMajor; // csc
    private Level level; // basic, intermediate, advanced
    private String companyName;

    public ReportCriteria(OpportunityStatus oppStatus, ApplicationStatus appStatus, String preferredMajor, Level level, String companyName) {
        this.oppStatus = oppStatus;
        this.appStatus = appStatus;
        this.preferredMajor = preferredMajor;
        this.level = level;
        this.companyName = companyName;
    }

    public OpportunityStatus getOppStatus(){
        return oppStatus;
    }

    public ApplicationStatus getAppStatus(){
        return appStatus;
    }

    public String getPreferredMajor(){
        return preferredMajor;
    }

    public Level getLevel(){
        return level;
    }

    public String getCompanyName(){
        return companyName;
    }

}
