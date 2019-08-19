package models;

import java.util.Random;

public class Villian extends Character {

    private Artifact artifact;

    private static Random rand = new Random();
    private static String[] villianNames = {"Tyrannosaura Wrecks", "Bewarewolf", "Captain Crunch", "Chewbacca", "Artichoker", "Cruelcumber", "Boaty Mcboatface", "Paul", "Mr. Poopy Head"};

    public Villian() {
        super();
    }

    public Villian(String name, int attack, int defense, int hitPoints, Coordinates coordinates, int experience) {
        super(name, attack, defense, hitPoints, coordinates, experience);
    }

    public static Villian newVillian(int heroLevel, int x, int y) {
        int highestAttack = 55 + (17 * (heroLevel - 1));
        int highestDefense = 30 + (17 * (heroLevel - 1));
        int highestHP = 180 + (17 * (heroLevel - 1));
        int highestXP = ((heroLevel * 1000) + ((heroLevel - 1) * 2 * 450)) / (20 + ((heroLevel - 1) * 20));
        int lowestXP = (85 * highestXP) / 100;
        int finalXP = rand.nextInt(highestXP - lowestXP) + lowestXP;
        int finalAttack = (85 * highestAttack) / 100;
        int finalDefense = (85 * highestDefense) / 100;
        int finalHP = (85 * highestHP) / 100;
        Coordinates coordinates = new Coordinates(x, y);
        int randomElement = rand.nextInt(villianNames.length);
        return new Villian(villianNames[randomElement], finalAttack, finalDefense, finalHP, coordinates, finalXP);
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    public Artifact getArtifact() {
        return artifact;
    }

}
