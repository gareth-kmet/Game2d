package game2d.sprites;

import java.awt.geom.AffineTransform;

public sealed class GameSprite permits ShapeSprite {

	protected AffineTransform transform = AffineTransform.getScaleInstance(1, 1);

	public AffineTransform transformation() {
		return transform;
	}

	/**
	 * @param transform
	 *                  the transform to set
	 */
	public void setTransform(AffineTransform transform) { this.transform = transform; }

}
