import java.time.LocalDate;

// Represents products that have an expiry date.
// If not shippable, weight is assumed to be zero.

public class Expirable extends Product {
    LocalDate expiryDate;

    public Expirable(String name, int price, int quantity, LocalDate expiryDate) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
    }

    public Expirable(String name, int price, int quantity, double weight, LocalDate expiryDate) {
        super(name, price, quantity, weight);
        this.expiryDate = expiryDate;
    }

    public boolean isExpired() {
        return expiryDate.isBefore(LocalDate.now());
    }

    public double getWeight() {
        if (isShippable) {
            return weight;
        }
        return 0.0;
    }
}
