package usjt.uno.src.entities;

import usjt.uno.src.cards.Card;

import usjt.uno.src.cards.especialcards.Draw2Card;
import usjt.uno.src.cards.especialcards.WildCard;
import usjt.uno.src.cards.especialcards.WildDrawCard;
import usjt.uno.src.entities.PlayerList.PlayerList;
import usjt.uno.src.usecase.GameUseCase;
import usjt.uno.src.usecase.impl.GameUseCaseImpl;
import usjt.uno.view.Color;

import java.util.ArrayList;
import java.util.Random;

public class Bot extends Player {
    private GameUseCaseImpl gameUseCase;

    public Bot(int num, GameUseCaseImpl gameUseCase) {
        super("Bot"+num);
        this.gameUseCase = gameUseCase;
    }

    public Card playTurn() {
        System.out.println();
        Card botChoosenCard = null;
        for (int n = 0; n < super.getPlayerCards().size(); n++) {
            botChoosenCard = super.getPlayerCards().get(n);
            if (gameUseCase.checkChoose(botChoosenCard, this)) {
                this.getPlayerCards().remove(botChoosenCard);
                break;
            }
        }

        // the cases that bot must choose a color
        if (botChoosenCard instanceof WildCard || botChoosenCard instanceof WildDrawCard) {
            Random rand = new Random();
            switch (rand.nextInt(4)+1) {
                case 1:
                    gameUseCase.applyChoose(botChoosenCard, Color.RED);
                break;

                case 2:
                    gameUseCase.applyChoose(botChoosenCard, Color.YELLOW);
                break;

                case 3:
                    gameUseCase.applyChoose(botChoosenCard, Color.GREEN);
                break;

                case 4:
                    gameUseCase.applyChoose(botChoosenCard, Color.BLUE);
                break;
            }
        }
        else
            gameUseCase.applyChoose(botChoosenCard, botChoosenCard.getCardColor());

        return botChoosenCard;
    }
}