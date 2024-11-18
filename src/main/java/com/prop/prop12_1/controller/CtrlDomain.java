package com.prop.prop12_1.controller;

import com.prop.prop12_1.exceptions.*;
import com.prop.prop12_1.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Controlador principal del dominio que gestiona todas las otras clases y más tarde se usará
 * para conectar con la capa de datos y presentación.
 * .
 */
@Component
public class CtrlDomain {

    private CtrlSupermarket ctrlSupermarket;
    private CtrlProd ctrlProd;
    private CtrlAlgorithm ctrlAlgorithm;
    private final UserPersistence userPersistence;

    private Map<String, User> users;
    private User loggedUser;
    private String selectedSupermarket;

    /**
     * Constructor de CtrlDomain. Inicializa los controladores y carga los usuarios desde persistencia.
     */
    public CtrlDomain() {
        this.ctrlSupermarket = new CtrlSupermarket();
        this.ctrlProd = new CtrlProd();
        this.ctrlAlgorithm = new CtrlAlgorithm();

        this.userPersistence = new UserPersistence();
        users = userPersistence.loadUsers();
    }

    /**
     * Añade un nuevo usuario al sistema.
     *
     * @param username Nombre del usuario.
     * @param password Contraseña del usuario.
     * @param role     Rol del usuario (admin o user).
     * @throws UserAlreadyExistsException Si el usuario ya existe.
     */
    public void addUser(String username, String password, String role) {
        if (users.containsKey(username)) {
            throw new UserAlreadyExistsException("User '" + username + "' already exists");
        }
        User user = new User(username, password, Role.fromString(role));
        users.put(username, user);
        userPersistence.saveUsers(users);
    }

