package dev.tilegame;

import dev.tilegame.Display.Display;

public class Launcher {

    public static void main(String[] args){
         Game game = new Game("Game", 1000, 700);
         game.start();
    }
}
