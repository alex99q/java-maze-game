package JavaMazeGame.Item.Items;

import JavaMazeGame.Constants.MyConstants;
import JavaMazeGame.Item.HealthItems;

public class Banana extends HealthItems {
    public static final int HEALING_POWER = 3;

    public Banana(){
        this.name = MyConstants.BANANA;
        this.weight = 3;
    }
}
