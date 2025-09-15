import java.util.Random;

public abstract class Item {

    public enum Rarity {
        POOR(0),
        COMMON(1),
        UNCOMMON(2),
        RARE(3),
        EPIC(4),
        LEGENDARY(5),
        UNIQUE(6),
        ARTIFACT(7);

        private final int statMultiplier;

        Rarity(int statMultiplier) {
            this.statMultiplier = statMultiplier;
        }

        public int getStatMultiplier() {
            return this.statMultiplier;
        }
    }

    private String name;
    private Rarity rarity;

    public Item(String name, Rarity rarity) {
        this.name = name;
        this.rarity = rarity;
    }

    public Item(Item item) {
        this.name = item.getName();
        this.rarity = item.getRarity();
    }

    public String getName() {
        return this.name;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public Rarity getRarity() {
        return this.rarity;
    }

    public Rarity getRandomRarity(Rarity lowesRarity, Rarity highestRarity) {
        int lowest = lowesRarity.getStatMultiplier();
        int highest = highestRarity.getStatMultiplier();
        if (lowest > highest) {
            throw new IllegalArgumentException(
                String.format("Lowest rarity cannot be a greater rarity than highest rarity")
            );
        }
        
        Random random = new Random();
        int randomInt = random.nextInt(lowest, highest + 1);
        for (Rarity rarity : Rarity.values()) {
            if (rarity.getStatMultiplier() == randomInt) {
                return rarity;
            }
        }
        throw new IllegalStateException(
            String.format("Did not find a matching rarity for: %s", rarity)
        );
    }

    public abstract String toStringShort();

    public abstract String toString();
}
