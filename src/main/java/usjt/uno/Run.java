package usjt.uno;

import usjt.uno.src.entities.Bot;
import usjt.uno.src.entities.Player;
import usjt.uno.src.usecase.impl.GameUseCaseImpl;
import usjt.uno.view.Printer;

import java.util.Scanner;

public class Run {
    private static Scanner inputs = new Scanner(System.in);

    private static GameUseCaseImpl gameUseCase;

    public static void main(String[] args) {
        Printer.calibrate(inputs);

        gameUseCase = new GameUseCaseImpl();

        String holdInput;
        String newPlayerName;

        while (true) {
            while (true) {
                Printer.printMenu();
                holdInput = inputs.nextLine();

                if (holdInput.length() == 1 && (holdInput.charAt(0) == '1' || holdInput.charAt(0) == '2'))
                    break;
                else 
                    Printer.inValidInputError(inputs);
            }

            switch (holdInput) {
                case "1":
                    Printer.getPlayerName();
                    newPlayerName = inputs.nextLine();
                    gameUseCase.addPlayer(new Player(newPlayerName)); //Adiciona os jogadores

                    for (int n = 0; n < 3; n++) { //Adiciona os bots
                        gameUseCase.addPlayer(new Bot(n, gameUseCase));
                    }

                    gameUseCase.preparationGameBoard(); //Prepara a mesa

                    gameUseCase.runGame(inputs);//Inicia jogo

                    gameUseCase.reset();
                break;

                case "2":
                    return;
            }
        }
    }
}