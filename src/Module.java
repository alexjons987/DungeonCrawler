import java.util.ArrayList;

public class Module {
    // TODO: Add module name
    private Mob mob;
    private ArrayList<Chest> chests;

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
}
