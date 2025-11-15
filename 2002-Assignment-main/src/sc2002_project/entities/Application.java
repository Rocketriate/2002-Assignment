package sc2002_project.entities;

import enums.*;
import java.time.LocalDate;

public class Application {

	private final int appId;
	private final String userId;
	private final int oppId;
	
	private final LocalDate appliedOn;
	private APPLICATION_STATUS app_status;
	private boolean acceptedByStudent;
	private WITHDRAWAL_STATUS withdrawal;
	
	public Application(String userId, int oppId, int appId) {
		this.userId = userId; 
		this.appId = appId;
		this.oppId = oppId;
		this.appliedOn = LocalDate.now();
		this.app_status = APPLICATION_STATUS.Pending;
		this.acceptedByStudent = false;
		this.withdrawal = WITHDRAWAL_STATUS.Active;
	}
	
	public int getAppId() {
		return appId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public int getOppId() {
		return oppId;
	}

	public LocalDate getAppliedOn() { return appliedOn; }
	
	public APPLICATION_STATUS getStatus() {
		return app_status;
	}
	
	public boolean getAccepted() {
		return acceptedByStudent;
	}
	
	public WITHDRAWAL_STATUS getWithdrawStatus() {
		return withdrawal;
	}
	
	public void markSuccessful() {
		app_status = APPLICATION_STATUS.Successful;
	}
	
	public void markUnsuccessful() {
		app_status = APPLICATION_STATUS.Unsuccessful;
	}
	
	public void studentAccepts() {
		acceptedByStudent = true;
	}

    public void requestWithdraw() { withdrawal = WITHDRAWAL_STATUS.Pending; }
	
	public void approveWithdraw() {
		withdrawal = WITHDRAWAL_STATUS.Withdrawn;
	}

    public void rejectWithdraw() { withdrawal = WITHDRAWAL_STATUS.Active; }

    public String toString() {
        return "Application{" +
                "AppID='" + this.getAppId() + '\'' +
                ", Applicant ='" + this.getUserId() + '\'' +
                ", Date Applied ='" + this.getAppliedOn() + '\'' +
                ", OppID ='" + this.getOppId() + '\'' +
                ", Application Status ='" + this.getStatus() + '\'' +
                ", Withdrawal Status ='" + this.getWithdrawStatus() +
                '}';
    }
}


