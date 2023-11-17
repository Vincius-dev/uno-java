package usjt.uno.src.entities;

import lombok.Data;
import usjt.uno.src.cards.Card;
import usjt.uno.view.Color;

@Data
public class GameBoard {
    private Deck deckCards = new Deck();
    private Card boardCard;
    private Color boardColor;
}
