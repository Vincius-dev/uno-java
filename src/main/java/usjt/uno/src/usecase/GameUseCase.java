package usjt.uno.src.usecase;

import usjt.uno.src.entities.Player;
import usjt.uno.src.cards.Card;
import usjt.uno.view.Color;

import java.util.Scanner;

public interface GameUseCase {

    void runGame(Scanner inputs);
    boolean checkChoose(Card playerChoosenCard, Player player);
    void applyChoose(Card playerChoosenCard, Color choosenColor);
    boolean addPlayer(Player playerToAdd);
    void preparationGameCards();
    void distributeCards();
    void reset();

}
