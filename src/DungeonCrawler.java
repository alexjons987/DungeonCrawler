import java.util.Scanner;

public class DungeonCrawler {
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
        // DungeonCrawlerUI.postCombatMenu(scanner, this.player, this.dungeon.getModule(moduleIndex));

        boolean playerStillAlive = true;
        while (playerStillAlive) {
            postCombat(scanner, this.player, this.dungeon.getModule(moduleIndex), moduleIndex > 0);
            moduleIndex++;
            playerStillAlive = combatEncounter(this.player, this.dungeon.getModule(moduleIndex));
        }
        System.out.println("You died (noob)");
    }

    // TODO: Implement
    private boolean combatEncounter(Player player, Module module) {
        Mob mob = module.getMob();
        System.out.printf("You enter the next module and encounter a %s (%s)!%n", mob.getName(), mob.getTier());
        System.out.println("- Combat log -");

        boolean usingOffhand = false;
        Weapon playerActiveWeapon = player.getEquippedMainHand();
        int playerWeaponAttackTimer = playerActiveWeapon.getAttackTimer();
        int mobAttackTimer = mob.getAttackTimer();

        long nextPlayerAttackTick = playerWeaponAttackTimer; // Set first player action tick
        long nextMobAttackTick = mobAttackTimer; // Set first mob action tick

        long tick;
        while (player.isAlive() && mob.isAlive()) {
            long nextTick = Math.min(nextPlayerAttackTick, nextMobAttackTick); // Skip to tick where action occurs

            if (nextTick == Long.MAX_VALUE) {
                throw new IllegalStateException("Combat took too long! (Tick reached Long.MAX_VALUE)");
            }
            tick = nextTick;

            boolean playerActionThisTick = (tick == nextPlayerAttackTick);
            boolean mobActionThisTick = (tick == nextMobAttackTick);

            // Resolve attacks scheduled at this tick. If both act, resolve simultaneously:
            int playerDamage = 0;
            int mobDamage = 0;

            if (playerActionThisTick && player.isAlive()) {
                playerDamage = playerActiveWeapon.getBasePhysWeaponDmg() + playerActiveWeapon.getBaseMagWeaponDmg();
            }
            if (mobActionThisTick && mob.isAlive()) {
                mobDamage = (int) ((float) mob.getDamage() * (1.0 - player.getPhysDmgReduction()));
            }

            // Apply damage for current tick
            if (playerDamage > 0) {
                mob.takeDamage(playerDamage);
                System.out.printf(
                        "[t=%d] You hit %s for %d damage. %s HP: %d%n",
                        tick,
                        mob.getName(),
                        playerDamage,
                        mob.getName(),
                        mob.getHealth()
                );
            }
            if (mobDamage > 0) {
                player.takeDamage(mobDamage);
                System.out.printf(
                        "[t=%d] %s hits you for %d damage. Your HP: %d%n",
                        tick,
                        mob.getName(),
                        mobDamage,
                        player.getHealth()
                );
            }

            // Update next action ticks
            if (playerActionThisTick) {
                // Alternate MH and OH
                if (!usingOffhand && player.getEquippedOffHand() != null) {
                    playerActiveWeapon = player.getEquippedOffHand();
                    usingOffhand = true;
                    playerWeaponAttackTimer = playerActiveWeapon.getAttackTimer();
                } else {
                    playerActiveWeapon = player.getEquippedMainHand();
                    usingOffhand = false;
                    playerWeaponAttackTimer = playerActiveWeapon.getAttackTimer();
                }
                nextPlayerAttackTick += playerWeaponAttackTimer;
            }
            if (mobActionThisTick) {
                nextMobAttackTick += mobAttackTimer;
            }
        }

        // Outcomes
        if (player.isAlive() && !mob.isAlive()) {
            System.out.printf("You deal the final blow to the %s!%n", mob.getName());
            return true;  // player wins
        } else if (!player.isAlive() && mob.isAlive()) {
            System.out.println("You're struck and your vision turns blurry...");
            return false; // player loses
        } else {
            System.out.println("You trade blows, both fall...");
            return false;
        }
    }

    // TODO: Implement
    private void postCombat(Scanner scanner, Player player, Module module, boolean printPostCombatText) {
        if (printPostCombatText) {
            System.out.printf("Exhausted from the %s encounter, you take a moment...%n", module.getMob().getName());
        }
        DungeonCrawlerUI.postCombatMenu(scanner, player, module);
    }
}
