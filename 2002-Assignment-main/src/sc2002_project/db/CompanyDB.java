package sc2002_project.db;

import sc2002_project.entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CompanyDB {
    private final Map<Integer, Company> companies;

    private final Map<String, Integer> canonicalNameIndex;

    private final AtomicInteger nextId;

    public CompanyDB() {
        this.companies = new HashMap<>();
        this.canonicalNameIndex = new HashMap<>();
        this.nextId = new AtomicInteger(1);
    }

    private static String standardizeName(String name) {
        if (name == null) return "";
        return name.trim().toLowerCase().replaceAll("[^a-z0-9]", "");
    }


    public Company createOrGetCompany(String inputName) {
        String canonicalName = standardizeName(inputName);
        if (canonicalNameIndex.containsKey(canonicalName)) {
            int existingId = canonicalNameIndex.get(canonicalName);
            System.out.println("Found match: '" + inputName + "' mapped to existing Company ID " + existingId);
            return companies.get(existingId);
        } else {
            int newId = nextId.getAndIncrement();
            Company newCompany = new Company(newId, inputName);
            companies.put(newId, newCompany);
            canonicalNameIndex.put(canonicalName, newId);
            System.out.println("Created NEW Company: '" + inputName + "' with ID " + newId);
            return newCompany;
        }
    }
}




