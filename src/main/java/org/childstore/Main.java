package org.childstore;

import org.childstore.db.DatabaseManager;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = DatabaseManager.connect();
        if (conn != null) {
            System.out.println("✅ Подключение к базе данных успешно!");
        } else {
            System.out.println("❌ Не удалось подключиться к базе.");
        }
    }
}
