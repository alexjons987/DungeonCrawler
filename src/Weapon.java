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

    private WeaponType weaponType;
    private HandType handType;
    private int basePhysWeaponDmg;
    private int baseMagWeaponDmg;
    private int attackTimer;

    public Weapon(String name, Rarity rarity, WeaponType weaponType, HandType handType, int basePhysWeaponDmg, int baseMagWeaponDmg) {
        super(name, rarity);
        this.weaponType = weaponType;
        this.handType = handType;
        this.basePhysWeaponDmg = basePhysWeaponDmg + (weaponType.getScaling() * rarity.getStatMultiplier());
        if (baseMagWeaponDmg > 0)
            this.baseMagWeaponDmg = baseMagWeaponDmg + (weaponType.getScaling() * rarity.getStatMultiplier());
        else
            this.baseMagWeaponDmg = 0;
        this.attackTimer = 44 * (basePhysWeaponDmg + baseMagWeaponDmg);
    }

    public Weapon(Weapon weapon) {
        super(weapon.getName(),weapon.getRarity());
        this.weaponType = weapon.weaponType;
        this.handType = weapon.handType;
        this.basePhysWeaponDmg = weapon.basePhysWeaponDmg;
        if (weapon.baseMagWeaponDmg > 0)
            this.baseMagWeaponDmg = weapon.baseMagWeaponDmg + (weapon.weaponType.getScaling() * weapon.getRarity().getStatMultiplier());
        else
            this.baseMagWeaponDmg = 0;
        this.attackTimer = 44 * (weapon.basePhysWeaponDmg + weapon.baseMagWeaponDmg);
    }

    public void setRarity(Rarity rarity) {
        super.setRarity(rarity);
        this.basePhysWeaponDmg = basePhysWeaponDmg + (weaponType.getScaling() * rarity.getStatMultiplier()); // Re-calculate weapone damage
        if (this.baseMagWeaponDmg > 0)
            this.baseMagWeaponDmg = baseMagWeaponDmg + (weaponType.getScaling() * rarity.getStatMultiplier());
        else
            this.baseMagWeaponDmg = 0;
    }

    public Weapon.WeaponType getWeaponType() {
        return this.weaponType;
    }

    public Weapon.HandType getHandType() {
        return this.handType;
    }

    private String getHandTypeString() {
        switch (this.handType) {
            case TWO_HANDED:
                return "2H";
            case MAIN_HAND:
                return "MH";
            case OFF_HAND:
                return "OH";
            default:
                throw new IllegalArgumentException(String.format("Unknown HandType given for getHandTypeString(): %s", this.handType));
        }
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
                "[%s] %s (%s) (%s) - %d+%d (%d)",
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
