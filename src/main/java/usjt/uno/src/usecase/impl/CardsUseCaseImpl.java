package usjt.uno.src.usecase.impl;

import usjt.uno.src.cards.especialcards.*;
import usjt.uno.src.entities.Player;
import usjt.uno.src.entities.Deck;
import usjt.uno.src.cards.Card;
import usjt.uno.src.usecase.CardsUseCase;
import usjt.uno.view.Color;

import java.util.ArrayList;

public class CardsUseCaseImpl implements CardsUseCase{
    @Override
    public void makeGameCards(Deck deckCards) {
        // the code of the cards
        int cardCode = 0;

        // make red cards
        makeCards(Color.RED, cardCode,deckCards);
        cardCode += 25;

        // make yellow cards
        makeCards(Color.YELLOW, cardCode,deckCards);
        cardCode += 25;

        // make green cards
        makeCards(Color.GREEN, cardCode,deckCards);
        cardCode += 25;

        // make blue cards
        makeCards(Color.BLUE, cardCode,deckCards);
        cardCode += 25;


        // make wild cards
        for (int n = 0; n < 4; n++)
            deckCards.push(new WildCard(++cardCode));

        // make wild draw cards
        for (int n = 0; n < 4; n++)
            deckCards.push(new WildDrawCard(++cardCode));
    }

    @Override
    public void suffleCards(Deck deckCards) {
        deckCards.shuffle();
    }

    @Override
    public void distributeCards(Deck deckCards, ArrayList<Player> players, Card boardCard, Color boardColor) {
        for (int n = 0; n < 7; n++) {
            for (Player p: players) {
                // get the card to the player
                p.addCard(deckCards.pop());
            }
        }

        // set the board card
        while (!(deckCards.top() instanceof NumberCard)) {
            boardCard = deckCards.pop();
            deckCards.push(boardCard);
        }

        boardCard = deckCards.pop();
        boardColor = Color.getBackgroundColor(boardCard.getCardColor());
    }

    // this method creat the cards of the given color

    private static void makeCards(Color cardColor, int cardCode, Deck deckCards) {
        // set the first set of cards
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

        // set the second set of cards
        deckCards.push(new NumberCard(1, cardColor, ++cardCode));
        deckCards.push(new NumberCard(2, cardColor, ++cardCode));
        deckCards.push(new NumberCard(3, cardColor, ++cardCode));
        deckCards.push(new NumberCard(4, cardColor, ++cardCode));
        deckCards.push(new NumberCard(5, cardColor, ++cardCode));
        deckCards.push(new NumberCard(6, cardColor, ++cardCode));
        deckCards.push(new NumberCard(7, cardColor, ++cardCode));
        deckCards.push(new NumberCard(8, cardColor, ++cardCode));
        deckCards.push(new NumberCard(9, cardColor, ++cardCode));

        // set the skip cards
        deckCards.push(new SkipCard(cardColor, ++cardCode));
        deckCards.push(new SkipCard(cardColor, ++cardCode));

        // set the reverse cards
        deckCards.push(new ReverseCard(cardColor, ++cardCode));
        deckCards.push(new ReverseCard(cardColor, ++cardCode));

        // set the draw2 cards
        deckCards.push(new Draw2Card(cardColor, ++cardCode));
        deckCards.push(new Draw2Card(cardColor, ++cardCode));
    }
}
