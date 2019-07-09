package JavaMazeGame.Item;

public class DamageItems extends Item {
    protected int attackDamage;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }
}
