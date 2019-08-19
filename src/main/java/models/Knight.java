package models;

public class Knight extends Hero implements ClassHero {

    public Knight(String name) {
        super(name, 65, 30, 430, "Knight");
    }

    public Knight(String name, String currentXp, String weapon, String armour, String helm) {
        super(name, 65, 30, 430, "Knight");
        this.setCurrentExperience(Integer.parseInt(currentXp));
        if (Integer.parseInt(weapon) != 0)
            this.setArtifact(new Artifact("sword", Integer.parseInt(weapon), 0));
        if (Integer.parseInt(armour) != 0)
            this.setArtifact(new Artifact("armour", Integer.parseInt(armour), 0));
        if (Integer.parseInt(helm) != 0)
            this.setArtifact(new Artifact("helm", Integer.parseInt(helm), 0));
    }

}