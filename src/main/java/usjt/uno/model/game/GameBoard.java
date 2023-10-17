package usjt.uno.model.game;

import usjt.uno.model.entities.Bot;
import usjt.uno.model.entities.Player;
import usjt.uno.model.game.cards.Card;
import usjt.uno.view.Color;

import java.util.ArrayList;

public class GameBoard {
    private ArrayList<Player> players = new ArrayList<>();
    private Deck deckCards = new Deck();
    private Card lastCard;

    public GameBoard(Player player){
        populateListOfPlayers(player);
        populateDeckOfCards();
    }

    private void populateListOfPlayers(Player player){
        players.add(player);
        players.add(new Bot(1));
        players.add(new Bot(2));
        players.add(new Bot(3));
    }

    private void populateDeckOfCards(){

    }
}
