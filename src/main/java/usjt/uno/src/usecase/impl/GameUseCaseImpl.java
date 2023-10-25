package usjt.uno.src.usecase.impl;

import usjt.uno.src.cards.Card;
import usjt.uno.src.cards.especialcards.*;
import usjt.uno.src.entities.Bot;
import usjt.uno.src.entities.Deck;
import usjt.uno.src.entities.Player;
import usjt.uno.src.usecase.CardsUseCase;
import usjt.uno.src.usecase.GameUseCase;
import usjt.uno.src.usecase.PlayerUseCase;
import usjt.uno.view.Color;
import usjt.uno.view.Printer;

import java.util.ArrayList;
import java.util.Scanner;

public class GameUseCaseImpl implements GameUseCase {


    private static ArrayList<Player> players = new ArrayList<>();
    private static Deck deckCards = new Deck();
    private static Card boardCard;
    private static Color boardColor;
    private static ArrayList<Card> penaltyCards = new ArrayList<>();
    private PlayerUseCase playerUseCase;
    private CardsUseCase cardsUseCase;

    public GameUseCaseImpl(PlayerUseCase playerUseCase, CardsUseCase cardsUseCase) {
        this.playerUseCase = playerUseCase;
        this.cardsUseCase = cardsUseCase;
    }


