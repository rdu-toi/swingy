package models;

public class Hero extends Character {
    private String classOfHero;
    private int level;
    private int currentExperience;
    private int currentHP;

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
        return currentHP;
    }

    public void levelUp() {
        if (this.currentExperience < this.getExperience())
            return;
        this.level += 1;
        this.setAttack(this.getAttack() + 15);
        this.setDefense(this.getDefense() + 15);
        this.setHitPoints(this.getHitPoints() + 15);
        this.currentHP = this.getHitPoints();
        int xpPoints = (this.level * 1000) + ((this.level - 1) * 2 * 450);
        this.setExperience(xpPoints);
    }

}