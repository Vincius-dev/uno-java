package usjt.uno;

import usjt.uno.src.entities.Bot;
import usjt.uno.src.entities.Player;
import usjt.uno.src.usecase.GameUseCase;
import usjt.uno.view.Printer;

import java.util.Scanner;


public class Run 
{
    // for get the players inputs
    private static Scanner inputs = new Scanner(System.in);

    private static GameUseCase gameUseCase;

    public Run(GameUseCase gameUseCase) {
        this.gameUseCase = gameUseCase;
    }


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
                // show the cards menu tho the player and get his/her choice
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
                    gameUseCase.addPlayer(new Player(newPlayerName));

                    // get the players detials
                    for (int n = 0; n < 3; n++) {
                        gameUseCase.addPlayer(new Bot(n));
                    }
                    
                    // get the cards to the players
                    gameUseCase.preparationGameCards();
                    gameUseCase.distributeCards();

                    // run the cards
                    gameUseCase.runGame(inputs);

                    // reset the cards
                    gameUseCase.reset();

                break;

                case "2":
                    return;
            }
        }
    }
}