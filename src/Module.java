import java.util.ArrayList;

public class Module {
    // TODO: Add module name
    private final Mob mob;
    private final ArrayList<Chest> chests;

    // Constructors
    public Module(Mob mob, ArrayList<Chest> chests) {
        this.mob = mob;
        this.chests = chests;
    }

    // Properties
    public Mob getMob() {
        return this.mob;
    }

    public ArrayList<Chest> getChests() {
        return this.chests;
    }

    public ArrayList<Chest> getUnopenedChests() {
        ArrayList<Chest> unopenedChests = new ArrayList<>();
        for (Chest chest : this.chests)
            if (!chest.isLooted())
                unopenedChests.add(chest);

        return unopenedChests;
    }
}
