package usjt.uno.src.usecase;

import usjt.uno.src.entities.Player;
import usjt.uno.src.entities.Deck;
import usjt.uno.src.cards.Card;
import usjt.uno.src.entities.PlayerList.PlayerList;

import java.util.ArrayList;

public interface PlayerUseCase {
    boolean checkPlayerCards(Player player);
    void giveCardToPlayer(Player currentPlayer, Deck deckCards);
    void setIndex(Card playerChoosenCard, PlayerList players);
    void addPlayer(Player playerToAdd,PlayerList players);


}
