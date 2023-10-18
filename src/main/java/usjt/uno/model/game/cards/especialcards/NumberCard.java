package usjt.uno.model.game.cards.especialcards;


import lombok.Getter;
import usjt.uno.model.game.cards.Card;
import usjt.uno.view.Color;

public class NumberCard extends Card {

    @Getter
    private int cardNumber;

    public NumberCard(int cardNumber, Color cardColor, int cardCode) {
        // set the super class
        super(cardNumber, cardColor, cardCode);

        // set the number of the card
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString(int lineNumber) {
        if (lineNumber < 0)
            return super.toString((-1)*lineNumber);

        String cardColorCode = Color.getColorCodeString(super.getCardColor());

        switch (lineNumber) {
            // the top and bottom of the card
            case 1:
            case 7: 
                return cardColorCode + "•~~~~~~~•" + 
                        Color.getColorCodeString(Color.RESET);

            case 2:
                return cardColorCode + "|" + cardNumber + "      |" + 
                        Color.getColorCodeString(Color.RESET);

            case 3:
            case 5:
                return cardColorCode + "|       |" + 
                        Color.getColorCodeString(Color.RESET);

            case 4:
                return cardColorCode + "|   " + cardNumber + "   |" +
                        Color.getColorCodeString(Color.RESET);

            case 6:
                return cardColorCode + "|      " + cardNumber + "|" +
                        Color.getColorCodeString(Color.RESET);

            case 8:
                return Color.getColorCodeString(Color.WHITE) + "code: " + super.getCardCode() +
                        Color.getColorCodeString(Color.RESET);
        }

        return null;
    }
}