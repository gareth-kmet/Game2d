package game2d.engine;

import javax.swing.JOptionPane;

public class Timer implements Runnable{
	
	private final Game game;
	private int delay;
	private boolean run = true;
	
	public Timer(Game game, int delay) {
		this.game=game;
		this.delay=delay;
	}

	@Override
	public void run() {
		long beforeTime, timeDiff, sleep;
		
		beforeTime = System.currentTimeMillis();
		
		while(run) {
			game.cycle();
			
			timeDiff= System.currentTimeMillis()-beforeTime;
			sleep=delay-timeDiff;
			
			if(sleep<0)sleep=2;
			
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			beforeTime = System.currentTimeMillis();
		}
		
	}
	
	public void destroy() {
		run = false;
	}

}
