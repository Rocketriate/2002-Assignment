package sc2002_project.entities;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
	private int yearOfStudy;
	private String major;
	private List<Integer> activeApps;

	public Student(String userId, String name, String password, String email, int yearOfStudy, String major) {
		super(userId, name, password, email);
		this.yearOfStudy = yearOfStudy;
		this.major = major;
		activeApps = new ArrayList<>();
	}
	
	public int getYearOfStudy() {
		return yearOfStudy;
	}
	
	public String getMajor() {
		return major;
	}

    public List<Integer> getActiveApps() { return activeApps; }
	
	public int numActiveApps() {
		return activeApps.size();
	}
	
	// Application controller should getNumActiveApps first then call this to check if students can still apply
	public void apply(int appId) { activeApps.add(appId);}
	
	public void acceptSuccess(int appId) {
		activeApps.removeIf(app -> app != appId);
	}
	
	// only call when staff authorises it, not when withdrawal is requested
	public void markWithdraw(int appId) { activeApps.remove((Integer) appId); }

    public void printDetails() {
        System.out.println("-----------------------------------------");
        System.out.println("Student ID:       " + this.getUserID());
        System.out.println("Name:             " + this.getName());
        System.out.println("Email:            " + this.getEmail());
        System.out.println("Major:            " + this.getMajor());
        System.out.println("Year of Study:    " + this.getYearOfStudy());
        System.out.println("Active Apps:      " + this.numActiveApps());
        System.out.println("-----------------------------------------");
    }
}
