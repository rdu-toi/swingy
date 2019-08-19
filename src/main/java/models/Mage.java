package models;

public class Mage extends Hero implements ClassHero {

    public Mage(String name) {
        super(name, 55, 40, 370, "Mage");
    }

    public Mage(String name, String currentXp, String weapon, String armour, String helm) {
        super(name, 65, 30, 430, "Mage");
        this.setCurrentExperience(Integer.parseInt(currentXp));
        if (Integer.parseInt(weapon) != 0)
            this.setArtifact(new Artifact("staff", Integer.parseInt(weapon), 0));
        if (Integer.parseInt(armour) != 0)
            this.setArtifact(new Artifact("armour", Integer.parseInt(armour), 0));
        if (Integer.parseInt(helm) != 0)
            this.setArtifact(new Artifact("helm", Integer.parseInt(helm), 0));
    }

}