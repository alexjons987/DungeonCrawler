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

    private String dungeonDisplayName;
    private ArrayList<Weapon> dungeonWeaponSet;
    private ArrayList<Armor> dungeonArmorSet;
    private ArrayList<Item> dungeonItemSet; // Items is a combination of both weapons and armor
    private ArrayList<Mob> dungeonMobSet;
    private ArrayList<Module> dungeonModules; // Modules are dungeon rooms/areas

    // Altars?

    public Dungeon(DungeonPreset preset) {

        this.dungeonWeaponSet = new ArrayList<Weapon>();
        this.dungeonArmorSet = new ArrayList<Armor>();
        this.dungeonItemSet = new ArrayList<Item>();
        this.dungeonMobSet = new ArrayList<Mob>();
        this.dungeonModules = new ArrayList<Module>();

        switch (preset) {
            case RUINS:
                this.dungeonDisplayName = "Ruins of the Forgotten Castle";
                try {
                    // Items
                    this.dungeonItemSet = generateStandardItemSet();
                    // Mobs
                    this.dungeonMobSet = generateMobSet(preset);
                    // Modules
                    this.dungeonModules = generateDungeonModules(10);
                } catch (Exception e) {
                    System.out.println("Unable to generate Dungeon content: " + e);
                }
                break;
            case CRYPTS:
                this.dungeonDisplayName = "Crypts";
                break;
            case INFERNO:
                this.dungeonDisplayName = "Inferno";
                // this.items.addAll(generateArtifactItemSet());
                break;
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown DungeonPreset given for Dungeon(): %s", preset)
                );
        }
    }

    public Module getModule(int i) {
        return this.dungeonModules.get(i);
    }

    public ArrayList<Module> getModules() {
        return this.dungeonModules;
    }

    // TODO: Fix so func does not alter other lists other than returned list
    private ArrayList<Item> generateStandardItemSet() throws IOException {

        ArrayList<Item> itemList = new ArrayList<Item>();

        String itemDataContent = new String(Files.readAllBytes(Paths.get("data/items.json")));

        JSONArray weapons = new JSONObject(itemDataContent).getJSONArray("weapons");
        for (Object obj : weapons) {
            JSONObject weapon = (JSONObject) obj;

            String weaponName = weapon.getString("name");
            Item.Rarity weaponRarity = Item.Rarity.POOR; // Default rarity to POOR
            Weapon.WeaponType weaponType = weapon.getEnum(Weapon.WeaponType.class, "weaponType");
            Weapon.HandType weaponHandType = weapon.getEnum(Weapon.HandType.class, "handType");
            int basePhysDmg = weapon.getInt("basePhysWeaponDmg");
            int baseMagDmg = weapon.getInt("baseMagWeaponDmg");

            Weapon generatedWeapon = new Weapon(weaponName, weaponRarity, weaponType, weaponHandType,
                    basePhysDmg, baseMagDmg);
            this.dungeonWeaponSet.add(generatedWeapon);
            itemList.add(generatedWeapon);
        }

        JSONArray armors = new JSONObject(itemDataContent).getJSONArray("armors");
        for (Object obj : armors) {
            JSONObject armor = (JSONObject) obj;

            String armorName = armor.getString("name");
            Item.Rarity armorRarity = Item.Rarity.POOR; // Default rarity to POOR
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
            this.dungeonArmorSet.add(generatedArmor);
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
                this.dungeonDisplayName = "Crypts";
                break;
            case INFERNO:
                this.dungeonDisplayName = "Inferno";
                break;
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown DungeonPreset given for generateMobSet(): %s", preset)
                );
        }
        throw new IllegalStateException("Fell out of switch-case -> generateMobSet()");
    }

    private ArrayList<Module> generateDungeonModules(int moduleCount) { // Unfinished function

        Random random = new Random();
        ArrayList<Module> modules = new ArrayList<Module>();

        for (int i = 0; i < moduleCount; i++) {

            // Pick a random mob
            Mob mob = new Mob(dungeonMobSet.get(random.nextInt(0, dungeonMobSet.size())));

            // Add random chests
            int chestsToAdd = random.nextInt(1, 4 + 1);
            ArrayList<Chest> chests = new ArrayList<Chest>();
            for (int j = 0; j < chestsToAdd; j++) {
                chests.add(new Chest(this.dungeonItemSet));
            }

            // Create new Module
            Module module = new Module(mob, chests);

            // Add to modules list
            modules.add(module);
        }

        return modules;
    }

    public Item generateRandomDungeonItem() {
        Random random = new Random();
        Item randomItem = this.dungeonItemSet.get(random.nextInt(0, this.dungeonItemSet.size()));
        if (randomItem.getClass() == Armor.class)
            return new Armor((Armor) randomItem);
        else if (randomItem.getClass() == Weapon.class)
            return new Weapon((Weapon) randomItem);

        throw new IllegalStateException("Unable to generate a random dungeon item generateRandomDungeonItem()");
    }

    public Weapon generateRandomDungeonWeapon() {
        Random random = new Random();
        Weapon randomWeapon = this.dungeonWeaponSet.get(random.nextInt(0, this.dungeonWeaponSet.size()));
        Weapon randomWeaponCopy = new Weapon(randomWeapon);
        return new Weapon(randomWeaponCopy);
    }

    public Weapon generateRandomDungeonWeapon(Item.Rarity rarity) {
        Random random = new Random();
        Weapon randomWeapon = this.dungeonWeaponSet.get(random.nextInt(0, this.dungeonWeaponSet.size()));
        Weapon randomWeaponCopy = new Weapon(randomWeapon);
        randomWeaponCopy.increaseRarity(rarity);
        return new Weapon(randomWeaponCopy);
    }

    public Armor generateRandomDungeonArmor() {
        Random random = new Random();
        Armor randomArmor = this.dungeonArmorSet.get(random.nextInt(0, this.dungeonArmorSet.size()));
        Armor randomArmorCopy = new Armor(randomArmor);
        return new Armor(randomArmor);
    }

    public Armor generateRandomDungeonArmor(Item.Rarity rarity) {
        Random random = new Random();
        Armor randomArmor = this.dungeonArmorSet.get(random.nextInt(0, this.dungeonArmorSet.size()));
        Armor randomArmorCopy = new Armor(randomArmor);
        randomArmorCopy.increaseRarity(rarity);
        return new Armor(randomArmor);
    }

    public String toString() {
        return String.format(
                "\"%s\" with %d modules containing %d possible items, %d possible mobs",
                this.dungeonDisplayName,
                this.dungeonModules.size(),
                this.dungeonItemSet.size(),
                this.dungeonMobSet.size()
        );
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
