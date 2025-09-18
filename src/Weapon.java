public class Weapon extends Item {

    public enum WeaponType {
        SWORD("Sword", 1),
        AXE("Axe", 2),
        MACE("Mace", 1),
        DAGGER("Dagger", 1);

        private final String displayName;
        private final int scaling;

        WeaponType(String displayName, int scaling) {
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

    public enum HandType {
        TWO_HANDED,
        MAIN_HAND,
        OFF_HAND
    }

    private final WeaponType weaponType;
    private final HandType handType;
    private int basePhysWeaponDmg;
    private int baseMagWeaponDmg;
    private final int attackTimer;

    public Weapon(String name, Rarity rarity, WeaponType weaponType, HandType handType, int basePhysWeaponDmg, int baseMagWeaponDmg) {
        super(name, rarity);
        this.weaponType = weaponType;
        this.handType = handType;
        this.basePhysWeaponDmg = basePhysWeaponDmg + (weaponType.getScaling() * rarity.getStatMultiplier());
        if (baseMagWeaponDmg > 0)
            this.baseMagWeaponDmg = baseMagWeaponDmg + (weaponType.getScaling() * rarity.getStatMultiplier());
        else
            this.baseMagWeaponDmg = 0;
        this.attackTimer = getBaseATFromHandType(this.handType) * (basePhysWeaponDmg + baseMagWeaponDmg);
    }

    public Weapon(Weapon weapon) {
        super(weapon.getName(), weapon.getRarity());
        this.weaponType = weapon.weaponType;
        this.handType = weapon.handType;
        this.basePhysWeaponDmg = weapon.basePhysWeaponDmg;
        this.baseMagWeaponDmg = weapon.baseMagWeaponDmg;
        this.attackTimer = getBaseATFromHandType(weapon.handType) * (weapon.basePhysWeaponDmg + weapon.baseMagWeaponDmg);
    }

    public int getBaseATFromHandType(HandType handType) {
        return switch (this.handType) {
            case TWO_HANDED -> 37;
            case MAIN_HAND -> 32;
            case OFF_HAND -> 43;
        };
    }

    public void increaseRarity(Rarity rarity) {
        super.increaseRarity(rarity);

        this.basePhysWeaponDmg = basePhysWeaponDmg + (weaponType.getScaling() * rarity.getStatMultiplier()); // Re-calculate weapon damage
        if (this.baseMagWeaponDmg > 0)
            this.baseMagWeaponDmg = baseMagWeaponDmg + (weaponType.getScaling() * rarity.getStatMultiplier());
        else
            this.baseMagWeaponDmg = 0;
    }

    public WeaponType getWeaponType() {
        return this.weaponType;
    }

    public HandType getHandType() {
        return this.handType;
    }

    private String getHandTypeString() {
        return switch (this.handType) {
            case TWO_HANDED -> "2H";
            case MAIN_HAND -> "MH";
            case OFF_HAND -> "OH";
        };
    }

    public String toStringShort() {
        return String.format(
                "[%s] %s (%s) (%s)",
                this.getRarity(),
                this.getName(),
                this.weaponType,
                this.getHandTypeString()
        );
    }

    public String toString() {
        return String.format(
                "[%s] %s (%s) (%s) - %d+%d (%dt)",
                this.getRarity(),
                this.getName(),
                this.weaponType,
                this.getHandTypeString(),
                this.basePhysWeaponDmg,
                this.baseMagWeaponDmg,
                this.attackTimer
        );
    }
}
