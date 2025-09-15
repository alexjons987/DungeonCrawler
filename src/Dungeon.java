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

    private String displayName;
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
                this.displayName = "Ruins of the Forgotten Castle";
                try {
                    // Items
                    this.items = generateStandardItemSet();
                    // Mobs
                    this.mobs = generateMobSet(preset);
                    // Chests
                    this.chests = new ArrayList<Chest>(); // TODO: Add generateChestSet()
                } catch (Exception e) {
                    System.out.println("Unable to generate Dungeon content: " + e);
                }
                break;
            case CRYPTS:
                this.displayName = "Crypts";
                break;
            case INFERNO:
                this.displayName = "Inferno";
                // this.items.addAll(generateArtifactItemSet());
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
        // TODO: Add artifact items + artifact effects
        return new ArrayList<>();
    }

    private ArrayList<Mob> generateMobSet(DungeonPreset preset) throws IOException {
        ArrayList<Mob> mobList = new ArrayList<Mob>();
        String mobContent = new String(Files.readAllBytes(Paths.get("data/mobs.json")));
        switch (preset) {
            case RUINS:
                try {
                    JSONArray ruinsMobs = new JSONObject(mobContent).getJSONArray("ruins");
                    for (Object obj : ruinsMobs) {
                        JSONObject mob = (JSONObject) obj;

                        String mobName = mob.getString("name");
                        Mob.MobTier mobTier = mob.getEnum(Mob.MobTier.class, "tier");
                        int mobHealth = mob.getInt("health");
                        int mobDamage = mob.getInt("damage");
                        int mobAttackTimer = mob.getInt("attackTimer");
                        int mobHitRate = mob.getInt("hitRate");

                        mobList.add(
                                new Mob(mobName, mobTier, mobHealth, mobDamage, mobAttackTimer, mobHitRate)
                        );
                    }
                    return mobList;
                } catch (Exception e) {
                    System.out.println("Unable to generate Mob set: " + e);
                }
                break;
            case CRYPTS:
                this.displayName = "Crypts";
                break;
            case INFERNO:
                this.displayName = "Inferno";
                break;
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown DungeonPreset given for generateMobSet(): %s", preset)
                );
        }
        throw new IllegalStateException("Fell out of switch-case -> generateMobSet()");
    }

    public String toString() {
        return String.format("\"%s\" contains %d possible items, %d different mobs and %d chest types", this.displayName, this.items.size(), this.mobs.size(), this.chests.size());
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
