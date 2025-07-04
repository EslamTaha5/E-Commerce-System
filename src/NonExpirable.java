public class NonExpirable extends Product {
    public NonExpirable(String name, int price, int quantity) {
        super(name, price, quantity);
    }

    public NonExpirable(String name, int price, int quantity, double weight) {
        super(name, price, quantity, weight);
    }

    // These are non expirable items so always return that they are not expired
    public boolean isExpired() {
        return false;
    }

    public double getWeight() {
        if (isShippable) {
            return weight;
        }
        return 0.0;
    }
}