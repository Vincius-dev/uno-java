package usjt.uno.src.usecase.impl;

import usjt.uno.src.cards.Card;
import usjt.uno.src.cards.especialcards.*;
import usjt.uno.src.entities.Bot;
import usjt.uno.src.entities.Deck;
import usjt.uno.src.entities.GameBoard;
import usjt.uno.src.entities.Player;
import usjt.uno.src.entities.PlayerList.PlayerList;
import usjt.uno.src.entities.PlayerList.PlayerNode;
import usjt.uno.src.usecase.CardsUseCase;
import usjt.uno.src.usecase.GameUseCase;
import usjt.uno.src.usecase.PlayerUseCase;
import usjt.uno.view.Color;
import usjt.uno.view.Printer;

import java.util.Random;
import java.util.Scanner;

public class GameUseCaseImpl implements GameUseCase {
    private static PlayerList players = new PlayerList();
    private GameBoard gameBoard;
    private final PlayerUseCase playerUseCase;
    private final CardsUseCase cardsUseCase;

    public GameUseCaseImpl() {
        this.playerUseCase = new PlayerUseCaseImpl(this);
        this.cardsUseCase = new CardsUseCaseImpl();
        this.gameBoard = new GameBoard();
    }

    @Override
    public void runGame(Scanner inputs) {
        Player currentPlayer;
        Card playerChoosenCard;
        String holdInput;
        Bot bot;

        while (!endGame()) {
            currentPlayer = players.getHead().getPlayer();

            System.out.println();
            System.out.println("É a vez do " + currentPlayer.getPlayerName());
            System.out.println();

            //Verifica se as cartas do monte acabaram
            if (gameBoard.getDeckCards().size() == 0){
                System.out.println("\n");
                System.out.println("As cartas do monte acabaram, pegando as que estão no descarte...");

                cardsUseCase.getCardsBackFromDiscart(gameBoard);
                cardsUseCase.suffleCards(gameBoard.getDeckCards());
            }

            //Verifica se tem cartas para jogar
            if (!playerUseCase.checkPlayerCards(currentPlayer)) {
                playerUseCase.giveCardToPlayer(currentPlayer, gameBoard.getDeckCards());

                if (!playerUseCase.checkPlayerCards(currentPlayer)){
                    if(currentPlayer instanceof Bot){
                        Printer.printNumberOfPlayersCards(players);
                        Printer.printGameBoard(gameBoard.getBoardCard(), gameBoard.getBoardColor());
                        System.out.println("O bot " + currentPlayer.getPlayerName() + " não possui castas para jogar");
                    } else {
                        System.out.println("Cartas no baralho: " + gameBoard.getDeckCards().size());
                        System.out.println("Cartas no monte de descarte: " + gameBoard.getDiscartDeck().size());
                        Printer.printNumberOfPlayersCards(players);
                        Printer.printGameBoard(gameBoard.getBoardCard(), gameBoard.getBoardColor());

                        Printer.noChoiceError(inputs);
                    }
                }

                players.playerPlayed();
                continue;
            }

            //Se for a vez de um bot
            if (currentPlayer instanceof Bot) {
                bot = (Bot) currentPlayer;

                Printer.printNumberOfPlayersCards(players);

                System.out.println(currentPlayer.getPlayerName() + " esta pensando...");
                Random rand = new Random();
                int tempo = rand.nextInt(10) + 20;
                tempo *= 1000;

                try {
                    Thread.sleep(tempo);
                    System.out.println("Passaram-se " + (tempo / 1000) + " segundos.");
                } catch (InterruptedException e) {
                    System.out.println(e);
                    Thread.currentThread().interrupt();
                }
                System.out.println("Cartas no baralho: " + gameBoard.getDeckCards().size());
                System.out.println("Cartas no monte de descarte: " + gameBoard.getDiscartDeck().size());

                Printer.printPlayerCards(currentPlayer);

                playerChoosenCard = bot.playTurn();


                playerUseCase.setIndex(playerChoosenCard, players);
                Printer.printGameBoard(gameBoard.getBoardCard(), gameBoard.getBoardColor());
                continue;
            }

            while (true) {
                while (true) {
                    System.out.println("Cartas no baralho: " + gameBoard.getDeckCards().size());
                    System.out.println("Cartas no monte de descarte: " + gameBoard.getDiscartDeck().size());
                    Printer.printNumberOfPlayersCards(players);
                    Printer.printGameBoard(gameBoard.getBoardCard(), gameBoard.getBoardColor());
                    Printer.printPlayerCards(currentPlayer);

                    Printer.getPlayerChoice(currentPlayer);
                    holdInput = inputs.nextLine();

                    if (holdInput.length() > 0 && holdInput.length() < 4 && isInt(holdInput))
                        if (Integer.valueOf(holdInput) <= 108 && Integer.valueOf(holdInput) > 0)
                            if (currentPlayer.haveCard(Integer.valueOf(holdInput)))
                                break;

                    Printer.inValidInputError(inputs);
                }

                playerChoosenCard = currentPlayer.removeCard(Integer.valueOf(holdInput));

                if (checkChoose(playerChoosenCard, currentPlayer)) {
                    if (playerChoosenCard instanceof WildCard || playerChoosenCard instanceof WildDrawCard) {
                        while (true) {
                            Printer.getPlayerChoosenColor();
                            holdInput = inputs.nextLine();

                            if (holdInput.length() == 1 && holdInput.charAt(0) > '0' && holdInput.charAt(0) < '5')
                                break;

                            Printer.inValidInputError(inputs);

                            Printer.printGameBoard(gameBoard.getBoardCard(), gameBoard.getBoardColor());
                            Printer.printNumberOfPlayersCards(players);
                            Printer.printPlayerCards(currentPlayer);
                        }

                        switch (holdInput) {
                            case "1":
                                applyChoose(playerChoosenCard, Color.RED);
                                break;

                            case "2":
                                applyChoose(playerChoosenCard, Color.YELLOW);
                                break;

                            case "3":
                                applyChoose(playerChoosenCard, Color.GREEN);
                                break;

                            case "4":
                                applyChoose(playerChoosenCard, Color.BLUE);
                                break;
                        }
                    } else
                        applyChoose(playerChoosenCard, playerChoosenCard.getCardColor());

                    break;
                }

                // give back the card to the player
                currentPlayer.addCard(playerChoosenCard);

                // say that player input is incorrect
                Printer.inValidInputError(inputs);
            }

            int n = 0;
            if (playerChoosenCard instanceof WildDrawCard) {
                n = 4;
            } else if (playerChoosenCard instanceof Draw2Card) {
                n = 2;
            }
            if (n > 0){
                for (int i = 0; i < n; i++) {
                    players.getHead().getNextPlayer().getPlayer().addCard(gameBoard.getDeckCards().pop());
                }
            }

            playerUseCase.setIndex(playerChoosenCard, players);
            Printer.printGameBoard(gameBoard.getBoardCard(), gameBoard.getBoardColor());
        }

        Printer.printScores(players, inputs);
    }

