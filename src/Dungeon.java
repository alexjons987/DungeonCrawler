import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.*;

public class Dungeon {

    public enum DungeonPreset {
        RUINS,
        CRYPTS,
        INFERNO
    }

    private String name;
    private ArrayList<Item> items;
    private ArrayList<Mob> mobs;
    private ArrayList<Chest> chests;

    // Altars?

    public Dungeon(DungeonPreset preset) {

        this.items = new ArrayList<Item>();
        this.mobs = new ArrayList<Mob>();
        this.chests = new ArrayList<Chest>();

        switch (preset) {
            case RUINS:
                this.name = "Ruins";
                try {
                    // Items
                    this.items = generateStandardItemSet();
                    // Mobs

                    // Chests
                } catch (Exception e) {
                    System.out.println("Unable to generate Dungeon content: " + e);
                }
                break;
            case CRYPTS:
                this.name = "Crypts";
                break;
            case INFERNO:
                this.name = "Inferno";
                this.items.addAll(generateArtifactItemSet());
                break;
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown DungeonPreset given for Dungeon(): %s", preset)
                );
        }
    }

    private ArrayList<Item> generateStandardItemSet() throws IOException {

        ArrayList<Item> itemList = new ArrayList<Item>();

        String itemDataContent = new String(Files.readAllBytes(Paths.get("data/items.json")));

        JSONArray weapons = new JSONObject(itemDataContent).getJSONArray("weapons");
        for (Object obj : weapons) {
            JSONObject weapon = (JSONObject) obj;

            String weaponName = weapon.getString("name");
            Item.Rarity weaponRarity = weapon.getEnum(Item.Rarity.class, "rarity");
            Weapon.WeaponType weaponType = weapon.getEnum(Weapon.WeaponType.class, "weaponType");
            Weapon.HandType weaponHandType = weapon.getEnum(Weapon.HandType.class, "handType");
            int basePhysDmg = weapon.getInt("basePhysWeaponDmg");
            int baseMagDmg = weapon.getInt("baseMagWeaponDmg");

            itemList.add(
                    new Weapon(weaponName, weaponRarity, weaponType, weaponHandType, basePhysDmg, baseMagDmg)
            );
        }

        JSONArray armors = new JSONObject(itemDataContent).getJSONArray("armors");
        for (Object obj : armors) {
            JSONObject armor = (JSONObject) obj;

            String armorName = armor.getString("name");
            Item.Rarity armorRarity = armor.getEnum(Item.Rarity.class, "rarity");
            Armor.ArmorType armorType = armor.getEnum(Armor.ArmorType.class, "armorType");
            Armor.ArmorEquipRegion armorEquipRegion = armor.getEnum(Armor.ArmorEquipRegion.class, "armorEquipRegion");
            int armorBaseDefense = armor.getInt("baseDefense");
            int strength = armor.getInt("strength");
            int vigor = armor.getInt("vigor");
            int agility = armor.getInt("agility");
            int dexterity = armor.getInt("dexterity");
            int will = armor.getInt("will");
            int knowledge = armor.getInt("knowledge");
            int resourcefulness = armor.getInt("resourcefulness");

            itemList.add(
                    new Armor(armorName, armorRarity, armorType, armorEquipRegion, armorBaseDefense, strength, vigor,
                            agility, dexterity, will, knowledge, resourcefulness)
            );
        }

        return itemList;
    }

    private ArrayList<Item> generateArtifactItemSet() {
        // TODO: Add artifact items
        return new ArrayList<>();
    }
}

// Name
// Loot table
// Loot rarity chances

// Enemies found in dungeon
// Enemy encounter chances (normal > subboss)

// Chest class
// chest types in dungeon
// chest type chances
