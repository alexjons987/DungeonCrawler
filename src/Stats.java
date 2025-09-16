import java.util.HashMap;

public class Stats {
    private int strength;
    private int vigor;
    private int agility;
    private int dexterity;
    private int will;
    private int knowledge;
    private int resourcefulness;

    public Stats() {
        
    }

    public Stats(int strength, int vigor, int agility, int dexterity, int will, int knowledge, int resourcefulness) {
        this.strength = strength;
        this.vigor = vigor;
        this.agility = agility;
        this.dexterity = dexterity;
        this.will = will;
        this.knowledge = knowledge;
        this.resourcefulness = resourcefulness;
    }

    public int getStrength() {
        return this.strength;
    }

    public void setStrength(int n) {
        this.strength = n;
    }

    public int getVigor() {
        return this.vigor;
    }

    public void setVigor(int n) {
        this.vigor = n;
    }

    public int getAgility() {
        return this.agility;
    }

    public void setAgility(int n) {
        this.agility = n;
    }

    public int getDexterity() {
        return this.dexterity;
    }

    public void setDexterity(int n) {
        this.dexterity = n;
    }

    public int getWill() {
        return this.will;
    }

    public void setWill(int n) {
        this.will = n;
    }

    public int getKnowledge() {
        return this.knowledge;
    }

    public void setKnowledge(int n) {
        this.knowledge = n;
    }

    public int getResourcefulness() {
        return this.resourcefulness;
    }

    public void setResourcefulness(int n) {
        this.resourcefulness = n;
    }

    public HashMap<String, Integer> getAllAttributes() {
        HashMap<String, Integer> hmStats = new HashMap<String, Integer>();
        hmStats.put("strength", this.getStrength());
        hmStats.put("vigor", this.getVigor());
        hmStats.put("agility", this.getAgility());
        hmStats.put("dexterity", this.getDexterity());
        hmStats.put("will", this.getWill());
        hmStats.put("knowledge", this.getKnowledge());
        hmStats.put("resourcefulness", this.getResourcefulness());
        return hmStats;
    }

    public int getMaxHP() {
        return (int) ((float) this.strength * 0.25f + (float) this.vigor * 0.75f) * 2 + 70;
    }

    public float getActionSpeed() {
        float actionSpeed = (this.agility * 0.25f + this.dexterity * 0.75f) / 15.0f;
        return (float) Math.pow(actionSpeed, 3) - 1.0f;
    }
}
