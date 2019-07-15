package models;

public interface ClassHero {

    public void setName(String name);

    public String getName();

    public void setAttack(int attack);

    public int getAttack();

    public void setDefense(int defense);

    public int getDefense();

    public void setHitPoints(int hitPoints);

    public int getHitPoints();

    public String getClassOfHero();

    public void setClassOfHero(String classOfHero);

    public void setLevel(int level);

    public int getLevel();

    public void setExperience(int experience);

    public int getExperience();

    public void setCoordinates(Coordinates coordinates);

    public Coordinates getCoordinates();

    public void setCurrentExperience(int experience);

    public int getCurrentExperience();

    public void levelUp();

    public void setCurrentHP(int currentHP);

    public int getCurrentHP();

    public int attack();
}