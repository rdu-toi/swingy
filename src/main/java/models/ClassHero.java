package models;

public interface ClassHero {

    public String getName();

    public int getAttack();

    public int getDefense();

    public int getHitPoints();

    public String getClassOfHero();

    public int getLevel();

    public int getExperience();

    public void setCoordinates(Coordinates coordinates);

    public Coordinates getCoordinates();

    public void setCurrentExperience(int experience);

    public int getCurrentExperience();

    public void levelUp();

    public void setCurrentHP(int currentHP);

    public int getCurrentHP();

    public int attack();

    public void setArtifact(Artifact artifact);

    public Artifact getWeaponArtifact();

    public Artifact getHelmArtifact();

    public Artifact getArmourArtifact();

    public int getCurrentHPPlus();

    public int getAttackPlus();

    public int getDefensePlus();

}