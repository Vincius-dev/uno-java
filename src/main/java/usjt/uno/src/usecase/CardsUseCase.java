package usjt.uno.src.usecase;

import usjt.uno.src.cards.Card;
import usjt.uno.src.entities.Deck;
import usjt.uno.src.entities.GameBoard;
import usjt.uno.src.entities.PlayerList.PlayerList;
import usjt.uno.src.usecase.impl.GameUseCaseImpl;
import usjt.uno.view.Color;

public interface CardsUseCase {

    void makeGameCards(GameBoard gameBoard);
    void suffleCards(Deck deckCards);
    void distributeCards(Deck deckCards, PlayerList players);
    void setBoard(GameBoard gameBoard);
}
