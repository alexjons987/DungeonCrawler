import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class DungeonCrawlerUI {
    public static void printTitle() {
        System.out.println("Welcome to DungeonCrawler!");
    }

    public static Player selectClass(Scanner scanner) {
        System.out.println("\n- Select a class -");
        System.out.println("1. Fighter - All-around balanced class.");
        System.out.println("2. Barbarian - Healthy frontliner, but less handy.");
        System.out.println("3. Rogue - Agile but a less healthy class.");
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

    public static void postCombatMenu(Scanner scanner, Player player, Module module) {

        AtomicInteger currResourcefulness = new AtomicInteger(player.getAttributes().getResourcefulness());
        boolean playerHasRested = false;

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
                    System.out.println(player);
                }
                case 3 -> backpackMenu(scanner, player);
                case 4 -> {
                    if (!playerHasRested) {
                        playerHasRested = playerRestChoice(scanner, player);
                    } else {
                        System.out.println("You've already rested!");
                    }
                }
                case 5 -> lootMenu(scanner, player, module, currResourcefulness);
                default -> throw new IllegalStateException("Fell out of playerMenu switch-case.");
            }
        }
    }

    public static void backpackMenu(Scanner scanner, Player player) {
        System.out.println("You open your backpack...");
        while (true) {
            System.out.println("\n- Your backpack -");
            if (player.getInventory().isEmpty()) {
                System.out.println("There is nothing in here...");
                break;
            }
            for (int i = 0; i < player.getInventory().size(); i++) {
                System.out.printf("%d. %s%n", i + 1, player.getInventory().get(i).toString());
            }
            System.out.println("\n0. Close backpack");
            System.out.println("Select item to equip:");

            int itemChoice = readMenuChoice(scanner, 0, player.getInventory().size());

            if (itemChoice == 0) {
                return;
            }

            if (itemChoice < player.getInventory().size() + 1) {
                Item selectedItem = player.getInventory().get(itemChoice - 1);

                if (selectedItem.getClass() == Armor.class) {
                    player.equipArmor((Armor) selectedItem);
                    System.out.printf("You equipped %s%n", selectedItem);
                } else if (selectedItem.getClass() == Weapon.class) {
                    player.equipWeapon((Weapon) selectedItem);
                    System.out.printf("You equipped %s%n", selectedItem);
                }
            }
        }
    }

    // TODO: Implement pray to reset certain abilities (requires Ability and Perk classes)
    public static boolean playerRestChoice(Scanner scanner, Player player) {
        System.out.println("\n- Select action -");
        System.out.println("1. Light a campfire");
        System.out.println("2. Pray");
        System.out.println("0. Cancel");

        int menuChoice = readMenuChoice(scanner, 1, 2);

        switch (menuChoice) {
            case 1 -> {
                int playerRes = player.getAttributes().getResourcefulness();
                System.out.println("You light a campfire...");
                player.heal(playerRes);
                System.out.printf("You restore %d health.%n", playerRes);
                return true;
            }
            case 2 -> {
                System.out.println("You begin to pray...");
                System.out.println("No one answers... [NOT IMPLEMENTED]");
                return false;
            }
            default -> throw new IllegalStateException("Fell out of playerRestChoice switch-case.");
        }
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
                    System.out.printf("You found %s%n", foundItem);
                } else if (foundItem.getClass() == Weapon.class) {
                    Weapon foundItemCopy = new Weapon((Weapon) foundItem);
                    player.getInventory().add(foundItemCopy);
                    System.out.printf("You found %s%n", foundItem);
                }
            }

            if (module.getUnopenedChests().isEmpty()) {
                System.out.println("There are no more unopened chests...");
                return;
            }
        }
    }

    // TODO: Implement custom class builder
    // Set all stats to 1, let user increase or decrease selected stat
    // Total of 98 stat points to distribute
    private static Player customClassBuilder(Scanner scanner) {
        System.out.println("Custom classes not implemented yet!");
        return new Player(15, 15, 15, 15, 15, 15, 15);
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
}
