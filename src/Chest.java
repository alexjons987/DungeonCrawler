import java.util.ArrayList;
import java.util.Random;

public class Chest {

    private String name;
    private int cost;
    private Item item;
    private boolean isLooted = false;

    public Chest(ArrayList<Item> itemTemplates) {
        Random random = new Random();

        switch (random.nextInt(1, 5 + 1)) {
            case 1:
                this.name = "Oak Chest"; // Poor - Epic
                this.cost = 5;
                this.item = rollItem(itemTemplates, 100, 200, 100, 50, 25, 0, 0, 0);
                break;
            case 2:
                this.name = "Reinforced Oak Chest"; // Common - Legendary
                this.cost = 6;
                this.item = rollItem(itemTemplates, 0, 215, 120, 70, 45, 20, 0, 0);
                break;
            case 3:
                this.name = "Bronze Ornate Chest"; // Uncommon - Legendary
                this.cost = 7;
                this.item = rollItem(itemTemplates, 0, 0, 160, 105, 75, 45, 0, 0);
                break;
            case 4:
                this.name = "Lion's Head Chest"; // Rare - Unique
                this.cost = 10;
                this.item = rollItem(itemTemplates, 0, 0, 0, 200, 125, 60, 20, 0);
                break;
            case 5:
                this.name = "Golden Chest"; // Epic - Unique
                this.cost = 15;
                this.item = rollItem(itemTemplates, 0, 0, 0, 0, 200, 100, 35, 0);
                break;
        }
    }

    public Chest(String name, int cost, Item item) {
        this.name = name;
        this.cost = cost;
        this.item = item;
    }

    public int getCost() {
        return this.cost;
    }

    public boolean isLooted() {
        return this.isLooted;
    }

    public Item openChest(int playerResourcefulness) {
        if (cost > playerResourcefulness) {
            // Player cannot afford to open
            throw new IllegalStateException("Player current Resourcefulness is too low");
        } else if (isLooted) {
            // Cannot open something that is already opened
            throw new IllegalStateException("Chest has already been opened");
        }

        this.isLooted = true;
        return this.item;
    }

    private Item rollItem(
            ArrayList<Item> itemTemplates,
            int poorTickets,
            int commonTickets,
            int uncommonTickets,
            int rareTickets,
            int epicTickets,
            int legendaryTickets,
            int uniqueTickets,
            int artifactTickets // TODO: Implement Artifacts
    ) {
        int[] weights = {poorTickets, commonTickets, uncommonTickets, rareTickets,
                epicTickets, legendaryTickets, uniqueTickets, artifactTickets};
        Item.Rarity[] rarities = Item.Rarity.getAllRarities();
        Item randomItem = getRandomItem(itemTemplates, weights, rarities);

        return switch(randomItem) {
            case Armor a -> new Armor((Armor) randomItem);
            case Weapon w -> new Weapon((Weapon) randomItem);
            default -> throw new IllegalStateException("Unexpected item: " + randomItem);
        };
    }

    private static Item getRandomItem(ArrayList<Item> itemTemplates, int[] weights, Item.Rarity[] rarities) {
        Random random = new Random();

        int totalTickets = 0;
        for (int weight : weights)
            totalTickets += weight;

        int winningTicket = random.nextInt(totalTickets + 1);

        int winningIndex = 0;
        for (int weight : weights) {
            if (weight <= 0) {
                winningIndex++;
                continue;
            }
            if (winningTicket < weight) {
                break;
            }
            winningTicket -= weight;
            winningIndex++;
        }
        Item.Rarity winningRarity = rarities[winningIndex];

        Item randomItem = itemTemplates.get(random.nextInt(0, itemTemplates.size()));
        Item randomItemCopy = switch(randomItem) {
            case Armor a -> new Armor((Armor) randomItem);
            case Weapon w -> new Weapon((Weapon) randomItem);
            default -> throw new IllegalStateException("Unexpected item: " + randomItem);
        };
        randomItemCopy.increaseRarity(winningRarity);
        System.out.printf("Chose %s and generated %s%n", randomItem, randomItemCopy);

        return randomItemCopy;
    }

    public String toString() {
        return String.format("%s (%d)", this.name, this.cost);
    }
}
