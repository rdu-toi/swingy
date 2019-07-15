package models;

import java.util.Random;

public class Character {
    private int attack;
    private int defense;
    private int hitPoints;
    private String name;
    private Coordinates coordinates;
    private int experience;
    double dodgePercent;

    Random rand = new Random();

    public Character() {
    }

    public Character(String name, int attack, int defense, int hitPoints) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.hitPoints = hitPoints;
        this.coordinates = new Coordinates();
        this.experience = 1000;
    }

    public Character(String name, int attack, int defense, int hitPoints, Coordinates coordinates, int experience) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.hitPoints = hitPoints;
        this.coordinates = coordinates;
        this.experience = experience;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getAttack() {
        return attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getDefense() {
        return defense;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getExperience() {
        return experience;
    }

    public int attack() {
        int low = (30 * getAttack()) / 100;
        return rand.nextInt(getAttack() - low) + low;
    }

}
