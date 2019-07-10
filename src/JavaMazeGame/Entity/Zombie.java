package JavaMazeGame.Entity;

import JavaMazeGame.Constants.MyConstants;

public class Zombie extends Entity {

    public Zombie(){
        this.name = MyConstants.ZOMBIE;
        this.health = 6;
        this.damage = 3;
    }

}
