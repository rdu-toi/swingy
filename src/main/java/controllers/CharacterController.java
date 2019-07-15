package controllers;

import models.*;
import views.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

public class CharacterController {

    private static ClassHero classHero;
    private static StartGame startGame;
    private char[][] map;
    private char[][] enemyCoordinates;
    private int mapSize;
    private int numOfEnemies;
    private int numOfEnemiesInSight;
    private String[] charStats;
    private List<Villian> villians;
    private Scanner scan = new Scanner(System.in);
    private Random rand = new Random();
    private String[] villianNames = {"Tyrannosaura Wrecks", "Bewarewolf", "Captain Crunch", "Chewbacca", "Artichoker", "Cruelcumber", "Boaty Mcboatface", "Paul", "Poopy Head"};

    private void newHero(String name, String classOfHero) {
        switch (classOfHero) {
            case "Knight":
                classHero = new Knight(name);
                break;
            case "Mage":
                classHero = new Mage(name);
                break;
            case "Archer":
                classHero = new Archer(name);
                break;
        }
    }

    public void start() {
        startGame = new StartGame();
        startGame.welcome();
        String hero = startGame.createNewHero();
        String[] heroData = hero.split(" ");
        newHero(heroData[0], heroData[1]);

        while (true) {
            this.mapSize = (classHero.getLevel() - 1) * 5 + 10 - (classHero.getLevel() % 2);
            int startPoint = mapSize / 2;
            Coordinates coordinates = new Coordinates(startPoint, startPoint);
            classHero.setCoordinates(coordinates);
            classHero.setCurrentHP(classHero.getHitPoints());
            villians = new ArrayList<Villian>();
            this.createMap();
            this.fillMap();
            this.gameplay();
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
                Coordinates coordinates1 = new Coordinates(x, y);
                Villian villian = newVillian(x, y);
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
    }

    private void gameplay() {
        while (classHero.getCoordinates().getY() != -1 && classHero.getCoordinates().getY() != mapSize &&
                classHero.getCoordinates().getX() != -1 && classHero.getCoordinates().getX() != mapSize) {
            classHero.levelUp();

            //  Printing hero stats.
            charStats = new String[9];
            charStats[0] = "Name: " + classHero.getName();
            charStats[1] = "Type: " + classHero.getClassOfHero();
            charStats[2] = "Attack: " + classHero.getAttack();
            charStats[3] = "Defense: " + classHero.getDefense();
            charStats[4] = "Xp: " + classHero.getCurrentExperience() + " / " + classHero.getExperience();
            charStats[5] = "Hp: " + classHero.getCurrentHP() + " / " + classHero.getHitPoints();
            charStats[6] = "Level: " + classHero.getLevel();
            charStats[7] = "Coordinate X: " + classHero.getCoordinates().getX();
            charStats[8] = "Coordinate Y: " + classHero.getCoordinates().getY();

            System.out.println("\nMap Size: " + mapSize + " * " + mapSize + " = " + (mapSize * mapSize));

            map[classHero.getCoordinates().getY()][classHero.getCoordinates().getX()] = 'i';

            //  Printing out the map.
            startGame.printMap(mapSize, map, charStats);

            System.out.println("Number of enemies generated: " + numOfEnemies);
            System.out.println("Number of elements in villians arrayList: " + villians.size());

            //  While loop scanning user input for moving around the map.
            while (scan.hasNextLine()) {
                if (this.playerMovement() == 1)
                    break;
                startGame.printMap(mapSize, map, charStats);
                System.out.println("Number of enemies generated: " + numOfEnemies);
                System.out.println("Number of elements in villians arrayList: " + villians.size());
            }
            //  Checking if the player ran into an enemy.
            this.collision();
        }
    }

    private int playerMovement() {
        String line = scan.nextLine();
        switch (line) {
            case "w":
                map[classHero.getCoordinates().getY()][classHero.getCoordinates().getX()] = 'o';
                classHero.getCoordinates().setY(-1);
                return 1;
            case "s":
                map[classHero.getCoordinates().getY()][classHero.getCoordinates().getX()] = 'o';
                classHero.getCoordinates().setY(1);
                return 1;
            case "a":
                map[classHero.getCoordinates().getY()][classHero.getCoordinates().getX()] = 'o';
                classHero.getCoordinates().setX(-1);
                return 1;
            case "d":
                map[classHero.getCoordinates().getY()][classHero.getCoordinates().getX()] = 'o';
                classHero.getCoordinates().setX(1);
                return 1;
            case "quit":
                System.exit(1);
        }
        return 0;
    }

    private void collision() {
        int y = classHero.getCoordinates().getY();
        int x = classHero.getCoordinates().getX();
        if ((y >= 0 && x >= 0) && (y < mapSize && x < mapSize) && enemyCoordinates[y][x] == 'x') {
            Villian enemy = new Villian();
            for (Villian villian : villians) {
                if (villian.getCoordinates().getX() == x && villian.getCoordinates().getY() == y) {
                    enemy = villian;
                    break;
                }
            }
            System.out.print("\u001B[36m");
            System.out.println("\nYou have run into villian '" + enemy.getName() + "' and now you must fight to the death!");
            System.out.print("\u001B[0m");
            //  Battle commences.
            //  Player gets xp and/or loses health if wins. Else, if player dies, the game quits. Updates still pending...
            this.battle(enemy, x, y);
        }
    }

    private void battle(Villian enemy, int x, int y) {
        while (true) {
            int damage = classHero.attack() - enemy.getDefense();
            if (damage > 0)
                enemy.setHitPoints(enemy.getHitPoints() - damage);
            if (enemy.getHitPoints() <= 0) {
                System.out.println("Villian: '" + enemy.getName() + "' has been defeated!");
                classHero.setCurrentExperience(classHero.getCurrentExperience() + enemy.getExperience());
                villians.remove(enemy);
                enemyCoordinates[y][x] = 'o';
                break;
            }
            damage = enemy.attack() - classHero.getDefense();
            if (damage > 0)
                classHero.setCurrentHP(classHero.getCurrentHP() - damage);
            if (classHero.getCurrentHP() <= 0) {
                System.out.println("Hero: " + classHero.getClassOfHero() + " '" + classHero.getName() + "' has been defeated!\n\nGAME OVER");
                ScanClose();
                System.exit(0);
            }
        }
    }

    private Villian newVillian(int x, int y) {
        int heroLevel = classHero.getLevel();
        int highestAttack = 55 + (15 * (heroLevel - 1));
        int highestDefense = 30 + (15 * (heroLevel - 1));
        int highestHP = 180 + (15 * (heroLevel - 1));
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

    public void ScanClose() {
        scan.close();
    }

}