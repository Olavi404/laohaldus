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
                    case "3" -> changeQuantity();
                    case "4" -> searchProducts();
                    case "5" -> showTotalValue();
                    case "0" -> running = false;
                    default -> System.out.println("Tundmatu valik, proovi uuesti.");
                }
            } catch (IllegalArgumentException e) {
                // Product ja Inventory viskavad selle erandi, kui andmed pole korrektsed
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
        System.out.println("3 - Muuda toote kogust");
        System.out.println("4 - Otsi toodet nime järgi");
        System.out.println("5 - Näita lao koguväärtust");
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
        int type = readInt("Tüüp (1 - toit, 2 - elektroonika): ");
        if (type != 1 && type != 2) {
            System.out.println("Tundmatu tüüp.");
            return;
        }
        String name = readNonEmpty("Nimi: ");
        if (inventory.findByName(name) != null) {
            System.out.println("Selle nimega toode on juba laos.");
            return;
        }
        double price = readDouble("Hind: ");
        int quantity = readInt("Kogus: ");

        Product product;
        if (type == 1) {
            LocalDate bestBefore = readDate("Parim enne (AAAA-KK-PP): ");
            product = new FoodProduct(name, price, quantity, bestBefore);
        } else {
            int warranty = readInt("Garantii (kuudes): ");
            product = new ElectronicProduct(name, price, quantity, warranty);
        }
        inventory.addProduct(product);
        System.out.println("Lisatud: " + product.getName());
    }

    private static void changeQuantity() {
        String name = readNonEmpty("Toote nimi: ");
        Product product = inventory.findByName(name);
        if (product == null) {
            System.out.println("Toodet \"" + name + "\" ei leitud.");
            return;
        }
        int action = readInt("1 - suurenda, 2 - vähenda: ");
        int amount = readInt("Kogus: ");
        if (action == 1) {
            inventory.increaseQuantity(name, amount);
        } else if (action == 2) {
            inventory.decreaseQuantity(name, amount);
        } else {
            System.out.println("Tundmatu valik.");
            return;
        }
        System.out.println(product.getName() + " uus kogus: " + product.getQuantity() + " tk");
    }

    private static void searchProducts() {
        String text = readNonEmpty("Otsingusõna: ");
        List<Product> found = inventory.searchByName(text);
        if (found.isEmpty()) {
            System.out.println("Ühtegi toodet ei leitud.");
        } else {
            printProducts(found);
        }
    }

    private static void showTotalValue() {
        System.out.printf("Lao koguväärtus: %.2f €%n", inventory.getTotalValue());
    }

    private static void printProducts(List<Product> products) {
        System.out.printf("%-14s %-12s %10s  %7s   %s%n", "Nimi", "Kategooria", "Hind", "Kogus", "Lisainfo");
        for (Product product : products) {
            // product.toString() kutsub välja getExtraInfo(), mis töötab iga tootetüübi puhul erinevalt
            System.out.println(product);
        }
    }

    // ---- Sisendi lugemine: küsib uuesti, kuni kasutaja sisestab korrektse väärtuse ----

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Väärtus ei tohi olla tühi.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Palun sisesta täisarv.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                // Lubame ka koma, sest eesti keeles kirjutatakse tihti 1,29
                return Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Palun sisesta arv (nt 1.29).");
            }
        }
    }

    private static LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Vale kuupäev. Kasuta kuju AAAA-KK-PP, nt 2026-12-31.");
            }
        }
    }
}
