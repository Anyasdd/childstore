package org.childstore.model;

public class Product {
    private int id;
    private String name;
    private String serialNumber;
    private double price;
    private int quantity;
    private String category;

    public Product() {
    }

    public Product(String name, String serialNumber, double price, int quantity, String category) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public Product(int id, String name, String serialNumber, double price, int quantity, String category) {
        this.id = id;
        this.name = name;
        this.serialNumber = serialNumber;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
