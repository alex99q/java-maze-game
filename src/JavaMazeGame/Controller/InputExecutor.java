package JavaMazeGame.Controller;

import JavaMazeGame.Environment.Door;
import JavaMazeGame.Environment.Room;
import JavaMazeGame.Item.EmptyItem;
import JavaMazeGame.Item.Item;
import JavaMazeGame.View.View;
import sun.invoke.empty.Empty;

import java.util.List;

import static JavaMazeGame.Main.currentRoom;
import static JavaMazeGame.Main.player1;
import static JavaMazeGame.Constants.MyConstants.*;

public class InputExecutor {
    public static void executeCommand(String command, String commandArguments){
        if (command.equals(NAME_COMMAND)){
            player1.setName(commandArguments);
            View.renderView(true, "");
        }
        else if (command.equals(GOTO_COMMAND)){

            if (!isRoomValid(commandArguments)){
                executeCommand(INVALID_COMMAND);
            }

            List<String> pastRoomNames = currentRoom.getPastRoomNames();

            currentRoom = new Room(commandArguments, pastRoomNames);

            View.renderView(true, "");
        }
        else if (command.equals(PICK_UP_ITEM_COMMAND)){
            Item currentItem = currentRoom.getItem();
            int newBackpackWeight = player1.getCurrentBackpackWeight() + currentItem.getWeight();

            if (newBackpackWeight <= player1.getMaxBackpackCarryWeight()
                    && commandArguments.equalsIgnoreCase(currentItem.getName()) && !(currentItem instanceof EmptyItem)){

                player1.getItems().add(currentItem);
                player1.setCurrentBackpackWeight(newBackpackWeight);

                currentRoom.setItem(new EmptyItem());

                executeCommand(ITEMS_COMMAND);
            }
            else {
                executeCommand(INVALID_COMMAND);
            }

        }
        else if (command.equals(DROP_ITEM_COMMAND)){
            List<Item> playerItems = player1.getItems();
            String commandOutput = "";

            boolean isInBackpack = true;
            for (int i = 0; i < playerItems.size(); i++){
                if (playerItems.get(i).getName().equalsIgnoreCase(commandArguments)){
                    player1.setCurrentBackpackWeight(player1.getCurrentBackpackWeight() - playerItems.get(i).getWeight());
                    playerItems.remove(i);
                    break;
                }
                else {
                    isInBackpack = false;
                }
            }

            if (!isInBackpack){
                commandArguments = "You don't have this item in your backpack.\n";
            }
            else {
                commandArguments = "You dropped " + commandArguments + "\n";
                commandArguments += itemCommandExecute();
            }

            View.renderView(false, commandArguments);
        }
    }

    public static void executeCommand(String command){
        if (command.equals(HELP_COMMAND)){
            String commandOutput = "- location = prints out the current player location and the list of exits\n" +
                    "- go to “room name” = player will move to the exit that connects the current room with the “room name” and will execute the location command\n" +
                    "- items = prints out the list of items in the backpack\n" +
                    "- pick up “item name” = will pickup the item and execute the items command\n" +
                    "- drop “item name” = will drop the item and execute the items command\n" +
                    "- quit = exits the game\n";

            View.renderView(false, commandOutput);
        }
        else if (command.equals(INVALID_COMMAND)){
            View.renderView(true, "Not a valid command");
        }
        else if (command.equals(LOCATION_COMMAND)){
            StringBuilder commandOutput = new StringBuilder("Your location is \"" + currentRoom.getName() + "\" and the exits in this room are:\n");

            for (Door door : currentRoom.getDoors()) {
                commandOutput.append("  -").append(door.getName()).append("\n");
            }

            View.renderView(true, commandOutput.toString());
        }
        else if (command.equals(ITEMS_COMMAND)){
            String commandOutput = itemCommandExecute();

            View.renderView(false, commandOutput);
        }
        else if (command.equals(QUIT_COMMAND)){
            System.exit(0);
        }
    }

    private static String itemCommandExecute() {
        List<Item> items = player1.getItems();

        StringBuilder commandOutput;

        if (items.size() == 0){
            commandOutput = new StringBuilder("You don't have any items at the moment.\n");
        }
        else {
            commandOutput = new StringBuilder("Your items are \n");

            for (Item item : items) {
                commandOutput.append(String.format("  -%1$s : weight %2$s\n" ,item.getName(), item.getWeight()));
            }

            commandOutput.append(String.format("You have %1$s/%2$s backpack space\n", player1.getCurrentBackpackWeight(), player1.getMaxBackpackCarryWeight()));
        }

        return commandOutput.toString();
    }

    private static boolean isRoomValid(String roomName){
        Door[] doors = currentRoom.getDoors();

        boolean isValid = false;
        for (Door door : doors) {
            if (door.getName().equals(roomName)){
                isValid = true;
            }
        }

        return isValid;
    }
}
