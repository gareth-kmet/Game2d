package game2d.engine;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import game2d.engine.Logic.LogicCycleResults;
import game2d.sprites.GameSprite;
import game2d.sprites.ShapeSprite;

public class Game extends JFrame {

	private Thread animator;

	private Settings settings;
	private Timer timer;
	private Logic logic;
	private Physics physics;
	private Graphics graphics;

	public Game() {
		super();

		this.settings = new Settings();
		this.graphics = new Graphics(this, settings);
		this.timer = new Timer(this, settings.timePerCycle);
		this.logic = new Logic(settings);
		this.physics = new Physics(settings);

		initUI();

		this.animator = new Thread(timer);
	}

	private void initUI() {
		add(this.graphics);
		setResizable(true);
		pack();

		setSize(1000, 1000);

		setTitle("Game");

		addWindowListener(new WindowListener() {
			@Override
			public void windowClosing(WindowEvent e) {
				destroy();
				dispose();
			}

			@Override
			public void windowOpened(WindowEvent e) {}

			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowDeactivated(WindowEvent e) {}

			@Override
			public void windowClosed(WindowEvent e) {}

			@Override
			public void windowActivated(WindowEvent e) {}
		});

	}

	public void init() {
		GameSprite box = new ShapeSprite(Color.red, Color.black, new BasicStroke(), new BasicStroke(),
				new Rectangle(0, 0, 2, 2));
		logic.addStaticSprite(box);

		this.animator.start();
	}

	public boolean cycle() {
		LogicCycleResults logicresults = logic.cycle();
		graphics.cycle(logicresults.staticSprites(), logicresults.activeSprites());
		return true;
	}

	private void destroy() {
		timer.destroy();
		try {
			animator.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		graphics.destroy();
		logic.destroy();
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(() -> {
			Game g = new Game();
			g.setVisible(true);
			g.init();
		});
	}

}
