package org.childstore.ui;

import java.io.*;
import java.util.Scanner;

public class DeliverymanMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final File ordersFile = new File("orders.txt");
    private final File deliveredFile = new File("delivered.txt");

    public void show() {
        while (true) {
            System.out.println("\n Меню Доставщика:");
            System.out.println("1. Посмотреть список заказов");
            System.out.println("2. Отметить доставку");
            System.out.println("3. Посмотреть доставленные товары");
            System.out.println("4. Показать количество заказанных товаров");
            System.out.println("5. Показать количество доставленных товаров");
            System.out.println("6. Показать мой заработок");
            System.out.println("0. Выход");

            System.out.print(" Ваш выбор: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    showOrders();
                    break;
                case "2":
                    markAsDelivered();
                    break;
                case "3":
                    showDelivered();
                    break;
                case "4":
                    countOrdered();
                    break;
                case "5":
                    countDelivered();
                    break;
                case "6":
                    showEarnings();
                    break;
                case "0":
                    System.out.println("Возврат в главное меню...");
                    return;
                default:
                    System.out.println("Неверный ввод. Повторите.");
            }
        }
    }

    private void showOrders() {
        System.out.println("\nСписок заказов:");
        if (!ordersFile.exists()) {
            System.out.println("Нет активных заказов.");
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(ordersFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("🔸 " + line);
                }
            } catch (IOException e) {
                System.out.println("Ошибка при чтении заказов: " + e.getMessage());
            }
        }
        pause();
    }

    private void markAsDelivered() {
        System.out.print("Введите точное название доставленного товара: ");
        String deliveredProduct = scanner.nextLine();

        File tempFile = new File("orders_temp.txt");
        boolean found = false;

        try (
                BufferedReader reader = new BufferedReader(new FileReader(ordersFile));
                PrintWriter writer = new PrintWriter(new FileOutputStream(deliveredFile, true));
                PrintWriter tempWriter = new PrintWriter(tempFile)
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equalsIgnoreCase(deliveredProduct.trim())) {
                    writer.println(line);
                    found = true;
                } else {
                    tempWriter.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при обработке доставки: " + e.getMessage());
            return;
        }

        if (ordersFile.delete() && tempFile.renameTo(ordersFile)) {
            if (found) {
                System.out.println("Заказ \"" + deliveredProduct + "\" отмечен как доставленный.");
            } else {
                System.out.println("Товар не найден в заказах.");
            }
        } else {
            System.out.println("Не удалось обновить файл заказов.");
        }

        pause();
    }

    private void showDelivered() {
        System.out.println("\nДоставленные товары:");
        if (!deliveredFile.exists()) {
            System.out.println("Пока ничего не доставлено.");
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(deliveredFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("✅ " + line);
                }
            } catch (IOException e) {
                System.out.println("Ошибка при чтении: " + e.getMessage());
            }
        }
        pause();
    }

    private void pause() {
        System.out.println("\nНажмите Enter для возврата...");
        scanner.nextLine();
    }

    private void countOrdered() {
        File ordersFile = new File("orders.txt");
        int count = countLinesInFile(ordersFile);
        System.out.println("Количество заказанных товаров: " + count);
        pause();
    }

    private void countDelivered() {
        File deliveredFile = new File("delivered.txt");
        int count = countLinesInFile(deliveredFile);
        System.out.println("Количество доставленных товаров: " + count);
        pause();
    }

    private void showEarnings() {
        int count = countLinesInFile(new File("delivered.txt"));
        int rate = 100; // фиксированная ставка
        int total = count * rate;
        System.out.println("Вы доставили " + count + " товаров.");
        System.out.println("Ваш заработок: " + total + "сом");
        pause();
    }

    private int countLinesInFile(File file) {
        int count = 0;
        if (!file.exists()) return 0;

        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                if (!s.nextLine().isBlank()) {
                    count++;
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return count;
    }

}
