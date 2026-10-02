package game.behaviours.assistantBehaviour;

import edu.monash.fit2099.engine.items.Item;

/**
 * an interface represent NPC has consume capability.
 * NPC consuming logic defined within this class.
 *
 * <p><b>Reduced Connascence:</b></p>
 *   <ul>
 *     <li><b>Connascence of Name:</b> Reduced by abstracting method names like {@code consume()} and {@code itemToConsume()}
 *     behind a shared interface, so callers don't need to know concrete class names or method signatures.</li>
 *     <li><b>Connascence of Type:</b> Reduced by programming to an interface (e.g., {@code Consumer} instead of specific class),
 *    enabling flexibility and interchangeability of NPCs that consume different items.</li>
 *   </ul>
 *
 * @author Goh Jia Xuan
 * @version 1.0
 */
public interface Consumer {

    /**
     * personalise consuming condition.
     * @return true if condition met otherwise false.
     */
    boolean canConsume();

    /**
     * personalise consume effect.
     */
    void consume();

    /**
     * personalise item to consume.
     * @return item consumed by implementer.
     */
    Item itemToConsume();
}