    @Override
    public void runGame(Scanner inputs) {
        Player currentPlayer; // hold the current player
        int currentPlayerindex = playerUseCase.firstPlayer(players); // hold the current player index
        Card playerChoosenCard; // hold the player choosen card
        String holdInput; // hold the player inputs
        Bot bot; // when bot want to play

        while (!endGame()) {
            // set the current player
            currentPlayer = players.get(currentPlayerindex);

            // the draw2 case
            if (penaltyCards.size() != 0) {
                boolean check = false;
                for (Card card : currentPlayer.getPlayerCards()) {
                    if (card instanceof Draw2Card) {
                        check = true;
                        break;
                    }
                }

                if (!check) {
                    int n = penaltyCards.size();
                    for (; n > 0; n--) {
                        currentPlayer.addCard(penaltyCards.get(0));
                        penaltyCards.remove(0);
                    }
                }

                // go the the next player
                currentPlayerindex = ((currentPlayerindex + 1) % players.size());
                continue;
            }

            // check the player cards
            if (!playerUseCase.checkPlayerCards(currentPlayer)) {
                // get a card to player
                playerUseCase.giveCardToPlayer(currentPlayer, deckCards);

                // check the player cards again
                if (!playerUseCase.checkPlayerCards(currentPlayer)) {
                    // show the board, number of the other players cards and current player cards
                    Printer.printGameBoard(boardCard, boardColor);
                    Printer.printNumberOfPlayersCards(players, currentPlayerindex);
                    Printer.printPlayerCards(currentPlayer);

                    // say to player that he/she can't choose any card
                    if (!(currentPlayer instanceof Bot))
                        Printer.noChoiceError(inputs);

                    // go the the next player
                    currentPlayerindex = ((currentPlayerindex + 1) % players.size());
                    continue;
                }
            }

            // it the current player is a bot
            if (currentPlayer instanceof Bot) {
                bot = (Bot) currentPlayer;
                playerChoosenCard = bot.playTurn(players, penaltyCards, currentPlayerindex);

                // go to the next player
                currentPlayerindex = playerUseCase.setIndex(playerChoosenCard, currentPlayerindex, players);
                continue;
            }

            // while player choose a valid card
            while (true) {
                // while player choose a valid card code
                while (true) {
                    // show the board, number of the other players cards and current player cards
                    Printer.printGameBoard(boardCard, boardColor);
                    Printer.printNumberOfPlayersCards(players, currentPlayerindex);
                    Printer.printPlayerCards(currentPlayer);

                    // ask the player choice
                    Printer.getPlayerChoice(currentPlayer);
                    holdInput = inputs.nextLine();

                    // check player choice
                    if (holdInput.length() > 0 && holdInput.length() < 4 && isInt(holdInput))
                        if (Integer.valueOf(holdInput) <= 108 && Integer.valueOf(holdInput) > 0)
                            if (currentPlayer.haveCard(Integer.valueOf(holdInput)))
                                break;

                    // say that player input is incorrect
                    Printer.inValidInputError(inputs);
                }

                // get player choosen card
                playerChoosenCard = currentPlayer.removeCard(Integer.valueOf(holdInput));

                // check the player choosen card
                if (checkChoose(playerChoosenCard, currentPlayer)) {
                    if (playerChoosenCard instanceof WildCard || playerChoosenCard instanceof WildDrawCard) {
                        // while player choose a currect input
                        while (true) {
                            // ask the player choosen color
                            Printer.getPlayerChoosenColor();
                            holdInput = inputs.nextLine();

                            // check the player input
                            if (holdInput.length() == 1 && holdInput.charAt(0) > '0' && holdInput.charAt(0) < '5')
                                break;

                            // say that player input is incorrect
                            Printer.inValidInputError(inputs);

                            // show the board, number of the other players cards and current player cards
                            Printer.printGameBoard(boardCard, boardColor);
                            Printer.printNumberOfPlayersCards(players, currentPlayerindex);
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

            // wild draw case
            if (playerChoosenCard instanceof WildDrawCard) {
                int index = (currentPlayerindex + 1) % players.size();
                int n = penaltyCards.size();

                for (; n > 0; n--) {
                    players.get(index).addCard(penaltyCards.get(0));
                    ;
                    penaltyCards.remove(0);
                }
            }

            // go to the next player
            currentPlayerindex = playerUseCase.setIndex(playerChoosenCard, currentPlayerindex, players);
        }

        playerUseCase.sortPlayers(players);
        Printer.printScores(players, inputs);
    }

    @Override
    public boolean checkChoose(Card playerChoosenCard, Player player) {
        // check the wild cards
        if (playerChoosenCard instanceof WildCard)
            return true;

        // check the wild draw cards
        if (playerChoosenCard instanceof WildDrawCard) {
            for (Card card : player.getPlayerCards()) {
                if (card instanceof WildDrawCard)
                    continue;

                if (checkChoose(card, player))
                    return false;
            }
            return true;
        }

        // check draw2 cards
        if (boardCard instanceof Draw2Card && penaltyCards.size() != 0) {
            if (playerChoosenCard instanceof Draw2Card)
                return true;
            else
                return false;
        }

        // revers card case
        if (boardCard instanceof ReverseCard && playerChoosenCard instanceof ReverseCard)
            return true;

        // check the color of cards
        if (Color.getBackgroundColor(playerChoosenCard.getCardColor()) == boardColor)
            return true;

        // check the number of number cards
        if (playerChoosenCard instanceof NumberCard && boardCard instanceof NumberCard)
            if (playerChoosenCard.getCardScore() == boardCard.getCardScore())
                return true;

        // check the skip cards
        if (playerChoosenCard instanceof SkipCard && boardCard instanceof SkipCard)
            return true;

        return false;
    }

    @Override
    public void applyChoose(Card playerChoosenCard, Color choosenColor) {
        changeBoardCard(playerChoosenCard);
        boardColor = Color.getBackgroundColor(choosenColor);

        if (playerChoosenCard instanceof WildDrawCard) {
            for (int n = 0; n < 4; n++) {
                penaltyCards.add(deckCards.pop());
            }
        } else if (playerChoosenCard instanceof Draw2Card) {
            for (int n = 0; n < 2; n++) {
                penaltyCards.add(deckCards.pop());
            }
        }
    }

    @Override
    public boolean addPlayer(Player playerToAdd) {
        return playerUseCase.addPlayer(playerToAdd, players);
    }

    @Override
    public void preparationGameCards() {
        cardsUseCase.makeGameCards(deckCards);
        cardsUseCase.suffleCards(deckCards);
    }

    @Override
    public void distributeCards() {
        cardsUseCase.distributeCards(deckCards,players,boardCard, boardColor);
    }

    @Override
    public void reset() {
        players = new ArrayList<>();
        deckCards = new Deck();
        penaltyCards = new ArrayList<>();
    }


    private static boolean endGame() {
        for (Player player : players) {
            if (player.getNumberOfPlayerCards() == 0)
                return true;
        }

        return false;
    }

    private static void changeBoardCard(Card newCard) {
        deckCards.push(boardCard);
        boardCard = newCard;
    }

    // this method check that the given string can refer to a int or not
    private static boolean isInt(String stringToCheck) {
        for (int n = 0; n < stringToCheck.length(); n++) {
            if (!('0' <= stringToCheck.charAt(n) && stringToCheck.charAt(0) <= '9'))
                return false;
        }

        return true;
    }
}
