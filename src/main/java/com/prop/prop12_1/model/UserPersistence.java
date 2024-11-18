package com.prop.prop12_1.model;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase para gestionar la persistencia de usuarios en el sistema.
 * Permite cargar y guardar usuarios desde/para un archivo de texto.
 */
public class UserPersistence {

    private static final String FILE_PATH = "users.txt";

    /**
     * Carga los usuarios desde un archivo de texto en `resources` y los almacena en un mapa.
     * El archivo debe contener una lista de usuarios en formato:
     * "username:password:roleName" (una línea por usuario).
     *
     * @return un mapa con los nombres de usuario como claves y los objetos {@link User} como valores
     */
    public Map<String, User> loadUsers() {
        Map<String, User> users = new HashMap<>();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_PATH)) {
            if (inputStream == null) {
                throw new FileNotFoundException("El archivo " + FILE_PATH + " no se encuentra en resources.");
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    User user = User.fromString(line);
                    users.put(user.getUsername(), user);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    /**
     * Guarda los usuarios en un archivo externo para persistencia.
     * Cada usuario se representa en una línea del archivo en el formato:
     * "username:password:roleName".
     *
     * @param users un mapa con los nombres de usuario como claves y los objetos {@link User} como valores
     */
    public void saveUsers(Map<String, User> users) {
        // Guarda en un archivo externo ubicado en el directorio actual
        String externalFilePath = "users.txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(externalFilePath))) {
            for (User user : users.values()) {
                bw.write(user.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
}
