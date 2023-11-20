package usjt.uno.src.cards.especialcards;

import usjt.uno.src.cards.Card;
import usjt.uno.view.Color;

public class WildCard extends Card {

    public WildCard(int cardCode)
    {
        super(20, Color.BLACK, cardCode);
    }

    @Override
    public String toString(int lineNumber)
    {
        if (lineNumber < 0)
            return super.toString((-1)*lineNumber);


        switch (lineNumber) {
            // the top and bottom of the card
            case 1:
            case 7: 
                return Color.getColorCodeString(Color.WHITE) + "•~~~~~~~•" +
                        Color.getColorCodeString(Color.RESET);

            case 2:
                return Color.getColorCodeString(Color.WHITE) + "|☯︎      |" +
                        Color.getColorCodeString(Color.RESET);

            case 3:
            case 5:
                return Color.getColorCodeString(Color.WHITE) + "|       |" +
                        Color.getColorCodeString(Color.RESET);

            case 4:
                return Color.getColorCodeString(Color.WHITE) + "|" + 
                       Color.getColorCodeString(Color.RED) + "W " + 
                       Color.getColorCodeString(Color.YELLOW) + "i " +
                       Color.getColorCodeString(Color.GREEN) + "l " +
                       Color.getColorCodeString(Color.BLUE) + "d" +
                       Color.getColorCodeString(Color.WHITE) + "|" +
                        Color.getColorCodeString(Color.RESET);

            case 6:
                return Color.getColorCodeString(Color.WHITE) + "|      ☯︎|" + 
                        Color.getColorCodeString(Color.RESET);

            case 8:
                return Color.getColorCodeString(Color.WHITE) + "code: " + super.getCardCode() +
                        Color.getColorCodeString(Color.RESET);
        }

        return null;
    }
}