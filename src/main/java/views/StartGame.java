package views;

import java.util.Arrays;
import java.util.Scanner;

public class StartGame {

    private String[] movement = {"      UP:", "       w", "LEFT:     RIGHT:", "  a         d", "     DOWN:", "       s"};
    private static String[] swingy;

    public Scanner scan = new Scanner(System.in);

    public void welcome() {
        this.clearScreen();
        this.swingyWelcome();
    }

    public String createNewHero() {
        this.clearScreen();
        this.swingyWelcome();
        String hero = "Kevin";
        System.out.println("\nPlease input a name for your hero:\n");
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (!line.isEmpty()) {
                hero = line;
                break;
            }
            this.swingyWelcome();
            this.clearScreen();
            System.out.println("\nPlease input a name for your hero:\n");
        }
        this.clearScreen();
        this.swingyWelcome();
        this.heroStats();
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            switch (line) {
                case "Knight":
                    hero += " " + "Knight";
                    break;
                case "Mage":
                    hero += " " + "Mage";
                    break;
                case "Archer":
                    hero += " " + "Archer";
                    break;
            }
            if (hero.contains(" "))
                break;
            this.clearScreen();
            this.swingyWelcome();
            this.heroStats();
        }
        return hero;
    }

    public void printMap(int mapSize, char[][] map, String[] charStats){
        this.clearScreen();
        this.swingyWelcome();
        for (int i = 0; i < mapSize; i++) {
            String spaces = "                   ";
            if (i < 6) {
                System.out.print(movement[i]);
                char[] repeat;
                repeat = new char[19 - movement[i].length()];
                Arrays.fill(repeat, ' ');
                spaces = new String(repeat);
            }
            System.out.print(spaces);
            for (int j = 0; j < mapSize; j++) {
                if (map[i][j] == 'o')
                    System.out.print("\u001B[37m");
                else if (map[i][j] == 'x') {
                    System.out.print("\u001B[31m");
                } else
                    System.out.print("\u001B[32m");
                System.out.print(map[i][j]);
                System.out.print("\u001B[0m");
                if (j != mapSize - 1)
                    System.out.print("  ");
                else if (i > 8)
                    System.out.println();
            }
            if (i < 9)
                System.out.println("    " + charStats[i]);
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void swingyWelcome() {
        if (swingy == null) {
            swingy = new String[6];
            swingy[0] = "\u001B[37m" + "  ___________      __.___ _______    _____________.___." + "\u001B[0m";
            swingy[1] = "\u001B[31m" + " /   _____/  \\    /  \\   |\\      \\  /  _____/\\__  |   |" + "\u001B[0m";
            swingy[2] = "\u001B[35m" + " \\_____  \\\\   \\/\\/   /   |/   |   \\/   \\  ___ /   |   |" + "\u001B[0m";
            swingy[3] = "\u001B[32m" + " /        \\\\        /|   /    |    \\    \\_\\  \\\\____   |" + "\u001B[0m";
            swingy[4] = "\u001B[34m" + "/_______  / \\__/\\  / |___\\____|__  /\\______  // ______|" + "\u001B[0m";
            swingy[5] = "\u001B[36m" + "        \\/       \\/              \\/        \\/ \\/       \n" + "\u001B[0m";
        }
        for (int i = 0; i < 6; i++)
            System.out.println(swingy[i]);
    }

    public void heroStats() {
        System.out.println("-----------------------------------------------------");
        System.out.println("|                  Choose a class:                  |");
        System.out.println("-----------------------------------------------------");
        System.out.println("|            |   Archer   |    Mage    |   Knight   |");
        System.out.println("| Attack     |     60     |     55     |     65     |");
        System.out.println("| Defense    |     35     |     40     |     30     |");
        System.out.println("| Hit Points |     400    |     370    |     430    |");
        System.out.println("-----------------------------------------------------");
    }


}