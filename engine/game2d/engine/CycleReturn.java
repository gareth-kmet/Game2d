package game2d.engine;

import java.util.HashSet;

import game2d.objects.GameObject;
import game2d.sprites.GameSprite;

/**
 * Represents the data the an object wishes to affect the game after completing
 * its cycle
 * 
 * @param  endgame
 *                    if the game should be ended after this cycle
 * @param  aliveState
 *                    an {@link game2d.engine.Logic.AliveState} flag describing
 *                    the current physical properties of this object
 * @param  addStatics
 *                    set of static objects to be added to the game at the end
 *                    of this cycle. These sprites will then be visible (if set
 *                    to be) on the draw call directly after this cycle
 * @param  addObjects
 *                    set of objects to be added to the game at the end of this
 *                    cycle These sprites will then act on and be visible in the
 *                    physics and draw call directly after this cycle.
 * 
 * @author            Gareth Kmet
 */
public record CycleReturn(boolean endgame, HashSet<GameSprite> addStatics, HashSet<GameObject> addObjects) {

}
