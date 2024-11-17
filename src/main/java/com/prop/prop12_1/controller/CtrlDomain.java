package com.prop.prop12_1.controller;

import com.prop.prop12_1.exceptions.IncorrectPasswordException;
import com.prop.prop12_1.exceptions.SupermarketNotFoundException;
import com.prop.prop12_1.exceptions.UserAlreadyExistsException;
import com.prop.prop12_1.exceptions.UserNotFoundException;
import com.prop.prop12_1.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class CtrlDomain {

    private CtrlSupermarket ctrlSupermarket;
    private CtrlProd ctrlProd;
    private CtrlAlgorithm ctrlAlgorithm;
    private final UserPersistence userPersistence;

    private Map<String, User> users;
    private User loggedUser;
    private String selectedSupermarket;

    public CtrlDomain() {
        this.ctrlSupermarket = new CtrlSupermarket();
        this.ctrlProd = new CtrlProd();
        this.ctrlAlgorithm = new CtrlAlgorithm();

        this.userPersistence = new UserPersistence();
        users = userPersistence.loadUsers();
    }

    public void addUser(String username, String password, String role) {
        if (users.containsKey(username)) {
            throw new UserAlreadyExistsException("User '" + username + "' already exists");
        }
        User user = new User(username, password, Role.fromString(role));
        users.put(username, user);
        userPersistence.saveUsers(users);
    }

    public void login(String username, String password) {
        User user = users.get(username);
        System.out.println(user.toString());
        if (user == null) {
            throw new UserNotFoundException("User '" + username + "' not found");
        } else if (!user.getPassword().equals(password)) {
            throw new IncorrectPasswordException("Incorrect password");
        } else {
            loggedUser = user;
        }
    }

    public void logout() {
        loggedUser = null;
    }

    public boolean isAdmin() {
        return loggedUser != null && loggedUser.isAdmin();
    }

    public String listUsers() {
        StringBuilder builder = new StringBuilder();
        for (User user : users.values()) {
            builder.append(user.getUsername()).append(" (").append(user.getRole()).append(")\n");
        }
        return builder.toString();
    }

    public void addSupermarket(String supermarketName) {
        ctrlSupermarket.addSupermarket(supermarketName);
    }

    public void removeSupermarket(String supermarketName) {
        ctrlSupermarket.removeSupermarket(supermarketName);
    }

    public void addShelf(String shelfName, int shelfSize){
        ctrlSupermarket.createShelf(selectedSupermarket, shelfName, shelfSize);
    }

    public void removeShelf(String shelfName){
        ctrlSupermarket.removeShelf(selectedSupermarket, shelfName);
    }

    public List<String> getShelves(){
        return ctrlSupermarket.getShelves(selectedSupermarket);
    }

    public String getShelf(String shelfName){
        return ctrlSupermarket.getShelf(selectedSupermarket, shelfName);
    }

    //revisar metode
    public void addRestrictionToShelf(String restriction, String shelfName, int index){
        if (ctrlProd.findCharacteristic(restriction) != null){
            ctrlSupermarket.addRestriction(selectedSupermarket, shelfName, restriction, index);
        }
    }

    public void removeRestrictionsFromShelf(String shelfName, int index){
        ctrlSupermarket.removeRestriction(selectedSupermarket, shelfName, index);
    }

    public void resizeShelf(String shelfName, int size){
        ctrlSupermarket.resizeShelf(selectedSupermarket, shelfName, size);
    }

    public void addCharacteristic(String characteristicName){
        ctrlProd.addCharacteristic(characteristicName);
    }

    public Map<String,Double> checkProductSimilarities(String productName) {
        return ctrlProd.checkProductSimilarities(productName);
    }

    public void modifySimilarity(String productName1, String productName2, Double newValue) {
        ctrlProd.modifySimilarity(productName1, productName2, newValue);
    }

    public Double checkProductsSimilarity(String productName1, String productName2) {
        return ctrlProd.checkProductsSimilarity(productName1, productName2);
    }

    public void addProduct(String productName){
        ctrlProd.addProduct(productName);
    }

    public void setSimilarities(Double[] similarities) {
        ctrlProd.setSimilarities(similarities);
    }

    public void modifyProductSimilarities(String productName, Double[] similarities) {
        ctrlProd.modifyProductSimilarities(productName, similarities);
    }

    public void addCharacteristicProduct(String characteristicName, String productName) {
        ctrlProd.addCharacteristicProduct(characteristicName, productName);
        ctrlSupermarket.invalidateProductSolution(productName);
    }

    public void removeCharacteristicProduct(String characteristicName, String productName) {
        ctrlProd.removeCharacteristicProduct(characteristicName, productName);
        ctrlSupermarket.invalidateProductSolution(productName);
    }

    public void addRestrictionProduct(String restrictionName, String productName) {
        ctrlProd.addRestrictionProduct(restrictionName, productName);
        ctrlSupermarket.invalidateProductSolution(productName);
    }

    public void removeRestrictionProduct(String restrictionName, String productName) {
        ctrlProd.removeRestrictionProduct(restrictionName, productName);
        ctrlSupermarket.invalidateProductSolution(productName);
    }

    public List<String> listCharacteristics() {
        return ctrlProd.listCharacteristics();
    }

    public List<String> listProducts() {
        return ctrlProd.listProducts();
    }

    public String printProduct(String productName) {
        return ctrlProd.printProduct(productName);
    }

    public List<String> listCharacteristicsProduct(String productName) {
        return ctrlProd.getCharacteristicsProducts(productName);
    }

    //enlloc de set list millor? marc.esteve.rodriguez
    //depen per a que,  si es fa servier per a l'algorisme, aquell fa servir set, sino no se marc.rams.estrada
    public Set<String> listRestrictionsProduct(String productName) {
        return ctrlProd.getRestrictionsProducts(productName);
    }

    public void addProductToCatalogue(String catalogueName, String productName){
        ctrlSupermarket.addProductToCatalogue(selectedSupermarket, productName, catalogueName);
    }

    public void removeProductCatalogue(String catalogueName, String productName){
        ctrlSupermarket.removeProductFromCatalogue(selectedSupermarket, productName, catalogueName);

    }

    public List<String> listProdsCatalogue(String catalogueName){
        return ctrlSupermarket.listProdsCatalogue(selectedSupermarket, catalogueName);
    }

    public List<String> listCatalogues() {
        return ctrlSupermarket.listCatalogues(selectedSupermarket);
    }

    public void addCatalogue(String catalogueName){
        ctrlSupermarket.addCatalog(selectedSupermarket, catalogueName);
    }

    public void removeCatalogue(String catalogueName){
        ctrlSupermarket.deleteCatalog(selectedSupermarket, catalogueName);
    }

    public void generateSolution(String solutionName, String shelfName, String catalogueName, int algorithm, boolean generatedSimilarity){
        ctrlSupermarket.generateSolution(selectedSupermarket, solutionName, shelfName, catalogueName, generatedSimilarity, algorithm);
        //no se si queremos que cuando se genere, automaticamente imprima la solucion
    }

    public List<String> listSolutions(){
        return ctrlSupermarket.getSolutions(selectedSupermarket);
    }

    public String checkSolution(String solutionName){
        return ctrlSupermarket.getSolution(selectedSupermarket, solutionName);
    }

    public List<String> listSupermarkets(){
        return ctrlSupermarket.getSupermarkets();
    }

    public void deleteSolution(String solutionName){
        ctrlSupermarket.deleteSolution(selectedSupermarket, solutionName);
    }

    public void deleteSolutionProduct(String solutionName, int index){
        ctrlSupermarket.deleteSolutionProduct(selectedSupermarket, solutionName, index);
    }

    public void addSolutionProduct(String solutionName, String productName, int index){
        ctrlSupermarket.addSolutionProduct(selectedSupermarket, solutionName, productName, index);
    }

    public void changeSolutionProducts(int idx1, int idx2, String solutionName){
        ctrlSupermarket.changeSolutionProducts(selectedSupermarket, idx1, idx2, solutionName);
    }

    public double checkDeleteSolutionProduct(String solution, int index){
        return ctrlSupermarket.checkDeleteSolutionProduct(selectedSupermarket, solution, index);
    }

    public double checkAddSolutionProduct(String solution, String product, int index){
        return ctrlSupermarket.checkAddSolutionProduct(selectedSupermarket, solution, product, index);
    }

    public double checkSwapSolution(String solution, int index1, int index2){
        return ctrlSupermarket.checkSwapSolution(selectedSupermarket, solution, index1, index2);
    }

    public void setSelectedSupermarket(String selectedSupermarket){
        if (ctrlSupermarket.existsSupermarket(selectedSupermarket)) {
            this.selectedSupermarket = selectedSupermarket;
        } else {
            throw new SupermarketNotFoundException("Supermarket '"+selectedSupermarket+"' not found");
        }
    }

    public List<List<Double>> getSimilarityTable(){
        return ctrlProd.getSimilarityTable();
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public String getSelectedSupermarket() {
        return selectedSupermarket;
    }
}
