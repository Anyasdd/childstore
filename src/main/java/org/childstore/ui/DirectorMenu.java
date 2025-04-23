package org.childstore.ui;

import org.childstore.model.Product;
import org.childstore.service.ProductService;

import java.util.List;
import java.util.Scanner;

public class DirectorMenu {
    private final ProductService productService = new ProductService();
    private final Scanner scanner = new Scanner(System.in);

    public void show() {
        while (true) {
            System.out.println("\n Меню Директора:");
            System.out.println("1. Показать список товаров");
            System.out.println("2. Добавить товар");
            System.out.println("3. Удалить товар по ID");
            System.out.println("0. Выход");

            System.out.print("Ваш выбор: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showAllProducts();
                    break;
                case "2":
                    addProduct();
                    break;
                case "3":
                    deleteProduct();
                    break;
                case "0":
                    System.out.println("Возврат в главное меню...");
                    return;
                default:
                    System.out.println(" Неверный выбор.");
            }
        }
    }

    private void showAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("Товаров пока нет.");
        } else {
            for (Product p : products) {
                System.out.println(" ID: " + p.getId() + " | " + p.getName() +
                        " | 💰 " + p.getPrice() + "сом | Кол-во: " + p.getQuantity());
            }
        }
        pause();
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

    private void deleteProduct() {
        System.out.print("Введите ID товара для удаления: ");
        int id = Integer.parseInt(scanner.nextLine());
        productService.deleteProductById(id);
    }

    private void pause() {
        System.out.println("\n Нажмите Enter для возврата...");
        scanner.nextLine();
    }
}
