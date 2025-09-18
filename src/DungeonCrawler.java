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
        System.out.println("You wake up in a dark place. Unsure of your whereabouts, you scramble and quickly move on...");

        // Give player one random [POOR] weapon
        Weapon weapon = this.dungeon.generateRandomDungeonWeapon(Item.Rarity.POOR);
        player.equipWeapon(weapon);
        System.out.printf("You equipped %s%n", weapon.toString());
    }

    private void mainGameLoop() {
        Scanner scanner = new Scanner(System.in);
        int moduleIndex = 0;
        preAdventureRest(scanner, moduleIndex);
    }

    // TODO: Implement
    private void preAdventureRest(Scanner scanner, int moduleCounter) {
        DungeonCrawlerUI.postCombatMenu(scanner, this.player, this.dungeon.getModule(moduleCounter));
    }

    // TODO: Implement
    private void combatEncounter() {

    }

    // TODO: Implement
    private void postCombat() {

    }
}
