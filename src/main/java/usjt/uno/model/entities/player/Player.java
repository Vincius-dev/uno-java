package usjt.uno.model.entities.player;

import lombok.Data;
import usjt.uno.model.game.cards.Card;

import java.util.ArrayList;

@Data
public class Player {
    protected int score;
    private String playerName;
    private ArrayList<Card> playerCards;

    public Player(String name) {
        this.playerName = name;
        this.score = 0;

        playerCards = new ArrayList<>();
    }

    public int getNumberOfPlayerCards() {
        return playerCards.size();
    }

    public void addCard(Card cardToAdd) {
        score += cardToAdd.getCardScore();
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

        score -= cardToRemove.getCardScore();
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
