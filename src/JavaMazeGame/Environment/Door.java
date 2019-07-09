package JavaMazeGame.Environment;

public class Door {
    private String name;
    private int doorLocation;
    private boolean isLocked;

    public void setName (String name){
        this.name = name;
    }

    public String getName (){
        return this.name;
    }

    public void setDoorLocation (int doorLocation){
        this.doorLocation = doorLocation;
    }

    public int getDoorLocation (){
        return this.doorLocation;
    }
}
