package com.prop.prop12_1.shell;

import com.prop.prop12_1.controller.CtrlDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class MenuCommands {

    @Autowired
    private CtrlDomain ctrlDomain;

    public MenuCommands(CtrlDomain ctrlDomain) {
        this.ctrlDomain = ctrlDomain;
    }

    @ShellMethod("Add a new user")
    public String addUser(
            @ShellOption(help = "Username") String username,
            @ShellOption(help = "Password") String password,
            @ShellOption(help = "Role") String role
    ) {
        ctrlDomain.addUser(username, password, role);
        return "User added successfully.";
    }

    @ShellMethod("Login as a user")
    public String login(
            @ShellOption(help = "Username") String username,
            @ShellOption(help = "Password") String password) {
        ctrlDomain.login(username, password);
        return "User logged in successfully.";
    }

    @ShellMethod("Logout the current user")
    public String logout() {
        ctrlDomain.logout();
        return "User logged out successfully.";
    }

    @ShellMethod(value = "List all users", key = {"list-users","ls-users"})
    public String listUsers() {
        return ctrlDomain.listUsers();
    }

    @ShellMethod("Exit the program")
    public String stop() {
        System.exit(0);
        return "";
    }

}
