public class Stats {
    private int strength;
    private int vigor;
    private int agility;
    private int dexterity;
    private int will;
    private int knowledge;
    private int resourcefulness;

    public Stats() {
        this.strength = 15;
        this.vigor = 15;
        this.agility = 15;
        this.dexterity = 15;
        this.will = 15;
        this.knowledge = 15;
        this.resourcefulness = 15;
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

    public int getVigor() {
        return this.vigor;
    }

    public int getAgility() {
        return this.agility;
    }

    public int getDexterity() {
        return this.dexterity;
    }

    public int getWill() {
        return this.will;
    }

    public int getKnowledge() {
        return this.knowledge;
    }

    public int getResourcefulness() {
        return this.resourcefulness;
    }

    public int getMaxHP() {
        return (int) ((float) ((float) this.strength * 0.25f + (float) this.vigor * 0.75f) * 2) + 70;
    }

    public float getActionSpeed() {
        float actionSpeed = (this.agility * 0.25f + this.dexterity * 0.75f) / 15.0f;
        return (float) Math.pow(actionSpeed, 2) - 1.0f; // Scale action speed quadtraticly
    }
}
