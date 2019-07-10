package JavaMazeGame.Controller;

import JavaMazeGame.Environment.Door;
import JavaMazeGame.Environment.Room;
import JavaMazeGame.Item.EmptyItem;
import JavaMazeGame.Item.Item;
import JavaMazeGame.Item.Items.Banana;
import JavaMazeGame.View.View;

import java.util.ArrayList;
import java.util.Arrays;
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
                executeCommand(INVALID_COMMAND, "That room doesn't exist.\n");
            }

            boolean isDoorDestroyable = Arrays.stream(currentRoom.getDoors())
                    .anyMatch(x -> x.isDestroyable() && x.getName().equals(commandArguments));
            boolean doesPlayerHaveBomb = player1.getItems().stream()
                    .anyMatch(x -> x.getName().equals(BOMB));

            if (isDoorDestroyable && !doesPlayerHaveBomb){
                executeCommand(INVALID_COMMAND, "You need a bomb to enter that room.\n");
            }
            else if (isDoorDestroyable && doesPlayerHaveBomb){
                player1.removeItemFromBackpack(BOMB);
            }

            List<String> pastRoomNames = currentRoom.getPastRoomNames();
            currentRoom = new Room(commandArguments, pastRoomNames);

            if (currentRoom.getZombie() != null){
                player1.attack(currentRoom.getZombie());

                if (player1.getHealth() <= 0){
                    View.displayQuitMessage();
                    executeCommand(QUIT_COMMAND);
                }

                View.renderView(true, String.format("You defeated the zombie. Your health is %1$s/%2$s\n", player1.getHealth(), PLAYER_MAX_HEALTH));
            }

            View.renderView(true, "");
        }
        else if (command.equals(PICK_UP_ITEM_COMMAND)){
            Item currentItem = currentRoom.getItem();

            if (currentItem == null) {
                executeCommand(INVALID_COMMAND, "There is no such item.\n");
            }

            int newBackpackWeight = player1.getCurrentBackpackWeight() + currentItem.getWeight();

            if (newBackpackWeight <= player1.getMaxBackpackCarryWeight()
                    && commandArguments.equalsIgnoreCase(currentItem.getName())
                    && !(currentItem instanceof EmptyItem)){

                player1.getItems().add(currentItem);
                player1.setCurrentBackpackWeight(newBackpackWeight);

                currentRoom.setItem(new EmptyItem());

                if (currentItem.getName().equals(SWORD)){
                    player1.setDamage(PLAYER_BASE_DAMAGE + SWORD_BASE_DAMAGE);
                }

                executeCommand(ITEMS_COMMAND);
            }
            else {
                executeCommand(INVALID_COMMAND, "Can't pick up that item.\n");
            }

        }
        else if (command.equals(DROP_ITEM_COMMAND)){
            String commandOutput = "";

            boolean isInBackpack = player1.removeItemFromBackpack(commandArguments);

            if (!isInBackpack){
                commandOutput = "You don't have this item in your backpack.\n";
            }
            else {
                commandOutput = "You dropped " + commandOutput + "\n";

                if (commandOutput.equals(SWORD)){
                    player1.setDamage(PLAYER_BASE_DAMAGE);
                }

                commandOutput += itemCommandExecute();
            }

            View.renderView(false, commandOutput);
        }
        else if (command.equals(INVALID_COMMAND)){
            String commandOutput = "";

            if (commandArguments.equals("")){
                commandOutput = "Not a valid command\n";
            }
            else {
                commandOutput = commandArguments;
            }

            View.renderView(true, commandOutput);
        }
    }

    public static void executeCommand(String command){
        if (command.equals(HELP_COMMAND)){
            String commandOutput = "- location = prints out the current player location and the list of exits\n" +
                    "- go to “room name” = player will move to the exit that connects the current room with the “room name” and will execute the location command\n" +
                    "- items = prints out the list of items in the backpack\n" +
                    "- pick up “item name” = will pickup the item and execute the items command\n" +
                    "- drop “item name” = will drop the item and execute the items command\n" +
                    "- heal = heals the player if he has a banana\n" +
                    "- quit = exits the game\n";

            View.renderView(false, commandOutput);
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
        else if (command.equals(HEAL_COMMAND)){
            List<String> playerItemNames = new ArrayList<>();

            player1.getItems().forEach(x -> playerItemNames.add(x.getName()));

            if (playerItemNames.contains(BANANA)){
                int playerNewHealth = player1.getHealth() + Banana.HEALING_POWER;

                if (playerNewHealth < PLAYER_MAX_HEALTH){
                    player1.setHealth(playerNewHealth);
                }
                else {
                    player1.setHealth(PLAYER_MAX_HEALTH);
                }

                player1.removeItemFromBackpack(BANANA);
            }
            else {
                executeCommand(INVALID_COMMAND, "You don't have the needed item to do that.\n");
            }

            View.renderView(false, String.format("Your health is %1$s/%2$s\n", player1.getHealth(), PLAYER_MAX_HEALTH));
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
                String itemDescription = "";

                if (item.getName().equals(SWORD)){
                    itemDescription = "(This item gives you +" + SWORD_BASE_DAMAGE + " attack)";
                }
                else if (item.getName().equals(BANANA)){
                    itemDescription = "(This item gives you +" + Banana.HEALING_POWER + " health if command heal is used)";
                }
                else if (item.getName().equals(BOMB)){
                    itemDescription = "(This item lets you pass through suitable exits)";
                }

                commandOutput.append(String.format("  -%1$s : weight %2$s %3$s\n" ,item.getName(), item.getWeight(), itemDescription));
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
