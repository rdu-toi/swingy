package controllers;

import models.*;
import views.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.*;

public class CharacterController {

    private static ClassHero hero;
    private static StartGame startGame;
    private char[][] map;
    private char[][] enemyCoordinates;
    private int mapSize;
    private int numOfEnemies;
    private int numOfEnemiesInSight;
    private int[] playerToMove = new int[2];
    private String[] charStats;
    private List<Villian> villians;
    private String[] artifactTypes = {"", "helm", "armour"};
    private int[] artifactTypesAmount = {0, 0, 0};
    private static int firstTime = 0;
    private Scanner scan = new Scanner(System.in);
    private Random rand = new Random();
    private String[] fileContents;
    private int chosen;
    private int fileLength = 0;
    private File file = new File("src/main/java/heroData/heroes.txt");

    private void newHero(String name, String classOfHero) {
        switch (classOfHero) {
            case "Knight":
                hero = new Knight(name);
                break;
            case "Mage":
                hero = new Mage(name);
                break;
            case "Archer":
                hero = new Archer(name);
                break;
        }
    }

    public void start() {
        startGame = new StartGame();
        this.getFileContents();
        int chosenResult;
        if (fileContents.length == 0)
            chosenResult = 0;
        else {
            chosenResult = startGame.menuScreen();
        }
        if (chosenResult == 0) {
            String heroString = startGame.createNewHero();
            String[] heroData = heroString.split(" ");
            int arrayLength = heroData.length;
            String first = "";
            for (int i = 0; i < arrayLength - 1; i++)
                first = String.join(" ", first, heroData[i]);
            first = first.substring(1);
            newHero(first, heroData[arrayLength - 1]);
            String[] temp = fileContents;
            fileContents = new String[fileLength + 1];
            int i = 0;
            for (String tempString: temp) {
                fileContents[i++] = tempString;
            }
            chosen = fileLength;
            fileLength++;
        } else if (chosenResult == 1) {
            this.chosen = startGame.chooseHero();
            String[] heroData = fileContents[chosen].split(", ");
            switch (heroData[1]) {
                case "Knight":
                    hero = new Knight(heroData[0], heroData[3], heroData[4], heroData[5], heroData[6]);
                    break;
                case "Mage":
                    hero = new Mage(heroData[0], heroData[3], heroData[4], heroData[5], heroData[6]);
                    break;
                case "Archer":
                    hero = new Archer(heroData[0], heroData[3], heroData[4], heroData[5], heroData[6]);
                    break;
            }
        }
        switch (hero.getClassOfHero()) {
            case "Knight":
                artifactTypes[0] = "sword";
                break;
            case "Mage":
                artifactTypes[0] = "staff";
                break;
            case "Archer":
                artifactTypes[0] = "bow";
                break;
        }

        while (true) {
            hero.levelUp();
            this.mapSize = (hero.getLevel() - 1) * 5 + 10 - (hero.getLevel() % 2);
            int startPoint = mapSize / 2;
            Coordinates coordinates = new Coordinates(startPoint, startPoint);
            hero.setCoordinates(coordinates);
            hero.setCurrentHP(hero.getHitPoints());
            if (hero.getHelmArtifact() != null)
                hero.setCurrentHP(hero.getCurrentHPPlus());
            villians = new ArrayList<>();
            artifactTypesAmount[0] = hero.getAttack() + (hero.getWeaponArtifact() != null ? (hero.getWeaponArtifact().getArtifactIncrease() / 2) : 0);
            artifactTypesAmount[1] = hero.getHitPoints() + (hero.getHelmArtifact() != null ? (hero.getHelmArtifact().getArtifactIncrease() / 2) : 0);
            artifactTypesAmount[2] = hero.getDefense() + (hero.getArmourArtifact() != null ? (hero.getArmourArtifact().getArtifactIncrease() / 2) : 0);
            if (firstTime == 1) {
                int continueGame = startGame.continueGame();
                if (continueGame == 0) {
                    scanClose();
                    System.exit(1);
                }
            }
            firstTime = 1;
            this.createMap();
            this.fillMap();
            this.gameplay();
        }
    }

