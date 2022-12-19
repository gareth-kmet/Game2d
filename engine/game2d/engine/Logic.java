package game2d.engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import game2d.objects.GameObject;
import game2d.sprites.GameSprite;

public class Logic {

	public static final char UNAWAKENED_ID = 0;

	private final ArrayList<GameSprite> staticSprites = new ArrayList<GameSprite>();
	private final ArrayList<GameSprite> activeSprites = new ArrayList<GameSprite>();

	final HashSet<GameObject> logicObjects = new HashSet<GameObject>();

	private final Settings settings;

	private char objects = 0;

	public Logic(Settings settings) {
		this.settings = settings;
	}

	public LogicCycleResults cycle() {

		activeSprites.clear();

		Iterator<GameObject> i = logicObjects.iterator();

		while (i.hasNext()) {
			GameObject o = i.next();

			if (o.getObjectState() != ObjectState.ASLEEP)
				cycleObject(o);

			if (o.getObjectState() == ObjectState.DEAD) {
				i.remove();
				continue;
			}

			workObjectAliveStateFactors(o);

		}
		return new LogicCycleResults(staticSprites, activeSprites);

	}

	/**
	 * @param  o
	 * 
	 * @return   if the object was awaken and added to the logic object list
	 */
	boolean createLogicObject(GameObject o) {
		if (o.awaken(settings)) {
			if (o.identify() == UNAWAKENED_ID) { o.identify(++objects); }
			logicObjects.add(o);
			return true;
		}
		return false;

	}

	void addStaticSprite(GameSprite s) {
		staticSprites.add(s);
	}

	private void cycleObject(GameObject o) {
		CycleReturn c = o.cycle(settings);

		for (GameObject oa : c.addObjects()) { if (createLogicObject(oa)) { workObjectAliveStateFactors(oa); } }

		for (GameSprite sa : c.addStatics()) { addStaticSprite(sa); }
	}

	private void workObjectAliveStateFactors(GameObject o) {
		if ((o.getAliveState() & AliveState.VISIBLE) != 0) { activeSprites.add(o.getCurrentSprite()); }
	}

	public void destroy() {}

	record LogicCycleResults(ArrayList<GameSprite> staticSprites, ArrayList<GameSprite> activeSprites) {}

	public enum ObjectState { DEAD, ALIVE, ASLEEP }

	public @interface AliveState {
		public static final int MOVING = 1 << 0, PHYSICAL = 1 << 1, VISIBLE = 1 << 2;
	}

}
