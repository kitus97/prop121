package com.prop.prop12_1.shell;

import com.prop.prop12_1.controller.CtrlDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.*;

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
        ctrlDomain.addCharacteristic(characteristicName);
        return "Characteristic was added successfully to the system.";
    }

    @ShellMethod(value = "List all characteristics", key = {"list-characteristics", "ls-char"},
            group = "Admin Similarity Management")
    public String listCharacteristics() {
        return "List of characteristics:\n" + ctrlDomain.listCharacteristics();
    }

    @ShellMethod(value = "Check the similarity between two existing products", key = {"check-similarity", "chk-sim"},
            group = "Admin Similarity Management")
    public String checkSimilarity(
            @ShellOption(help = "Product 1 name") String product1Name,
            @ShellOption(help = "Product 2 name") String product2Name
    ) {
        return "Similarity between '" + product1Name + "' and '" + product2Name + "' is: "
                + ctrlDomain.checkProductsSimilarity(product1Name, product2Name);
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
            throw new IllegalArgumentException("Similarity must be between 0.0 and 1.0");
        } else {
            ctrlDomain.modifySimilarity(product1Name, product2Name, newSimilarity);
            return "Similarity between '" + product1Name + "' and '" + product2Name + "' has been changed to " + newSimilarityString;
        }
    }

    @ShellMethod(value = "Add a new supermarket to the system", key = {"add-supermarket", "add-super"},
            group = "Admin Supermarket Management")
    public String addSupermarket(
            @ShellOption(help = "Supermarket name") String supermarketName
    ) {
        ctrlDomain.addSupermarket(supermarketName);
        return "Supermarket was added successfully to the system.";
    }

    @ShellMethod(value = "Removes an existing supermarket from the system", key = {"remove-supermarket","rm-super"},
            group = "Admin Supermarket Management")
    public String removeSupermarket(
            @ShellOption(help = "Supermarket name") String supermarketName
    ) {
        ctrlDomain.removeSupermarket(supermarketName);
        return "Supermarket was removed successfully from the system.";
    }

    @ShellMethod(value = "Lists all the supermarkets from the system", key = {"list-supermarkets","ls-super"},
            group = "Admin Supermarket Management")
    public String listSupermarkets() {
        return "Supermarkets list:\n" + ctrlDomain.listSupermarkets();
    }

    @ShellMethod(value = "Add a new product to the system", key = {"add-product", "add-prod"},
            group = "Admin Product Management")
    public String addProduct(
            @ShellOption(help = "Product name") String productName
    ) {
        Set<String> systemCharacteristics = new HashSet<>(ctrlDomain.listCharacteristics());
        Set<String> characteristics = new HashSet<>();
        Scanner scanner = new Scanner(System.in);
        ctrlDomain.addProduct(productName);
        System.out.println("Product was added successfully\n");

        System.out.print("Do you want to add a characterístic to the product? (Y/N) ");

        String confirmation = scanner.nextLine().trim().toUpperCase();

        while (confirmation.equals("Y")) {
            System.out.print("\nEnter the name of the characteristic: ");
            String newCharacteristic = scanner.nextLine();
            if (!systemCharacteristics.contains(newCharacteristic)) {
                System.err.println("Characteristic with name '" + newCharacteristic + "' was not found");
            } else {
                if (!characteristics.add(newCharacteristic)) {
                    System.err.println("Characteristic with name '" + newCharacteristic + "' was already added");
                } else {
                    System.out.println("Characteristic '" + newCharacteristic + "' added.");
                }
            }

            System.out.print("\nDo you want to add another characteristic to the product? (Y/N) ");
            confirmation = scanner.nextLine().trim().toUpperCase();
        }


        characteristics.forEach(characteristicName -> ctrlDomain.addCharacteristicProduct(characteristicName, productName));
        System.out.println("Product characteristics: " + characteristics + "\n");

        List<String> products = ctrlDomain.listProducts();
        products.removeLast();
        if (products.isEmpty()) {
            Double [] x = {0.0};
            ctrlDomain.setSimilarities(x);
            return "";
        }

        System.out.print("Do you want to add the similarities with other products manually? (Y/N) ");
        confirmation = scanner.nextLine().trim().toUpperCase();

        while (true) {
            if (confirmation.equals("Y")) {
                List<Double> similarities = new ArrayList<>();

                products.forEach(product -> {
                    System.out.print("Enter the similarity value with product '" + product + "': ");

                    double value = Double.parseDouble(scanner.nextLine());

                    while (value < 0.0 || value > 1.0) {
                        System.err.println("Similarity value must be between 0.0 and 1.0");

                        System.out.print("Enter the similarity value with product '" + product + "': ");
                        value = Double.parseDouble(scanner.nextLine());
                    }

                    similarities.addLast(value);
                });

                Double[] result = similarities.toArray(Double[]::new);
                ctrlDomain.setSimilarities(result);
                return "Product '" + productName + "' was added successfully to the system.";
            } else if (confirmation.equals("N")) {
                ctrlDomain.setSimilarities(null);
                return "Product '" + productName + "' was added successfully to the system.";
            } else {
                System.out.print("Do you want to add the similarities with other products manually? (Y/N)");
                confirmation = scanner.nextLine().trim().toUpperCase();
            }
        }
    }

    @ShellMethod(value = "Modify a product by adding or removing a characteristic", key = {"modify-product", "mod-prod"},
            group = "Admin Product Management")
    public String modifyProduct(
            @ShellOption(help = "Product name") String productName,
            @ShellOption(help = "Characteristic to modify") String characteristicName,
            @ShellOption(value = {"-r","--remove"}, arity = 0, help = "Remove the characteristic", defaultValue = "false") boolean remove
    ) {

        if (!remove) {
            ctrlDomain.addCharacteristicProduct(characteristicName, productName);
            System.out.println("Characteristic '" + characteristicName + "' added to product '" + productName + "'.");
        } else {
            ctrlDomain.removeCharacteristicProduct(characteristicName, productName);
            System.out.println("Characteristic '" + characteristicName + "' removed from product '" + productName + "'.");
        }

        Scanner scanner = new Scanner(System.in);

        List<String> products = ctrlDomain.listProducts();
        if (products.size() == 1) {
            return "";
        }

        System.out.print("Do you want to update the similarities of the modified product? (Y/N) ");
        String confirmation = new Scanner(System.in).nextLine().trim().toUpperCase();

        while (!confirmation.equals("Y")) {
            if (confirmation.equals("N")) {
                return "Product '" + productName + "' was modified successfully.";
            } else {
                System.out.println("Only Y or N accepted.");
                System.out.print("Do you want to update the similarities of the modified product? (Y/N) ");
                confirmation = scanner.nextLine().trim().toUpperCase();
            }
        }

        System.out.print("Do you want to add the similarities with other products manually? (Y/N) ");
        confirmation = new Scanner(System.in).nextLine().trim().toUpperCase();

        while (true) {
            if (confirmation.equals("Y")) {
                List<Double> similarities = new ArrayList<>();

                products.forEach(product -> {
                    System.out.print("Enter the similarity value with product '" + product + "': ");

                    double value = Double.parseDouble(scanner.nextLine());

                    while (value < 0.0 || value > 1.0) {
                        System.err.println("Similarity value must be between 0.0 and 1.0");

                        System.out.print("Enter the similarity value with product '" + product + "': ");
                        value = Double.parseDouble(scanner.nextLine());
                    }

                    similarities.addLast(value);
                });

                Double[] result = similarities.toArray(Double[]::new);
                ctrlDomain.modifyProductSimilarities(productName, result);
                return "Product '" + productName + "' was modified successfully.";
            } else if (confirmation.equals("N")) {
                ctrlDomain.modifyProductSimilarities(productName, null);
                return "Product '" + productName + "' was modified successfully.";
            } else {
                System.out.println("Only Y or N accepted.");
                System.out.print("Do you want to add the similarities with other products manually? (Y/N)");
                confirmation = scanner.nextLine().trim().toUpperCase();
            }
        }
    }

    @ShellMethod(value = "Add a restriction to an existing product", key = {"add-product-restriction", "add-prod-rest"},
            group = "Admin Product Management")
    public String addProductRestriction(
            @ShellOption(help = "Product name") String productName,
            @ShellOption(help = "Restriction name") String restrictionName
    ) {
        ctrlDomain.addRestrictionProduct(restrictionName, productName);
        return "Restriction '" + restrictionName + "' was added to product '" + productName + "'.";
    }

    @ShellMethod(value = "Removes a restriction from an existing product", key = {"remove-product-restriction", "rm-prod-rest"},
            group = "Admin Product Management")
    public String removeProductRestriction(
            @ShellOption(help = "Product name") String productName,
            @ShellOption(help = "Restriction name") String restrictionName
    ) {
        ctrlDomain.removeRestrictionProduct(restrictionName, productName);
        return "Restriction '" + restrictionName + "' was added to product '" + productName + "'.";
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
        return ctrlDomain.printProduct(productName);
    }
}