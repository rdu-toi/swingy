package views;

import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class StartGame {

    private String[] movement = {
            "      UP:",
            "       w",
            "LEFT:     RIGHT:",
            "  a         d",
            "     DOWN:",
            "       s",
            "                   ",
            "  QUIT: quit"
    };
    private static String[] swingy;

    public Scanner scan = new Scanner(System.in);
    public File file = new File("src/main/java/heroData/heroes.txt");

    public void welcome() {
        this.clearScreen();
        this.swingyWelcome();
    }

    public int menuScreen() {
        this.welcome();
        System.out.println("  1: Start new game\n  2: Load existing character.\n\n");
        System.out.print("  Your choice? : ");
        while (true) {
            try {
                int answer = scan.nextInt();
                switch (answer) {
                    case 1:
                        return 0;
                    case 2:
                        return 1;
                }
            } catch (java.util.InputMismatchException e) {
                scan.nextLine();
            }
            this.welcome();
            System.out.println("  1: Start new game\n  2: Load existing character.\n\n");
            System.out.print("  Your choice? : ");
            if (false)
                break;
        }
        return -1;
    }

    public String createNewHero() {
        this.welcome();
        String hero = "Kevin";
        System.out.println("\nPlease input a name for your hero:\n");
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (!line.isEmpty()) {
                hero = line;
                break;
            }
            this.welcome();
            System.out.println("\nPlease input a name for your hero:\n");
        }
        this.welcome();
        this.heroStats();
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            switch (line) {
                case "3":
                    hero += " " + "Knight";
                    break;
                case "2":
                    hero += " " + "Mage";
                    break;
                case "1":
                    hero += " " + "Archer";
                    break;
            }
            if (hero.contains(" "))
                break;
            this.welcome();
            this.heroStats();
        }
        return hero;
    }

    public int chooseHero() {
        try {
            int fileLength = (int) file.length();
            String[] fileContents = new String[fileLength];
            Scanner scannedFile = new Scanner(file);
            int i = 0;
            while (scannedFile.hasNextLine()) {
                fileContents[i++] = scannedFile.nextLine();
            }
            scan = new Scanner(System.in);
            while (true) {
                try {
                    this.welcome();
                    int j = 0;
                    System.out.println("Choose a character:\n");
                    while (j < i) {
                        String[] heroData = fileContents[j].split(", ");
                        j++;
                        System.out.println("(" + j + ") " + heroData[0] + " ==> [ Type: " + heroData[1] + ", Level: " + heroData[2] + " ]");
                    }
                    System.out.print("\nYour choice : ");
                    int num = 0;
                    num = scan.nextInt();
                    if (num >= 1 && num <= i)
                        return num - 1;
                } catch (java.util.InputMismatchException e) {
                    scan.nextLine();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return 0;
    }

    public void printMap(int mapSize, char[][] map, String[] charStats) {
        this.welcome();
        for (int i = 0; i < mapSize; i++) {
            String spaces = "                   ";
            if (i < 8) {
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
        System.out.print("\nWhere to? : ");
    }

    public void printCharStats(String[] charStats, String[] enemyStats) {
        System.out.print("\u001B[34m");
        System.out.println("    Hero:");
        System.out.println("  " + charStats[0]);
        System.out.println("  " + charStats[2]);
        System.out.println("  " + charStats[3]);
        System.out.println("  " + charStats[5] + "\n");
        System.out.print("\u001B[0m");
        System.out.print("\u001B[31m");
        System.out.println("    Villian:");
        for (int i = 0; i < 4; i++)
            System.out.println("  " + enemyStats[i] + "");
        System.out.print("\u001B[0m\n");
    }

    public int foundArtifact(String[] charStats, String[] artifactInfo) {
        this.displayArtifact(charStats, artifactInfo);
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            switch (line) {
                case "y":
                    return 1;
                case "n":
                    return 0;
            }
            this.displayArtifact(charStats, artifactInfo);
        }
        return 0;
    }

    public void displayArtifact(String[] charStats, String[] artifactInfo) {
        this.welcome();
        int intIncrease;
        switch (artifactInfo[1]) {
            case "helm":
                intIncrease = Integer.parseInt(artifactInfo[2]) - (Integer.parseInt(charStats[5]) - Integer.parseInt(charStats[2]));
                charStats[5] += " ==> " + intIncrease;
                break;
            case "armour":
                intIncrease = Integer.parseInt(artifactInfo[2]) - (Integer.parseInt(charStats[4]) - Integer.parseInt(charStats[1]));
                charStats[4] += " ==> " + intIncrease;
                break;
        }
        if (artifactInfo[1].equals("sword") || artifactInfo[1].equals("staff") || artifactInfo[1].equals("bow")) {
            intIncrease = Integer.parseInt(artifactInfo[2]) - (Integer.parseInt(charStats[3]) - Integer.parseInt(charStats[0]));
            charStats[3] += " ==> " + intIncrease;
        }
        artifactInfo[1] += " ";
        System.out.print("\u001B[32m");
        System.out.println("  The enemy dropped: ");
        System.out.print("\u001B[31m");
        System.out.println("  " + artifactInfo[0]);
        System.out.println("  Type: " + artifactInfo[1]);
        System.out.println("  Increase: +" + artifactInfo[2] + "\n");
        System.out.print("\u001B[34m");
        System.out.println("  Your Stats:");
        System.out.println("  Attack: " + charStats[3]);
        System.out.println("  Defense: " + charStats[4]);
        System.out.println("  Hit Points: " + charStats[5] + "\n");
        System.out.print("\u001B[36m");
        System.out.println("\nDo you wish to equip it? 'y' or 'n':\n");
        System.out.print("\u001B[0m");
    }

    public int continueGame() {
        String line;
        while (true) {
            try {
                this.welcome();
                System.out.println("\u001B[36m" + "    Do you wish to continue?:\n    Type 'y' or 'n'\n");
                System.out.print( "\u001B[32m" + "    Your answer: " + "\u001B[0m");
                line = scan.nextLine();
                if (line.equals("y"))
                    return 1;
                else if (line.equals("n")) {
                    this.welcome();
                    System.out.print( "\u001B[32m" + "    Thanks For Playing!" + "\u001B[0m" + "\n");
                    return 0;
                }
            } catch (java.util.InputMismatchException e) {
                scan.nextLine();
            }
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void swingyWelcome() {
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
        System.out.println("-----------------------------------------------------\n");
        System.out.println("1 : Archer");
        System.out.println("2 : Mage");
        System.out.println("3 : Knight\n");
        System.out.print("Your choice : ");
    }


}