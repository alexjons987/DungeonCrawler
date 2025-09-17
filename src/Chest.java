import java.util.ArrayList;
import java.util.Random;

public class Chest {

    private String name;
    private int cost;
    private Item item;

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
                this.item = rollItem(itemTemplates, 0, 0, 0, 150, 135, 95, 20, 0);
                break;
            case 5:
                this.name = "Golden Chest"; // Epic - Unique
                this.cost = 15;
                this.item = rollItem(itemTemplates, 0, 0, 0, 0, 200, 150, 40, 0);
                break;
        }
    }

    public Chest(String name, int cost, Item item) {
        this.name = name;
        this.cost = cost;
        this.item = item;
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
            int artifactTickets
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

        return randomItemCopy;
    }
}