    private void getFileContents() {
        try {
            Scanner scannedFile = new Scanner(file);
            while (scannedFile.hasNextLine()) {
                this.fileLength++;
                scannedFile.nextLine();
            }
            this.fileContents = new String[fileLength];
            scannedFile = new Scanner(file);
            int i = 0;
            while (scannedFile.hasNextLine()) {
                fileContents[i++] = scannedFile.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    private void createMap() {
        map = new char[mapSize][mapSize];
        enemyCoordinates = new char[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                map[i][j] = 'o';
                enemyCoordinates[i][j] = 'o';
            }
        }
    }

    private void fillMap() {
        numOfEnemies = (30 * mapSize * mapSize) / 100;
        numOfEnemiesInSight = (50 * numOfEnemies) / 100;
        for (int i = 0; i < numOfEnemies; i++) {
            int flag = 1;
            int x = rand.nextInt(mapSize);
            int y = rand.nextInt(mapSize);
            for (Villian villian : villians) {
                if ((villian.getCoordinates().getX() == x && villian.getCoordinates().getY() == y) || (x == 0 && y == 0)) {
                    flag = 0;
                    break;
                }
            }
            if (flag == 1) {
                Villian villian = Villian.newVillian(hero.getLevel(), x, y);
                if (numOfEnemiesInSight > i) {
                    int attack = ((10 * villian.getAttack()) / 100) + villian.getAttack();
                    int defense = ((10 * villian.getDefense()) / 100) + villian.getDefense();
                    int hp = ((10 * villian.getHitPoints()) / 100) + villian.getHitPoints();
                    int xp = ((40 * villian.getExperience()) / 100) + villian.getExperience();
                    villian.setAttack(attack);
                    villian.setDefense(defense);
                    villian.setHitPoints(hp);
                    villian.setExperience(xp);
                    map[villian.getCoordinates().getY()][villian.getCoordinates().getX()] = 'x';
                }
                villians.add(villian);
                enemyCoordinates[villian.getCoordinates().getY()][villian.getCoordinates().getX()] = 'x';
            } else
                i--;
        }
        Collections.shuffle(villians);
        int numOfArtifacts = (numOfEnemies * 30) / 100;
        for (Villian villian : villians) {
            int r = rand.nextInt(3);
            villian.setArtifact(new Artifact(artifactTypes[r], artifactTypesAmount[r]));
            numOfArtifacts--;
            if (numOfArtifacts == 0)
                break;
        }
    }

    private void gameplay() {
        this.saveToFile();
        while (hero.getCoordinates().getY() != -1 && hero.getCoordinates().getY() != mapSize &&
                hero.getCoordinates().getX() != -1 && hero.getCoordinates().getX() != mapSize) {
            hero.levelUp();
            charStats = new String[9];
            charStats[0] = "Name: " + hero.getName();
            charStats[1] = "Type: " + hero.getClassOfHero();
            if (hero.getWeaponArtifact() != null)
                charStats[2] = "Attack: " + hero.getAttackPlus();
            else
                charStats[2] = "Attack: " + hero.getAttack();
            if (hero.getArmourArtifact() != null)
                charStats[3] = "Defense: " + hero.getDefensePlus();
            else
                charStats[3] = "Defense: " + hero.getDefense();
            charStats[4] = "Xp: " + hero.getCurrentExperience() + " / " + hero.getExperience();
            if (hero.getHelmArtifact() != null)
                charStats[5] = "Hp: " + hero.getCurrentHP() + " / " + hero.getCurrentHPPlus();
            else
                charStats[5] = "Hp: " + hero.getCurrentHP() + " / " + hero.getHitPoints();
            charStats[6] = "Level: " + hero.getLevel();
            charStats[7] = "Coordinate X: " + hero.getCoordinates().getX();
            charStats[8] = "Coordinate Y: " + hero.getCoordinates().getY();

            System.out.println("\nMap Size: " + mapSize + " * " + mapSize + " = " + (mapSize * mapSize));

            map[hero.getCoordinates().getY()][hero.getCoordinates().getX()] = 'i';

            startGame.printMap(mapSize, map, charStats);

            while (scan.hasNextLine()) {
                if (this.playerMovement() == 1)
                    break;
                startGame.printMap(mapSize, map, charStats);
            }
            this.collision();
        }
    }

    private int playerMovement() {
        String line = scan.nextLine();
        int playerX = hero.getCoordinates().getX();
        int playerY = hero.getCoordinates().getY();
        playerToMove[0] = playerY;
        playerToMove[1] = playerX;
        switch (line) {
            case "w":
                map[playerY][playerX] = 'o';
                playerToMove[0] = playerY - 1;
                return 1;
            case "s":
                map[playerY][playerX] = 'o';
                playerToMove[0] = playerY + 1;
                return 1;
            case "a":
                map[playerY][playerX] = 'o';
                playerToMove[1] = playerX - 1;
                return 1;
            case "d":
                map[playerY][playerX] = 'o';
                playerToMove[1] = playerX + 1;
                return 1;
            case "quit":
                scanClose();
                System.exit(1);
        }
        return 0;
    }

    private void collision() {
        if ((playerToMove[0] >= 0 && playerToMove[1] >= 0) && (playerToMove[0] < mapSize && playerToMove[1] < mapSize) && enemyCoordinates[playerToMove[0]][playerToMove[1]] == 'x') {
            Villian enemy = new Villian();
            for (Villian villian : villians) {
                if (villian.getCoordinates().getX() == playerToMove[1] && villian.getCoordinates().getY() == playerToMove[0]) {
                    enemy = villian;
                    break;
                }
            }
            this.battle(enemy);
        } else {
            hero.getCoordinates().setY(playerToMove[0]);
            hero.getCoordinates().setX(playerToMove[1]);
        }
    }

    private void battle(Villian enemy) {
        StartGame.clearScreen();
        StartGame.swingyWelcome();
        System.out.print("\u001B[36m");
        System.out.println("\nYou have run into villian '" + enemy.getName() + "' and now you must fight to the death!\n");
        int run = 1;
        if (Math.random() > 0.50)
            run = 0;
        int flag = 1;
        if (Math.random() > 0.80)
            flag = 0;
        String[] enemyStats = {"Name: " + enemy.getName(), "Attack: " + (enemy.getAttack()), "Defense: " + (enemy.getDefense()), "Hit Points: " + (enemy.getHitPoints())};
        startGame.printCharStats(charStats, enemyStats);
        System.out.print("\u001B[36m");
        System.out.println("Do you wish to fight or run away?\n'q': fight   'e': run\n");
        System.out.println("\u001B[0m");
        System.out.print("Your choice? : ");
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (line.equals("e")) {
                if (run == 1)
                    break;
                System.out.print("\u001B[31m");
                System.out.println("You have failed to run away.\n");
                System.out.print("\u001B[0m");
                run = 2;
            }
            if (run == 2 || line.equals("q")) {
                while (true) {
                    int damage;
                    if (flag == 1) {
                        damage = hero.attack() - enemy.defense();
                        if (damage > 0)
                            enemy.setHitPoints(enemy.getHitPoints() - damage);
                        if (enemy.getHitPoints() <= 0) {
                            System.out.print("\u001B[32m");
                            System.out.println("\nVillian: '" + enemy.getName() + "' has been defeated! Hoorah!\n");
                            System.out.print("\u001B[0m");
                            hero.setCurrentExperience(hero.getCurrentExperience() + enemy.getExperience());
                            hero.getCoordinates().setY(playerToMove[0]);
                            hero.getCoordinates().setX(playerToMove[1]);
                            enemyCoordinates[playerToMove[0]][playerToMove[1]] = 'o';
                            villians.remove(enemy);
                            if (enemy.getArtifact() != null) {
                                Artifact currentArtifact = enemy.getArtifact();
                                String[] heroStats = new String[6];
                                heroStats[0] = Integer.toString(hero.getAttack());
                                heroStats[1] = Integer.toString(hero.getDefense());
                                heroStats[2] = Integer.toString(hero.getHitPoints());
                                if (hero.getWeaponArtifact() != null)
                                    heroStats[3] = Integer.toString(hero.getAttackPlus());
                                else
                                    heroStats[3] = Integer.toString(hero.getAttack());
                                if (hero.getArmourArtifact() != null)
                                    heroStats[4] = Integer.toString(hero.getDefensePlus());
                                else
                                    heroStats[4] = Integer.toString(hero.getDefense());
                                if (hero.getHelmArtifact() != null)
                                    heroStats[5] = Integer.toString(hero.getCurrentHPPlus());
                                else
                                    heroStats[5] = Integer.toString(hero.getHitPoints());
                                String[] artifactInfo = new String[3];
                                artifactInfo[0] = currentArtifact.getArtifactName();
                                artifactInfo[1] = currentArtifact.getArtifactType();
                                artifactInfo[2] = Integer.toString(currentArtifact.getArtifactIncrease());
                                if (startGame.foundArtifact(heroStats, artifactInfo) == 1)
                                    hero.setArtifact(currentArtifact);
                            }
                            System.out.print("\u001B[36m");
                            System.out.println("Press any button to continue!");
                            System.out.print("\u001B[0m");
                            line = scan.nextLine();
                            break;
                        }
                    } else {
                        System.out.print("\u001B[31m");
                        System.out.println("\n" + enemy.getName() + " got the jump on you and attacks first.");
                        System.out.print("\u001B[0m");
                    }
                    int dodge = hero.getDefense();
                    damage = enemy.attack() - dodge;
                    if (damage > 0 && dodge != -1)
                        hero.setCurrentHP(hero.getCurrentHP() - damage);
                    if (hero.getCurrentHP() <= 0) {
                        System.out.print("\u001B[31m");
                        System.out.println("\nHero: " + hero.getClassOfHero() + " '" + hero.getName() + "' has been defeated!\n\n              GAME OVER");
                        System.out.print("\u001B[0m");
                        scanClose();
                        System.exit(0);
                    }
                    flag = 1;
                }
                break;
            }
            startGame.welcome();
            System.out.print("\u001B[36m");
            System.out.println("\nYou have run into villian '" + enemy.getName() + "' and now you must fight to the death!\n");
            startGame.printCharStats(charStats, enemyStats);
            System.out.print("\u001B[36m");
            System.out.println("Do you wish to fight or run away?\n'q': fight   'e': run\n");
            System.out.print("\u001B[0m");
            System.out.print("Your choice? : ");
        }
    }

    private void saveToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/heroData/heroes.txt"));
            fileContents[chosen] = hero.getName() + ", " + hero.getClassOfHero() + ", " + hero.getLevel() + ", " + hero.getCurrentExperience() +
                    ", " + (hero.getWeaponArtifact() != null ? hero.getWeaponArtifact().getArtifactIncrease() : "0") +
                    ", " + (hero.getArmourArtifact() != null ? hero.getArmourArtifact().getArtifactIncrease() : "0") +
                    ", " + (hero.getHelmArtifact() != null ? hero.getHelmArtifact().getArtifactIncrease() : "0");
            for (int i = 0; i < this.fileLength; i++) {
                System.out.println(fileContents[i]);
                writer.write(fileContents[i]);
                writer.write(System.lineSeparator());
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void scanClose() {
        scan.close();
    }

}