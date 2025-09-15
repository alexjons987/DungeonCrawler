import java.util.ArrayList;

public class Player {

    public enum PlayerRace {

    }

    private Stats stats;
    private int health;
    private ArrayList<Item> inventory;

    // Equipped armor
    Armor equippedHead;
    Armor equippedChest;
    Armor equippedLegs;
    Armor equippedHands;
    Armor equippedFeet;
    Armor equippedNecklace;
    Armor equippedRing;

    // Equipped weapon(s)
    Weapon equippedMainHand;
    Weapon equippedOffHand;

    // Altars
    // Active buffs

    // Constructors
    public Player() {
        this.stats = new Stats();
        this.health = this.stats.getMaxHP();
        this.equippedMainHand = null;
        this.equippedOffHand = null;
    }

    public Player(int strength, int vigor, int agility, int dexterity, int will, int knowledge, int resourcefulness) {
        this.stats = new Stats(strength, vigor, agility, dexterity, will, knowledge, resourcefulness);
        this.health = this.stats.getMaxHP();
        this.equippedHead = null;
        this.equippedChest = null;
        this.equippedLegs = null;
        this.equippedHands = null;
        this.equippedFeet = null;
        this.equippedNecklace = null;
        this.equippedRing = null;
        this.equippedMainHand = null;
        this.equippedOffHand = null;
        this.inventory = new ArrayList<Item>();
    }

    // Getters / Setters
    public Stats getStats() {
        return this.stats;
    }

    public int getHealth() {
        return this.health;
    }

    public ArrayList<Item> getInventory() {
        return this.inventory;
    }

    public float getHealthPercentage() {
        return (float) this.health / this.stats.getMaxHP();
    }

    // Funcs
    public void equipArmor(Armor armor) {
        switch (armor.getArmorEquipRegion()) {
            case Armor.ArmorEquipRegion.HEAD:
                if (this.equippedHead != null)
                    this.inventory.add(this.equippedHead);
                this.equippedHead = armor;
                this.inventory.remove(armor);
                break;
            case Armor.ArmorEquipRegion.CHEST:
                if (this.equippedChest != null)
                    this.inventory.add(this.equippedChest);
                this.equippedChest = armor;
                this.inventory.remove(armor);
                break;
            case Armor.ArmorEquipRegion.LEGS:
                if (this.equippedLegs != null)
                    this.inventory.add(this.equippedLegs);
                this.equippedLegs = armor;
                this.inventory.remove(armor);
                break;
            case Armor.ArmorEquipRegion.HANDS:
                if (this.equippedHands != null)
                    this.inventory.add(this.equippedHands);
                this.equippedHands = armor;
                this.inventory.remove(armor);
                break;
            case Armor.ArmorEquipRegion.FEET:
                if (this.equippedFeet != null)
                    this.inventory.add(this.equippedFeet);
                this.equippedFeet = armor;
                this.inventory.remove(armor);
                break;
            case Armor.ArmorEquipRegion.NECKLACE:
                if (this.equippedNecklace != null)
                    this.inventory.add(this.equippedNecklace);
                this.equippedNecklace = armor;
                this.inventory.remove(armor);
                break;
            case Armor.ArmorEquipRegion.RING:
                if (this.equippedRing != null)
                    this.inventory.add(this.equippedRing);
                this.equippedRing = armor;
                this.inventory.remove(armor);
                break;
        }
        // TODO: Implement stats being modified by equipped armor
    }

    public void recalculateStats() {

    }

    public boolean equipWeapon(Weapon weapon) {
        // TODO: Add base func, add handtype checks
        return true;
    }

    public void addItemToInventory(Item item) {
        System.out.printf("Added %s to inventory.%n", item.toStringShort());
    }

    public void showInventory() {
        for (Item item : this.inventory)
            System.out.println(item.toString());
    }

