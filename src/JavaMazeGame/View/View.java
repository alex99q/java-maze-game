package JavaMazeGame.View;

import JavaMazeGame.Constants.MyConstants;
import JavaMazeGame.Controller.InputHandler;
import JavaMazeGame.Environment.Door;

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

        if (!commandOutput.equals("")){
            System.out.println(commandOutput);
        }

        System.out.print("What should " + player1.getName() + " do?: ");
        InputHandler.inputCommand();
    }

    public static void namePrompt() {
        System.out.print("Enter the name of your character to start the game: ");

        InputHandler.inputName();
    }

    public static void displayQuitMessage(){
        clearScreen();
        renderRoom();

        System.out.println("You died. Game over.");
    }

    private static void renderRoom() {
        boolean hasLeftExit = false;
        boolean hasUpperExit = false;
        boolean hasRightExit = false;

        boolean isLeftExitDestroyable = false;
        boolean isUpperExitDestroyable = false;
        boolean isRightExitDestroyable = false;

        String itemNameOrZombieStr = "";

        if (currentRoom.getItem() != null){
            itemNameOrZombieStr = "Item: " + currentRoom.getItem().getName();
        }
        else {
            itemNameOrZombieStr = currentRoom.getZombie().getName();
        }

        String leftExitName = "";
        String rightExitName = "";
        String upperExitName = "";

        Door[] doorsInCurrentRoom = currentRoom.getDoors();

        for (Door door : doorsInCurrentRoom) {
            if (door.getDoorLocation() == MyConstants.LEFT_WALL){
                hasLeftExit = true;
                isLeftExitDestroyable = door.isDestroyable();
                leftExitName = door.getName() + " Room";
            }
            else if (door.getDoorLocation() == MyConstants.UPPER_WALL){
                hasUpperExit = true;
                isUpperExitDestroyable = door.isDestroyable();
                upperExitName = door.getName() + " Room";
            }
            else if (door.getDoorLocation() == MyConstants.RIGHT_WALL){
                hasRightExit = true;
                isRightExitDestroyable = door.isDestroyable();
                rightExitName = door.getName() + " Room";
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
                            System.out.print(leftExitName);
                            col += leftExitName.length() - 1;
                        }
                        else if (col == LEFT_DOOR_NAME_START_POSITION - 1
                                && hasLeftExit && row == VERTICAL_NAME_START_POSITION + 1 && isLeftExitDestroyable) { // Prints "Bomb Needed" if the left door is destroyable
                            System.out.print("(Bomb Needed)");
                            col += 12;
                        }
                        else if (col == RIGHT_DOOR_NAME_START_POSITION
                                && hasRightExit && row == VERTICAL_NAME_START_POSITION) { // Prints the right exit name if a right door exists
                            System.out.print(rightExitName);
                            col += rightExitName.length() - 1;
                        }
                        else if (col == RIGHT_DOOR_NAME_START_POSITION - 2
                                && hasRightExit && row == VERTICAL_NAME_START_POSITION + 1 && isRightExitDestroyable) { // Prints "Bomb Needed" if the right door is destroyable
                            System.out.print("(Bomb Needed)");
                            col += 12;
                        }
                        else if (row == 2
                                && col >= DOOR_HORIZONTAL_START_POSITION && col < DOOR_HORIZONTAL_START_POSITION + DOOR_WIDTH
                                && hasUpperExit){ // Prints upper door
                            System.out.print("#");
                        }
                        else if (row == 3
                                && col == HORIZONTAL_DOOR_NAME_START_POSITION
                                && hasUpperExit){ // Prints the upper exit name if an upper door exists
                            System.out.print(upperExitName);
                            col += upperExitName.length() - 1;
                        }
                        else if (row == 4
                                && col == HORIZONTAL_DOOR_NAME_START_POSITION - 1
                                && hasUpperExit
                                && isUpperExitDestroyable){ // Prints "Bomb Needed" if the upper door is destroyable
                            System.out.print("(Bomb Needed)");
                            col += 12;
                        }
                        else if (row == VERTICAL_NAME_START_POSITION
                                && col == ITEM_NAME_START_POSITION){ // Prints the item name of a rooms item
                            System.out.print(itemNameOrZombieStr);
                            col += itemNameOrZombieStr.length() - 1;
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
