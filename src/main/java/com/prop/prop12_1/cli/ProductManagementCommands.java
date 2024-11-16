package com.prop.prop12_1.cli;
/*
import com.prop.prop12_1.controller.CtrlDomain;
import com.prop.prop12_1.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@ShellComponent
public class ProductManagementCommands {

    @Autowired
    private final CtrlDomain ctrlDomain;

    public ProductManagementCommands(CtrlDomain ctrlDomain) {
        this.ctrlDomain = ctrlDomain;
    }

    @ShellMethod(value = "Add a new product to the system", key = {"add-product", "add-prod"})
    public String addProduct(
            @ShellOption(help = "Product name") String productName) {
        ctrlDomain.addProduct(productName);
        return "Product was added successfully to the system.";
    }

    @ShellMethod(value = "Add a new characteristic to the system", key = {"add-characteristic", "add-char"})
    public String addCharacteristic(
            @ShellOption(help = "Characteristic name") String characteristicName) {
        ctrlDomain.addCharacteristic(characteristicName);
        return "Characteristic was added successfully to the system.";
    }

    @ShellMethod(value = "Modify a product by adding or removing a characteristic", key = {"modify-product", "mod-prod"})
    public String modifyProduct(
            @ShellOption(help = "Product name") String productName,
            @ShellOption(help = "Characteristic to modify") String characteristicName,
            @ShellOption(value = {"-r","--remove"}, arity = 0, help = "Remove the characteristic", defaultValue = "false") boolean remove) {

        if (!remove) {
            ctrlDomain.addCharacteristicProduct(characteristicName, productName);
            return "Characteristic '" + characteristicName + "' added to product '" + productName + "'.";
        } else {
            ctrlDomain.removeCharacteristicProduct(characteristicName, productName);
            return "Characteristic '" + characteristicName + "' removed from product '" + productName + "'.";
        }
    }

    @ShellMethod(value = "List all products", key = {"list-products", "ls-prod"})
    public String listProducts() {
        return "List of products:\n" + ctrlDomain.listProducts();
    }

    @ShellMethod(value = "List all characteristics", key = {"list-characteristics", "ls-char"})
    public String listCharacteristics() {
        return "List of characteristics:\n" + ctrlDomain.listCharacteristics();
    }

    @ShellMethod(value = "List the characteristics of a product", key = {"list-product-characteristics", "ls-prod-char"})
    public String listProductCharacteristics(
            @ShellOption("prodName") String productName) {
        Product product = ctrlDomain.getProduct(productName);
        return product.toString();
    }

}
*/