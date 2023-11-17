package usjt.uno.src.usecase.impl;

import usjt.uno.src.cards.Card;
import usjt.uno.src.cards.especialcards.Draw2Card;
import usjt.uno.src.cards.especialcards.ReverseCard;
import usjt.uno.src.cards.especialcards.SkipCard;
import usjt.uno.src.cards.especialcards.WildDrawCard;
import usjt.uno.src.entities.Deck;
import usjt.uno.src.entities.Player;
import usjt.uno.src.entities.PlayerList.PlayerList;
import usjt.uno.src.usecase.PlayerUseCase;

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
    public boolean checkPlayerCards(Player player) {
        for (Card card : player.getPlayerCards()) {
            if (gameUseCase.checkChoose(card, player))
                return true;
        }
        return false;
    }

    @Override
    public void setIndex(Card playerChoosenCard, PlayerList players) {
        // Coringa ou bloqueio
        if (playerChoosenCard instanceof SkipCard) {
            players.playerPlayed();
            System.out.println();
            System.out.println("O jogador " + players.getHead().getPlayer().getPlayerName() + " foi pulado!");
            players.playerPlayed();
        }

        // Reverso
        else if (playerChoosenCard instanceof ReverseCard) {
            revesePlayers(players);
            players.playerPlayed();
            System.out.println();
            System.out.println("Ordem invertida!");
        }

        else if (playerChoosenCard instanceof Draw2Card) {
            players.playerPlayed();
            System.out.println();
            System.out.println("Ordem invertida!");
        }

        // other cases
        else
            players.playerPlayed();
    }

    @Override
    public void addPlayer(Player playerToAdd, PlayerList players) {
        players.insertNewPlayer(playerToAdd);
    }

    private static void revesePlayers(PlayerList players) {
        players.reverseList();
    }
}
