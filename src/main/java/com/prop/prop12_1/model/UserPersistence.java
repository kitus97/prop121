package com.prop.prop12_1.model;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserPersistence {

    private static final String FILE_PATH = "src/main/resources/users.txt";

    public Map<String, User> loadUsers() {
        Map<String, User> users = new HashMap<String, User>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                User user = User.fromString(line);
                users.put(user.getUsername(), user);
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e);
        }
        return users;
    }

    public void saveUsers(Map<String, User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users.values()) {
                bw.write(user.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
}
