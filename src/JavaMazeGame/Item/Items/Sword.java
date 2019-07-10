package JavaMazeGame.Item.Items;

import JavaMazeGame.Constants.MyConstants;
import JavaMazeGame.Item.DamageItems;

public class Sword extends DamageItems {
    public Sword(){
        this.name = MyConstants.SWORD;
        this.weight = 5;
        this.attackDamage = MyConstants.SWORD_BASE_DAMAGE;
    }
}
