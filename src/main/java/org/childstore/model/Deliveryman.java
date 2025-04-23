package org.childstore.model;

public class Deliveryman extends User {
    public Deliveryman(String username) {
        super(username);
    }

    @Override
    public void showMenu() {
        System.out.println("Меню Доставщика (заглушка)");
    }
}
