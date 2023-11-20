package usjt.uno.src.entities;

import lombok.Data;
import usjt.uno.src.cards.Card;
import usjt.uno.src.cards.CardPenality;

import java.util.ArrayList;

@Data
public class Player {
    private String playerName;
    private ArrayList<Card> playerCards;
    private CardPenality currentPenalty;

    public Player(String name) {
        this.playerName = name;
        this.currentPenalty = null;
        playerCards = new ArrayList<>();
    }

    public int getNumberOfPlayerCards() {
        return playerCards.size();
    }

    public void addCard(Card cardToAdd) {
        playerCards.add(cardToAdd);
    }

    public Card removeCard(int cardCodeToRemove) {
        Card cardToRemove = null;
        for (Card card: playerCards)
        {
            if (card.getCardCode() == cardCodeToRemove)
            {
                cardToRemove = card;
                break;
            }
        } 

        playerCards.remove(cardToRemove);

        return cardToRemove;
    }

    public boolean haveCard(int cardCode) {
        for (Card card: playerCards)
        {
            if (card.getCardCode() == cardCode)
                return true;
        }

        return false;
    }
}
