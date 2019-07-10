package JavaMazeGame.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import JavaMazeGame.Entity.Zombie;
import JavaMazeGame.Item.Item;

public class Room {
    private String name = "";
    private int numberOfDoors;
    private Door[] doors;
    private List<String> pastRoomNames;
    private Item item = null;
    private Zombie zombie = null;

    public Room() {
        init();
    }

    public Room(String roomName, List<String> pastRoomNames) {
        this.pastRoomNames = pastRoomNames;
        this.name = roomName;

        init();
    }

    public Door[] getDoors(){
        return this.doors;
    }

    public Zombie getZombie() {
        return zombie;
    }

    public String getName() {
        return this.name;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<String> getPastRoomNames() {
        return this.pastRoomNames;
    }

    private void init(){
        Random rand = new Random();
        numberOfDoors = rand.nextInt(3) + 1;
        doors = new Door[numberOfDoors];

        int generateZombieOrItem = rand.nextInt(3) + 1;

        if (generateZombieOrItem == 1 && pastRoomNames != null){
            zombie = new Zombie();
        }
        else {
            int itemId = rand.nextInt(3) + 1;
            item = Item.parseItem(itemId);
        }

        if (pastRoomNames == null){
            pastRoomNames = new ArrayList();
        }

        if (name.equals("")){
            this.name = generateRoomName();
        }

        for (int i = 0; i < numberOfDoors; i++) {
            doors[i] = new Door();

            if (i == 0){
                doors[i].setDestroyable(false);
            }
            else {
                doors[i].setDestroyable(rand.nextBoolean());
            }

            doors[i].setDoorLocation(generateDoorLocation());
            doors[i].setName(generateRoomName());
        }
    }

    private String generateRoomName() {
        String currentName = "";
        int counter = 0;
        while (true){
            currentName = generateNewName();

            if (counter == 100){
                pastRoomNames.clear();
                counter = 0;
            }

            if (!pastRoomNames.contains(currentName)){
                pastRoomNames.add(currentName);
                break;
            }

            counter++;
        }

        return currentName;
    }

    private String generateNewName(){
        final String letters = "abcdefghijklmnopqrstuvwxyz";
        final String numbers = "123456789";

        Random rand = new Random();

        char[] generatedName = new char[5];
        for (int i = 0; i < 5; i++){
            char currentChar;

            if (i == 4){
                currentChar = numbers.charAt(rand.nextInt(numbers.length()));
            }
            else {
                currentChar = letters.charAt(rand.nextInt(letters.length()));
            }

            generatedName[i] = currentChar;
        }

        return new String(generatedName);
    }

    private int generateDoorLocation(){
        Random rand = new Random();

        int currentDoorLocation;
        while (true){
            currentDoorLocation = rand.nextInt(3) + 1;

            boolean isDoorLocationTaken = false;
            for (Door door : doors) {

                if (door == null)
                    continue;

                if (currentDoorLocation == door.getDoorLocation()){
                    isDoorLocationTaken = true;
                }
            }

            if (!isDoorLocationTaken){
                break;
            }
        }

        return currentDoorLocation;
    }
}
