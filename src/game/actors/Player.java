package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.positions.GameMap;
import game.attributes.Ability;
import game.attributes.Status;
import game.utilities.FancyMessage;
import game.weapons.BareFist;

/**
 * Class representing the Player.
 * @author Adrian Kristanto
 * Modified by: hewjiajing
 */
public class Player extends Actor
{
    /**
     * Constructor.
     *
     * @param name        Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints   Player's starting number of hitpoints
     */
    public Player(String name, char displayChar, int hitPoints, int stamina)
    {
        super(name, displayChar, hitPoints);
        this.addCapability(Status.HOSTILE_TO_ENEMY);
        this.addCapability(Ability.CAN_BE_FOLLOWED);
        this.addCapability(Ability.MOVE_ABILITY);
        this.addCapability(Ability.LISTEN_ABILITY);
        this.addCapability(Ability.CONSUME);
        this.addCapability(Ability.PURCHASE_ABILITY);
        this.addCapability(Ability.CRAFT_ABILITY);
        this.addCapability(Ability.TELEPORT_ABILITY);
        this.setIntrinsicWeapon(new BareFist());
        this.addAttribute(BaseActorAttributes.STAMINA, new BaseActorAttribute(stamina));
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display)
    {
        // Handle multi-turn Actions
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();

        // display the player's attributes (hit points and stamina) at each game tick.
        displayCurrentStatus(display);

        // return/print the console menu
        Menu menu = new Menu(actions);
        return menu.showMenu(this, display);
    }

    /**
     * Display actor's current status.
     *
     * @param display the I/O object to which messages may be written
     */
    public void displayCurrentStatus(Display display)
    {
        display.println("\n" + this.name + "'s Status");
        display.println("Hit points: " + this.getAttribute(BaseActorAttributes.HEALTH));
        display.println("Stamina: " + this.getAttribute(BaseActorAttributes.STAMINA));
        display.println("Wallet Balance: " + this.getBalance() + "\n");
    }

    /**
     * Method that can be executed when the player is unconscious due to the
     * action of another actor
     *
     * @param actor the perpetrator
     * @param map   where the Player fell unconscious
     * @return a FancyMessage showing the death of the player.
     */
    @Override
    public String unconscious(Actor actor, GameMap map)
    {
        Display youDiedMessage = new Display();
        youDiedMessage.println(FancyMessage.YOU_DIED);
        return super.unconscious(actor, map);
    }

    /**
     * Method that can be executed when the player is unconscious due to natural
     * causes or accident.
     *
     * @param map where the player fell unconscious
     * @return a FancyMessage showing the death of the player.
     */
    @Override
    public String unconscious(GameMap map)
    {
        Display youDiedMessage = new Display();
        youDiedMessage.println(FancyMessage.YOU_DIED);
        return super.unconscious(map);
    }

}
