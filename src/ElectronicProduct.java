/** Elektroonikatoode – lisaks tavalisele tootele on tal garantiiaeg kuudes. */
public class ElectronicProduct extends Product {

    private final int warrantyMonths;

    public ElectronicProduct(String name, double price, int quantity, int warrantyMonths) {
        super(name, price, quantity);
        if (warrantyMonths < 0) {
            throw new IllegalArgumentException("Garantiiaeg ei tohi olla negatiivne.");
        }
        this.warrantyMonths = warrantyMonths;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    @Override
    public String getCategory() {
        return "Elektroonika";
    }

    @Override
    public String getExtraInfo() {
        return "Garantii: " + warrantyMonths + " kuud";
    }
}
