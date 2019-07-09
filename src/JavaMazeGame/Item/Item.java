package JavaMazeGame.Item;

import JavaMazeGame.Constants.MyConstants;
import JavaMazeGame.Item.Items.Banana;
import JavaMazeGame.Item.Items.Bomb;
import JavaMazeGame.Item.Items.Sword;

public abstract class Item {
    protected String name;
    protected int weight;

    public abstract String getName();

    public abstract int getWeight();

    public static Item parseItem(String itemName){
        Item item = null;

        if (itemName.equalsIgnoreCase(MyConstants.BANANA)) {
            item = new Banana();
        } else if (itemName.equalsIgnoreCase(MyConstants.BOMB)) {
            item = new Bomb();
        } else if (itemName.equalsIgnoreCase(MyConstants.SWORD)) {
            item = new Sword();
        }

        return item;
    }

    public static Item parseItem(int itemId){
        Item item = null;

        if (itemId == MyConstants.BANANA_ID) {
            item = new Banana();
        } else if (itemId == MyConstants.BOMB_ID) {
            item = new Bomb();
        } else if (itemId == MyConstants.SWORD_ID) {
            item = new Sword();
        }

        return item;
    }
}
