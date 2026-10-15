import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/** Konsoolimenüü – loeb kasutaja sisendit ja kutsub välja Inventory meetodeid. */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Inventory inventory = new Inventory();

    public static void main(String[] args) {
        addSampleProducts();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> showAllProducts();
                    case "2" -> addProduct();
                    case "4" -> searchProducts();
                    case "0" -> running = false;
                    default -> System.out.println("Tundmatu valik, proovi uuesti.");
                }
            } catch (IllegalArgumentException | DateTimeParseException e) {
                System.out.println("Viga: " + e.getMessage());
            }
        }
        System.out.println("Head aega!");
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== LAOHALDUS =====");
        System.out.println("1 - Näita kõiki tooteid");
        System.out.println("2 - Lisa uus toode");
        System.out.println("4 - Otsi toodet nime järgi");
        System.out.println("0 - Välju");
        System.out.print("Vali: ");
    }

    private static void addSampleProducts() {
        inventory.addProduct(new FoodProduct("Piim", 1.29, 40, LocalDate.now().plusDays(7)));
        inventory.addProduct(new FoodProduct("Leib", 2.15, 25, LocalDate.now().plusDays(4)));
        inventory.addProduct(new ElectronicProduct("Hiir", 24.90, 8, 24));
        inventory.addProduct(new ElectronicProduct("Laadija", 19.99, 0, 12));
    }

    private static void showAllProducts() {
        System.out.println();
        printProducts(inventory.getAllProducts());
        System.out.println("Kokku tooteid: " + inventory.getProductCount());
    }

    private static void addProduct() {
        System.out.print("Tüüp (1 - toit, 2 - elektroonika): ");
        String type = scanner.nextLine().trim();
        System.out.print("Nimi: ");
        String name = scanner.nextLine();
        System.out.print("Hind: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Kogus: ");
        int quantity = Integer.parseInt(scanner.nextLine().trim());

        Product product;
        if (type.equals("1")) {
            System.out.print("Parim enne (AAAA-KK-PP): ");
            product = new FoodProduct(name, price, quantity, LocalDate.parse(scanner.nextLine().trim()));
        } else {
            System.out.print("Garantii (kuudes): ");
            product = new ElectronicProduct(name, price, quantity, Integer.parseInt(scanner.nextLine().trim()));
        }
        inventory.addProduct(product);
        System.out.println("Lisatud: " + product.getName());
    }

    private static void searchProducts() {
        System.out.print("Otsingusõna: ");
        List<Product> found = inventory.searchByName(scanner.nextLine());
        if (found.isEmpty()) {
            System.out.println("Ühtegi toodet ei leitud.");
        } else {
            printProducts(found);
        }
    }

    private static void printProducts(List<Product> products) {
        System.out.printf("%-14s %-12s %10s  %7s   %s%n", "Nimi", "Kategooria", "Hind", "Kogus", "Lisainfo");
        for (Product product : products) {
            // product.toString() kutsub välja getExtraInfo(), mis töötab iga tootetüübi puhul erinevalt
            System.out.println(product);
        }
    }
}
