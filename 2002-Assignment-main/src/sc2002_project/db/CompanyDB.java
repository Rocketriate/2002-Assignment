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
        // Convert to lowercase, remove all non-alphanumeric characters (spaces, periods, etc.)
        return name.trim().toLowerCase().replaceAll("[^a-z0-9]", "");
    }

    /**
     * The core method: Finds an existing Company object by name, or creates a new one
     * if no match is found after standardization.
     * * @param inputName The name provided by the user (e.g., "tech giant").
     * @return The existing or newly created unique Company object.
     */
    public Company createOrGetCompany(String inputName) {
        // 1. Standardize the input name
        String canonicalName = standardizeName(inputName);

        // 2. Check the index for an existing ID
        if (canonicalNameIndex.containsKey(canonicalName)) {
            // Found a match: retrieve the existing company object
            int existingId = canonicalNameIndex.get(canonicalName);
            System.out.println("Found match: '" + inputName + "' mapped to existing Company ID " + existingId);
            return companies.get(existingId);
        } else {
            // No match found: create a new company
            int newId = nextId.getAndIncrement();

            // Create a new Company object (We assume the Company constructor takes ID and name)
            Company newCompany = new Company(newId, inputName);

            // Store the new company in both maps
            companies.put(newId, newCompany);
            canonicalNameIndex.put(canonicalName, newId);

            System.out.println("Created NEW Company: '" + inputName + "' with ID " + newId);
            return newCompany;
        }
    }
}




