package com.prop.prop12_1.shell;

import com.prop.prop12_1.controller.CtrlDomain;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthenticationAspect {

    private final CtrlDomain ctrlDomain;

    public AuthenticationAspect(CtrlDomain ctrlDomain) {
        this.ctrlDomain = ctrlDomain;
    }

    @Before("@annotation(org.springframework.shell.standard.ShellMethod) && !execution(* org.springframework.shell.standard.commands.Help.*(..))"
            + "&& !execution(* com.prop.prop12_1.shell.MenuCommands.login(..)) && !execution(* com.prop.prop12_1.shell.MenuCommands.addUser(..))"
            + "&& !execution(* org.springframework.shell.standard.commands.Script.*(..)) && !execution(* com.prop.prop12_1.shell.MenuCommands.stop())")
    public void checkAuthentication() {
        if (ctrlDomain.getLoggedUser() == null) {
            throw new IllegalStateException("You must log in to execute this command.");
        }
    }

    @Before("within(com.prop.prop12_1.shell.UserCommands) && !execution(* com.prop.prop12_1.shell.UserCommands.selectSupermarket(..))"
            + "&& !execution(* com.prop.prop12_1.shell.UserCommands.getSimilarityTable())")
    public void checkSupermarketSelected() {
        if (ctrlDomain.getSelectedSupermarket().isEmpty()) {
            throw new IllegalStateException("You must select a supermarket.");
        }
    }

    @Before("within(com.prop.prop12_1.shell.AdminCommands) && !execution(* com.prop.prop12_1.shell.AdminCommands.listProducts())"
            + "&& !execution(* com.prop.prop12_1.shell.AdminCommands.listProductCharacteristics())")
    public void checkUserIsAdmin() {
        if (!ctrlDomain.getLoggedUser().isAdmin()) {
            throw new IllegalStateException("You do not have admin privileges.");
        }
    }
}
