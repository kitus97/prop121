package com.prop.prop12_1.model;

/**
 * Representa un usuario del sistema con un nombre de usuario, contraseña y un rol asociado.
 */
public class User {

    private String username;
    private String password;
    private Role role;

    /**
     * Crea una nueva instancia de usuario.
     *
     * @param username el nombre de usuario
     * @param password la contraseña del usuario
     * @param role     el rol del usuario
     */
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return el nombre de usuario
     */
    public String getUsername() {
        return username;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return la contraseña del usuario
     */
    public String getPassword() {
        return password;
    }

    /**
     * Obtiene el rol del usuario.
     *
     * @return el rol del usuario
     */
    public Role getRole() {
        return role;
    }

    /**
     * Verifica si el usuario tiene rol de administrador.
     *
     * @return true si el usuario es administrador, false en caso contrario
     */
    public boolean isAdmin() {
        return role.equals(Role.ADMIN);
    }

    /**
     * Devuelve una representación textual del usuario en el formato:
     * "username:password:roleName".
     *
     * @return una cadena que representa al usuario
     */
    @Override
    public String toString() {
        return username + ":" + password + ":" + role.getRoleName();
    }

    /**
     * Crea una instancia de usuario a partir de una string.
     * La string debe estar en el formato: "username:password:roleName".
     *
     * @param string la cadena que representa al usuario
     * @return una nueva instancia de User
     */
    public static User fromString(String string) {
        String[] split = string.split(":");
        Role role = Role.fromString(split[2]);
        return new User(split[0], split[1], role);
    }
}
