package usjt.uno.src.usecase;

import usjt.uno.src.entities.GameBoard;
import usjt.uno.src.entities.Player;
import usjt.uno.src.entities.Deck;
import usjt.uno.src.cards.Card;
import usjt.uno.src.entities.PlayerList.PlayerList;

public interface PlayerUseCase {
    boolean checkPlayerCards(Player player);
    void giveCardToPlayer(Player currentPlayer, Deck deckCards);
    void setEffectCard(Card playerChoosenCard, PlayerList players, GameBoard gameBoard);
    void addPlayer(Player playerToAdd,PlayerList players);
    Boolean applyPenalty(PlayerList players, GameBoard gameBoard);

}
