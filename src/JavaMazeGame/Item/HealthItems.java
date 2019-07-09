package JavaMazeGame.Item;

public class HealthItems extends Item {
    protected int healingPower;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }
}
