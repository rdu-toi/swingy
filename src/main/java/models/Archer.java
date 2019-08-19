package models;

public class Archer extends Hero implements ClassHero {

    public Archer(String name) {
        super(name, 60, 35, 400, "Archer");
    }

    public Archer(String name, String currentXp, String weapon, String armour, String helm) {
        super(name, 65, 30, 430, "Archer");
        this.setCurrentExperience(Integer.parseInt(currentXp));
        if (Integer.parseInt(weapon) != 0)
            this.setArtifact(new Artifact("bow", Integer.parseInt(weapon), 0));
        if (Integer.parseInt(armour) != 0)
            this.setArtifact(new Artifact("armour", Integer.parseInt(armour), 0));
        if (Integer.parseInt(helm) != 0)
            this.setArtifact(new Artifact("helm", Integer.parseInt(helm), 0));
    }

}