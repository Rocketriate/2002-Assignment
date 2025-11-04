package sc2002_project;
import java.time.LocalDate;

public class Application {
	public enum APPLICATION_STATUS{
		Pending,
		Successful,
		Unsuccessful
	}
	
	private final int appId;
	private final int userId;
	private final int oppId;
	
	private final LocalDate appliedOn;
	private APPLICATION_STATUS status;
	private boolean acceptedByStudent;
	private boolean withdrawn;
	
	public Application(int userId, int oppId, int appId) {
		this.userId = userId; 
		this.appId = appId;
		this.oppId = oppId;
		this.appliedOn = LocalDate.now();
		this.status = APPLICATION_STATUS.Pending;
		this.acceptedByStudent = false;
		this.withdrawn = false;
	}
	
	public int getAppId() {
		return appId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public int getOppId() {
		return oppId;
	}

	public void getAppliedOn() { System.out.println(appliedOn); }
	
	public APPLICATION_STATUS getStatus() {
		return status;
	}
	
	public boolean getAccepted() {
		return acceptedByStudent;
	}
	
	public boolean getWithdrawn() {
		return withdrawn;
	}
	
	public void markSuccessful() {
		status = APPLICATION_STATUS.Successful;
	}
	
	public void markUnsuccessful() {
		status = APPLICATION_STATUS.Unsuccessful;
	}
	
	public void studentAccepts() {
		acceptedByStudent = true;
	}
	
	public void requestWithdraw() {
		withdrawn = true;
	}
	
}
