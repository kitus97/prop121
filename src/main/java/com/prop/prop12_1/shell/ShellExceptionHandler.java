package com.prop.prop12_1.shell;

import org.springframework.stereotype.Component;

@Component
public class ShellExceptionHandler {

    @org.springframework.shell.command.annotation.ExceptionResolver
    public String handleException(IllegalStateException e) {
        return e.getMessage();
    }
}
