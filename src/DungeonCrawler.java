import java.util.Scanner;

public class DungeonCrawler {
    private boolean gameOver = false;
    private Player player;
    private Mob mob;
    private Dungeon dungeon;

    public void startGame() {
        initGame();
        mainGameLoop();
    }

    // Select stats
    // Random starting weapon, 3 random gear pieces
    // Enter in-between (0 chests) -> combat -> in-between (0-3 chests)
    private void initGame() {
        Scanner scanner = new Scanner(System.in);

        // Welcome to DungeonCrawler
        System.out.println("Welcome to DungeonCrawler!");

        // Select class
        int classChoice;
        do {
            System.out.println("\n- Select a class -");
            System.out.println("1. Fighter - All-around balanced class.");
            System.out.println("2. Barbarian - Tanky frontliner, but less handy with loot.");
            System.out.println("3. Rogue - Agile but less tanky class.");
            System.out.print("> ");
        } while ((classChoice = scanner.nextInt()) < 0 || classChoice > 3);

        switch (classChoice) {
            case 1:
                this.player = new Player(15, 15, 15, 15, 15, 15, 15);
                System.out.println("- Your base stats -");
                System.out.println(this.player.toString());
                break;
            case 2:
                this.player = new Player(20, 25, 13, 12, 18, 5, 12);
                System.out.println("- Your base stats -");
                System.out.println(this.player.toString());
                break;
            case 3:
                this.player = new Player(9, 6, 25, 20, 10, 10, 25);
                System.out.println("- Your base stats -");
                System.out.println(this.player.toString());
                break;
        }

        // Select dungeon
        int dungeonChoice;
        do {
            System.out.println("\n- Select a dungeon -");
            System.out.println("1. Ruins of the Forgotten Castle");
            System.out.println("2. Crypts of the Forgotten Castle [UNAVAILABLE]");
            System.out.println("3. Inferno [UNAVAILABLE]");
            System.out.print("> ");
        } while ((dungeonChoice = scanner.nextInt()) < 0 || dungeonChoice > 1);

        switch (dungeonChoice) {
            case 1:
                this.dungeon = new Dungeon(Dungeon.DungeonPreset.RUINS);
                break;
            case 2:
                this.dungeon = new Dungeon(Dungeon.DungeonPreset.CRYPTS);
                break;
            case 3:
                this.dungeon = new Dungeon(Dungeon.DungeonPreset.INFERNO);
                break;
        }
        System.out.printf("Generated %s%n", this.dungeon.toString());

        System.out.println("You wake up. It being dark and unsure of your whereabouts, you scramble and quickly grab what you find...");
        // Give three random [POOR] armor items
        this.player.addItemToInventory(this.dungeon.getRandomDungeonArmor());
        this.player.addItemToInventory(this.dungeon.getRandomDungeonArmor());
        this.player.addItemToInventory(this.dungeon.getRandomDungeonArmor());
        // Give player one random [POOR] weapon
        this.player.addItemToInventory(this.dungeon.getRandomDungeonWeapon());

        mainGameLoop();
    }

    private void mainGameLoop() {
        while (!gameOver) {

        }
    }
}
