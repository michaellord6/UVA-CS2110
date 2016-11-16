package com.example.splashscreen;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
	
	public static final int FPS = 30;
	public static final int PERIOD = 1000 / FPS;
	boolean running;
	private SurfaceHolder surfaceHolder;
	private GameView GameView;
	private long time;
	
	public void setRunning(boolean b) {
		this.running = b;
	}
	
	
	
	public GameThread(SurfaceHolder surfaceHolder, GameView GameView) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.GameView = GameView;
	}
	
	//ADDED THIS PART
	
	public int getScore() {
		return this.GameView.getScore();
	}
	
	//END OF ADDITION
	
	
	@Override
	public void run() {
		Canvas canvas;
		while (running) {
			canvas = null;
			long sleepTime;
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					time = System.currentTimeMillis();
					this.GameView.update();
					this.GameView.render(canvas);
					long duration = System.currentTimeMillis() - time;
					sleepTime = PERIOD - duration;
					if (sleepTime > 0) {
						try {
							Thread.sleep(sleepTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}		
				}
			} finally {
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}	
}
