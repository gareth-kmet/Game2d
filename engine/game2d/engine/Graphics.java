package game2d.engine;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JPanel;

import game2d.physics.PhysicsRecords;
import game2d.sprites.GameSprite;
import game2d.sprites.ShapeSprite;
import game2d.utils.Unit.Distance;

public class Graphics extends JPanel {

	private final Game game;
	private final Settings settings;

	private ArrayList<GameSprite> staticSprites = new ArrayList<GameSprite>(),
			activeSprites = new ArrayList<GameSprite>();

	public Graphics(Game game, Settings settings) {
		super();
		this.game = game;
		this.settings = settings;
	}

	public void cycle(ArrayList<GameSprite> staticSprites, ArrayList<GameSprite> activeSprites) {
		this.staticSprites = staticSprites;
		this.activeSprites = activeSprites;
		repaint();
	}

	@Override
	protected void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = initGraphics(g);
		initTransforms(g2);
		draw(g2);
		iterate(g2, staticSprites);
		Toolkit.getDefaultToolkit().sync();
	}

	private void initTransforms(Graphics2D g2) {
		Dimension size = getSize();
		double w = size.getWidth();
		double h = size.getHeight();

		g2.rotate(settings.screenRot.rot(), w / 2, h / 2);
		g2.translate(
				w / 2 + Distance.convert(Distance.METRE, Distance.PIXEL, settings.metreDefinition)
						* settings.screenXOffset,
				h / 2 + Distance.convert(Distance.METRE, Distance.PIXEL, settings.metreDefinition)
						* settings.screenYOffset);
		g2.scale(settings.getMetreDefinition(), settings.getMetreDefinition());

	}

	private Graphics2D initGraphics(java.awt.Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		rh.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

		g2.setRenderingHints(rh);

		return g2;
	}

	private void iterate(Graphics2D g2, Collection<GameSprite> collection) {
		for (GameSprite s : collection) { if (s instanceof ShapeSprite shape) { draw(g2, shape); } }
	}

	private void draw(Graphics2D g2, ShapeSprite shape) {

		AffineTransform t = new AffineTransform(shape.transformation());

		// Shape ts = t.createTransformedShape(shape.getShape());
		Shape ts = t.createTransformedShape(shape.getShape());
		g2.setStroke(shape.getFstroke());
		g2.setColor(shape.getFcolor());
		g2.fill(ts);
		g2.setStroke(shape.getOstroke());
		g2.setColor(shape.getoColor());
		g2.draw(ts);

	}

	float f = 0;

	private void draw(Graphics2D g2) {

		settings.screenRot = new PhysicsRecords.Rotation(settings.screenRot.rot() + 0.01, 0, 0);
		Shape xaxis = new Rectangle(-45, -1, 45 * 2, 2);
		xaxis = AffineTransform.getScaleInstance(1, 1 / 5.).createTransformedShape(xaxis);
		g2.setColor(Color.black);
		g2.fill(xaxis);

		Shape yaxis = new Rectangle(-1, -45, 2, 45 * 2);
		yaxis = AffineTransform.getScaleInstance(1 / 5., 1).createTransformedShape(yaxis);
		g2.setColor(Color.black);
		g2.fill(yaxis);

		Rectangle r = new Rectangle(0, 0, 3, 3);
		AffineTransform t = new AffineTransform();
		t.rotate(f, 0, 0);
		t.scale(1.5, 1);

		f += 0.01;
		Shape xr = t.createTransformedShape(r);
		g2.setStroke(new BasicStroke(0.1f));
		g2.setColor(Color.gray);
		g2.fill(xr);
		g2.setColor(Color.green);
		g2.draw(xr);

		g2.setColor(Color.red);
		g2.fillOval((int) (getSize().getWidth() / 2) - 2, (int) (getSize().getHeight() / 2) - 2, 4, 4);
	}

	public void destroy() {

	}

}
