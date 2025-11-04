import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportController {
    private final OpportunityDB opportunityDB;

    public ReportController(OpportunityDB opportunityDB){
        this.opportunityDB = opportunityDB;
    }

    public List<InternshipOpportunity> generate(ReportCriteria criteria){
        List<InternshipOpportunity> all = opportunityDB.findAll();

        return all.stream()
            .filter(o -> criteria.getOppStatus() == null
                    || o.getOpportunityStatus() == criteria.getOppStatus())
            .filter(o -> criteria.getAppStatus() == null
                    || o.getApplicationStatus() == criteria.getAppStatus())
            .filter(o -> criteria.getPreferredMajor() == null
                    || (o.getStudent() != null 
                        && equalsIgnoreCaseSafe(criteria.getPreferredMajor(), o.getStudent().getMajor())))
            .filter(o -> criteria.getLevel() == null
                    || o.getLevel() == criteria.getLevel())
            .filter(o -> criteria.getCompanyName() == null
                    || equalsIgnoreCaseSafe(criteria.getCompanyName(), o.getCompanyName()))
            .collect(Collectors.toList());
    }

    private boolean equalsIgnoreCaseSafe(String a, String b) {
        return a != null && b != null && a.equalsIgnoreCase(b);
    }
}

