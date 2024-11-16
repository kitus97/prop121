package com.prop.prop12_1.model;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase para gestionar la persistencia de usuarios en el sistema.
 * Permite cargar y guardar usuarios desde/para un archivo de texto.
 */
public class UserPersistence {

    private static final String FILE_PATH = "src/main/resources/users.txt";

    /**
     * Carga los usuarios desde un archivo de texto y los almacena en un mapa.
     * El archivo debe contener una lista de usuarios en formato:
     * "username:password:roleName" (una línea por usuario).
     *
     * @return un mapa con los nombres de usuario como claves y los objetos {@link User} como valores
     */
    public Map<String, User> loadUsers() {
        Map<String, User> users = new HashMap<>();
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

    /**
     * Guarda los usuarios en un archivo de texto.
     * Cada usuario se representa en una línea del archivo en el formato:
     * "username:password:roleName".
     *
     * @param users un mapa con los nombres de usuario como claves y los objetos {@link User} como valores
     */
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
