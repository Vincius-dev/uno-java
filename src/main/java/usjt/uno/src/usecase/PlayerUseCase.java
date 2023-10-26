package usjt.uno.src.usecase;

import usjt.uno.src.entities.Player;
import usjt.uno.src.entities.Deck;
import usjt.uno.src.cards.Card;

import java.util.ArrayList;

public interface PlayerUseCase {

    int firstPlayer(ArrayList<Player> players);
    boolean checkPlayerCards(Player player);
    void giveCardToPlayer(Player currentPlayer, Deck deckCards);
    void sortPlayers(ArrayList<Player> players);
    int setIndex(Card playerChoosenCard, int currentPlayerindex,ArrayList<Player> players);
    boolean addPlayer(Player playerToAdd,ArrayList<Player> players);


}
