package usjt.uno;

import usjt.uno.src.entities.Player;
import usjt.uno.src.entities.PlayerList.PlayerList;

public class Testes {
    public static void main(String[] args) {

        PlayerList playerList = new PlayerList();

        playerList.insertNewPlayer(new Player("João"));

        System.out.println(playerList);

        playerList.insertNewPlayer(new Player("Miguel"));
        playerList.insertNewPlayer(new Player("Bot"));

        System.out.println(playerList);

    }
}
