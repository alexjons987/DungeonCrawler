import java.util.HashMap;

public class Armor extends Item {

    public enum ArmorType {
        PLATE("Plate", 3, 1),
        LEATHER("Leather", 2, 1),
        CLOTH("Cloth", 1, 1),
        ACCESSORY("Accessory", 0, 1);

        private final String displayName;
        private final int armorScaling;
        private final int statScaling;

        ArmorType(String displayName, int armorScaling, int statScaling) {
            this.displayName = displayName;
            this.armorScaling = armorScaling;
            this.statScaling = statScaling;
        }

        public String getDisplayName() {
            return this.displayName;
        }

        public int getArmorScaling() {
            return this.armorScaling;
        }

        public int getStatScaling() {
            return this.statScaling;
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

    private final ArmorType armorType;
    private final ArmorEquipRegion armorEquipRegion;
    private final Attributes attributes;
    private int armorRating;

    public Armor(String name, Rarity rarity, ArmorType armorType, ArmorEquipRegion armorEquipRegion, int armorRating, int strength, int vigor,
            int agility, int dexterity, int will, int knowledge, int resourcefulness) {
        super(name, rarity);
        this.armorType = armorType;
        this.armorEquipRegion = armorEquipRegion;
        this.armorRating = armorRating + (armorType.getArmorScaling() * rarity.getStatMultiplier());
        this.attributes = new Attributes(strength, vigor, agility, dexterity, will, knowledge, resourcefulness);
    }

    public Armor(Armor armor) {
        super(armor.getName(), armor.getRarity());
        this.armorType = armor.armorType;
        this.armorEquipRegion = armor.armorEquipRegion;
        this.armorRating = armor.armorRating;
        this.attributes = new Attributes(armor.attributes);
    }

    public ArmorType getArmorType() {
        return this.armorType;
    }

    public ArmorEquipRegion getArmorEquipRegion() {
        return this.armorEquipRegion;
    }

    public Attributes getStats() {
        return this.attributes;
    }

    public int getArmorRating() {
        return this.armorRating;
    }

    public void increaseRarity(Rarity rarity) {
        super.increaseRarity(rarity);

        this.armorRating = armorRating + (armorType.getArmorScaling() * rarity.getStatMultiplier());

        HashMap<String, Integer> statMap = this.attributes.getAllAttributes();

        if (statMap.get("strength") != 0)
            this.attributes.setStrength(statMap.get("strength") + (armorType.getStatScaling() * rarity.getStatMultiplier()));
        if (statMap.get("vigor") != 0)
            this.attributes.setVigor(statMap.get("vigor") + (armorType.getStatScaling() * rarity.getStatMultiplier()));
        if (statMap.get("agility") != 0)
            this.attributes.setAgility(statMap.get("agility") + (armorType.getStatScaling() * rarity.getStatMultiplier()));
        if (statMap.get("dexterity") != 0)
            this.attributes.setDexterity(statMap.get("dexterity") + (armorType.getStatScaling() * rarity.getStatMultiplier()));
        if (statMap.get("will") != 0)
            this.attributes.setWill(statMap.get("will") + (armorType.getStatScaling() * rarity.getStatMultiplier()));
        if (statMap.get("knowledge") != 0)
            this.attributes.setKnowledge(statMap.get("knowledge") + (armorType.getStatScaling() * rarity.getStatMultiplier()));
        if (statMap.get("resourcefulness") != 0)
            this.attributes.setResourcefulness(statMap.get("resourcefulness") + (armorType.getStatScaling() * rarity.getStatMultiplier()));
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
                this.armorRating
            )
        );

        int str = this.attributes.getStrength();
        int vig = this.attributes.getVigor();
        int agi = this.attributes.getAgility();
        int dex = this.attributes.getDexterity();
        int wil = this.attributes.getWill();
        int knw = this.attributes.getKnowledge();
        int res = this.attributes.getResourcefulness();

        if (str != 0) sb.append(String.format(" / STR: %+d", str));
        if (vig != 0) sb.append(String.format(" / VIG: %+d", vig));
        if (agi != 0) sb.append(String.format(" / AGI: %+d", agi));
        if (dex != 0) sb.append(String.format(" / DEX: %+d", dex));
        if (wil != 0) sb.append(String.format(" / WIL: %+d", wil));
        if (knw != 0) sb.append(String.format(" / KNW: %+d", knw));
        if (res != 0) sb.append(String.format(" / RES: %+d", res));

        return sb.toString();
    }
}
