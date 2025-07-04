import java.util.HashMap;

// If the same product is added multiple times, we avoid duplication
// by using a HashMap and simply updating the quantity.
public class Cart {
    HashMap<String, CartItem> items;

    Cart() {
        items = new HashMap<>();
    }

    public void addProduct(Product product, int quantity) {
        // Negative quantity is not allowed
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return;
        }
        // If that item already exists in the cart we will just update the quantity
        int newQuantity = quantity;
        if (items.containsKey(product.getName())) {
            newQuantity += items.get(product.getName()).getQuantity();
        }

        // if the total quantity is greater than the available quantity we will just inform the customer
        if (!checkQuantity(product, newQuantity)) {
            System.out.println("Required Quantity of " + product.name + " " + newQuantity + " must not be greater than available product quantity " + product.quantity + ".\n");

            return;
        }
        items.put(product.getName(), new CartItem(product, newQuantity));
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (CartItem item : items.values()) {
            totalPrice += item.getItemTotalPrice();
        }
        return totalPrice;
    }

    public boolean checkQuantity(Product product, int quantity) {
        return (quantity <= product.getQuantity());
    }

    public boolean checkProductsQuantity() {
        for (CartItem item : items.values()) {
            if (!checkQuantity(item.getProduct(), item.getQuantity())) {
                return false;
            }
        }
        return true;
    }

    public boolean checkExpiryDate() {
        for (CartItem item : items.values()) {
            if (item.product.isExpired()) {
                return false;
            }
        }
        return true;
    }

    public void clear() {
        items.clear();
    }
}
