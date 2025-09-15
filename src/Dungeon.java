import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import org.json.*;

public class Dungeon {

    public enum DungeonPreset {
        RUINS,
        CRYPTS,
        INFERNO
    }

    private String displayName;
    private ArrayList<Weapon> dungeonWeapons;
    private ArrayList<Armor> dungeonArmor;
    private ArrayList<Item> dungeonItems; // Items is a combination of both weapons and armor
    private ArrayList<Mob> mobs;
    private ArrayList<Chest> chests;

    // Altars?

    public Dungeon(DungeonPreset preset) {

        this.dungeonWeapons = new ArrayList<Weapon>();
        this.dungeonArmor = new ArrayList<Armor>();
        this.dungeonItems = new ArrayList<Item>();
        this.mobs = new ArrayList<Mob>();
        this.chests = new ArrayList<Chest>();

        switch (preset) {
            case RUINS:
                this.displayName = "Ruins of the Forgotten Castle";
                try {
                    // Items
                    this.dungeonItems = generateStandardItemSet();
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

            Weapon generatedWeapon = new Weapon(weaponName, weaponRarity, weaponType, weaponHandType,
                    basePhysDmg, baseMagDmg);
            this.dungeonWeapons.add(generatedWeapon);
            itemList.add(generatedWeapon);
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

            Armor generatedArmor = new Armor(armorName, armorRarity, armorType, armorEquipRegion,
                    armorBaseDefense, strength, vigor, agility, dexterity, will, knowledge, resourcefulness);
            this.dungeonArmor.add(generatedArmor);
            itemList.add(generatedArmor);
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

    // TODO: Implement generateChestSet()
    private ArrayList<Chest> generateChestSet(DungeonPreset preset) {
        return new ArrayList<Chest>();
    }

    public Item generateRandomDungeonItem() {
        Random random = new Random();
        Item randomItem = this.dungeonItems.get(random.nextInt(0, this.dungeonItems.size()));
        if (randomItem.getClass() == Armor.class)
            return new Armor((Armor) randomItem);
        else if (randomItem.getClass() == Weapon.class)
            return new Weapon((Weapon) randomItem);

        throw new IllegalStateException("Unable to generate a random dungeon item generateRandomDungeonItem()");
    }

    public Weapon generateRandomDungeonWeapon() {
        Random random = new Random();
        Weapon randomWeapon = this.dungeonWeapons.get(random.nextInt(0, this.dungeonWeapons.size()));
        return new Weapon(randomWeapon);
    }

    public Armor generateRandomDungeonArmor() {
        Random random = new Random();
        Armor randomArmor = this.dungeonArmor.get(random.nextInt(0, this.dungeonArmor.size()));
        return new Armor(randomArmor);
    }


    public String toString() {
        return String.format("\"%s\" with %d possible items, %d different mobs and %d chest types.", this.displayName, this.dungeonItems.size(), this.mobs.size(), this.chests.size());
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
