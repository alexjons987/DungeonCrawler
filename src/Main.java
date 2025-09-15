import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // test();
        DungeonCrawler dc = new DungeonCrawler();
        dc.startGame();
    }

    static void test() {
        ArrayList<Weapon> weapons = new ArrayList<Weapon>();
        ArrayList<Armor> armors = new ArrayList<Armor>();
        
        // Generate some weapons
        weapons.add(new Weapon("Longsword", Item.Rarity.POOR, Weapon.WeaponType.SWORD, Weapon.HandType.TWO_HANDED, 38, 0));
        weapons.add(new Weapon("Longsword", Item.Rarity.COMMON, Weapon.WeaponType.SWORD, Weapon.HandType.TWO_HANDED, 38, 0));
        weapons.add(new Weapon("Longsword", Item.Rarity.UNCOMMON, Weapon.WeaponType.SWORD, Weapon.HandType.TWO_HANDED, 38, 0));
        weapons.add(new Weapon("Longsword", Item.Rarity.RARE, Weapon.WeaponType.SWORD, Weapon.HandType.TWO_HANDED, 38, 0));
        weapons.add(new Weapon("Longsword", Item.Rarity.EPIC, Weapon.WeaponType.SWORD, Weapon.HandType.TWO_HANDED, 38, 0));
        weapons.add(new Weapon("Longsword", Item.Rarity.LEGENDARY, Weapon.WeaponType.SWORD, Weapon.HandType.TWO_HANDED, 38, 0));
        weapons.add(new Weapon("Longsword", Item.Rarity.UNIQUE, Weapon.WeaponType.SWORD, Weapon.HandType.TWO_HANDED, 38, 0));
        weapons.add(new Weapon("Fulgor", Item.Rarity.ARTIFACT, Weapon.WeaponType.SWORD, Weapon.HandType.TWO_HANDED, 38, 0));
        weapons.add(new Weapon("Crystal Sword", Item.Rarity.POOR, Weapon.WeaponType.SWORD, Weapon.HandType.TWO_HANDED, 13, 14));
        weapons.add(new Weapon("Rapier", Item.Rarity.POOR, Weapon.WeaponType.SWORD, Weapon.HandType.MAIN_HAND, 20, 0));
        weapons.add(new Weapon("Arming Sword", Item.Rarity.POOR, Weapon.WeaponType.SWORD, Weapon.HandType.MAIN_HAND, 27, 0));
        weapons.add(new Weapon("Short Sword", Item.Rarity.POOR, Weapon.WeaponType.SWORD, Weapon.HandType.OFF_HAND, 23, 0));
        weapons.add(new Weapon("Double Axe", Item.Rarity.POOR, Weapon.WeaponType.AXE, Weapon.HandType.TWO_HANDED, 51, 0));
        weapons.add(new Weapon("Bardiche", Item.Rarity.POOR, Weapon.WeaponType.AXE, Weapon.HandType.TWO_HANDED, 54, 0));
        weapons.add(new Weapon("Bardiche", Item.Rarity.EPIC, Weapon.WeaponType.AXE, Weapon.HandType.TWO_HANDED, 54, 0));
        weapons.add(new Weapon("Morning Star", Item.Rarity.POOR, Weapon.WeaponType.MACE, Weapon.HandType.MAIN_HAND, 32, 0));
        weapons.add(new Weapon("Flanged Mace", Item.Rarity.POOR, Weapon.WeaponType.MACE, Weapon.HandType.MAIN_HAND, 31, 0));
        weapons.add(new Weapon("War Hammer", Item.Rarity.POOR, Weapon.WeaponType.MACE, Weapon.HandType.MAIN_HAND, 33, 0));
        weapons.add(new Weapon("Club", Item.Rarity.POOR, Weapon.WeaponType.MACE, Weapon.HandType.MAIN_HAND, 28, 0));
        weapons.add(new Weapon("War Maul", Item.Rarity.POOR, Weapon.WeaponType.MACE, Weapon.HandType.TWO_HANDED, 50, 0));
        weapons.add(new Weapon("Rondel Dagger", Item.Rarity.POOR, Weapon.WeaponType.DAGGER, Weapon.HandType.MAIN_HAND, 14, 0));
        weapons.add(new Weapon("Kris Dagger", Item.Rarity.POOR, Weapon.WeaponType.DAGGER, Weapon.HandType.MAIN_HAND, 15, 0));
        weapons.add(new Weapon("Stiletto Dagger", Item.Rarity.POOR, Weapon.WeaponType.DAGGER, Weapon.HandType.OFF_HAND, 16, 0));
        weapons.add(new Weapon("Castillon Dagger", Item.Rarity.POOR, Weapon.WeaponType.DAGGER, Weapon.HandType.OFF_HAND, 19, 0));

        // Generate some armor
        armors.add(new Armor("Templar Armor", Item.Rarity.POOR, Armor.ArmorType.PLATE, Armor.ArmorEquipRegion.CHEST, 95, 0, 0, 0, 0, 2, 0, 0));
        armors.add(new Armor("Templar Armor", Item.Rarity.COMMON, Armor.ArmorType.PLATE, Armor.ArmorEquipRegion.CHEST, 95, 0, 0, 0, 0, 2, 0, 0));
        armors.add(new Armor("Dark Cuirass", Item.Rarity.POOR, Armor.ArmorType.PLATE, Armor.ArmorEquipRegion.CHEST, 122, 1, 0, 0, 0, 1, 0, 2));
        armors.add(new Armor("Wanderer Attire", Item.Rarity.POOR, Armor.ArmorType.CLOTH, Armor.ArmorEquipRegion.CHEST, 37, 1, 0, 0, 2, 0, 0, 0));
        armors.add(new Armor("Pourpoint", Item.Rarity.POOR, Armor.ArmorType.CLOTH, Armor.ArmorEquipRegion.CHEST, 62, 0, 0, 0, 0, 1, 1, 2));
        // armors.add(new Armor("Tanglethread Trousers", Item.Rarity.EPIC, Armor.ArmorType.LEATHER, Armor.ArmorEquipRegion.CHEST, 66, 0, 5, 0, 0, 0, 0, 0));

        // Print weapons
        for (Weapon weapon : weapons)
            System.out.println(weapon.toString());

        System.out.println();
            
        // Print armors
        for (Armor armor : armors)
            System.out.println(armor.toString());

        // Player with base stats:
        Player player = new Player();
        System.out.println("- Fighter - (base stats)");
        System.out.println(player.toString() + "\n");

        // Player barbarian stats:
        Player barbarian = new Player(20, 25, 13, 12, 18, 5, 12);
        System.out.println("- Barbarian -");
        System.out.println(barbarian.toString() + "\n");

        // Player rogue stats:
        Player rogue = new Player(9, 6, 25, 20, 10, 10, 25);
        System.out.println("- Rogue -");
        System.out.println(rogue.toString() + "\n");

        // Sword change rarity
        Weapon someWeapon = new Weapon("Generic Magic Sword", Item.Rarity.RARE, Weapon.WeaponType.SWORD, Weapon.HandType.TWO_HANDED, 20, 21);
        System.out.println(someWeapon.toString());
        someWeapon.setRarity(Item.Rarity.EPIC);
        System.out.println("someWeapon.setRarity(Item.Rarity.EPIC)");
        System.out.println(someWeapon.toString());

        Dungeon dung = new Dungeon(Dungeon.DungeonPreset.RUINS);
    }
}
