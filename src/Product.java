/**
 * Abstraktne toode. Sisaldab kõigile toodetele ühiseid andmeid ja käitumist.
 * Otse Product-objekti luua ei saa – tuleb kasutada mõnda alamklassi
 * (FoodProduct, ElectronicProduct).
 */
public abstract class Product {

    // Väljad on private: neid saab muuta ainult selle klassi meetodite kaudu,
    // mis kontrollivad, et väärtused jääksid korrektseks (kapseldamine).
    private final String name;
    private double price;
    private int quantity;

    protected Product(String name, double price, int quantity) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Toote nimi ei tohi olla tühi.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Kogus ei tohi olla negatiivne.");
        }
        this.name = name.trim();
        setPrice(price);
        this.quantity = quantity;
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

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Hind ei tohi olla negatiivne.");
        }
        this.price = price;
    }

    public void increaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Lisatav kogus peab olema positiivne.");
        }
        quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Eemaldatav kogus peab olema positiivne.");
        }
        if (amount > quantity) {
            throw new IllegalArgumentException(
                    "Laos on ainult " + quantity + " tk, ei saa eemaldada " + amount + " tk.");
        }
        quantity -= amount;
    }

    /** Selle toote laoseisu väärtus: hind korda kogus. */
    public double getTotalValue() {
        return price * quantity;
    }

    public boolean isOutOfStock() {
        return quantity == 0;
    }

    /** Tootetüübi nimi, nt "Toit" või "Elektroonika". */
    public abstract String getCategory();

    /** Alamklassi oma lisainfo – iga tootetüüp kuvab seda erinevalt (polümorfism). */
    public abstract String getExtraInfo();

    @Override
    public String toString() {
        return String.format("%-14s %-12s %8.2f €  %4d tk   %s",
                name, getCategory(), price, quantity, getExtraInfo());
    }
}
