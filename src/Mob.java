public class Mob {

    // Normal -> Elite -> Nightmare
    // Scale HP by +60%

    public enum MobTier {
        NORMAL("Normal"),
        ELITE("Elite"),
        NIGHTMARE("Nightmare");

        private final String tierDisplayString;

        MobTier(String tierDisplayString) {
            this.tierDisplayString = tierDisplayString;
        }
    }

    private String name;
    private MobTier mobTier;
    private int health;
    private int damage;
    private float attackTimer;
    private float hitRate;

    public Mob(String name, MobTier mobTier, int health, int damage, int attackTimer, float hitRate) {
        this.name = name;
        this.mobTier = mobTier;
        this.health = health;
        this.damage = damage;
        this.attackTimer = attackTimer;
        this.hitRate = hitRate;
    }

    public Mob(Mob mob) {
        this.name = mob.name;
        this.mobTier = mob.mobTier;
        this.health = mob.health;
        this.damage = mob.damage;
        this.attackTimer = mob.attackTimer;
        this.hitRate = mob.hitRate;
    }
}
