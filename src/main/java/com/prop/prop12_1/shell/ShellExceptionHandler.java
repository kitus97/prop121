package com.prop.prop12_1.shell;

import org.springframework.shell.command.CommandExecution;
import org.springframework.stereotype.Component;

@Component
public class ShellExceptionHandler {

    @org.springframework.shell.command.annotation.ExceptionResolver
    public String handleCommandExecutionException(CommandExecution.CommandExecutionException e) {
        return e.getMessage();
    }

    @org.springframework.shell.command.annotation.ExceptionResolver
    public String handleGenericException(Exception e) {
        return e.getMessage();
    }
}
