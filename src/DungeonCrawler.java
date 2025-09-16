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

        DungeonCrawlerUI.printTitle();

        // Select class
        this.player = DungeonCrawlerUI.selectClass(scanner);
        System.out.println("- Your base stats -");
        System.out.println(this.player.toStringStats());

        // Select dungeon
        this.dungeon = DungeonCrawlerUI.selectDungeon(scanner);
        System.out.printf("Generated %s%n%n", this.dungeon.toString());

        // Game intro
        System.out.println("You wake up. It being dark and unsure of your whereabouts, you scramble and quickly move on...");

        // TODO: Remove and replace with an initial loot room sequence
        // Give three random [POOR] armor items
        for (int i = 0; i < 3; i++)
            this.player.addItemToInventory(this.dungeon.generateRandomDungeonArmor(Item.Rarity.UNIQUE));

        // Give player one random [POOR] weapon
        Weapon weapon = this.dungeon.generateRandomDungeonWeapon(Item.Rarity.POOR);
        this.player.addItemToInventory(weapon);

        // System.out.printf("%nInventory (%d):%n", this.player.getInventory().size());
        // this.player.showInventory();

        // System.out.println("\n- Full player info -");
        // System.out.println(this.player.toString());

        mainGameLoop();
    }

    private void mainGameLoop() {
        int moduleCounter = 1;
        preAdventureRest();

        // Module 1 always "rest/loot"
        // 1. Go deeper into the dungeon
        // 2. Examine yourself (Show stats)
        // 3. Open your backpack (Show all items and their stats)
        //      3a. Equip an item
        //      3b. Close your backpack
        // 4. Rest
        //      4a. Light a campfire
        //      4b. Pray


        while (!gameOver) {
            combatEncounter();


        }
    }

    // TODO: Implement
    private void preAdventureRest() {

    }

    // TODO: Implement
    private void combatEncounter() {

    }

    // TODO: Implement
    private void postCombat() {

    }
}
