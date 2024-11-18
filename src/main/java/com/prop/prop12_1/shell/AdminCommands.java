package com.prop.prop12_1.shell;

import com.prop.prop12_1.controller.CtrlDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.*;
import java.util.stream.Collectors;

@ShellComponent
public class AdminCommands {

    @Autowired
    private CtrlDomain ctrlDomain;

    public AdminCommands(CtrlDomain ctrlDomain) {
        this.ctrlDomain = ctrlDomain;
    }

    @ShellMethod(value = "Add a new characteristic to the system", key = {"add-characteristic", "add-char"},
            group = "Admin Similarity Management")
    public String addCharacteristic(
            @ShellOption(help = "Characteristic name") String characteristicName
    ) {
        try {
            ctrlDomain.addCharacteristic(characteristicName);
            return "Characteristic was added successfully to the system.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";

    }

    @ShellMethod(value = "List all characteristics", key = {"list-characteristics", "ls-char"},
            group = "Admin Similarity Management")
    public String listCharacteristics() {
        return "List of characteristics:\n" + ctrlDomain.listCharacteristics();
    }

    @ShellMethod(value = "Modify the similarity between two existing products", key = {"modify-similarity", "mod-sim"},
            group = "Admin Similarity Management")
    public String modifySimilarity(
            @ShellOption(help = "Product 1 name") String product1Name,
            @ShellOption(help = "Product 2 name") String product2Name,
            @ShellOption(help = "New similarity value") String newSimilarityString
    ) {
        double newSimilarity = Double.parseDouble(newSimilarityString);
        if (newSimilarity < 0.0 || newSimilarity > 1.0) {
            System.out.println("Error: Similarity must be between 0.0 and 1.0");
            return "";
        } else {
            try {
                ctrlDomain.modifySimilarity(product1Name, product2Name, newSimilarity);
                return "Similarity between '" + product1Name + "' and '" + product2Name + "' has been changed to " + newSimilarityString;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return "";
        }
    }

    @ShellMethod(value = "Add a new supermarket to the system", key = {"add-supermarket", "add-super"},
            group = "Admin Supermarket Management")
    public String addSupermarket(
            @ShellOption(help = "Supermarket name") String supermarketName
    ) {
        try {
            ctrlDomain.addSupermarket(supermarketName);
            return "Supermarket was added successfully to the system.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Removes an existing supermarket from the system", key = {"remove-supermarket","rm-super"},
            group = "Admin Supermarket Management")
    public String removeSupermarket(
            @ShellOption(help = "Supermarket name") String supermarketName
    ) {
        try {
            ctrlDomain.removeSupermarket(supermarketName);
            ctrlDomain.setSelectedSupermarket("");
            return "Supermarket was removed successfully from the system.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Lists all the supermarkets from the system", key = {"list-supermarkets","ls-super"},
            group = "Admin Supermarket Management")
    public String listSupermarkets() {
        return "Supermarkets list:\n" + ctrlDomain.listSupermarkets();
    }

    @ShellMethod(value = "Add a new product to the system", key = {"add-product", "add-prod"},
            group = "Admin Product Management")
    public String addProduct(
            @ShellOption(help = "Product name") String productName,
            @ShellOption(defaultValue = "", value = {"-c"}, help = "Characteristics") String characteristicsList,
            @ShellOption(defaultValue = "", value = {"-s"}, help = "Similarities") String similarities
    ) {
        try {
            Set<String> systemCharacteristics = new HashSet<>(ctrlDomain.listCharacteristics());
            Set<String> characteristics = Arrays.stream(characteristicsList.split(",")).collect(Collectors.toSet());

            ctrlDomain.addProduct(productName);
            System.out.println("Product was added successfully\n");

            if (!characteristicsList.isEmpty()) {
                for (String newCharacteristic : characteristics) {
                    if (!systemCharacteristics.contains(newCharacteristic)) {
                        System.out.println("Error: Characteristic with name '" + newCharacteristic + "' was not found");
                    } else {
                        System.out.println("Characteristic '" + newCharacteristic + "' added.");
                    }
                }


                characteristics.forEach(characteristicName -> ctrlDomain.addCharacteristicProduct(characteristicName, productName));
                System.out.println("Product characteristics: " + characteristics + "\n");
            }

            List<String> products = ctrlDomain.listProducts();
            if (products.size() == 1) {
                ctrlDomain.setSimilarities(new Double[0]);
                return "Product similarities were added successfully to the system.";
            }

            if (!similarities.isEmpty()) {
                List<Double> similaritiesList = Arrays.stream(similarities.split(","))
                        .map(Double::parseDouble)
                        .toList();
                if (products.size() - 1 != similaritiesList.size()) {
                    System.out.println("Error: Incorrect similarity list size. Size must be: " + (products.size()-1));
                    return "";
                }

                for (double value : similaritiesList) {
                    if (value < 0.0 || value > 1.0) {
                        System.out.println("Error: Similarity value must be between 0.0 and 1.0");
                        System.out.println("Error: Default value: 0.0");
                        value = 0.0;
                    }
                }

                Double[] result = similaritiesList.toArray(Double[]::new);
                ctrlDomain.setSimilarities(result);
            } else {
                ctrlDomain.setSimilarities(null);
            }
            return "Product similarities were added successfully to the system.";

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Modify a product by adding or removing a characteristic", key = {"modify-product", "mod-prod"},
            group = "Admin Product Management")
    public String modifyProduct(
            @ShellOption(help = "Product name") String productName,
            @ShellOption(help = "Characteristic to modify") String characteristicName,
            @ShellOption(value = {"-r","--remove"}, arity = 0, help = "Remove the characteristic", defaultValue = "false") boolean remove
    ) {

        try {
            if (!remove) {
                ctrlDomain.addCharacteristicProduct(characteristicName, productName);
                System.out.println("Characteristic '" + characteristicName + "' added to product '" + productName + "'.");
            } else {
                ctrlDomain.removeCharacteristicProduct(characteristicName, productName);
                System.out.println("Characteristic '" + characteristicName + "' removed from product '" + productName + "'.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Modify the similarities of a product", key = {"modify-product-similarities", "mod-prod-sim"},
            group = "Admin Product Management")
    public String modifyProductSimilarities(
            @ShellOption (help = "Product Name") String productName,
            @ShellOption (defaultValue = "", help = "Similarities", value = {"-s"}) String similarities
    ) {
        try {
            List<String> products = ctrlDomain.listProducts();
            if (!products.contains(productName)) {
                System.out.println("Error: Product '" + productName + "' does not exist.");
                return "";
            }

            if (products.size() == 1) {
                ctrlDomain.modifyProductSimilarities(productName, new Double[0]);
                return "Product similarities were added successfully to the system.";
            }

            if (!similarities.isEmpty()) {
                List<Double> similaritiesList = new ArrayList<>(Arrays.stream(similarities.split(","))
                        .map(Double::parseDouble)
                        .toList());

                if (products.size() - 1 != similaritiesList.size()) {
                    System.out.println("Error: Incorrect similarity list size. Size must be: " + (products.size()-1));
                    return "";
                }

                for (int i = 0; i < similaritiesList.size(); i++) {
                    double value = similaritiesList.get(i);
                    if (value < 0.0 || value > 1.0) {
                        System.out.println("Error: Similarity value must be between 0.0 and 1.0");
                        System.out.println("Error: Default value: 0.0");
                        similaritiesList.set(i, 0.0);
                    }
                }

                Double[] result = similaritiesList.toArray(Double[]::new);
                ctrlDomain.modifyProductSimilarities(productName,result);
            } else {
                ctrlDomain.modifyProductSimilarities(productName,null);
            }
            return "Product similarities were added successfully to the system.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Add a restriction to an existing product", key = {"add-product-restriction", "add-prod-rest"},
            group = "Admin Product Management")
    public String addProductRestriction(
            @ShellOption(help = "Product name") String productName,
            @ShellOption(help = "Restriction name") String restrictionName
    ) {
        try {
            ctrlDomain.addRestrictionProduct(restrictionName, productName);
            return "Restriction '" + restrictionName + "' was added to product '" + productName + "'.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Removes a restriction from an existing product", key = {"remove-product-restriction", "rm-prod-rest"},
            group = "Admin Product Management")
    public String removeProductRestriction(
            @ShellOption(help = "Product name") String productName,
            @ShellOption(help = "Restriction name") String restrictionName
    ) {
        try {
            ctrlDomain.removeRestrictionProduct(restrictionName, productName);
            return "Restriction '" + restrictionName + "' was added to product '" + productName + "'.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "List all products", key = {"list-products", "ls-prod"},
            group = "Admin Product Management")
    public String listProducts() {
        return "List of products:\n" + ctrlDomain.listProducts();
    }

    @ShellMethod(value = "List the characteristics of a product", key = {"list-product-characteristics", "ls-prod-char"},
            group = "Admin Product Management")
    public String listProductCharacteristics(
            @ShellOption("prodName") String productName
    ) {
        try {
            return ctrlDomain.printProduct(productName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}