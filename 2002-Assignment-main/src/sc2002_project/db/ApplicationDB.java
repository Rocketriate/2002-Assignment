package sc2002_project.db;

import sc2002_project.entities.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
public class ApplicationDB {
    private Map<Integer, Application> appDB;
    public ApplicationDB(){
        this.appDB = new HashMap<>();
    }

    public void addApp(Application app){
        if (app != null) {
            this.appDB.put(app.getAppId(), app);
            System.out.println("Added: " + app.getAppId());
        }
    }

    public boolean removeApp(int id) {
        Application removedApp = this.appDB.remove(id);
        if (removedApp != null) {
            System.out.println("Removed " + id + " Successfully");
            return true;
        } else{
            System.out.println("Unable to find application");
            return false;
        }
    }

    public Application findApp(int id) {
        if (this.appDB.get(id) != null) {
            return this.appDB.get(id);
        } else {
            return null;
        }
    }

    public List<Application> filterApp(Predicate<Application> criteria) {
        List<Application> filterList = new ArrayList<>();
        for (Application app:this.appDB.values()) {
            if (criteria.test(app)) {
                    filterList.add(app);
            }
        }
        return filterList;
    }

}
