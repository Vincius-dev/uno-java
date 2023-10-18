package usjt.uno;

import usjt.uno.model.entities.Bot;
import usjt.uno.model.entities.player.Player;
import usjt.uno.model.game.Rules;
import usjt.uno.view.Printer;

import java.util.Scanner;

/**
 * The main class of the game.
 * This game designed for UNIX bash !. 
 * (may not work on windows)
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.1.0 
 */
public class Run 
{
    // for get the players inputs
    private static Scanner inputs = new Scanner(System.in);

    public static void main(String[] args) {
        // calibrate the font size of the terminal
        Printer.calibrate(inputs);

        //  * required variables *
        String holdInput; // hold the input to check that its valid or not
        int numberOfPlayers; // the number of the game players
        String newPlayerName, newPlayerPass; // get the new player details

        // while player choose exit option 
        while (true) {
            // while player choose valid option
            while (true) {
                // show the game menu tho the player and get his/her choice
                Printer.printMenu();
                holdInput = inputs.nextLine();

                // check the player input
                if (holdInput.length() == 1 && (holdInput.charAt(0) == '1' || holdInput.charAt(0) == '2'))
                    break;
                else 
                    Printer.inValidInputError(inputs);
            }

            switch (holdInput) {
                case "1":
                    // set the number of the players
                    numberOfPlayers = 4;

                    Printer.getPlayerName();
                    newPlayerName = inputs.nextLine();
                    Rules.addPlayer(new Player(newPlayerName));

                    // get the players detials
                    for (int n = 0; n < 3; n++) {
                        Rules.addPlayer(new Bot(n));
                    }
                    
                    // get the cards to the players
                    Rules.preparationGameCards();
                    Rules.distributeCards();

                    // run the game
                    Rules.runGame(inputs);

                    // reset the game
                    Rules.reset();

                break;

                case "2":
                    return;
            }
        }
    }
}