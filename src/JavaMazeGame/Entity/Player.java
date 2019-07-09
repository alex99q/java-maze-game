package JavaMazeGame.Entity;

import JavaMazeGame.Item.Item;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity{
    private List<Item> items;
    private int maxBackpackCarryWeight = 5;
    private int currentBackpackWeight;

    public Player(){
        if (items == null){
            items = new ArrayList<Item>();
        }
    }

    public List<Item> getItems(){
        return this.items;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public int getMaxBackpackCarryWeight(){
        return this.maxBackpackCarryWeight;
    }

    public int getCurrentBackpackWeight(){
        return this.currentBackpackWeight;
    }

    public void setCurrentBackpackWeight(int currentBackpackWeight){
        this.currentBackpackWeight = currentBackpackWeight;
    }
}
