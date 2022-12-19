package game2d.objects;

import game2d.engine.CycleReturn;
import game2d.engine.Logic;
import game2d.engine.Settings;
import game2d.sprites.GameSprite;

public class GameObject {

	private char id = Logic.UNAWAKENED_ID;

	public final void identify(char id) {
		this.id = id;
	}

	public final char identify() {
		return id;
	}

	public boolean awaken(Settings setting) {
		return false;
	}

	public @Logic.AliveState int getAliveState() {
		return 0;
	}

	public Logic.ObjectState getObjectState() {
		return null;
	}

	public CycleReturn cycle(Settings setting) {
		return null;
	}

	public GameSprite getCurrentSprite() {
		return null;
	}

	@Override
	public int hashCode() {
		return id;
	}
}
