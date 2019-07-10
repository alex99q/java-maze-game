package JavaMazeGame.Environment;

public class Door {
    private String name;
    private int doorLocation;
    private boolean isDestroyable;

    public void setName (String name){
        this.name = name;
    }

    public boolean isDestroyable() {
        return isDestroyable;
    }

    public void setDestroyable(boolean destroyable) {
        isDestroyable = destroyable;
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
