package models;

public class Hero extends Character {
    private String classOfHero;
    private int level;
    private int currentExperience;
    private int currentHP;
    private Artifact weaponArtifact;
    private Artifact helmArtifact;
    private Artifact armourArtifact;

    protected Hero(String name, int attack, int defense, int hitPoints, String classOfHero) {
        super(name, attack, defense, hitPoints);
        this.level = 1;
        this.currentExperience = 0;
        this.classOfHero = classOfHero;
        this.currentHP = hitPoints;
    }

    public void setClassOfHero(String classOfHero) {
        this.classOfHero = classOfHero;
    }

    public String getClassOfHero() {
        return classOfHero;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setCurrentExperience(int experience) {
        this.currentExperience = experience;
    }

    public int getCurrentExperience() {
        return currentExperience;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public int getCurrentHP() {
        return this.currentHP;
    }

    public int getCurrentHPPlus() {
        return this.getHitPoints() + this.helmArtifact.getArtifactIncrease();
    }

    public int getAttackPlus() {
        return this.getAttack() + this.weaponArtifact.getArtifactIncrease();
    }

    public int getDefensePlus() {
        return this.getDefense() + this.armourArtifact.getArtifactIncrease();
    }

    public void levelUp() {
        while (this.currentExperience > this.getExperience()) {
            this.level += 1;
            this.setAttack(this.getAttack() + 15);
            this.setDefense(this.getDefense() + 15);
            this.setHitPoints(this.getHitPoints() + 15);
            this.currentHP = this.getHitPoints();
            int xpPoints = (this.level * 1000) + ((this.level - 1) * 2 * 450);
            this.setExperience(xpPoints);
        }
        return;
    }

    public void setArtifact(Artifact artifact) {
        String type = artifact.getArtifactType();
        if (type.equals("sword") || type.equals("bow") || type.equals("staff"))
            this.weaponArtifact = artifact;
        else if (type.equals("helm"))
            this.helmArtifact = artifact;
        if (type.equals("armour"))
            this.armourArtifact = artifact;
    }

    public Artifact getWeaponArtifact() {
        return weaponArtifact;
    }

    public Artifact getHelmArtifact() {
        return helmArtifact;
    }

    public Artifact getArmourArtifact() {
        return armourArtifact;
    }

    public int attack() {
        int attack = getAttack();
        if (this.weaponArtifact != null)
            attack = getAttackPlus();
        int low = (30 * attack) / 100;
        return rand.nextInt(attack - low) + low;
    }

    public int defense() {
        double chance = rand.nextDouble();
        if (chance <= .20)
            return -1;
        int defense = getDefense();
        if (this.armourArtifact != null)
            defense = getDefensePlus();
        int low = (70 * defense) / 100;
        return rand.nextInt(defense - low) + low;
    }
}