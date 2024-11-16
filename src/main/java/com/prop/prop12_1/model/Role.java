package com.prop.prop12_1.model;

/**
 * Enumeración que representa los roles disponibles para los usuarios.
 * Los roles posibles son:
 * - ADMIN: Administrador del sistema
 * - USER: Usuario regular
 */
public enum Role {
    ADMIN("Admin"),
    USER("User");

    private final String roleName;

    /**
     * Constructor del rol con el nombre asociado.
     *
     * @param roleName el nombre del rol
     */
    Role(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Obtiene el nombre del rol.
     *
     * @return una cadena que representa el nombre del rol
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Convierte una cadena en un rol. Compara la cadena con los nombres de rol disponibles.
     *
     * @param roleName la cadena que representa el nombre del rol
     * @return el rol correspondiente a la cadena proporcionada
     * @throws IllegalArgumentException si el nombre del rol no es válido
     */
    public static Role fromString(String roleName) {
        for (Role role : Role.values()) {
            if (role.roleName.equalsIgnoreCase(roleName)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role name: " + roleName);
    }
}
