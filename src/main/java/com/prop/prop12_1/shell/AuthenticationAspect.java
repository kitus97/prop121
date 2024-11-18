package com.prop.prop12_1.shell;

import com.prop.prop12_1.controller.CtrlDomain;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
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

    @Around("@annotation(org.springframework.shell.standard.ShellMethod) && !execution(* org.springframework.shell.standard.commands.Help.*(..))"
            + "&& !execution(* com.prop.prop12_1.shell.MenuCommands.login(..)) && !execution(* com.prop.prop12_1.shell.MenuCommands.addUser(..))"
            + "&& !execution(* org.springframework.shell.standard.commands.Script.*(..)) && !execution(* com.prop.prop12_1.shell.MenuCommands.stop())")
    public Object checkAuthentication(ProceedingJoinPoint joinPoint) throws Throwable {
        if (ctrlDomain.getLoggedUser() == null) {
            System.out.print("Error: You must log in to execute this command.");
            return null;
        }
        return joinPoint.proceed();
    }

    @Around("within(com.prop.prop12_1.shell.UserCommands) && !execution(* com.prop.prop12_1.shell.UserCommands.selectSupermarket(..))"
            + "&& !execution(* com.prop.prop12_1.shell.UserCommands.getSimilarityTable()) && !execution(* com.prop.prop12_1.shell.UserCommands.checkSimilarity(..))")
    public Object checkSupermarketSelected(ProceedingJoinPoint joinPoint) throws Throwable {
        if (ctrlDomain.getSelectedSupermarket() == null || ctrlDomain.getSelectedSupermarket().isEmpty()) {
            System.out.print("Error: You must select a supermarket.");
            return null;
        }
        return joinPoint.proceed();
    }

    @Around("within(com.prop.prop12_1.shell.AdminCommands) && !execution(* com.prop.prop12_1.shell.AdminCommands.listProducts())"
                        + "&& !execution(* com.prop.prop12_1.shell.AdminCommands.listProductCharacteristics())")
    public Object checkUserIsAdmin(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!ctrlDomain.getLoggedUser().isAdmin()) {
            System.out.print("Error: You do not have admin privileges.");
            return null;
        }
        return joinPoint.proceed();
    }

}
