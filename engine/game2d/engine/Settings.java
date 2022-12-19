package game2d.engine;

import game2d.physics.PhysicsRecords;
import game2d.utils.Unit;

public class Settings {

	@Unit.Measurement(unit = Unit.MILLISECOND)
	int timePerCycle = 25;

	@Unit.Measurement(unit = Unit.RADIAN, var = "rot")
	@Unit.Measurement(unit = Unit.PIXEL, var = "anchorX")
	@Unit.Measurement(unit = Unit.PIXEL, var = "anchorY")
	PhysicsRecords.Rotation screenRot = new PhysicsRecords.Rotation(0, 0, 0);

	@Unit.Measurement(unit = Unit.METRE)
	float screenXOffset = 0;
	@Unit.Measurement(unit = Unit.METRE)
	float screenYOffset = 0;

	@Unit.Measurement(unit = Unit.PIXEL)
	int metreDefinition = 40;

	@Unit.Measurement(unit = Unit.PIXEL)
	public int getMetreDefinition() { return metreDefinition; }

}
