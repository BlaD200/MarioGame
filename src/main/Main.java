package main;

import game.Game;
import menu.Menu;

public class Main {

    public static void main(String[] args) {

        Game mario = new Game(new Menu());
        mario.start();

    }
}