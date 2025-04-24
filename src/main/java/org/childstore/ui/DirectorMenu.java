package org.childstore.ui;

import org.childstore.model.Product;
import org.childstore.service.ProductService;
import java.util.Map;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
            System.out.println("4. Сортировать товары по цене");
            System.out.println("5. Показать отчёт по категориям");
            System.out.println("6. Экспорт товаров в CSV");
            System.out.println("7. Редактировать товар по ID");
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
                case "4":
                    sortByPrice();
                    break;
                case "5":
                    showCategoryReport();
                    break;
                case "6":
                    exportToCSV();
                    break;
                case "7":
                    editProduct();
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

    private void sortByPrice() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("Товары не найдены.");
        } else {
            products.sort((a, b) -> Double.compare(a.getPrice(), b.getPrice()));
            System.out.println("\nСортировка по цене:");
            for (Product p : products) {
                System.out.println("💰 " + p.getPrice() + " — " + p.getName());
            }
        }
        pause();
    }

    private void showCategoryReport() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("Нет данных.");
            return;
        }

        Map<String, Integer> categoryCount = new HashMap<>();
        for (Product p : products) {
            categoryCount.put(
                    p.getCategory(),
                    categoryCount.getOrDefault(p.getCategory(), 0) + 1
            );
        }

        System.out.println("\nОтчёт по категориям:");
        for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            System.out.println("🏷️ " + entry.getKey() + " → " + entry.getValue() + " товар(ов)");
        }

        pause();
    }

    private void exportToCSV() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("Нет товаров для экспорта.");
            pause();
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("products.csv"))) {
            writer.println("ID,Name,SerialNumber,Price,Quantity,Category");

            for (Product p : products) {
                writer.printf("%d,%s,%s,%.2f,%d,%s%n",
                        p.getId(),
                        p.getName(),
                        p.getSerialNumber(),
                        p.getPrice(),
                        p.getQuantity(),
                        p.getCategory()
                );
            }

            System.out.println("Файл products.csv успешно создан.");
        } catch (IOException e) {
            System.out.println("Ошибка при создании CSV: " + e.getMessage());
        }

        pause();
    }

    private void editProduct() {
        System.out.print("Введите ID товара для редактирования: ");
        int id = Integer.parseInt(scanner.nextLine());

        List<Product> all = productService.getAllProducts();
        Product target = all.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if (target == null) {
            System.out.println("Товар с таким ID не найден.");
            pause();
            return;
        }

        System.out.println("Оставьте поле пустым, если не хотите менять.");

        System.out.print("Новое название [" + target.getName() + "]: ");
        String name = scanner.nextLine();
        if (!name.isBlank()) target.setName(name);

        System.out.print("Новый серийный номер [" + target.getSerialNumber() + "]: ");
        String serial = scanner.nextLine();
        if (!serial.isBlank()) target.setSerialNumber(serial);

        System.out.print("Новая цена [" + target.getPrice() + "]: ");
        String priceStr = scanner.nextLine();
        if (!priceStr.isBlank()) target.setPrice(Double.parseDouble(priceStr));

        System.out.print("Новое количество [" + target.getQuantity() + "]: ");
        String qtyStr = scanner.nextLine();
        if (!qtyStr.isBlank()) target.setQuantity(Integer.parseInt(qtyStr));

        System.out.print("Новая категория [" + target.getCategory() + "]: ");
        String category = scanner.nextLine();
        if (!category.isBlank()) target.setCategory(category);

        productService.updateProduct(target);
        System.out.println("Товар обновлён.");
        pause();
    }


}
