import java.time.LocalDate;

/** Toiduaine – lisaks tavalisele tootele on tal parim-enne kuupäev. */
public class FoodProduct extends Product {

    private final LocalDate bestBefore;

    public FoodProduct(String name, double price, int quantity, LocalDate bestBefore) {
        super(name, price, quantity);
        if (bestBefore == null) {
            throw new IllegalArgumentException("Toiduainel peab olema parim-enne kuupäev.");
        }
        this.bestBefore = bestBefore;
    }

    public LocalDate getBestBefore() {
        return bestBefore;
    }

    public boolean isExpired() {
        return bestBefore.isBefore(LocalDate.now());
    }

    @Override
    public String getCategory() {
        return "Toit";
    }

    @Override
    public String getExtraInfo() {
        String info = "Parim enne: " + bestBefore;
        return isExpired() ? info + " (AEGUNUD)" : info;
    }
}
