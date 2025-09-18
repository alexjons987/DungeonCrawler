public class Mob {
    public enum MobTier {
        NORMAL("Normal"),
        ELITE("Elite"),
        NIGHTMARE("Nightmare");

        private final String tierDisplayString;

        MobTier(String tierDisplayString) {
            this.tierDisplayString = tierDisplayString;
        }

        public String getTierDisplayString() {
            return this.tierDisplayString;
        }
    }

    boolean isAlive;
    private final String name;
    private final MobTier tier;
    private int health;
    private final int damage;
    private final int attackTimer;
    private final float hitRate;

    public Mob(String name, MobTier tier, int health, int damage, int attackTimer, float hitRate) {
        this.isAlive = health > 0;
        this.name = name;
        this.tier = tier;
        this.health = health;
        this.damage = damage;
        this.attackTimer = attackTimer;
        this.hitRate = hitRate;
    }

    public Mob(Mob mob) {
        this.isAlive = mob.health > 0;
        this.name = mob.name;
        this.tier = mob.tier;
        this.health = mob.health;
        this.damage = mob.damage;
        this.attackTimer = mob.attackTimer;
        this.hitRate = mob.hitRate;
    }

    // Props
    public boolean isAlive() {
        return this.isAlive;
    }

    public String getName() {
        return this.name;
    }

    public MobTier getTier() {
        return this.tier;
    }

    public int getHealth() {
        return this.health;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getAttackTimer() {
        return this.attackTimer;
    }

    public float getHitRate() {
        return this.hitRate;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            this.isAlive = false;
        }
    }
}