    @Override
    public boolean checkChoose(Card playerChoosenCard, Player player) {
        if (playerChoosenCard instanceof WildCard)
            return true;

        if (playerChoosenCard instanceof WildDrawCard) {
            for (Card card : player.getPlayerCards()) {
                if (card instanceof WildDrawCard)
                    continue;

                if (checkChoose(card, player))
                    return false;
            }
            return true;
        }

        if (gameBoard.getBoardCard() instanceof Draw2Card) {
            if (playerChoosenCard instanceof Draw2Card)
                return true;
            else
                return false;
        }

        if (gameBoard.getBoardCard() instanceof ReverseCard && playerChoosenCard instanceof ReverseCard)
            return true;

        if (Color.getBackgroundColor(playerChoosenCard.getCardColor()) == gameBoard.getBoardColor())
            return true;

        if (playerChoosenCard instanceof NumberCard && gameBoard.getBoardCard() instanceof NumberCard)
            if (playerChoosenCard.getCardScore() == gameBoard.getBoardCard().getCardScore())
                return true;

        if (playerChoosenCard instanceof SkipCard && gameBoard.getBoardCard() instanceof SkipCard)
            return true;

        return false;
    }

    @Override
    public void applyChoose(Card playerChoosenCard, Color choosenColor) {
        gameBoard.getDiscartDeck().push(playerChoosenCard);
        gameBoard.setBoardColor(Color.getBackgroundColor(choosenColor));
    }

    @Override
    public void addPlayer(Player playerToAdd) {
        playerUseCase.addPlayer(playerToAdd, players);
    }

    @Override
    public void preparationGameBoard() {
        cardsUseCase.makeGameCards(gameBoard);
        cardsUseCase.suffleCards(gameBoard.getDeckCards());

        cardsUseCase.setBoard(gameBoard);
        cardsUseCase.distributeCards(gameBoard.getDeckCards(), players);
    }

    @Override
    public void reset() {
        players = new PlayerList();
        gameBoard.setDeckCards(new Deck());
    }

    private static boolean endGame() {
        PlayerNode pAtual = players.getHead();
        for (int i = 0; i < players.getSize() - 1; i++){
            if (pAtual.getPlayer().getNumberOfPlayerCards() == 0)
                return true;

            pAtual = pAtual.getNextPlayer();
        }

        return false;
    }

    private static boolean isInt(String stringToCheck) {
        for (int n = 0; n < stringToCheck.length(); n++) {
            if (!('0' <= stringToCheck.charAt(n) && stringToCheck.charAt(0) <= '9'))
                return false;
        }

        return true;
    }
}
