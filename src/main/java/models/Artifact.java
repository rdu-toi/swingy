package models;

import java.util.Random;

public class Artifact {

    private static Random rand = new Random();
    private static ArtifactNames artifactNames = new ArtifactNames();

    String type;
    int increase;
    String name;

    public Artifact(String type, int statAmount) {
        this.type = type;
        int highestStatAmount = ((10 * statAmount) / 100) + statAmount;
        this.increase = rand.nextInt(highestStatAmount - statAmount - 1) + 1;
        this.name = artifactNames.getName(type);
    }

    public Artifact(String type, int increase, int flag) {
        this.type = type;
        this.increase = increase;
        this.name = artifactNames.getName(type);
    }

    public String getArtifactType() {
        return type;
    }

    public int getArtifactIncrease() {
        return increase;
    }

    public String getArtifactName() {
        return name;
    }
}
