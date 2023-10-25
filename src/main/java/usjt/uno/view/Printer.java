package usjt.uno.view;

import usjt.uno.src.entities.Player;
import usjt.uno.src.cards.Card;

import java.util.ArrayList;
import java.util.Scanner;

public class Printer {
    private static final String INDENT = "\t\t\t      ";
    public static void calibrate(Scanner finish) {
        clear();

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println(Color.getColorCodeString(Color.RESET) + INDENT + "\b\b\b\b\b\b\b" +
                                "Use (cntrl, +) e (cntrl, -) para ajustar a interface ao tamanho da tela.");
        System.out.println("<~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~>");
        finishEnter(finish);
    }


    public static void printMenu() {
        clear();
        System.out.println(Color.getColorCodeString(Color.RESET));
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println(INDENT + "\t       " + "🃏 <@••••••. UNO .••••••@> 🀄️");
        System.out.print("\n\n");
        System.out.println(INDENT + "\t      " + "         1. Novo jogo");
        System.out.print("\n");
        System.out.println(INDENT + "\t      " + "            2. Sair");
        System.err.println(INDENT + "\t       " + "🀄️ <@••••••••••••••••••••••••@> 🃏");
        System.out.print("\n\n");
        System.out.print(  INDENT + "\t      " + "                0_0? ");
    }

    public static void getPlayerName() {
        clear();
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.print(INDENT + "\b\b\b\b\b\b\b\b\b\b\b" + 
                    "Digite o nome do jogador: ");
    }

    public static void getPlayerChoosenColor() {
        System.out.print("Escolha a cor: ( " + Color.getColorCodeString(Color.WHITE) +
                                Color.getColorCodeString(Color.RED_B) + " 1 " + Color.getColorCodeString(Color.RESET) +
                                ", " + Color.getColorCodeString(Color.WHITE) +
                                Color.getColorCodeString(Color.YELLOW_B) + " 2 " + Color.getColorCodeString(Color.RESET) + 
                                ", " + Color.getColorCodeString(Color.WHITE) + 
                                Color.getColorCodeString(Color.GREEN_B) + " 3 " + Color.getColorCodeString(Color.RESET) + 
                                ", " + Color.getColorCodeString(Color.WHITE) + 
                                Color.getColorCodeString(Color.BLUE_B) + " 4 " + Color.getColorCodeString(Color.RESET) + 
                                " ) :   ");
    }

    public static void printGameBoard(Card theCardOnTheBoard, Color colorOnTheBoard) {
        clear();

        for (int j = 1; j <= 7; j++) {
            System.out.print(INDENT + "\t\t\b" + theCardOnTheBoard.toString(-j) + "    " + theCardOnTheBoard.toString(j));

            if (j == 2)
                System.out.print("  Cor da ultima carta: ");
            else if (j > 2 && j < 6)
                System.out.print("    " + Color.getColorCodeString(colorOnTheBoard) + "      " + 
                    Color.getColorCodeString(Color.RESET));

            System.out.print("\n");
        }

        System.out.print("\n\n\n");
    }

    public static void printNumberOfPlayersCards(ArrayList<Player> players, int currentPlayerIndex) {
        System.out.println("\nNumero de cartas dos jogadores:\n");

        int cntr = 0;
        for (Player player: players) {
            System.out.print("\t " + player.getPlayerName() + ":  " + player.getNumberOfPlayerCards());

            if (cntr == 0 && (currentPlayerIndex == (players.size()-1)))
                System.out.print("\t---> (Próximo jogador)");

            if (cntr == currentPlayerIndex)
                System.out.print("\t---> (Jogando agora)");
            else if ((cntr-1) == currentPlayerIndex)
                System.out.print("\t---> (Próximo jogador)");

            System.out.print("\n");
            cntr++;
        }

        System.out.print("\n\n");
    }

    public static void printPlayerCards(Player player) {
        for (int j = 0; j < player.getPlayerCards().size(); j += 9) {
            for (int i = 1; i <= 8; i++) {
                System.out.print("\t\b");
                for (int k = j; (k < j+9) && (k < player.getPlayerCards().size()); k++) {
                    System.out.print(player.getPlayerCards().get(k).toString(i) + "  ");
                   
                    if (i == 8)
                        //      space = lenght of the previous line - current line lenght 
                        for(int space =  player.getPlayerCards().get(k).toString(i-1).length() - player.getPlayerCards().get(k).toString(i).length(); 
                            space > 0; space--)
                            System.out.print(" ");
                }
                System.out.print("\n");
            }
            System.out.print("\n");
        }
    }

    public static void getPlayerChoice(Player player) {
        System.out.print("\n Ei " + Color.getColorCodeString(Color.BLACK_BRIGHT_B) +
                            player.getPlayerName() + Color.getColorCodeString(Color.RESET) +
                                ", escolha uma carta (digite o codigo da carta escolhida):  ");
    }

    public static void printScores(ArrayList<Player> players, Scanner finish) {
        clear();
        System.out.print(Color.getColorCodeString(Color.WHITE)  + "\n\n\n\n\n\n\n");

        System.out.println(INDENT + "\b\b\b\b\b\b     Nome |  Pontuação                             N° de Cartas");
        System.out.println(INDENT + "\b\b\b\b\b\b–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––");

        Player currPlayer;
        for (int index = 0; index < players.size(); index++) {
            currPlayer = players.get(index);
            System.out.printf("%s\b\b\b\b\b\b%13s :  %7d                      %8d\n", INDENT, 
                                                                        currPlayer.getPlayerName(),
                                                                        currPlayer.getScore(),  
                                                                        currPlayer.getNumberOfPlayerCards());
        }

        System.out.print("\n\n\n\n" + Color.getColorCodeString(Color.RESET));
        finishEnter(finish);
    }

    public static void inValidInputError(Scanner finish) {
        System.out.println(INDENT + "\t         " + 
                                Color.getColorCodeString(Color.YELLOW)+ Color.getColorCodeString(Color.RED) +
                                            "<@ ! Entrada invalida! @>" +
                                            Color.getColorCodeString(Color.RESET));
        finishEnter(finish);
    }

    public static void noChoiceError(Scanner finish) {
        System.out.println("\t\t\t" + 
                                Color.getColorCodeString(Color.YELLOW)+ Color.getColorCodeString(Color.RED) +
                                            "<@ ! Não havia nenhuma carta correspondente. Você comprou uma carta! @>" +
                                            Color.getColorCodeString(Color.RESET));

        finishEnter(finish);
    }

    // this method wait until player push 'enter' bottom
    private static void finishEnter(Scanner inputsSource) {
        System.out.println(INDENT + "\t\t    " + "(digite enter para continuar)");
        inputsSource.nextLine();
    }

    // this method clear the terminal
    private static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}