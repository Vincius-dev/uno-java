package usjt.uno.src.entities.PlayerList;

import lombok.Data;
import usjt.uno.src.entities.Player;

@Data
public class PlayerList {
    private PlayerNode head;
    private PlayerNode lastPlayer;
    private int size = 0;

    public void insertNewPlayer(Player p){
        PlayerNode newPlayer = new PlayerNode(p);

        if (size == 0){
            this.head = newPlayer;
        } else {
            this.lastPlayer.setNextPlayer(newPlayer);
        }

        this.lastPlayer = newPlayer;
        this.size++;
    }

    @Override
    public String toString() {
        if (this.size == 0){
            return "[]";
        }

        StringBuilder builder = new StringBuilder("[");

        PlayerNode pAtual = this.head;
        for (int i = 0; i < this.size - 1; i++){
            builder.append(pAtual.getPlayer().getPlayerName()).append(", ");
            pAtual = pAtual.getNextPlayer();
        }

        builder.append(pAtual.getPlayer().getPlayerName()).append("]");

        return "Lista de jogadores: " + builder.toString();
    }
}
