package org.childstore.model;

import org.childstore.ui.WorkerMenu;

public class Worker extends User {
    public Worker(String username) {
        super(username);
    }

    @Override
    public void showMenu() {
        new WorkerMenu().show();
    }
}
