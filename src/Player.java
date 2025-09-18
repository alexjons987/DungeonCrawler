import java.util.ArrayList;

public class Player {

    public enum PlayerRace {

    }

    private boolean isAlive = true;
    private final Attributes attributes;
    private int health;
    private final ArrayList<Item> inventory;
    private int armorRating = 0;

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
    public Player(int strength, int vigor, int agility, int dexterity, int will, int knowledge, int resourcefulness) {
        this.attributes = new Attributes(strength, vigor, agility, dexterity, will, knowledge, resourcefulness);
        this.health = this.attributes.getMaxHP();
        this.inventory = new ArrayList<Item>();
        this.equippedHead = null;
        this.equippedChest = null;
        this.equippedLegs = null;
        this.equippedHands = null;
        this.equippedFeet = null;
        this.equippedNecklace = null;
        this.equippedRing = null;
        this.equippedMainHand = null;
        this.equippedOffHand = null;
    }

    // Getters / Setters
    public boolean isAlive() {
        return this.isAlive;
    }

    public Attributes getAttributes() {
        return this.attributes;
    }

    public int getHealth() {
        return this.health;
    }

    public ArrayList<Item> getInventory() {
        return this.inventory;
    }

    public float getHealthPercentage() {
        return (float) this.health / this.attributes.getMaxHP();
    }

    public Weapon getEquippedMainHand() {
        return this.equippedMainHand;
    }

    public Weapon getEquippedOffHand() {
        return this.equippedOffHand;
    }

