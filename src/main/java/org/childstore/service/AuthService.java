package org.childstore.service;

import org.childstore.model.*;

import java.util.Scanner;

public class AuthService {
    private final Scanner scanner = new Scanner(System.in);

    public User login() {
        System.out.println("\nАвторизация");

        System.out.print("Логин: ");
        String username = scanner.nextLine();

        System.out.print("Пароль: ");
        String password = scanner.nextLine();

        if (username.equalsIgnoreCase("worker") && password.equals("123")) {
            return new Worker(username);
        } else if (username.equalsIgnoreCase("director") && password.equals("123")) {
            return new Director(username);
        } else if (username.equalsIgnoreCase("delivery") && password.equals("123")) {
            return new Deliveryman(username);
        } else {
            System.out.println("Неверные данные. Доступ запрещён.");
            return null;
        }
    }
}
