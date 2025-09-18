import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class DungeonCrawlerUI {
    public static void printTitle() {
        System.out.println("Welcome to DungeonCrawler!");
    }

    private static int readMenuChoice(Scanner sc, int min, int max) {
        while (true) {
            System.out.print("> ");
            String userInput = sc.nextLine().trim();
            try {
                int choice = Integer.parseInt(userInput);
                if (choice >= min && choice <= max)
                    return choice;
            } catch (NumberFormatException ignored) {
            }
            System.out.printf("Please enter a number between %d and %d.%n", min, max);
        }
    }

    public static Player selectClass(Scanner scanner) {
        System.out.println("\n- Select a class -");
        System.out.println("1. Fighter - All-around balanced class.");
        System.out.println("2. Barbarian - Tanky frontliner, but less handy with loot.");
        System.out.println("3. Rogue - Agile but a less tanky class.");
        System.out.println("4. Custom - Custom class. [UNAVAILABLE]");

        int classChoice = readMenuChoice(scanner, 1, 4);

        return switch (classChoice) {
            case 1 -> new Player(15, 15, 15, 15, 15, 15, 15);
            case 2 -> new Player(20, 25, 13, 12, 18, 5, 12);
            case 3 -> new Player(9, 6, 25, 20, 10, 10, 25);
            case 4 -> customClassBuilder(scanner);
            default -> throw new IllegalStateException("Fell out of class select switch-case.");
        };
    }

    // TODO: Implement custom class builder
    // Set all stats to 0, let user increase or decrease selected stat
    // Total of 105 stat points to distribute
    private static Player customClassBuilder(Scanner scanner) {
        System.out.println("Custom classes not implemented yet!");
        return new Player(15, 15, 15, 15, 15, 15, 15);
    }

    public static Dungeon selectDungeon(Scanner scanner) {
        System.out.println("\n- Select a dungeon -");
        System.out.println("1. Ruins of the Forgotten Castle");
        System.out.println("2. Crypts of the Forgotten Castle [UNAVAILABLE]");
        System.out.println("3. Inferno [UNAVAILABLE]");

        int dungeonChoice = readMenuChoice(scanner, 1, 1);

        return switch (dungeonChoice) {
            case 1 -> new Dungeon(Dungeon.DungeonPreset.RUINS);
            case 2 -> new Dungeon(Dungeon.DungeonPreset.CRYPTS);
            case 3 -> new Dungeon(Dungeon.DungeonPreset.INFERNO);
            default -> throw new IllegalStateException("Fell out of Dungeon select switch-case.");
        };
    }

    // Module 1 always "rest/loot"
    // 1. Go deeper into the dungeon
    // 2. Examine yourself (Show stats)
    // 3. Open your backpack (Show all items and their stats)
    //      3a. Equip an item
    //      3b. Close your backpack
    // 4. Rest
    //      4a. Light a campfire
    //      4b. Pray
    public static void postCombatMenu(Scanner scanner, Player player, Module module) {

        AtomicInteger currResourcefulness = new AtomicInteger(player.getStats().getResourcefulness());

        while (true) {
            System.out.println("\n- Select action -");
            System.out.println("1. Progress to next module");
            System.out.println("2. Examine yourself (Show stats)");
            System.out.println("3. Open your backpack");
            System.out.println("4. Rest");
            System.out.println("5. Check for loot");

            int menuChoice = readMenuChoice(scanner, 1, 5);

            switch (menuChoice) {
                case 1 -> {
                    return;
                }
                case 2 -> {
                    System.out.println("You examine yourself...");
                    System.out.println("- Your gear/stats -");
                    System.out.println(player.toString());
                }
                case 3 -> backpackMenu(scanner, player);
                case 4 -> playerRestChoice(scanner, player);
                case 5 -> lootMenu(scanner, player, module, currResourcefulness);
                default -> throw new IllegalStateException("Fell out of playerMenu switch-case.");
            }
            ;
        }
    }

    public static void backpackMenu(Scanner scanner, Player player) {
        System.out.println("You open your backpack...");
        while (true) {
            System.out.println("\n- Your backpack -");
            System.out.println(player.getInventoryString());
            if (player.getInventory().isEmpty())
                break;

            System.out.println("1. Equip an item");
            System.out.println("2. Close backpack");

            int menuChoice = readMenuChoice(scanner, 1, 2);

            switch (menuChoice) {
                case 1 -> equipMenu(scanner, player);
                case 2 -> {
                    System.out.println("You close your backpack...");
                    return;
                }
                default -> throw new IllegalStateException("Fell out of backpackMenu switch-case.");
            }
            ;
        }
    }

    public static void equipMenu(Scanner scanner, Player player) {
        System.out.println("\n- Equip an item -");
        System.out.println("Select item to equip (0 = cancel)");
        for (int i = 0; i < player.getInventory().size(); i++) {
            System.out.printf("%d. %s%n", i + 1, player.getInventory().get(i).toString());
        }

        int itemChoice = readMenuChoice(scanner, 0, player.getInventory().size());

        if (itemChoice != 0 && itemChoice < player.getInventory().size() + 1) {
            Item selectedItem = player.getInventory().get(itemChoice - 1);

            if (selectedItem.getClass() == Armor.class) {
                player.equipArmor((Armor) selectedItem);
                System.out.printf("You equipped %s%n", selectedItem.toString());
            } else if (selectedItem.getClass() == Weapon.class) {
                player.equipWeapon((Weapon) selectedItem);
                System.out.printf("You equipped %s%n", selectedItem.toString());
            }
        }
    }

    // TODO: Implement abilities/perks, campfire/pray resting
    public static void playerRestChoice(Scanner scanner, Player player) {
        System.out.println("[NOT IMPLEMENTED]");
    }

    private static void lootMenu(Scanner scanner, Player player, Module module, AtomicInteger currentResourcefulness) {

        System.out.printf("You scan your surroundings and see %d unopened chests...%n", module.getUnopenedChests().size());

        if (module.getUnopenedChests().isEmpty()) {
            return;
        }

        while (true) {
            ArrayList<Chest> unopenedChests = module.getUnopenedChests();
            int unopenedChestsCount = unopenedChests.size();
            System.out.println("\n- Open a chest -");
            System.out.printf("Current resourcefulness: %d - (0 = cancel)%n", currentResourcefulness.get());
            for (int i = 0; i < unopenedChestsCount; i++) {
                System.out.printf("%d. %s%n", i + 1, unopenedChests.get(i).toString());
            }

            int chestChoice = readMenuChoice(scanner, 0, unopenedChests.size());

            if (chestChoice == 0) {
                return;
            }

            if (currentResourcefulness.get() < unopenedChests.get(chestChoice - 1).getCost()) {
                System.out.println("You don't have enough resourcefulness left to open that!");
            }
            else if (chestChoice < unopenedChests.size() + 1) {
                Item foundItem = unopenedChests.get(chestChoice - 1).openChest(currentResourcefulness.get());
                currentResourcefulness.set(currentResourcefulness.get() - unopenedChests.get(chestChoice - 1).getCost());

                if (foundItem.getClass() == Armor.class) {
                    Armor foundItemCopy = new Armor((Armor) foundItem);
                    player.getInventory().add(foundItemCopy);
                    System.out.printf("You found %s%n", foundItem.toString());
                } else if (foundItem.getClass() == Weapon.class) {
                    Weapon foundItemCopy = new Weapon((Weapon) foundItem);
                    player.getInventory().add(foundItemCopy);
                    System.out.printf("You found %s%n", foundItem.toString());
                }
            }

            if (module.getUnopenedChests().isEmpty()) {
                System.out.println("There are no more unopened chests...");
                return;
            }
        }
    }
}
