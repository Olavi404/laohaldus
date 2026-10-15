import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Ladu – haldab toodete kogumit.
 *
 * Tooted on LinkedHashMap-is, kus võtmeks on toote nimi väiketähtedega:
 *  - nime järgi leidmine on kiire (ei pea kogu nimekirja läbi käima);
 *  - sama nimega toodet ei saa kaks korda lisada (võti on unikaalne);
 *  - LinkedHashMap jätab meelde lisamise järjekorra, seega nimekiri kuvatakse alati samas järjekorras.
 */
public class Inventory {

    private final Map<String, Product> products = new LinkedHashMap<>();

    public void addProduct(Product product) {
        String key = toKey(product.getName());
        if (products.containsKey(key)) {
            throw new IllegalArgumentException("Toode nimega \"" + product.getName() + "\" on juba laos.");
        }
        products.put(key, product);
    }

    /** Leiab toote täpse nime järgi (suur- ja väiketähed ei loe). Tagastab null, kui toodet pole. */
    public Product findByName(String name) {
        return products.get(toKey(name));
    }

    /** Leiab kõik tooted, mille nimes esineb otsingusõna. */
    public List<Product> searchByName(String searchText) {
        String text = toKey(searchText);
        List<Product> result = new ArrayList<>();
        for (Product product : products.values()) {
            if (toKey(product.getName()).contains(text)) {
                result.add(product);
            }
        }
        return result;
    }

    public void increaseQuantity(String name, int amount) {
        getExisting(name).increaseQuantity(amount);
    }

    public void decreaseQuantity(String name, int amount) {
        getExisting(name).decreaseQuantity(amount);
    }

    /** Kõigi laos olevate toodete koguväärtus. */
    public double getTotalValue() {
        double total = 0;
        for (Product product : products.values()) {
            total += product.getTotalValue();
        }
        return total;
    }

    /** Tagastab koopia, et väljaspool klassi ei saaks laoseisu otse muuta. */
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public int getProductCount() {
        return products.size();
    }

    private Product getExisting(String name) {
        Product product = findByName(name);
        if (product == null) {
            throw new IllegalArgumentException("Toodet \"" + name + "\" ei leitud.");
        }
        return product;
    }

    private static String toKey(String name) {
        return name.trim().toLowerCase();
    }
}
