import java.util.List;

class ShippingService {
    List<ShippableItem> shippableItems;

    ShippingService(List<ShippableItem> shippableItems) {
        this.shippableItems = shippableItems;
    }

    public void printNotice() {
        if (shippableItems.isEmpty()) {
            return;
        }
        System.out.println("** Shipment notice **");
        double totalWeight = 0.0;
        String unit;

        for (ShippableItem item : shippableItems) {
            unit = "g";
            double totalItemWeight = item.quantity * item.getWeight();
            totalWeight += totalItemWeight;

            if (totalItemWeight >= 1000) {
                totalItemWeight /= 1000;
                unit = "kg";
            }
            System.out.printf("%dx %-15s %.2f%s%n", item.quantity, item.getName(), totalItemWeight, unit);
        }
        unit = "g";
        if (totalWeight >= 1000) {
            totalWeight = totalWeight / 1000;
            unit = "kg";
        }
        System.out.printf("Total package weight   %.2f%s%n", totalWeight, unit);
        System.out.println();
    }

    public int getShippingCost() {
        if (shippableItems.isEmpty()) {
            return 0;
        }
        double totalWeight = 0.0;

        for (ShippableItem item : shippableItems) {
            double totalItemWeight = item.quantity * item.getWeight();
            totalWeight += totalItemWeight;
        }
        // 30 for each 1kg
        return (int) Math.round(30 * (totalWeight / 1000));
    }
}