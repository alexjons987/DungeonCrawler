import java.util.Scanner;

public class DungeonCrawlerUI {
    public static void printTitle() {
        System.out.println("Welcome to DungeonCrawler!");
    }

    public static Player selectClass(Scanner scanner) {
        int classChoice;
        do {
            System.out.println("- Select a class -");
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
            case 4 -> customClass(scanner);
            default -> throw new IllegalStateException("Fell out of class select case.");
        };
    }

    // TODO: Implement custom class builder
    // Set all stats to 0, let user increase or decrease selected stat
    // Total of 105 stat points to distribute
    private static Player customClass(Scanner scanner) {
        return new Player();
    }

    public static Dungeon selectDungeon(Scanner scanner) {
        int dungeonChoice;
        do {
            System.out.println("- Select a dungeon -");
            System.out.println("1. Ruins of the Forgotten Castle");
            System.out.println("2. Crypts of the Forgotten Castle [UNAVAILABLE]");
            System.out.println("3. Inferno [UNAVAILABLE]");
            System.out.print("> ");
        } while ((dungeonChoice = scanner.nextInt()) < 0 || dungeonChoice > 1);

        return switch (dungeonChoice) {
            case 1 -> new Dungeon(Dungeon.DungeonPreset.RUINS);
            case 2 -> new Dungeon(Dungeon.DungeonPreset.CRYPTS);
            case 3 -> new Dungeon(Dungeon.DungeonPreset.INFERNO);
            default -> throw new IllegalStateException("Fell out of Dungeon select case.");
        };
    }
}
