/**
 * 
 */
package game2d.sprites;

import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;

/**
 * @author Gareth Kmet
 */
public final class ShapeSprite extends GameSprite {

	private Color fcolor;
	private Color oColor;
	private Stroke fstroke;
	private Stroke ostroke;
	private Shape shape;

	/**
	 * @param fcolor
	 * @param oColor
	 * @param fstroke
	 * @param ostroke
	 * @param shape
	 */
	public ShapeSprite(Color fcolor, Color oColor, Stroke fstroke, Stroke ostroke, Shape shape) {
		this.fcolor = fcolor;
		this.oColor = oColor;
		this.fstroke = fstroke;
		this.ostroke = ostroke;
		this.shape = shape;

	}

	/**
	 * @return the fcolor
	 */
	public Color getFcolor() { return this.fcolor; }

	/**
	 * @return the oColor
	 */
	public Color getoColor() {
		return this.oColor;
	}

	/**
	 * @return the fstroke
	 */
	public Stroke getFstroke() { return this.fstroke; }

	/**
	 * @return the ostroke
	 */
	public Stroke getOstroke() { return this.ostroke; }

	/**
	 * @return the shape
	 */
	public Shape getShape() { return this.shape; }

}
