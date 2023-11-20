package usjt.uno.src.usecase.impl;

import usjt.uno.src.cards.Card;
import usjt.uno.src.cards.CardPenality;
import usjt.uno.src.cards.especialcards.Draw2Card;
import usjt.uno.src.cards.especialcards.ReverseCard;
import usjt.uno.src.cards.especialcards.SkipCard;
import usjt.uno.src.cards.especialcards.WildDrawCard;
import usjt.uno.src.entities.Deck;
import usjt.uno.src.entities.GameBoard;
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
    public void setEffectCard(Card playerChoosenCard, PlayerList players, GameBoard gameBoard) {
        // Pular turno
        if (playerChoosenCard instanceof SkipCard) {
            players.playerPlayed();
            System.out.println();
            players.getHead().getPlayer().setCurrentPenalty(CardPenality.SKIPED);
        }

        // Reverso
        else if (playerChoosenCard instanceof ReverseCard) {
            revesePlayers(players);
            System.out.println();
            System.out.println("Ordem invertida!");
        }

        //Compra 2
        else if (playerChoosenCard instanceof Draw2Card) {
            players.playerPlayed();
            System.out.println();
            System.out.println("O jogador " + players.getHead().getPlayer().getPlayerName() + " vai precisar comprar +2 cartas!");
            players.getHead().getPlayer().setCurrentPenalty(CardPenality.GET2CARDS);
            gameBoard.setCardsToDraw(gameBoard.getCardsToDraw() + 2);
        }

        //Compra 4
        else if (playerChoosenCard instanceof WildDrawCard) {
            players.playerPlayed();
            System.out.println();
            System.out.println("O jogador " + players.getHead().getPlayer().getPlayerName() + " vai precisar comprar +4 cartas!");
            players.getHead().getPlayer().setCurrentPenalty(CardPenality.GET4CARDS);
            gameBoard.setCardsToDraw(gameBoard.getCardsToDraw() + 4);
        }

        // other cases
        else
            players.playerPlayed();
    }

    @Override
    public Boolean applyPenalty(PlayerList players, GameBoard gameBoard){
        Player currentPlayer = players.getHead().getPlayer();

        if (currentPlayer.getCurrentPenalty() == null) {
            return false;

        } else if (currentPlayer.getCurrentPenalty() == CardPenality.SKIPED){
            System.out.println("O jogador " + players.getHead().getPlayer().getPlayerName() + " foi pulado!");
            currentPlayer.setCurrentPenalty(null);
            return true;

        } else if (currentPlayer.getCurrentPenalty() == CardPenality.GET2CARDS) {

            Boolean playerHasTheCard = false;

            for (Card card : currentPlayer.getPlayerCards()) {
                if (card instanceof Draw2Card) {
                    playerHasTheCard = true;
                    break;
                }
            }

            if (playerHasTheCard){
                System.out.println(players.getHead().getPlayer().getPlayerName() + " tem uma +2 para jogar.");
                currentPlayer.setCurrentPenalty(null);
                return false;

            } else {
                for (int i = 0; i < gameBoard.getCardsToDraw(); i++) {
                    System.out.println(players.getHead().getPlayer().getPlayerName() + " recebeu uma carta do baralho");
                    currentPlayer.getPlayerCards().add(gameBoard.getDeckCards().pop());
                }

                gameBoard.setCardsToDraw(0);

                currentPlayer.setCurrentPenalty(null);
                return true;
            }

        } else if (currentPlayer.getCurrentPenalty() == CardPenality.GET4CARDS) {

            Boolean playerHasTheCard = false;

            for (Card card : currentPlayer.getPlayerCards()) {
                if (card instanceof WildDrawCard) {
                    playerHasTheCard = true;
                    break;
                }
            }

            if (playerHasTheCard){
                System.out.println(players.getHead().getPlayer().getPlayerName() + " tem uma +4 para jogar.");
                currentPlayer.setCurrentPenalty(null);
                return false;

            } else {
                for (int i = 0; i < gameBoard.getCardsToDraw(); i++) {
                    System.out.println(players.getHead().getPlayer().getPlayerName() + " recebeu uma carta do baralho");
                    currentPlayer.getPlayerCards().add(gameBoard.getDeckCards().pop());
                }

                gameBoard.setCardsToDraw(0);
                currentPlayer.setCurrentPenalty(null);
                return true;
            }
        }

        return false;
    }

    @Override
    public void addPlayer(Player playerToAdd, PlayerList players) {
        players.insertNewPlayer(playerToAdd);
    }

    private static void revesePlayers(PlayerList players) {
        players.reverseList();
    }
}
