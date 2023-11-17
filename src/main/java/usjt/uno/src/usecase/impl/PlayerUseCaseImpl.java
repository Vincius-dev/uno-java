package usjt.uno.src.usecase.impl;

import usjt.uno.src.cards.Card;
import usjt.uno.src.cards.especialcards.ReverseCard;
import usjt.uno.src.cards.especialcards.SkipCard;
import usjt.uno.src.cards.especialcards.WildDrawCard;
import usjt.uno.src.entities.Deck;
import usjt.uno.src.entities.Player;
import usjt.uno.src.usecase.PlayerUseCase;


import java.util.ArrayList;
import java.util.Random;

public class PlayerUseCaseImpl implements PlayerUseCase {


    private GameUseCaseImpl gameUseCase;


    public PlayerUseCaseImpl(GameUseCaseImpl gameUseCase){
        this.gameUseCase = gameUseCase;
    }


    @Override
    public void giveCardToPlayer(Player currentPlayer, Deck deckCards) {
        currentPlayer.addCard(deckCards.pop());
    }

    @Override
    public int firstPlayer(ArrayList<Player> players) {
        Random rand = new Random();
        return rand.nextInt(players.size());
    }

    @Override
    public boolean checkPlayerCards(Player player) {
        for (Card card : player.getPlayerCards()) {
            if (gameUseCase.checkChoose(card, player))
                return true;
        }
        return false;
    }

    @Override
    public void sortPlayers(ArrayList<Player> players) {
        // hold the player for swap
        Player holdPlayer;

        for (int i = 0; i < players.size(); i++)
            for (int j = i; j < players.size(); j++)
                if (players.get(i).getScore() > players.get(j).getScore()) {
                    holdPlayer = players.get(i);
                    players.set(i, players.get(j));
                    players.set(j, holdPlayer);
                } else if (players.get(i).getScore() == players.get(j).getScore()
                        &&
                        (players.get(i).getNumberOfPlayerCards() > players.get(j).getNumberOfPlayerCards())) {
                    holdPlayer = players.get(i);
                    players.set(i, players.get(j));
                    players.set(j, holdPlayer);
                }
    }

    @Override
    public int setIndex(Card playerChoosenCard, int currentPlayerindex, ArrayList<Player> players) {
        // skip card case
        if (playerChoosenCard instanceof SkipCard || playerChoosenCard instanceof WildDrawCard)
            currentPlayerindex = currentPlayerindex + 2;

            // reverse card case
        else if (playerChoosenCard instanceof ReverseCard) {
            revesePlayers(players);
            currentPlayerindex = (players.size() - currentPlayerindex);
        }

        // finish one round case
        else if (currentPlayerindex + 1 == players.size())
            currentPlayerindex = 0;

            // other cases
        else
            currentPlayerindex++;

        return (currentPlayerindex % players.size());
    }

    @Override
    public boolean addPlayer(Player playerToAdd, ArrayList<Player> players) {
        return players.add(playerToAdd);
    }


    private static void revesePlayers(ArrayList<Player> players) {
        // hold the player for swap
        Player holdPlayer;

        for (int first = 0, end = players.size() - 1; first < players.size() / 2; first++, end--) {
            holdPlayer = players.get(first);
            players.set(first, players.get(end));
            players.set(end, holdPlayer);
        }
    }
}
