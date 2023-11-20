package usjt.uno.src.entities.PlayerList;

import lombok.Data;
import usjt.uno.src.entities.Player;

@Data
public class PlayerNode {
    private Player player;
    private PlayerNode nextPlayer;

    public PlayerNode(Player p){
        this.player = p;
        this.nextPlayer = null;
    }
}
