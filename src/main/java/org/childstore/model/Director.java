package org.childstore.model;

public class Director extends User {
    public Director(String username) {
        super(username);
    }

    @Override
    public void showMenu() {
        System.out.println("Меню Директора (заглушка)");
    }
}