    /**
     * Inicia sesión en el sistema con el nombre de usuario y contraseña proporcionados.
     *
     * @param username Nombre del usuario.
     * @param password Contraseña del usuario.
     * @throws UserNotFoundException      Si el usuario no existe.
     * @throws IncorrectPasswordException Si la contraseña es incorrecta.
     */
    public void login(String username, String password) {
        User user = users.get(username);
        if (user == null) {
            throw new UserNotFoundException("User '" + username + "' not found");
        } else if (!user.getPassword().equals(password)) {
            throw new IncorrectPasswordException("Incorrect password");
        } else {
            loggedUser = user;
        }
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    public void logout() {
        loggedUser = null;
    }

    /**
     * Comprueba si el usuario actual tiene permisos de administrador.
     *
     * @return true si el usuario es administrador, false en caso contrario.
     */
    public boolean isAdmin() {
        return loggedUser != null && loggedUser.isAdmin();
    }

    /**
     * Devuelve una lista de usuarios registrados en el sistema.
     *
     * @return Cadena con los nombres de los usuarios y sus roles.
     */
    public String listUsers() {
        StringBuilder builder = new StringBuilder();
        for (User user : users.values()) {
            builder.append(user.getUsername()).append(" (").append(user.getRole()).append(")\n");
        }
        return builder.toString();
    }

    /**
     * Añade un supermercado al sistema.
     *
     * @param supermarketName Nombre del supermercado.
     */
    public void addSupermarket(String supermarketName) {
        ctrlSupermarket.addSupermarket(supermarketName);
    }

    /**
     * Elimina un supermercado del sistema.
     *
     * @param supermarketName Nombre del supermercado.
     */
    public void removeSupermarket(String supermarketName) {
        ctrlSupermarket.removeSupermarket(supermarketName);
    }

    /**
     * Añade una nueva estantería al supermercado seleccionado.
     *
     * @param shelfName Nombre de la estantería.
     * @param shelfSize Tamaño de la estantería.
     */
    public void addShelf(String shelfName, int shelfSize) {
        ctrlSupermarket.createShelf(selectedSupermarket, shelfName, shelfSize);
    }

    /**
     * Elimina una estantería del supermercado seleccionado.
     *
     * @param shelfName Nombre de la estantería.
     */
    public void removeShelf(String shelfName) {
        ctrlSupermarket.removeShelf(selectedSupermarket, shelfName);
    }

    /**
     * Devuelve una lista de todas las estanterías del supermercado seleccionado.
     *
     * @return Lista de nombres de estanterías.
     */
    public List<String> getShelves() {
        return ctrlSupermarket.getShelves(selectedSupermarket);
    }

    /**
     * Devuelve la información de una estantería específica.
     *
     * @param shelfName Nombre de la estantería.
     * @return Información de la estantería.
     */
    public String getShelf(String shelfName) {
        return ctrlSupermarket.getShelf(selectedSupermarket, shelfName);
    }

    /**
     * Añade una restricción a una posición específica de una estantería.
     *
     * @param restriction Restricción a añadir.
     * @param shelfName   Nombre de la estantería.
     * @param index       Posición en la estantería.
     */
    public void addRestrictionToShelf(String restriction, String shelfName, int index) {
        if (ctrlProd.findCharacteristic(restriction) != null) {
            ctrlSupermarket.addRestriction(selectedSupermarket, shelfName, restriction, index);
        }
        else{
            throw new NoSuchElementException("No such restriction");
        }
    }

    /**
     * Elimina todas las restricciones de una posición en una estantería.
     *
     * @param shelfName Nombre de la estantería.
     * @param index     Posición en la estantería.
     */
    public void removeRestrictionsFromShelf(String shelfName, int index) {
        ctrlSupermarket.removeRestriction(selectedSupermarket, shelfName, index);
    }

    /**
     * Cambia el tamaño de una estantería en el supermercado seleccionado.
     *
     * @param shelfName Nombre de la estantería.
     * @param size      Nuevo tamaño de la estantería.
     */
    public void resizeShelf(String shelfName, int size) {
        ctrlSupermarket.resizeShelf(selectedSupermarket, shelfName, size);
    }

    /**
     * Añade una nueva característica al sistema.
     *
     * @param characteristicName Nombre de la característica.
     */
    public void addCharacteristic(String characteristicName) {
        ctrlProd.addCharacteristic(characteristicName);
    }

    /**
     * Comprueba las similitudes de un producto con otros.
     *
     * @param productName Nombre del producto.
     * @return Mapa con las similitudes del producto con otros productos.
     */
    public Map<String, Double> checkProductSimilarities(String productName) {
        return ctrlProd.checkProductSimilarities(productName);
    }

    /**
     * Modifica la similitud entre dos productos.
     *
     * @param productName1 Nombre del primer producto.
     * @param productName2 Nombre del segundo producto.
     * @param newValue     Nuevo valor de similitud.
     */
    public void modifySimilarity(String productName1, String productName2, Double newValue) {
        ctrlProd.modifySimilarity(productName1, productName2, newValue);
        ctrlSupermarket.updateSolutionMarks(productName1, ctrlProd.getSimilarityTable(), false);
    }

    /**
     * Comprueba la similitud entre dos productos.
     *
     * @param productName1 Nombre del primer producto.
     * @param productName2 Nombre del segundo producto.
     * @return Valor de similitud entre los dos productos.
     */
    public Double checkProductsSimilarity(String productName1, String productName2) {
        return ctrlProd.checkProductsSimilarity(productName1, productName2);
    }

    /**
     * Añade un producto al sistema.
     *
     * @param productName Nombre del producto.
     */
    public void addProduct(String productName) {
        ctrlProd.addProduct(productName);
    }

    /**
     * Establece las similitudes entre productos.
     *
     * @param similarities Matriz de similitudes entre productos.
     */
    public void setSimilarities(Double[] similarities) {
        ctrlProd.setSimilarities(similarities);
    }

    /**
     * Modifica las similitudes de un producto específico.
     *
     * @param productName   Nombre del producto a modificar.
     * @param similarities  Nuevas similitudes del producto.
     */
    public void modifyProductSimilarities(String productName, Double[] similarities) {
        ctrlProd.modifyProductSimilarities(productName, similarities);
        ctrlSupermarket.updateSolutionMarks(productName, ctrlProd.getSimilarityTable(), false);
    }

    /**
     * Añade una característica a un producto.
     *
     * @param characteristicName Nombre de la característica.
     * @param productName        Nombre del producto.
     */
    public void addCharacteristicProduct(String characteristicName, String productName) {
        ctrlProd.addCharacteristicProduct(characteristicName, productName);
        ctrlSupermarket.updateSolutionMarks(productName, ctrlProd.generateSimilarityTable(), true);
    }

    /**
     * Elimina una característica de un producto.
     *
     * @param characteristicName Nombre de la característica.
     * @param productName        Nombre del producto.
     */
    public void removeCharacteristicProduct(String characteristicName, String productName) {
        ctrlProd.removeCharacteristicProduct(characteristicName, productName);
        ctrlSupermarket.updateSolutionMarks(productName, ctrlProd.generateSimilarityTable(), true);
    }

    /**
     * Añade una restricción a un producto.
     *
     * @param restrictionName Nombre de la restricción.
     * @param productName     Nombre del producto.
     */
    public void addRestrictionProduct(String restrictionName, String productName) {
        ctrlProd.addRestrictionProduct(restrictionName, productName);
        ctrlSupermarket.invalidateProductSolution(productName);
    }

    /**
     * Elimina una restricción de un producto.
     *
     * @param restrictionName Nombre de la restricción.
     * @param productName     Nombre del producto.
     */
    public void removeRestrictionProduct(String restrictionName, String productName) {
        ctrlProd.removeRestrictionProduct(restrictionName, productName);
        ctrlSupermarket.invalidateProductSolution(productName);
    }

    /**
     * Lista todas las características disponibles en el sistema.
     *
     * @return Lista de nombres de características.
     */
    public List<String> listCharacteristics() {
        return ctrlProd.listCharacteristics();
    }

    /**
     * Lista todos los productos disponibles en el sistema.
     *
     * @return Lista de nombres de productos.
     */
    public List<String> listProducts() {
        return ctrlProd.listProducts();
    }

    /**
     * Muestra información detallada de un producto.
     *
     * @param productName Nombre del producto.
     * @return Información del producto.
     */
    public String printProduct(String productName) {
        return ctrlProd.printProduct(productName);
    }

    /**
     * Lista las características asociadas a un producto.
     *
     * @param productName Nombre del producto.
     * @return Lista de características del producto.
     */
    public List<String> listCharacteristicsProduct(String productName) {
        return ctrlProd.getCharacteristicsProducts(productName);
    }

    /**
     * Lista las restricciones asociadas a un producto.
     *
     * @param productName Nombre del producto.
     * @return Conjunto de restricciones del producto.
     */
    public Set<String> listRestrictionsProduct(String productName) {
        return ctrlProd.getRestrictionsProducts(productName);
    }

    /**
     * Añade un producto a un catálogo específico.
     *
     * @param catalogueName Nombre del catálogo.
     * @param productName   Nombre del producto.
     */
    public void addProductToCatalogue(String catalogueName, String productName) {
        ctrlSupermarket.addProductToCatalogue(selectedSupermarket, productName, catalogueName);
    }

    /**
     * Elimina un producto de un catálogo específico.
     *
     * @param catalogueName Nombre del catálogo.
     * @param productName   Nombre del producto.
     */
    public void removeProductCatalogue(String catalogueName, String productName) {
        ctrlSupermarket.removeProductFromCatalogue(selectedSupermarket, productName, catalogueName);
    }

    /**
     * Lista los productos de un catálogo específico.
     *
     * @param catalogueName Nombre del catálogo.
     * @return Lista de productos del catálogo.
     */
    public List<String> listProdsCatalogue(String catalogueName) {
        return ctrlSupermarket.listProdsCatalogue(selectedSupermarket, catalogueName);
    }

    /**
     * Lista los catálogos de un supermercado seleccionado.
     *
     * @return Lista de nombres de catálogos.
     */
    public List<String> listCatalogues() {
        return ctrlSupermarket.listCatalogues(selectedSupermarket);
    }

    /**
     * Añade un nuevo catálogo a un supermercado.
     *
     * @param catalogueName Nombre del catálogo.
     */
    public void addCatalogue(String catalogueName) {
        ctrlSupermarket.addCatalog(selectedSupermarket, catalogueName);
    }

    /**
     * Elimina un catálogo de un supermercado.
     *
     * @param catalogueName Nombre del catálogo.
     */
    public void removeCatalogue(String catalogueName) {
        ctrlSupermarket.deleteCatalog(selectedSupermarket, catalogueName);
    }

    /**
     * Genera una solución para un supermercado.
     *
     * @param solutionName       Nombre de la solución.
     * @param shelfName          Nombre de la estantería.
     * @param catalogueName      Nombre del catálogo.
     * @param algorithm          Algoritmo utilizado.
     * @param generatedSimilarity Indicador de similitudes generadas.
     */
    public void generateSolution(String solutionName, String shelfName, String catalogueName, int algorithm, boolean generatedSimilarity) {
        ctrlSupermarket.generateSolution(selectedSupermarket, solutionName, shelfName, catalogueName, generatedSimilarity, algorithm);
    }

    /**
     * Lista las soluciones de un supermercado.
     *
     * @return Lista de nombres de soluciones.
     */
    public List<String> listSolutions() {
        return ctrlSupermarket.getSolutions(selectedSupermarket);
    }

    /**
     * Devuelve información detallada de una solución.
     *
     * @param solutionName Nombre de la solución.
     * @return Información de la solución.
     */
    public String checkSolution(String solutionName) {
        return ctrlSupermarket.getSolution(selectedSupermarket, solutionName);
    }

    /**
     * Lista todos los supermercados registrados.
     *
     * @return Lista de nombres de supermercados.
     */
    public List<String> listSupermarkets() {
        return ctrlSupermarket.getSupermarkets();
    }

    /**
     * Elimina una solución de un supermercado.
     *
     * @param solutionName Nombre de la solución.
     */
    public void deleteSolution(String solutionName) {
        ctrlSupermarket.deleteSolution(selectedSupermarket, solutionName);
    }

    /**
     * Elimina un producto de una solución en un supermercado.
     *
     * @param solutionName Nombre de la solución.
     * @param index        Índice del producto en la solución.
     */
    public void deleteSolutionProduct(String solutionName, int index) {
        ctrlSupermarket.deleteSolutionProduct(selectedSupermarket, solutionName, index);
    }

    /**
     * Añade un producto a una solución en un supermercado.
     *
     * @param solutionName Nombre de la solución.
     * @param productName  Nombre del producto.
     * @param index        Índice en la solución.
     */
    public void addSolutionProduct(String solutionName, String productName, int index) {
        ctrlSupermarket.addSolutionProduct(selectedSupermarket, solutionName, productName, index);
    }

    /**
     * Cambia la posición de dos productos en una solución.
     *
     * @param idx1         Índice del primer producto.
     * @param idx2         Índice del segundo producto.
     * @param solutionName Nombre de la solución.
     */
    public void changeSolutionProducts(int idx1, int idx2, String solutionName) {
        ctrlSupermarket.changeSolutionProducts(selectedSupermarket, idx1, idx2, solutionName);
    }

    /**
     * Verifica el impacto de eliminar un producto de una solución.
     *
     * @param solution Nombre de la solución.
     * @param index    Índice del producto.
     * @return Puntuación resultante tras la eliminación.
     */
    public double checkDeleteSolutionProduct(String solution, int index) {
        return ctrlSupermarket.checkDeleteSolutionProduct(selectedSupermarket, solution, index);
    }

    /**
     * Verifica el impacto de añadir un producto a una solución.
     *
     * @param solution Nombre de la solución.
     * @param product  Nombre del producto.
     * @param index    Índice en la solución.
     * @return Puntuación resultante tras la adición.
     */
    public double checkAddSolutionProduct(String solution, String product, int index) {
        return ctrlSupermarket.checkAddSolutionProduct(selectedSupermarket, solution, product, index);
    }

    /**
     * Verifica el impacto de intercambiar dos productos en una solución.
     *
     * @param solution Nombre de la solución.
     * @param index1   Índice del primer producto.
     * @param index2   Índice del segundo producto.
     * @return Puntuación resultante tras el intercambio.
     */
    public double checkSwapSolution(String solution, int index1, int index2) {
        return ctrlSupermarket.checkSwapSolution(selectedSupermarket, solution, index1, index2);
    }

    /**
     * Selecciona un supermercado para operar.
     *
     * @param selectedSupermarket Nombre del supermercado.
     * @throws SupermarketNotFoundException si el supermercado no existe.
     */
    public void setSelectedSupermarket(String selectedSupermarket) {
        if (ctrlSupermarket.existsSupermarket(selectedSupermarket)) {
            this.selectedSupermarket = selectedSupermarket;
        } else {
            throw new SupermarketNotFoundException("Supermarket '" + selectedSupermarket + "' not found");
        }
    }

    /**
     * Obtiene la tabla de similitudes entre productos.
     *
     * @return Tabla de similitudes.
     */
    public List<List<Double>> getSimilarityTable() {
        return ctrlProd.getSimilarityTable();
    }

    /**
     * Obtiene el usuario actualmente logueado.
     *
     * @return Usuario logueado.
     */
    public User getLoggedUser() {
        return loggedUser;
    }

    /**
     * Obtiene el supermercado actualmente seleccionado.
     *
     * @return Nombre del supermercado seleccionado.
     */
    public String getSelectedSupermarket() {
        return selectedSupermarket;
    }



}






