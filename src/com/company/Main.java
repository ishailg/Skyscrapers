package com.company;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner in = new Scanner(System.in);
        System.out.println("welcome to");
        System.out.println(" ______   __  __   __  __   ______   ______   ______   ______   ______  ______   ______   ______    \n" +
                "/\\  ___\\ /\\ \\/ /  /\\ \\_\\ \\ /\\  ___\\ /\\  ___\\ /\\  == \\ /\\  __ \\ /\\  == \\/\\  ___\\ /\\  == \\ /\\  ___\\   \n" +
                "\\ \\___  \\\\ \\  _\"-.\\ \\____ \\\\ \\___  \\\\ \\ \\____\\ \\  __< \\ \\  __ \\\\ \\  _-/\\ \\  __\\ \\ \\  __< \\ \\___  \\  \n" +
                " \\/\\_____\\\\ \\_\\ \\_\\\\/\\_____\\\\/\\_____\\\\ \\_____\\\\ \\_\\ \\_\\\\ \\_\\ \\_\\\\ \\_\\   \\ \\_____\\\\ \\_\\ \\_\\\\/\\_____\\ \n" +
                "  \\/_____/ \\/_/\\/_/ \\/_____/ \\/_____/ \\/_____/ \\/_/ /_/ \\/_/\\/_/ \\/_/    \\/_____/ \\/_/ /_/ \\/_____/ \n" +
                "                                                                                                    ");
        System.out.println("by ishailg.\n\n");
        System.out.println("Please enter desired board size: ");
        int size = in.nextInt();
        while(size<2){
            System.out.println("invalid size.");
            size = in.nextInt();
        }
        System.out.println("Enjoy!");
        Session s = new Session(size);
        s.play();
        String a = in.nextLine();
        String b =in.nextLine();
    }
}
