public class Armor extends Item {

    public enum ArmorType {
        PLATE("Plate", 3),
        LEATHER("Leather", 2),
        CLOTH("Cloth", 1);

        private final String displayName;
        private final int scaling;

        ArmorType(String displayName, int scaling) {
            this.displayName = displayName;
            this.scaling = scaling;
        }

        public String getDisplayName() {
            return this.displayName;
        }

        public int getScaling() {
            return this.scaling;
        }
    }

    public enum ArmorEquipRegion {
        HEAD("Head"),
        CHEST("Chest"),
        HANDS("Hands"),
        LEGS("Legs"),
        FEET("Feet"),
        NECKLACE("Necklace"),
        RING("Ring");

        private final String displayName;

        ArmorEquipRegion(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return this.displayName;
        }
    }

    private ArmorType armorType;
    private ArmorEquipRegion armorEquipRegion;
    private int baseArmorRating;
    private Stats stats;

    public Armor(String name, Rarity rarity, ArmorType armorType, ArmorEquipRegion armorEquipRegion, int baseArmorRating, int strength, int vigor,
            int agility, int dexterity, int will, int knowledge, int resourcefulness) {
        super(name, rarity);
        this.armorType = armorType;
        this.armorEquipRegion = armorEquipRegion;
        this.baseArmorRating = baseArmorRating + (armorType.getScaling() * rarity.getStatMultiplier());
        this.stats = new Stats(strength, vigor, agility, dexterity, will, knowledge, resourcefulness);
    }

    public Armor(Armor armor) {
        super(armor.getName(), armor.getRarity());
        this.armorType = armor.armorType;
        this.armorEquipRegion = armor.armorEquipRegion;
        this.baseArmorRating = armor.baseArmorRating;
        this.stats = armor.stats;
    }

    // TODO: Implement setRarity and recalculate armor rating and stats

    public ArmorEquipRegion getArmorEquipRegion() {
        return this.armorEquipRegion;
    }

    public String toStringShort() {
        return String.format(
                "[%s] %s (%s - %s)",
                this.getRarity(),
                this.getName(),
                this.armorType.getDisplayName(),
                this.armorEquipRegion.getDisplayName()
        );
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(
            String.format(
                "[%s] %s (%s - %s) - Armor rating: +%d",
                this.getRarity(),
                this.getName(),
                this.armorType.getDisplayName(),
                this.armorEquipRegion.getDisplayName(),
                this.baseArmorRating
            )
        );

        int str = this.stats.getStrength();
        int vig = this.stats.getVigor();
        int agi = this.stats.getAgility();
        int dex = this.stats.getDexterity();
        int wil = this.stats.getWill();
        int knw = this.stats.getKnowledge();
        int res = this.stats.getResourcefulness();

        if (str != 0)
            sb.append(" / STR: +" + str);
        if (vig != 0)
            sb.append(" / VIG: +" + vig);
        if (agi != 0)
            sb.append(" / AGI: +" + agi);
        if (dex != 0)
            sb.append(" / DEX: +" + dex);
        if (wil != 0)
            sb.append(" / WIL: +" + wil);
        if (knw != 0)
            sb.append(" / KNW: +" + knw);
        if (res != 0)
            sb.append(" / RES: +" + res);

        return sb.toString();
    }
}
