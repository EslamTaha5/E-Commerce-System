
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

public class Main {


    public List<ShippableItem> getShippableItems(Cart cart) {
        List<ShippableItem> shippableItems = new ArrayList<>();
        for (CartItem item : cart.items.values()) {
            if (item.product.isShippable()) {
                shippableItems.add(new ShippableItem(item.product.name, item.product.weight, item.quantity));
            }
        }
        return shippableItems;
    }

    public void printCheckOutReceipt(Customer customer, Cart cart, double subtotal, int shippingCost) {
        System.out.println("** Checkout Receipt **");
        for (CartItem item : cart.items.values()) {

            System.out.printf("%dx %-15s %.2f%n", item.quantity, item.product.getName(), item.getItemTotalPrice());
        }
        System.out.println("-----------------------------------");
        System.out.printf("%-15s %.2f%n", "Subtotal", subtotal);
        System.out.printf("%-15s %d%n", "Shipping", shippingCost);
        System.out.printf("%-15s %.2f%n", "Amount", subtotal + shippingCost);
        System.out.printf("%-15s %.2f%n", "Customer New Balance", customer.getBalance());


    }

    public void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        // // Rechecking quantities in case stock changed after the item was added to the cart.
        if (!cart.checkProductsQuantity()) {
            System.out.println("One or more products are out of stock. Transaction cancelled.\n");
            return;
        }
        // Check if at least one item is expired
        if (!cart.checkExpiryDate()) {
            System.out.println("One or more products have expired. Transaction cancelled.\n");
            return;
        }
        List<ShippableItem> shippableItems = getShippableItems(cart);

        ShippingService service = new ShippingService(shippableItems);
        double subtotal = cart.getTotalPrice();
        int shippingCost = service.getShippingCost();
        double amount = subtotal + shippingCost;

        if (amount > customer.getBalance()) {
            System.out.println("Customer balance: " + customer.getBalance() + "\nTotal Cost: " + amount + ". \nTransaction cancelled.\n");
            return;
        }
        for (CartItem item : cart.items.values()) {
            item.product.updateQuantity(item.quantity);
        }


        customer.updateBalance(amount);

        service.printNotice();
        printCheckOutReceipt(customer, cart, subtotal, shippingCost);
        cart.clear();
    }

    public void main2() {
        String sep = "\n=============================================\n";
        TEST1();
        System.out.println(sep);
        TEST2();
        System.out.println(sep);
        TEST3();
        System.out.println(sep);
        TEST4();
    }

    public void TEST1() {

        Product cheese = new Expirable("Cheese", 100, 10, 200, LocalDate.parse("2025-12-05"));
        Product Biscut = new Expirable("Biscuit", 100, 10, 700, LocalDate.parse("2025-12-05"));
        Product tv = new NonExpirable("TV", 1000, 10, 1000);
        Product scratchCard = new NonExpirable("Scratch Card", 20, 10);
        Customer cus = new Customer("Eslam", 10000);
        Cart cart = new Cart();
        cart.addProduct(cheese, 2);
        cart.addProduct(Biscut, 1);
        cart.addProduct(Biscut, 2);
        cart.addProduct(scratchCard, 4);
        cart.addProduct(tv, 2);
        checkout(cus, cart);
    }

    // Customer doesn't have enough balance
    public void TEST2() {

        Product cheese = new Expirable("Cheese", 100, 10, 200, LocalDate.parse("2025-12-05"));
        Product Biscut = new Expirable("Biscuit", 100, 10, 700, LocalDate.parse("2025-12-05"));
        Product tv = new NonExpirable("TV", 1000, 10, 1000);
        Product scratchCard = new NonExpirable("Scratch Card", 20, 10);
        Customer cus = new Customer("Eslam", 1000);
        Cart cart = new Cart();
        cart.addProduct(cheese, 2);
        cart.addProduct(Biscut, 1);
        cart.addProduct(Biscut, 2);
        cart.addProduct(scratchCard, 4);
        cart.addProduct(tv, 2);
        checkout(cus, cart);
    }

    // Product "cheese" is expired. It will be added to the cart
    // but the transaction will fail during checkout.

    public void TEST3() {
        Product cheese = new Expirable("Cheese", 100, 10, 200, LocalDate.parse("2024-12-05"));
        Product Biscut = new Expirable("Biscuit", 100, 10, 700, LocalDate.parse("2025-12-05"));
        Product tv = new NonExpirable("TV", 1000, 10, 1000);
        Product scratchCard = new NonExpirable("Scratch Card", 20, 10);
        Customer cus = new Customer("Eslam", 1000);
        Cart cart = new Cart();
        checkout(cus, cart);// Empty cart
        cart.addProduct(cheese, 2);
        cart.addProduct(Biscut, 20); // will not be added to the cart as the available quantity equals 10
        cart.addProduct(Biscut, 2);
        cart.addProduct(scratchCard, 4);
        cart.addProduct(tv, 2);
        checkout(cus, cart); // Transaction must fail because cheese has expired
    }

    public void TEST4() {
        Product laptop = new NonExpirable("DELL", 2000, 10, 1500);
        Customer cus = new Customer("Eslam", 100000);
        Cart cart = new Cart();
        cart.addProduct(laptop, 2);
        cart.addProduct(laptop, 7);
        cart.addProduct(laptop, 2); // will not add these 2 laptops as the available quantity is 10
        checkout(cus, cart);
    }

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        new Main().main2();
    }
}