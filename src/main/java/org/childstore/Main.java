package org.childstore;

import org.childstore.model.User;
import org.childstore.service.AuthService;

public class Main {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        User user = authService.login();

        if (user != null) {
            user.showMenu();
        } else {
            System.out.println("Завершение программы.");
        }
    }
}


