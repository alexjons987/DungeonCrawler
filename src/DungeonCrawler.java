import java.util.Scanner;

public class DungeonCrawler {
    private boolean gameOver = false;
    private Player player;
    private Dungeon dungeon;
    private Module module;

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
            this.player.addItemToInventory(this.dungeon.generateRandomDungeonArmor());

        // Give player one random [POOR] weapon
        Weapon weapon = this.dungeon.generateRandomDungeonWeapon(Item.Rarity.POOR);
        player.equipWeapon(weapon);
        System.out.printf("You equipped %s%n", weapon.toString());
    }

    private void mainGameLoop() {
        Scanner scanner = new Scanner(System.in);
        int moduleCounter = 1;
        preAdventureRest(scanner);
    }

    // TODO: Implement
    private void preAdventureRest(Scanner scanner) {
        DungeonCrawlerUI.playerMenu(scanner, this.player, this.dungeon);
    }

    // TODO: Implement
    private void combatEncounter() {

    }

    // TODO: Implement
    private void postCombat() {

    }
}
