import java.util.ArrayList;
import java.util.Random;

public class Creature {
    private double replicationChance;
    private double deathChance;
    
    public Creature(double replicationChance, double deathChance) {
        this.replicationChance = replicationChance;
        this.deathChance = deathChance;
    }
    
    public void die() {
        System.out.println("Creature has died.");
    }
    
    public Creature reproduce() {
        Random rand = new Random();
        if (rand.nextDouble() < replicationChance) {
            System.out.println("Creature has reproduced.");
            return new Creature(replicationChance, deathChance);
        } else {
            System.out.println("Creature failed to reproduce.");
            return null;
        }
    }
    
    public void eat(Food food) {
        if (food != null) {
            System.out.println("Creature has eaten the food.");
            food.beEaten();
        }
    }
}

class World {
    private ArrayList<Creature> creatures;
    private double foodSpawnChance;
    private double foodEatenChance;
    private int maxCreatures;
    
    public World(double foodSpawnChance, double foodEatenChance, int maxCreatures) {
        this.foodSpawnChance = foodSpawnChance;
        this.foodEatenChance = foodEatenChance;
        this.maxCreatures = maxCreatures;
        creatures = new ArrayList<Creature>();
    }
    
    public Creature createCreature() {
        if (creatures.size() < maxCreatures) {
            Creature newCreature = new Creature(0.5, 0.1);
            creatures.add(newCreature);
            System.out.println("Creature has been created.");
            return newCreature;
        } else {
            System.out.println("Cannot create new creature, maximum creatures reached.");
            return null;
        }
    }
    
    public void spawnFood() {
        Random rand = new Random();
        if (rand.nextDouble() < foodSpawnChance) {
            new Food(foodEatenChance);
            System.out.println("Food has spawned.");
        }
    }
    
    public void runSimulation(int numIterations) {
        for (int i = 0; i < numIterations; i++) {
            spawnFood();
            for (Creature creature : creatures) {
                creature.eat(Food.getFood());
                creature.reproduce();
            }
        }
    }
}

class Food {
    private static Food food;
    private double eatenChance;
    
    Food(double eatenChance) {
        this.eatenChance = eatenChance;
    }
    
    public static Food getFood() {
        Random rand = new Random();
        if (food == null && rand.nextDouble() < 0.2) {
            food = new Food(0.5);
            System.out.println("New food has been created.");
        }
        return food;
    }
    
    public void beEaten() {
        Random rand = new Random();
        if (rand.nextDouble() < eatenChance) {
            System.out.println("Food has been eaten.");
            food = null;
        } else {
            System.out.println("Food has not been eaten.");
        }
    }
}

class Main {
    public static void main(String[] args) {
        World world = new World(0.3, 0.8, 5);
        world.createCreature();
        world.runSimulation(10);
    }
}
