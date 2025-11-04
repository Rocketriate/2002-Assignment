package sc2002_project;

public class Student extends User {
	private int yearOfStudy;
	private String major;
	private int numActiveApps;
	
	public Student(String userId, String name, String password, String email, int yearOfStudy, String major) {
		super(userId, name, password, email);
		this.yearOfStudy = yearOfStudy;
		this.major = major;
		numActiveApps = 0;
	}
	
	public int getYearOfStudy() {
		return yearOfStudy;
	}
	
	public String getMajor() {
		return major;
	}
	
	public int numActiveApps() {
		return numActiveApps;
	}
	
	// Application controller should getNumActiveApps first then call this to check if students can still apply
	public void apply() { 
		numActiveApps += 1;
		
	}
	
	public void acceptSuccess(Application application) {
		//automatically withdraw all other applications
		numActiveApps = 1;
	}
	
	// only call when staff authorises it, not when withdrawal is requested
	public void markWithdraw(Application application) {
		numActiveApps -= 1;
	}
	
}
