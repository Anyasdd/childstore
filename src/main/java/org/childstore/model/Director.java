package org.childstore.model;

import org.childstore.ui.DirectorMenu;

public class Director extends User {
    public Director(String username) {
        super(username);
    }

    @Override
    public void showMenu() {
        new DirectorMenu().show();
    }
}