    // Funcs
    public int getArmorRating() {
        int armorRating = 0;

        if (equippedHead != null)
            armorRating += this.equippedHead.getArmorRating();
        if (equippedChest != null)
            armorRating += this.equippedChest.getArmorRating();
        if (equippedLegs != null)
            armorRating += this.equippedLegs.getArmorRating();
        if (equippedHands != null)
            armorRating += this.equippedHands.getArmorRating();
        if (equippedFeet != null)
            armorRating += this.equippedFeet.getArmorRating();
        if (equippedNecklace != null)
            armorRating += this.equippedNecklace.getArmorRating();
        if (equippedRing != null)
            armorRating += this.equippedRing.getArmorRating();

        return armorRating;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            this.isAlive = false;
        }
    }

    public void heal(int amount) {
        this.health += amount;
        if (this.health > this.attributes.getMaxHP()) {
            this.health = this.attributes.getMaxHP();
        }
    }

    public float getPhysDmgReduction() {
        return 0.0015f * this.armorRating; // 0.15% Phys dmg reduction per armor point
    }

    public String getInventoryString() {

        if (this.inventory.isEmpty())
            return "There are no items in here...";

        StringBuilder inventorySB = new StringBuilder();
        for (Item item : this.inventory)
            inventorySB.append(item.toString()).append("\n");

        return inventorySB.toString();
    }

    public void equipArmor(Armor armor) {

        switch (armor.getArmorEquipRegion()) {
            case Armor.ArmorEquipRegion.HEAD:
                if (this.equippedHead != null) { // TODO: Code within cases -> Make into function
                    removeStatsFromArmor(this.equippedHead);
                    this.inventory.add(this.equippedHead);
                }
                this.equippedHead = armor;
                addStatsFromArmor(armor);
                this.inventory.remove(armor);
                break;
            case Armor.ArmorEquipRegion.CHEST:
                if (this.equippedChest != null) {
                    removeStatsFromArmor(equippedChest);
                    this.inventory.add(this.equippedChest);
                }
                this.equippedChest = armor;
                addStatsFromArmor(armor);
                this.inventory.remove(armor);
                break;
            case Armor.ArmorEquipRegion.LEGS:
                if (this.equippedLegs != null) {
                    removeStatsFromArmor(equippedLegs);
                    this.inventory.add(this.equippedLegs);
                }
                this.equippedLegs = armor;
                addStatsFromArmor(armor);
                this.inventory.remove(armor);
                break;
            case Armor.ArmorEquipRegion.HANDS:
                if (this.equippedHands != null) {
                    removeStatsFromArmor(equippedHands);
                    this.inventory.add(this.equippedHands);
                }
                this.equippedHands = armor;
                addStatsFromArmor(armor);
                this.inventory.remove(armor);
                break;
            case Armor.ArmorEquipRegion.FEET:
                if (this.equippedFeet != null) {
                    removeStatsFromArmor(equippedFeet);
                    this.inventory.add(this.equippedFeet);
                }
                this.equippedFeet = armor;
                addStatsFromArmor(armor);
                this.inventory.remove(armor);
                break;
            case Armor.ArmorEquipRegion.NECKLACE:
                if (this.equippedNecklace != null) {
                    removeStatsFromArmor(this.equippedNecklace);
                    this.inventory.add(this.equippedNecklace);
                }
                this.equippedNecklace = armor;
                addStatsFromArmor(armor);
                this.inventory.remove(armor);
                break;
            case Armor.ArmorEquipRegion.RING:
                if (this.equippedRing != null) {
                    removeStatsFromArmor(this.equippedRing);
                    this.inventory.add(this.equippedRing);
                }
                this.equippedRing = armor;
                addStatsFromArmor(armor);
                this.inventory.remove(armor);
                break;
        }
        recalculateArmorRating();
    }

    private void recalculateArmorRating() {
        this.armorRating = getArmorRating();
    }



    // TODO: Combine add/remove attributes to one func?
    private void addStatsFromArmor(Armor armor) {

        Attributes armorAttributes = armor.getStats();
        float currHealthPerc = getHealthPercentage();

        int armorStrength = armorAttributes.getStrength();
        int armorVigor = armorAttributes.getVigor();
        int armorAgility = armorAttributes.getAgility();
        int armorDexterity = armorAttributes.getDexterity();
        int armorWill = armorAttributes.getWill();
        int armorKnowledge = armorAttributes.getKnowledge();
        int armorResourcefulness = armorAttributes.getResourcefulness();

        this.attributes.setStrength(this.attributes.getStrength() + armorStrength);
        this.attributes.setVigor(this.attributes.getVigor() + armorVigor);
        this.attributes.setAgility(this.attributes.getAgility() + armorAgility);
        this.attributes.setDexterity(this.attributes.getDexterity() + armorDexterity);
        this.attributes.setWill(this.attributes.getWill() + armorWill);
        this.attributes.setKnowledge(this.attributes.getKnowledge() + armorKnowledge);
        this.attributes.setResourcefulness(this.attributes.getResourcefulness() + armorResourcefulness);

        // Update new health accordingly
        float newCurrHealth = this.attributes.getMaxHP() * currHealthPerc;
        this.health = (int) newCurrHealth;
    }

    private void removeStatsFromArmor(Armor armor) {

        Attributes armorAttributes = armor.getStats();
        float currHealthPerc = getHealthPercentage();

        int armorStrength = armorAttributes.getStrength() * -1;
        int armorVigor = armorAttributes.getVigor() * -1;
        int armorAgility = armorAttributes.getAgility() * -1;
        int armorDexterity = armorAttributes.getDexterity() * -1;
        int armorWill = armorAttributes.getWill() * -1;
        int armorKnowledge = armorAttributes.getKnowledge() * -1;
        int armorResourcefulness = armorAttributes.getResourcefulness() * -1;

        this.attributes.setStrength(this.attributes.getStrength() + armorStrength);
        this.attributes.setVigor(this.attributes.getVigor() + armorVigor);
        this.attributes.setAgility(this.attributes.getAgility() + armorAgility);
        this.attributes.setDexterity(this.attributes.getDexterity() + armorDexterity);
        this.attributes.setWill(this.attributes.getWill() + armorWill);
        this.attributes.setKnowledge(this.attributes.getKnowledge() + armorKnowledge);
        this.attributes.setResourcefulness(this.attributes.getResourcefulness() + armorResourcefulness);

        // Update new health accordingly
        float newCurrHealth = this.attributes.getMaxHP() * currHealthPerc;
        this.health = (int) newCurrHealth;
    }

    public void equipWeapon(Weapon weapon) {

        switch (weapon.getHandType()) {
            case Weapon.HandType.TWO_HANDED:
                // Unequip current MH
                if (this.equippedMainHand != null) {
                    this.inventory.add(this.equippedMainHand);
                    this.equippedMainHand = null;
                }
                // And unequip current OH
                if (this.equippedOffHand != null) {
                    this.inventory.add(this.equippedOffHand);
                    this.equippedOffHand = null;
                }
                this.equippedMainHand = weapon;
                this.inventory.remove(weapon);
            case Weapon.HandType.MAIN_HAND:
                // Unequip current MH
                if (this.equippedMainHand != null) {
                    this.inventory.add(this.equippedMainHand);
                    this.equippedMainHand = null;
                }
                this.equippedMainHand = weapon;
                this.inventory.remove(weapon);
                break;
            case Weapon.HandType.OFF_HAND:
                // Unequip current MH if 2H
                if (this.equippedMainHand != null && this.equippedMainHand.getHandType() == Weapon.HandType.TWO_HANDED) {
                    this.inventory.add(this.equippedMainHand);
                    this.equippedMainHand = null;
                }
                // Unequip current OH
                if (this.equippedOffHand != null) {
                    this.inventory.add(this.equippedOffHand);
                    this.equippedOffHand = null;
                }
                this.equippedOffHand = weapon;
                this.inventory.remove(weapon);
                break;
        }
    }

    public void addItemToInventory(Item item) {
        this.inventory.add(item);
        System.out.printf("Added %s to inventory.%n", item.toStringShort());
    }

    public void showInventory() {
        for (Item item : this.inventory)
            System.out.printf("%s%n", item.toString());
    }

    // To String
    public String toStringEquipped() {

        String mhStr = this.equippedMainHand == null ? "None" : this.equippedMainHand.toString();
        String ohStr = this.equippedOffHand == null ? "None" : this.equippedOffHand.toString();

        if (this.equippedMainHand != null && this.equippedMainHand.getHandType() == Weapon.HandType.TWO_HANDED) {
            mhStr = this.equippedMainHand.toString();
            ohStr = "[BLOCKED - USED BY 2H]";
        }

        return String.format(
            "Head: %s%n" +
            "Chest: %s%n" +
            "Legs: %s%n" +
            "Hands: %s%n" +
            "Feet: %s%n" +
            "Necklace: %s%n" +
            "Ring: %s%n" +
            "Main-hand: %s%n" +
            "Off-hand: %s%n",
            this.equippedHead == null ? "None" : this.equippedHead.toString(),
            this.equippedChest == null ? "None" : this.equippedChest.toString(),
            this.equippedLegs == null ? "None" : this.equippedLegs.toString(),
            this.equippedHands == null ? "None" : this.equippedHands.toString(),
            this.equippedFeet == null ? "None" : this.equippedFeet.toString(),
            this.equippedNecklace == null ? "None" : this.equippedNecklace.toString(),
            this.equippedRing == null ? "None" : this.equippedRing.toString(),
            mhStr,
            ohStr
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
            "Action Speed: %.1f%%%n" +
            "Phys. Dmg Reduction: %.2f%%",
            this.attributes.getStrength(),
            this.attributes.getVigor(),
            this.attributes.getAgility(),
            this.attributes.getDexterity(),
            this.attributes.getWill(),
            this.attributes.getKnowledge(),
            this.attributes.getResourcefulness(),
            this.health,
            this.attributes.getMaxHP(),
            this.getHealthPercentage() * 100,
            this.attributes.getActionSpeed(),
            this.getPhysDmgReduction() * 100
        );
    }

    public String toString() {
        return String.format("%s%n%s", this.toStringEquipped(), this.toStringStats());
    }

    // TODO: Make attributes (will/knowledge) increase/decrease effectiveness of abilities/perks
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
