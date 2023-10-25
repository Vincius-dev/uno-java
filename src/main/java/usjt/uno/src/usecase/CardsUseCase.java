package usjt.uno.src.usecase;

import usjt.uno.src.entities.Player;
import usjt.uno.src.entities.Deck;
import usjt.uno.src.cards.Card;
import usjt.uno.view.Color;

import java.util.ArrayList;

public interface CardsUseCase {

    void makeGameCards(Deck deckCards);
    void suffleCards(Deck deckCards);
    void distributeCards(Deck deckCards, ArrayList<Player> players, Card boardCard,  Color boardColor);
}
