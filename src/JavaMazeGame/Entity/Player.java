package JavaMazeGame.Entity;

import JavaMazeGame.Constants.MyConstants;
import JavaMazeGame.Item.Item;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity{
    private List<Item> items;
    private int maxBackpackCarryWeight = 10;
    private int currentBackpackWeight;

    public Player(){
        items = new ArrayList<Item>();

        this.health = MyConstants.PLAYER_MAX_HEALTH;
        this.damage = MyConstants.PLAYER_BASE_DAMAGE;
    }

    public List<Item> getItems(){
        return this.items;
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

    public boolean removeItemFromBackpack(String commandArguments){
        boolean isInBackpack = true;
        for (int i = 0; i < this.items.size(); i++){
            Item currentItem = this.items.get(i);

            if (currentItem.getName().equalsIgnoreCase(commandArguments)){
                this.setCurrentBackpackWeight(this.getCurrentBackpackWeight() - this.items.get(i).getWeight());

                this.items.remove(i);
                break;
            }
            else {
                isInBackpack = false;
            }
        }

        return isInBackpack;
    }

    public void attack(Zombie zombie){
        int zombieHealth = zombie.health;

        while (zombieHealth > 0){
            zombieHealth -= this.damage;
            this.health -= zombie.damage;
        }
    }
}
