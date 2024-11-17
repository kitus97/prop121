package com.prop.prop12_1.shell;

import com.prop.prop12_1.controller.CtrlDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.Scanner;

@ShellComponent
public class UserCommands {

    @Autowired
    private CtrlDomain ctrlDomain;

    public UserCommands(CtrlDomain ctrlDomain) {
        this.ctrlDomain = ctrlDomain;
    }

    @ShellMethod(value = "Select a supermarket", key = {"select-supermarket", "select-super"},
                 group = "User Supermarket Management")
    public String selectSupermarket(
            @ShellOption(help = "Supermarket name") String supermarketName
    ) {
        try {
            ctrlDomain.setSelectedSupermarket(supermarketName);
            return "Supermarket '" + supermarketName + "' was selected.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Check the supermarket you are working on", key = {"check-supermarket", "chk-super"},
            group = "User Supermarket Management")
    public String checkSupermarket() {
        return ctrlDomain.getSelectedSupermarket();
    }

    @ShellMethod(value = "Changes the selected supermarket", key = {"change-supermarket", "change-super"},
            group = "User Supermarket Management")
    public String changeSupermarket(
            @ShellOption(help = "New supermarket") String supermarketName
    ) {
        try {
            ctrlDomain.setSelectedSupermarket(supermarketName);
            return "Supermarket was changed to '" + supermarketName + "'.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "List all the shelves of the supermarket", key = {"show-similarities", "sh-sim"})
    public String getSimilarityTable() {
        List<List<Double>> mat = ctrlDomain.getSimilarityTable();
        System.out.println("Similarities:");
        for (List<Double> row : mat) {
            System.out.println(row);
        }
        return "";
    }

    @ShellMethod(value = "Check the similarity between two products", key = {"check-products-similarity", "chk-prods-sim"},
                 group = "Similarity Management")
    public String checkProductsSimilarity(
            @ShellOption(help = "Product 1 name") String product1Name,
            @ShellOption(help = "Product 2 name") String product2Name
    ) {
        try {
            double value = ctrlDomain.checkProductsSimilarity(product1Name, product2Name);
            return "Similarity between '" + product1Name + "' and '" + product2Name + "': " + value;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Adds a new catalogue to the supermarket", key = {"add-catalogue", "add-cat"},
            group = "Catalogue Management")
    public String createCatalogue(
            @ShellOption(help = "Catalogue name") String catalogueName
    ) {
        try {
            ctrlDomain.addCatalogue(catalogueName);
            return "Catalogue '" + catalogueName + "' was added.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Removes an existing catalogue of the supermarket", key = {"delete-catalogue", "rm-cat"},
                 group = "Catalogue Management")
    public String deleteCatalogue(
            @ShellOption(help = "Catalogue name") String catalogueName
    ) {
        try {
            ctrlDomain.removeCatalogue(catalogueName);
            return "Catalogue '" + catalogueName + "' was removed.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Modifies a catalogue of the supermarket by adding or removing a product", key = {"modify-catalogue", "mod-cat"},
            group = "Catalogue Management")
    public String modifyCatalogue(
            @ShellOption(help = "Catalogue name") String catalogueName,
            @ShellOption(help = "Product name") String productName,
            @ShellOption(value = {"-r","--remove"}, arity = 0, help = "Remove the product", defaultValue = "false") boolean remove
    ) {
        try {
            if (!remove) {
                ctrlDomain.addProductToCatalogue(catalogueName, productName);
                return "Product '" + productName + "' was added to the catalogue '" + catalogueName + "'.";
            } else {
                ctrlDomain.removeProductCatalogue(catalogueName, productName);
                return "Product '" + productName + "' was removed from the catalogue '" + catalogueName + "'.";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Lists all the catalogues of the supermarket", key = {"list-catalogues", "ls-cats"},
            group = "Catalogue Management")
    public String listCatalogues() {
        return String.format("List of catalogues:\n [%s]", ctrlDomain.listCatalogues());
    }

    @ShellMethod(value = "Lists all the products of a catalogue of the supermarket", key = {"list-catalogue-products", "ls-cat-prods"},
                 group = "Catalogue Management")
    public String listCatalogueProducts(
            @ShellOption(help = "Catalogue name") String catalogueName
    ) {
        try {
            return String.format("List of products of catalogue '[%s]':\n [%s]", catalogueName, ctrlDomain.listProdsCatalogue(catalogueName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Adds a new shelf to the supermarket", group = "Shelf Management")
    public String addShelf(
            @ShellOption(help = "Shelf name") String shelfName,
            @ShellOption(help = "Shelf size") int shelfSize
    ) {
        try {
            ctrlDomain.addShelf(shelfName, shelfSize);
            return "Shelf '" + shelfName + "' was added.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Removes an existing shelf of the supermarket", key = {"remove-shelf", "rm-shelf"},
            group = "Shelf Management")
    public String removeShelf(
            @ShellOption (help = "Shelf name") String shelfName
    ) {
        try {
            ctrlDomain.removeShelf(shelfName);
            return "Shelf '" + shelfName + "' was removed.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Add a restriction to an existing shelf of the supermarket", key = {"add-restriction", "add-rest"},
            group = "Shelf Management")
    public String addRestriction(
            @ShellOption(help = "Shelf name") String shelfName,
            @ShellOption(help = "Restriction name") String restrictionName,
            @ShellOption(help = "Shelf index") int idx
    ) {
        try {
            ctrlDomain.addRestrictionToShelf(restrictionName, shelfName, idx);
            return "Restriction '" + restrictionName + "' was added to the shelf '" + shelfName + "' at index '" + idx + "'.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Remove a restriction from an existing shelf of the supermarket", key = {"remove-restriction", "rm-rest"},
            group = "Shelf Management")
    public String removeRestriction(
            @ShellOption(help = "Shelf name") String shelfName,
            @ShellOption(help = "Shelf index") int idx
    ) {
        try {
            ctrlDomain.removeRestrictionsFromShelf(shelfName, idx);
            return "Restriction was removed from the shelf '" + shelfName + "' at index '" + idx + "'.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Resize an existing shelf of the supermarket", key = {"resize-shelf", "rs-shelf"},
            group = "Shelf Management")
    public String resizeShelf(
            @ShellOption(help = "Shelf name") String shelfName,
            @ShellOption(help = "New shelf size") int size
    ) {
        try {
            ctrlDomain.resizeShelf(shelfName, size);
            return "Shelf '" + shelfName + "' was resized.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Check an existing shelf of the supermarket", key = {"check-shelf", "chk-shelf"},
                 group = "Shelf Management")
    public String checkShelf(
            @ShellOption(help = "Shelf name") String shelfName
    ) {
        try {
            return ctrlDomain.getShelf(shelfName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "List all the shelves of the supermarket", key = {"list-shelves", "ls-shelves"},
            group = "Shelf Management")
    public String listShelves() {
        return String.format("List of shelves:\n [%s]", ctrlDomain.getShelves());
    }


    /**
     * Solution management
     */
    @ShellMethod(value = "Generates an optimal shelf distribution based on the similarity of the products from the choosed catalogue",
                 group = "Solution Management")
    public String generateSolution(
            @ShellOption(help = "Solution name") String solutionName,
            @ShellOption(help = "Catalogue name") String catalogueName,
            @ShellOption(help = "Shelf name") String shelfName,
            @ShellOption(help = "Algorithm | Backtracking, HillClimbing or Greedy") String algorithm,
            @ShellOption(help = "Used generated similarity table or manual table | 1 - generated, 2 - manual") int heuristic
    ) {
        int algorithmID;
        if (algorithm.equals("Backtracking")) algorithmID = 0;
        else if (algorithm.equals("HillClimbing")) algorithmID = 1;
        else if (algorithm.equals("Greedy")) algorithmID = 2;
        else {
            System.out.println("Error: Unsupported algorithm: Algorithm must be Backtracking, HillClimbing or Greedy. Written: " + algorithm);
            return "";
        }

        boolean heuristicBool;
        if (heuristic == 1) heuristicBool = true;
        else if (heuristic == 2) heuristicBool = false;
        else {
            System.out.println("Error: Heuristic must be 1 or 2. Written: " + heuristic);
            return "";
        }


        try {
            ctrlDomain.generateSolution(solutionName, shelfName, catalogueName, algorithmID, heuristicBool);
            return "Solution '" + solutionName + "' was generated.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Modifies a solution by interchanging the products of two positions", key = {"swap-positions-solution", "swap-pos"},
            group = "Solution Management")
    public String swapPositionsSolution(
            @ShellOption(help = "Solution name") String solutionName,
            @ShellOption(help = "Position 1") int position1,
            @ShellOption(help = "Position 2") int position2
    ) {
        try {
            double newMark = ctrlDomain.checkSwapSolution(solutionName, position1, position2);
            System.out.println("New mark after the modification: " + newMark);


            String confirmation;

            do {
                System.out.print("Do you want to keep the changes) (Y/N): ");
                confirmation = new Scanner(System.in).nextLine().trim().toUpperCase();
                if ("Y".equals(confirmation)) {
                    ctrlDomain.changeSolutionProducts(position1, position2, solutionName);
                    return "Solution '" + solutionName + "' was changed.";
                } else if ("N".equals(confirmation)) {
                    return "Solution '" + solutionName + "' was not changed.";
                }
            } while (true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Adds a product to an existing solution at the index of the shelf", key = {"add-product-solution", "add-prod-sol"},
            group = "Solution Management")
    public String addProductSolution(
            @ShellOption(help = "Solution name") String solutionName,
            @ShellOption(help = "Product name") String productName,
            @ShellOption(help = "Shelf index") int shelfIndex
    ) {
        try {
            double newMark = ctrlDomain.checkAddSolutionProduct(solutionName, productName, shelfIndex);
            System.out.println("New mark after the modification: " + newMark);

            ctrlDomain.addSolutionProduct(solutionName, productName, shelfIndex);
            return "Solution '" + solutionName + "' was changed.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Removes a product of an existing solution from the index of the shelf", key = {"remove-product-solution", "rm-prod-sol"},
            group = "Solution Management")
    public String removeProductSolution(
            @ShellOption(help = "Solution name") String solutionName,
            @ShellOption(help = "Shelf index") int shelfIndex
    ) {
        try {
            double newMark = ctrlDomain.checkDeleteSolutionProduct(solutionName, shelfIndex);
            System.out.println("New mark after the modification: " + newMark);

            String confirmation;

            do {
                System.out.print("Do you want to keep the changes) (Y/N): ");
                confirmation = new Scanner(System.in).nextLine().trim().toUpperCase();
                if ("Y".equals(confirmation)) {
                    ctrlDomain.deleteSolutionProduct(solutionName, shelfIndex);
                    return "Solution '" + solutionName + "' was changed.";
                } else if ("N".equals(confirmation)) {
                    return "Solution '" + solutionName + "' was not changed.";
                }
            } while (true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Removes an existing solution from the supermarket", key = {"remove-solution", "rm-sol"},
            group = "Solution Management")
    public String removeSolution(
            @ShellOption(help = "Solution name") String solutionName
    ) {
        try {
            ctrlDomain.deleteSolution(solutionName);
            return "Solution '" + solutionName + "' was deleted.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @ShellMethod(value = "Lists all the solutions from the supermarket", key = {"list-solutions", "ls-sols"},
            group = "Solution Management")
    public String listSolutions() {
        return String.format("List of solutions:\n [%s]",ctrlDomain.listSolutions());
    }

    @ShellMethod(value = "Check the properties of an existing solution of the supermarket", key = {"check-solution", "check-sol"},
            group = "Solution Management")
    public String checkSolution(
            @ShellOption(help = "Solution name") String solutionName
    ) {
        try {
            return ctrlDomain.checkSolution(solutionName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}
