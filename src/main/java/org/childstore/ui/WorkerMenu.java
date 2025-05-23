package org.childstore.ui;

import org.childstore.model.Product;
import org.childstore.service.ProductService;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.stream.Collectors;
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
            System.out.println("3. Оформить заказ");
            System.out.println("4. Поиск товара по названию");
            System.out.println("5. Поиск по категории");
            System.out.println("6. Поиск по серийному номеру");
            System.out.println("7. Показать список заказанных товаров");
            System.out.println("8. Показать отсутствующие товары");
            System.out.println("9. Показать товары со скидкой");
            System.out.println("10. Удалить заказ по названию");
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
                case "3":
                    placeOrder();
                    break;
                case "4":
                    searchByName();
                    break;
                case "5":
                    searchByCategory();
                    break;
                case "6":
                    searchBySerial();
                    break;
                case "7":
                    showOrderedProducts();
                    break;
                case "8":
                    showOutOfStock();
                    break;
                case "9":
                    showDiscountedProducts();
                    break;
                case "10":
                    deleteOrder();
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
                System.out.println("* " + p.getName() + " | " + p.getPrice() +
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

    private void placeOrder() {
        System.out.print("Введите название товара для заказа: ");
        String name = scanner.nextLine();
        int quantity = 1;

        productService.sellUpdate(name, quantity);

        try (PrintWriter writer = new PrintWriter(new FileOutputStream("orders.txt", true))) {
            writer.println(name);
            System.out.println("Товар \"" + name + "\" добавлен в список заказов.");
        } catch (IOException e) {
            System.out.println("Ошибка при записи заказа: " + e.getMessage());
        }

        System.out.println("\nНажмите Enter для возврата...");
        scanner.nextLine();
    }

    private void searchByName() {
        System.out.print("Введите название товара: ");
        String name = scanner.nextLine();

        List<Product> results = productService.searchByName(name);
        if (results.isEmpty()) {
            System.out.println("Ничего не найдено");
        } else {
            System.out.println("Найдено: ");
            for (Product p : results) {
                System.out.println("* " + p.getName() + " | " + p.getCategory());
            }
        }
        pause();
    }

    private void searchByCategory() {
        System.out.print("Введите категорию: ");
        String category = scanner.nextLine();

        List<Product> results = productService.searchByCategory(category);
        if (results.isEmpty()) {
            System.out.println("Ничего не найдено");
        } else {
            System.out.println("Найдено: ");
            for (Product p : results) {
                System.out.println("* " + p.getName() + " | " + p.getCategory());
            }
        }
        pause();
    }

    private void pause() {
        System.out.println("\nНажмите Enter для возврата...");
        scanner.nextLine();
    }
    private void searchBySerial() {
        System.out.print("Введите серийный номер: ");
        String serial = scanner.nextLine();

        Product p = productService.searchBySerial(serial);
        if (p != null) {
            System.out.println("Найден товар:");
            System.out.println("* " + p.getName() + " |  " + p.getPrice() +
                    " | Кол-во: " + p.getQuantity() +
                    " | Категория: " + p.getCategory());
        } else {
            System.out.println("Товар с таким серийным номером не найден.");
        }

        pause();
    }

    private void showOrderedProducts() {
        File ordersFile = new File("orders.txt");

        System.out.println("\nСписок заказанных товаров:");
        if (!ordersFile.exists()) {
            System.out.println("Заказов пока нет.");
        } else {
            try (Scanner fileScanner = new Scanner(ordersFile)) {
                boolean empty = true;
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    if (!line.isBlank()) {
                        System.out.println("🔸 " + line);
                        empty = false;
                    }
                }
                if (empty) {
                    System.out.println("Заказов пока нет.");
                }
            } catch (IOException e) {
                System.out.println("Ошибка при чтении заказов: " + e.getMessage());
            }
        }

        pause();
    }

    private void showOutOfStock() {
        List<Product> products = productService.getAllProducts();
        List<Product> outOfStock = products.stream()
                .filter(p -> p.getQuantity() == 0)
                .collect(Collectors.toList());

        if (outOfStock.isEmpty()) {
            System.out.println("Все товары в наличии.");
        } else {
            System.out.println("\nОтсутствующие товары:");
            for (Product p : outOfStock) {
                System.out.println("❌ " + p.getName() + " | Серийный номер: " + p.getSerialNumber());
            }
        }

        pause();
    }

    private void showDiscountedProducts() {
        List<Product> products = productService.getAllProducts();

        List<Product> discounted = products.stream()
                .filter(p -> p.getCategory() != null &&
                        p.getCategory().toLowerCase().contains("sale"))
                .collect(Collectors.toList());

        if (discounted.isEmpty()) {
            System.out.println("Нет товаров со скидкой.");
        } else {
            System.out.println("\nТовары со скидкой:");
            for (Product p : discounted) {
                System.out.println("🔻 " + p.getName() + " |  " + p.getPrice() +
                        " | Категория: " + p.getCategory());
            }
        }

        pause();
    }

    private void deleteOrder() {
        File ordersFile = new File("orders.txt");
        File tempFile = new File("orders_temp.txt");

        if (!ordersFile.exists()) {
            System.out.println("Список заказов пуст.");
            pause();
            return;
        }

        System.out.print("Введите точное название товара для удаления: ");
        String target = scanner.nextLine();
        boolean found = false;

        try (
                Scanner fileScanner = new Scanner(ordersFile);
                PrintWriter writer = new PrintWriter(tempFile)
        ) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.trim().equalsIgnoreCase(target.trim())) {
                    found = true;
                    continue;
                }
                writer.println(line);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при удалении заказа: " + e.getMessage());
            return;
        }

        if (!ordersFile.delete() || !tempFile.renameTo(ordersFile)) {
            System.out.println("Не удалось обновить файл заказов.");
        } else if (found) {
            System.out.println("Заказ \"" + target + "\" удалён.");
        } else {
            System.out.println("Заказ не найден.");
        }

        pause();
    }
}
