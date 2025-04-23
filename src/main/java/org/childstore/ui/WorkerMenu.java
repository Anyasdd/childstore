package org.childstore.ui;

import org.childstore.model.Product;
import org.childstore.service.ProductService;

import java.util.List;
import java.util.Scanner;

public class WorkerMenu {
    private final ProductService productService = new ProductService();
    private final Scanner scanner = new Scanner(System.in);

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\nМеню Работника:");
            System.out.println("1. Показать список товаров");
            System.out.println("2. Добавить новый товар");
            System.out.println("0. Выход");

            System.out.print("Ваш выбор: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    showProducts();
                    break;
                case "2":
                    addProduct();
                    break;
                case "0":
                    running = false;
                    System.out.println("Выход в главное меню...");
                    break;
                default:
                    System.out.println("Неверный ввод. Повторите.");
            }
        }
    }

    private void showProducts() {
        List<Product> products = productService.getAllProducts();

        if (products.isEmpty()) {
            System.out.println("Нет товаров в базе.");
        } else {
            System.out.println("\nСписок товаров:");
            for (Product p : products) {
                System.out.println("🔹 " + p.getName() + " | 💰 " + p.getPrice() +
                        " | Кол-во: " + p.getQuantity() +
                        " | Категория: " + p.getCategory());
            }
        }

        System.out.println("\nНажмите Enter для возврата в меню...");
        scanner.nextLine(); // Ожидание нажатия Enter
    }

    private void addProduct() {
        System.out.print("Название: ");
        String name = scanner.nextLine();

        System.out.print("Серийный номер: ");
        String serial = scanner.nextLine();

        System.out.print("Цена: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Количество: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        System.out.print("Категория: ");
        String category = scanner.nextLine();

        Product p = new Product(name, serial, price, quantity, category);
        productService.addProduct(p);
    }
}
