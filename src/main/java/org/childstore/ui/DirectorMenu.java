package org.childstore.ui;

import org.childstore.model.Product;
import org.childstore.service.ProductService;

import java.util.*;
import java.util.stream.Collectors;

public class DirectorMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final ProductService productService = new ProductService();

    public void show() {
        while (true) {
            System.out.println("\nМеню Директора:");
            System.out.println("1. Показать список всех товаров");
            System.out.println("2. Показать количество товаров по категориям");
            System.out.println("3. Показать товар с максимальным количеством");
            System.out.println("4. Показать товар с минимальным количеством");
            System.out.println("5. Показать отчёт по закупкам");
            System.out.println("6. Выход");

            System.out.print("Ваш выбор: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    showAllProducts();
                    break;
                case "2":
                    showCountByCategory();
                    break;
                case "3":
                    showMaxQuantityProduct();
                    break;
                case "4":
                    showMinQuantityProduct();
                    break;
                case "5":
                    showPurchaseReport();
                    break;
                case "6":
                    System.out.println("Выход в главное меню...");
                    return;
                default:
                    System.out.println("Неверный ввод.");
            }
        }
    }

    private void showAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("Товары не найдены.");
        } else {
            System.out.println("Все товары:");
            for (Product p : products) {
                System.out.println("* " + p.getName() + " | Кол-во: " + p.getQuantity() + " | Категория: " + p.getCategory());
            }
        }
        pause();
    }

    private void showCountByCategory() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("Нет товаров.");
            pause();
            return;
        }

        Map<String, Integer> countMap = new HashMap<>();
        for (Product p : products) {
            countMap.put(p.getCategory(), countMap.getOrDefault(p.getCategory(), 0) + p.getQuantity());
        }

        System.out.println("\nКоличество товаров по категориям:");
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            System.out.println("* " + entry.getKey() + ": " + entry.getValue() + " шт.");
        }
        pause();
    }

    private void showMaxQuantityProduct() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("Нет товаров.");
            pause();
            return;
        }

        Product max = Collections.max(products, Comparator.comparingInt(Product::getQuantity));
        System.out.println("Товар с максимальным количеством:");
        System.out.println("* " + max.getName() + " | Кол-во: " + max.getQuantity());
        pause();
    }

    private void showMinQuantityProduct() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("Нет товаров.");
            pause();
            return;
        }

        Product min = Collections.min(products, Comparator.comparingInt(Product::getQuantity));
        System.out.println("Товар с минимальным количеством:");
        System.out.println("* " + min.getName() + " | Кол-во: " + min.getQuantity());
        pause();
    }

    private void showPurchaseReport() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("Нет данных.");
            pause();
            return;
        }

        double totalSum = products.stream()
                .mapToDouble(p -> p.getPrice() * p.getQuantity())
                .sum();

        System.out.println("Общая стоимость закупки всех товаров: " + totalSum + "сом");
        pause();
    }

    private void pause() {
        System.out.println("\nНажмите Enter для возврата...");
        scanner.nextLine();
    }
}
