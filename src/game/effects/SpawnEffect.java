package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.conditions.Condition;
import game.offspring.producer.Producer;
import game.utilities.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * This class represents the side effect of spawning an actor
 *
 * @author ngziwei
 * @version 1.0
 */
public class SpawnEffect implements SideEffect{
    private Actor targetActor;
    private Producer producer;
    private Condition itemCondition = null;

    /**
     * Constructor for effect to spawn Creature that is able to produce offspring
     * @param producer the Producer to create the Spawn Creature
     */
    public SpawnEffect(Producer producer){
        this.producer = producer;
    }

    /**
     * Constructor for effect to spawn Creature that is able to produce an Item
     *
     * @param producer The Producer to create the Spawn Creature
     * @param itemCondition The condition for the Item produced by Spawned Creature to hatch into something
     */
    public SpawnEffect(Producer producer, Condition itemCondition) {
        this.producer = producer;
        this.itemCondition = itemCondition;
    }

    /**
     * Constructor for effect to spawn Creature that is able to produce an offspring around a target Actor
     *
     * @param producer the Producer to produce the Creature
     * @param targetActor The actor that the spawnedActor need to spawn around
     */
    public SpawnEffect(Producer producer, Actor targetActor) {
        this.producer = producer;
        this.targetActor = targetActor;
    }

    /**
     * Constructor for effect to spawn Creature that is able to produce an Item around a target Actor
     * @param producer the Producer to produce the Creature
     * @param itemCondition the condition for the item produced by spawned creature to hatch into something
     * @param targetActor The actor that the spawnedActor need to spawn around
     */
    public SpawnEffect(Producer producer, Condition itemCondition, Actor targetActor) {
        this.producer = producer;
        this.itemCondition = itemCondition;
        this.targetActor = targetActor;
    }

    /**
     * This method apply the spawn effect
     *
     * @param actor The actor affected by or involved in the spawn side effect
     * @param map The map the affected or involved actor is on
     */
    @Override
    public void apply(Actor actor, GameMap map) {

        Location actorLoc;

        if (targetActor != null){
            actorLoc = map.locationOf(targetActor);
        }else{
            actorLoc = map.locationOf(actor);
        }

        List<Exit> availableExits = new ArrayList<>();

        // Check the exit that an actor can spawn
        for (Exit exit : actorLoc.getExits()){
            Location targetLoc = exit.getDestination();

            if (!targetLoc.containsAnActor() && targetLoc.canActorEnter(actor)){
                availableExits.add(exit);
            }
        }

        // Spawn the spawnedActor at a random exit
        if (availableExits.size() > 0)
        {
            int randExit = Utils.generateRandomNumber(availableExits.size());
            Location randSpawnLoc = availableExits.get(randExit).getDestination();
            Actor actorSpawned;

            // check if itemCondition is not null first
            // not null means Producer is to produce Creature that can produce item
            // else means Producer is to produce Creature that can produce offspring
            if (itemCondition != null){
                actorSpawned = producer.create(itemCondition);
            }
            else{
                actorSpawned = producer.create();
            }

            map.addActor(actorSpawned, randSpawnLoc);
        }
    }
}
