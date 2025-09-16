import java.util.ArrayList;
import java.util.Random;

public class Chest {
    // Oak Chest - 5 RES
    // Reinforced Oak Chest - 6 RES
    // Bronze Ornate Chest - 7 RES
    // Lion's Head Chest - 10 RES
    // Golden Chest - 15 RES
    // Marvelous Chest - 5 RES (on defeating boss)

    String name;
    int cost;
    ArrayList<Item> possibleItems;

    public Chest(String name, int cost, ArrayList<Item> possibleItems) {
        this.name = name;
        this.cost = cost;
        this.possibleItems = possibleItems;
    }

    // TODO: Re-write and implement
    public Item openChest(Player player, Item.Rarity lowesRarity, Item.Rarity highestRarity) {
        Random random = new Random();

        // Roll item that is found in chest
        Item item = this.possibleItems.get(random.nextInt(this.possibleItems.size()));

        // Roll random rarity for found item
        Item.Rarity rarity = item.getRandomRarity(lowesRarity, highestRarity);

        // Set rolled rarity for item
        item.increaseRarity(rarity);

        return item;
    }
}