    // To String
    public String toStringEquipped() {
        return String.format(
            "Head: %s%n" +
            "Chest: %s%n" +
            "Legs: %s%n" +
            "Hands: %s%n" +
            "Feet: %s%n" +
            "Necklace: %s%n" +
            "Ring: %s%n",
            this.equippedHead == null ? "None" : this.equippedHead.toString(),
            this.equippedChest == null ? "None" : this.equippedChest.toString(),
            this.equippedLegs == null ? "None" : this.equippedLegs.toString(),
            this.equippedHands == null ? "None" : this.equippedHands.toString(),
            this.equippedFeet == null ? "None" : this.equippedFeet.toString(),
            this.equippedNecklace == null ? "None" : this.equippedNecklace.toString(),
            this.equippedRing == null ? "None" : this.equippedRing.toString()
            // TODO: Add equipped weapon
        );
    }

    public String toStringStats() {
        return String.format(
            "Strength: %d%n" +
            "Vigor: %d%n" +
            "Agility: %d%n" +
            "Dexterity: %d%n" +
            "Will: %d%n" +
            "Knowledge: %d%n" +
            "Resourcefulness: %d%n" +
            "Health: %d/%d (%.1f%%)%n" +
            "Action Speed: %.1f%%",
            this.stats.getStrength(),
            this.stats.getVigor(),
            this.stats.getAgility(),
            this.stats.getDexterity(),
            this.stats.getWill(),
            this.stats.getKnowledge(),
            this.stats.getResourcefulness(),
            this.health,
            this.stats.getMaxHP(),
            this.getHealthPercentage() * 100,
            this.stats.getActionSpeed()
        );
    }

    public String toString() {
        return String.format("%s%n%s", this.toStringEquipped(), this.toStringStats());
    }

    // TODO: Make stats (will/knowledge) increase/decrease effectiveness of abilities/perks
    // TODO: Abilites
    // Blood Exchange (Campfire to refresh) - When your health drops below 30%, gain +40% lifesteal for your 12 next attacks.
    // War Cry (Campfire to refresh) - When your health drops below 50%, gain +25% max health bonus for the rest of the combat encounter (current health scales accordingly).
    // Divine Protection (Pray to refresh) - At the start of the combat encounter, receive the blessing of the divine and gain +30% physical damage reduction for the 6 next incoming attacks.

    // TODO: Add perks
    // Adrenaline Spike - When your health goes below 40%, gain +15% action speed for your 12 next attacks.
    // Combo Attack - For every other attack, gain +10% damage bonus.
    // Defense Master - Gain an additional +10% armor from equipped armor.
    // Sword Mastery - While using a Sword-type weapon, gain +2 weapon damage and 5% action speed.
    // Axe Specialization - While using an Axe-type weapon, gain +3 weapon damage.
    // Two-Hander - While using a two-handed (2H) weapon, gain +5% damage bonus.
    // Executioner - While using an Axe-type weapon, gain +5% critical chance bonus.
    // Blunt Weapon Mastery - While using a Mace-type weapon, gain +10% damage bonus.
    // Jokester - Grant yourself +2 to all Attributes.
    // Poisoned Daggers  - While using a Dagger-type weapon, deal an additional 4 magic damage.
    // Ambush - When entering a combat encounter, gain +100% Action Speed and +25% physical damage bonus for your first attack.'
    // Dagger Mastery - While using a Dagger-type weapon, gain +2 weapon damage.
    // Traps and Locks - You can unlock locked chests without lockpicks.
    // Fermata - Gain +5 Resourcefulness.
    // Rapier Mastery - When a rapier is equipped, gain +2 weapon damage and 5% action speed.
    // Story Teller - Gain +3 Will and +3 Knowledge.

    // Attacking
    // Each weapon has a swing timer (affected by player action speed)
    // Critical hits +100% damage bonus
    // On miss, increase time of next attack
    // Miss - 10%: Increase time of next attack by +100%.
    // Crit - 5%: Deal +100% weapon damage.
    // Hit - 85%: Deal weapon damage.

    // Looting chests
    // Create 1 of each item -> randomly pick one -> randomize rarity
    // POOR 15%
    // UNCOMMON 30%
    // RARE 25%
    // EPIC 15%
    // LEGENDARY 10%
    // UNIQUE 4%
    // ARTIFACT 1%
}
