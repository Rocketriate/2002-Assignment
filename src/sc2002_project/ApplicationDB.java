package sc2002_project;

import java.util.ArrayList;
import java.util.List;

public class ApplicationDB {
    private List<Application> apps;
    public ApplicationDB(){
        this.apps = new ArrayList<>();
    }
    public void addApp(Application app){
        if (app != null) {
            this.apps.add(app);
            System.out.println("Added: " + app.getAppId());
        }
    }
    public boolean removeApp(int id) {
        boolean removed = this.apps.removeIf(app -> app.getAppId() == id);
        if (removed) {
            System.out.println("Removed " + id + " Successfully")
        } else{
            System.out.println("Unable to find application")
        }
        return removed;
    }
    private Application findById(int id) {
        for (Application app: this.apps) {
            if (app.getId() == id) {
                return app;
            }
        }
        return null;
    }
    public void printDB() {
        System.out.println("\n--- Current List of Applications ---");
        if (this.apps.isEmpty()) {
            System.out.println("The database is empty");
        } else {
            for (Application app: this.apps) {
                app.displayApp();
            }
        }
        System.out.println("------------------------");
    }
}
