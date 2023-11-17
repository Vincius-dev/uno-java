package usjt.uno.src.usecase.impl;

import usjt.uno.src.cards.Card;
import usjt.uno.src.cards.especialcards.*;
import usjt.uno.src.entities.GameBoard;
import usjt.uno.src.entities.Deck;
import usjt.uno.src.entities.PlayerList.PlayerList;
import usjt.uno.src.entities.PlayerList.PlayerNode;
import usjt.uno.src.usecase.CardsUseCase;
import usjt.uno.view.Color;

public class CardsUseCaseImpl implements CardsUseCase{
    @Override
    public void makeGameCards(GameBoard gameBoard) {
        // the code of the cards
        int cardCode = 0;

        // make red cards
        makeCards(Color.RED, cardCode,gameBoard.getDeckCards());
        cardCode += 25;

        // make yellow cards
        makeCards(Color.YELLOW, cardCode,gameBoard.getDeckCards());
        cardCode += 25;

        // make green cards
        makeCards(Color.GREEN, cardCode,gameBoard.getDeckCards());
        cardCode += 25;

        // make blue cards
        makeCards(Color.BLUE, cardCode,gameBoard.getDeckCards());
        cardCode += 25;

        // make wild cards
        for (int n = 0; n < 4; n++)
            gameBoard.getDeckCards().push(new WildCard(++cardCode));

        // make wild draw cards
        for (int n = 0; n < 4; n++)
            gameBoard.getDeckCards().push(new WildDrawCard(++cardCode));
    }

    @Override
    public void suffleCards(Deck deckCards) {
        deckCards.shuffle();
        System.out.println("As cartas foram embaralhadas");
    }

    @Override
    public void distributeCards(Deck deckCards, PlayerList players) {
        for (int n = 0; n < 7; n++) {

            PlayerNode pAtual = players.getHead();
            for (int i = 0; i < players.getSize(); i++){

                pAtual.getPlayer().addCard(deckCards.pop());

                pAtual = pAtual.getNextPlayer();
            }
        }
    }

    @Override
    public void setBoard(GameBoard gameBoard) {

        System.out.println("\n Colocando as cartas na mesa...");

        while (!(gameBoard.getDeckCards().top() instanceof NumberCard)) {
            gameBoard.getDeckCards().pop();
        }

        gameBoard.getDiscartDeck().push(gameBoard.getDeckCards().pop());
        gameBoard.setBoardColor(Color.getBackgroundColor(gameBoard.getBoardCard().getCardColor()));
    }

    private static void makeCards(Color cardColor, int cardCode, Deck deckCards) {
        deckCards.push(new NumberCard(0, cardColor, ++cardCode));
        deckCards.push(new NumberCard(1, cardColor, ++cardCode));
        deckCards.push(new NumberCard(2, cardColor, ++cardCode));
        deckCards.push(new NumberCard(3, cardColor, ++cardCode));
        deckCards.push(new NumberCard(4, cardColor, ++cardCode));
        deckCards.push(new NumberCard(5, cardColor, ++cardCode));
        deckCards.push(new NumberCard(6, cardColor, ++cardCode));
        deckCards.push(new NumberCard(7, cardColor, ++cardCode));
        deckCards.push(new NumberCard(8, cardColor, ++cardCode));
        deckCards.push(new NumberCard(9, cardColor, ++cardCode));

        deckCards.push(new NumberCard(1, cardColor, ++cardCode));
        deckCards.push(new NumberCard(2, cardColor, ++cardCode));
        deckCards.push(new NumberCard(3, cardColor, ++cardCode));
        deckCards.push(new NumberCard(4, cardColor, ++cardCode));
        deckCards.push(new NumberCard(5, cardColor, ++cardCode));
        deckCards.push(new NumberCard(6, cardColor, ++cardCode));
        deckCards.push(new NumberCard(7, cardColor, ++cardCode));
        deckCards.push(new NumberCard(8, cardColor, ++cardCode));
        deckCards.push(new NumberCard(9, cardColor, ++cardCode));

        deckCards.push(new SkipCard(cardColor, ++cardCode));
        deckCards.push(new SkipCard(cardColor, ++cardCode));

        deckCards.push(new ReverseCard(cardColor, ++cardCode));
        deckCards.push(new ReverseCard(cardColor, ++cardCode));

        deckCards.push(new Draw2Card(cardColor, ++cardCode));
        deckCards.push(new Draw2Card(cardColor, ++cardCode));
    }

    @Override
    public void getCardsBackFromDiscart(GameBoard gameBoard) {
        while (true){
            Card cardFromDiscart = gameBoard.getDiscartDeck().pop();

            if (cardFromDiscart != null){
                gameBoard.getDeckCards().push(cardFromDiscart);
            } else {
                break;
            }
        }
    }
}
