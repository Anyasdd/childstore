package org.childstore.model;

public class Deliveryman extends User {
    public Deliveryman(String username) {
        super(username);
    }

    @Override
    public void showMenu() {
        new org.childstore.ui.DeliverymanMenu().show();
    }
}
