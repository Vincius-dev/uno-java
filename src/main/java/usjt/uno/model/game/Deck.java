package usjt.uno.model.game;

import usjt.uno.model.game.cards.Card;

import java.util.Arrays;
import java.util.Random;

public class Deck {
    private final int iSize;
    private final Card[] iStack;
    public int iPointerPosition;

    public Deck() {
        iSize = 200;
        iPointerPosition = 0;
        iStack = new Card[iSize];
    }

    public Card top(){
        if (iPointerPosition == 0){
            return null;
        } else {
            return iStack[iPointerPosition - 1];
        }
    }

    public void push(Card cardToAdd){
        if (iPointerPosition <= iSize) {
            iStack[iPointerPosition++] = cardToAdd;
        }
    }

    public Card pop(){
        if (iPointerPosition == 0){
            return null;
        } else {
            return iStack[--iPointerPosition];
        }
    }

    public void shuffle() {
        Random rand = new Random();

        for (int i = iPointerPosition - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);

            // Troque a posição das cartas i e j
            Card temp = iStack[i];
            iStack[i] = iStack[j];
            iStack[j] = temp;
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(iStack);
    }
}
