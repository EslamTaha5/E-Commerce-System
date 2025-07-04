public abstract class Product {
    String name;
    int price;
    int quantity;
    double weight;
    boolean isShippable;

    public Product(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.weight = 0;
        this.isShippable = false;
    }

    public Product(String name, int price, int quantity, double weight) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.weight = weight;

        if (this.weight > 0) {
            isShippable = true;
        } else {
            isShippable = false;
        }
    }
    public boolean isShippable() {
        return isShippable;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {

        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity -= quantity;
    }

    public abstract boolean isExpired();

    public abstract double getWeight();
}
