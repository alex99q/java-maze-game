package JavaMazeGame.View;

import JavaMazeGame.Constants.MyConstants;
import JavaMazeGame.Controller.InputHandler;
import JavaMazeGame.Environment.Door;
import JavaMazeGame.Item.Item;
import JavaMazeGame.Item.Items.Bomb;

import static JavaMazeGame.Main.currentRoom;
import static JavaMazeGame.Main.player1;

public class View {
    private static final int ROOM_WIDTH = 100;
    private static final int ROOM_HEIGHT = 25;
    private static final int HORIZONTAL_DOOR_NAME_START_POSITION = 40;
    private static final int VERTICAL_NAME_START_POSITION = 14;
    private static final int ITEM_NAME_START_POSITION = 37;
    private static final int LEFT_DOOR_NAME_START_POSITION = 4;
    private static final int RIGHT_DOOR_NAME_START_POSITION = ROOM_WIDTH - 12;
    private static final int DOOR_WIDTH = 5;
    private static final int DOOR_VETICAL_START_POSITION = 12;
    private static final int DOOR_HORIZONTAL_START_POSITION = 42;


    public static void renderView(boolean needRefresh, String commandOutput){
        if(needRefresh){
            clearScreen();
            renderRoom();
        }

        if (commandOutput != ""){
            System.out.println(commandOutput);
        }

        System.out.print("What should " + player1.getName() + " do?: ");
        InputHandler.inputCommand();
    }

    public static void namePrompt() {
        System.out.print("Enter the name of your character to start the game: ");

        InputHandler.inputName();
    }

    private static void renderRoom() {
        boolean hasLeftExit = false;
        boolean hasUpperExit = false;
        boolean hasRightExit = false;

        String itemName = currentRoom.getItem().getName();

        String leftExitName = "";
        String rightExitName = "";
        String upperExitName = "";

        Door[] doorsInCurrentRoom = currentRoom.getDoors();

        for (Door door : doorsInCurrentRoom) {
            if (door.getDoorLocation() == MyConstants.LEFT_WALL){
                hasLeftExit = true;
                leftExitName = door.getName();
            }
            else if (door.getDoorLocation() == MyConstants.UPPER_WALL){
                hasUpperExit = true;
                upperExitName = door.getName();
            }
            else if (door.getDoorLocation() == MyConstants.RIGHT_WALL){
                hasRightExit = true;
                rightExitName = door.getName();
            }
        }

        for (int row = 1; row <= ROOM_HEIGHT; row++){ // Prints the current room
            for (int col = 1; col <= ROOM_WIDTH; col++){
                if (row == 1 || row == ROOM_HEIGHT){ // Prints the upper and bottom walls
                    System.out.print("#");
                }
                else {
                    if (col == 1 || col == ROOM_WIDTH) { // Prints the left and right walls
                        System.out.print("#");
                    }
                    else {
                        if (row >= DOOR_VETICAL_START_POSITION && row < DOOR_VETICAL_START_POSITION + DOOR_WIDTH
                                && (hasLeftExit && col == 2  || hasRightExit && col == ROOM_WIDTH - 1)) { // Prints left and right door
                                System.out.print("#");
                        }
                        else if (col == LEFT_DOOR_NAME_START_POSITION
                                && hasLeftExit && row == VERTICAL_NAME_START_POSITION) { // Prints the left exit name if a left door exists
                            System.out.print(leftExitName + " Room");
                            col = col + 9;
                        }
                        else if (col == RIGHT_DOOR_NAME_START_POSITION
                                && hasRightExit && row == VERTICAL_NAME_START_POSITION) { // Prints the right exit name if a right door exists
                            System.out.print(rightExitName + " Room");
                            col = col + 9;
                        }
                        else if (row == 2
                                && col >= DOOR_HORIZONTAL_START_POSITION && col < DOOR_HORIZONTAL_START_POSITION + DOOR_WIDTH
                                && hasUpperExit){ // Prints upper door
                            System.out.print("#");
                        }
                        else if (row == 3
                                && col == HORIZONTAL_DOOR_NAME_START_POSITION
                                && hasUpperExit){ // Prints the upper exit name if an upper door exists
                            System.out.print(upperExitName + " Room");
                            col = col + 9;
                        }
                        else if (row == VERTICAL_NAME_START_POSITION
                                && col == ITEM_NAME_START_POSITION){ // Prints the item name of a room's item
                            System.out.print( "Item: " + itemName);
                            col = col + itemName.length() + 5;
                        }
                        else {
                            System.out.print(" ");
                        }
                    }
                }
            }
            System.out.println();
        }
    }

    private static void clearScreen(){
        for(int i = 0; i < 50; i++){
            System.out.println();
        }
    }
}
