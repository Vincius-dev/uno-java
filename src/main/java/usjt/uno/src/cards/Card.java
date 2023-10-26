package usjt.uno.src.cards;

import lombok.AllArgsConstructor;
import lombok.Getter;
import usjt.uno.view.Color;

@Getter @AllArgsConstructor
public class Card {

    private int cardScore;
    private Color cardColor;
    private int cardCode;

    public Card(){
        cardScore = 0;
        cardColor = Color.WHITE;
        cardCode = 0;
    }

    @Override
    public boolean equals(Object obj) 
    {
        // check the pointers
        if (obj == this)
            return true;

        // check the class
        if (!(obj instanceof Card)) 
            return false;
        
        Card card = (Card) obj;
        return cardCode == card.cardCode;
    }

    public String toString(int lineNumber)
    {
        switch (lineNumber)
        {
            // the top and bottom of the card
            case 1:
            case 7: 
                return Color.getColorCodeString(Color.WHITE) + "•~~~~~~~•" + 
                        Color.getColorCodeString(Color.RESET);

            case 2:
                return Color.getColorCodeString(Color.WHITE) + "|◉  " +
                       Color.getColorCodeString(Color.GREEN) + "♢  " +
                       Color.getColorCodeString(Color.WHITE) + "◎|" +
                        Color.getColorCodeString(Color.RESET);

            case 3:
            case 5:
                return Color.getColorCodeString(Color.WHITE) + "|  " + 
                       Color.getColorCodeString(Color.GREEN) + "♢♢♢  " +
                       Color.getColorCodeString(Color.WHITE) + "|" +
                        Color.getColorCodeString(Color.RESET);

            case 4:
                return Color.getColorCodeString(Color.WHITE) + "| " + 
                       Color.getColorCodeString(Color.RED) + "U " + 
                       Color.getColorCodeString(Color.YELLOW) + "N " +
                       Color.getColorCodeString(Color.BLUE) + "O " +
                       Color.getColorCodeString(Color.WHITE) + "|" +
                        Color.getColorCodeString(Color.RESET); 

            case 6:
                return Color.getColorCodeString(Color.WHITE) + "|◎  " +
                       Color.getColorCodeString(Color.GREEN) + "♢  " + 
                       Color.getColorCodeString(Color.WHITE) + "◉|" +
                        Color.getColorCodeString(Color.RESET);
        }

        return null;
    }
}