import java.util.Scanner;

public class DungeonCrawlerUI {
    public static void printTitle() {
        System.out.println("Welcome to DungeonCrawler!");
    }

    public static Player selectClass(Scanner scanner) {
        int classChoice;
        do {
            System.out.println("\n- Select a class -");
            System.out.println("1. Fighter - All-around balanced class.");
            System.out.println("2. Barbarian - Tanky frontliner, but less handy with loot.");
            System.out.println("3. Rogue - Agile but a less tanky class.");
            System.out.println("4. Custom - Custom class. [UNAVAILABLE]");
            System.out.print("> ");
        } while ((classChoice = scanner.nextInt()) < 0 || classChoice > 3);

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
        return new Player();
    }

    public static Dungeon selectDungeon(Scanner scanner) {
        int dungeonChoice;
        do {
            System.out.println("\n- Select a dungeon -");
            System.out.println("1. Ruins of the Forgotten Castle");
            System.out.println("2. Crypts of the Forgotten Castle [UNAVAILABLE]");
            System.out.println("3. Inferno [UNAVAILABLE]");
            System.out.print("> ");
        } while ((dungeonChoice = scanner.nextInt()) < 0 || dungeonChoice > 1);

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
    public static void playerMenu(Scanner scanner, Player player, Dungeon dungeon) {

        boolean playerResting = true;
        while (playerResting) {
            int menuChoice;
            do {
                System.out.println("\n- Select action -");
                System.out.println("1. Progress to next module");
                System.out.println("2. Examine yourself (Show stats)");
                System.out.println("3. Open your backpack");
                System.out.println("4. Rest");
                System.out.print("> ");
            } while ((menuChoice = scanner.nextInt()) < 0 || menuChoice > 4);

            switch (menuChoice) {
                case 1 -> playerResting = false;
                case 2 -> {
                    System.out.println("- Your gear/stats -");
                    System.out.println(player.toString());
                }
                case 3 -> backpackMenu(scanner, player);
                case 4 -> playerRestChoice(scanner, player);
                default -> throw new IllegalStateException("Fell out of playerMenu switch-case.");
            };
        }
    }

    public static void backpackMenu(Scanner scanner, Player player) {
        boolean playerEquipping = true;
        while (playerEquipping) {
            int menuChoice;
            do {
                System.out.println("\n- Your backpack -");
                System.out.println(player.getInventoryString());
                System.out.println("1. Equip an item");
                System.out.println("2. Close backpack");
                System.out.print("> ");
            } while ((menuChoice = scanner.nextInt()) < 0 || menuChoice > 2);

            switch (menuChoice) {
                case 1 -> equipMenu(scanner, player);
                case 2 -> {
                    System.out.println("You close your backpack...");
                    playerEquipping = false;
                }
                default -> throw new IllegalStateException("Fell out of backpackMenu switch-case.");
            };
        }
    }

    public static void equipMenu(Scanner scanner, Player player) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            System.out.printf("%d. %s%n", i + 1, player.getInventory().get(i).toString());
        }
        int itemChoice;
        do {
            System.out.println("Select item to equip (0 = cancel):");
            System.out.print("> ");
        } while ((itemChoice = scanner.nextInt()) < 0 || itemChoice > player.getInventory().size() + 1);

        if (itemChoice != 0 && itemChoice < player.getInventory().size() + 1) {
            Item selectedItem = player.getInventory().get(itemChoice - 1);

            if (selectedItem.getClass() == Armor.class) {
                player.equipArmor((Armor) selectedItem);
            }
            else if (selectedItem.getClass() == Weapon.class) {
                player.equipWeapon((Weapon) selectedItem);
            }
        }
    }


    // TODO: Implement abilities/perks, campfire/pray resting
    public static void playerRestChoice(Scanner scanner, Player player) {
        System.out.println("[UNAVAILABLE]");
    }
}
