package usjt.uno.model.game;

import usjt.uno.model.game.cards.Card;

import java.util.Arrays;

public class Deck {
    private int iSize;
    private Card iStack[];
    public int iPointerPosition;

    public Deck() {
        iSize = 112;
        iPointerPosition = 0;
        iStack = new Card[iSize];
    }

    public int size(){
        return iPointerPosition;
    }

    public Card push(Card cardToAdd){
        if ( iPointerPosition >= iSize){
            return null;
        } else {
            return iStack[iPointerPosition++] = cardToAdd;
        }
    }

    public Card pop(){
        if (iPointerPosition == 0){
            return null;
        } else {
            return iStack[--iPointerPosition];
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(iStack);
    }
}
