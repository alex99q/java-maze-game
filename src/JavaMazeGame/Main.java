package JavaMazeGame;

import JavaMazeGame.Entity.Player;
import JavaMazeGame.Environment.Room;
import JavaMazeGame.View.View;

public class Main {
    public static Player player1;
    public static Room currentRoom;

    public static void main(String args[]) {
        Init();
    }

    private static void Init(){
        player1 = new Player();
        currentRoom = new Room();
        View.namePrompt();
    }
}
