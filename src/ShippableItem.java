public class ShippableItem implements Shippable {
    public String name;
    public double weight;
    public int quantity;

    ShippableItem(String name, double weight, int quantity) {
        this.name = name;
        this.weight = weight;
        this.quantity = quantity;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}
