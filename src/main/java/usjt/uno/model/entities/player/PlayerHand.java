package usjt.uno.model.entities.player;

import usjt.uno.model.game.cards.Card;
import usjt.uno.view.Color;

import java.util.ArrayList;

public class PlayerHand {
    ArrayList<Card> listaDeCards = new ArrayList<>();

    public void addCardToHand(Card card){
        listaDeCards.add(card);
    }

    public void searchCard(int code){

    }

    public Card removeCard(int code){
        return null;
    }
}
