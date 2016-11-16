package com.example.splashscreen;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Character {
	
	private double xPos;
	private double yPos;
	private double xVelo;
	private double yVelo;
	private Bitmap map;
	private Bitmap oldMap;
	private boolean isShooting;
	private long shootTime;
	private GameView gameView;
//	Bitmap charDown;
//	Bitmap charUp;
//	Bitmap charRight;
//	Bitmap charLeft;
	
	public Character (Bitmap bitmap, double x, double y, GameView gameView){
		this.map = bitmap;
		this.xPos = x;
		this.yPos = y;
		isShooting = false;
		this.gameView = gameView;
		
//		charDown = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.hunterdown);
//		charUp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.hunterup);
//		charRight = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.hunterright);
//		charLeft = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.hunterleft);

	}
	
	public void setIsShooting(boolean b) {
		if (!isShooting) {
			this.oldMap = this.map;
		}
		if (b) {
			shootTime = System.currentTimeMillis();
		}
		isShooting = b;
	}
	
	public boolean isShooting() {
		return this.isShooting;
	}
	
	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	public double getxVelo() {
		return xVelo;
	}

	public void setxVelo(double xVelo) {
		this.xVelo = xVelo;
	}

	public double getyVelo() {
		return yVelo;
	}

	public void setyVelo(double yVelo) {
		this.yVelo = yVelo;
	}

	public Bitmap getMap() {
		return map;
	}

	public void setMap(Bitmap map) {
		this.map = map;
	}

	public void update() {
		if (isShooting) {
			if (System.currentTimeMillis() - shootTime > 2000) {
				isShooting = false;
				
//	did			if (Math.abs(getxVelo()) > Math.abs(getyVelo()) && getxVelo() > 0) {
//	not				this.map = charRight;
//	work		} else if (Math.abs(getxVelo()) > Math.abs(getyVelo()) && getxVelo() < 0) {
//					this.map = charLeft;
//				} else if (Math.abs(getxVelo()) < Math.abs(getyVelo()) && getyVelo() > 0) {
//					this.map = charDown;
//				} else if (Math.abs(getxVelo()) < Math.abs(getyVelo()) && getyVelo() < 0) {
//					this.map = charUp;
//				}
				
				this.map = this.oldMap;
			}
		}
		
		this.xPos += this.xVelo;
		this.yPos += this.yVelo;
		
		// Create walls around the edges
		if (xPos >= GameView.screenWidth - this.map.getWidth()) {
			this.xVelo =  -xVelo / 2;
			this.xPos = GameView.screenWidth - this.map.getWidth();;
		} else if (xPos <= 0) {
			this.xVelo = -xVelo / 2;
			this.xPos = 1;
		}
		if (yPos >= GameView.screenHeight - this.map.getHeight()) {
			this.yVelo = -yVelo / 2;
			this.yPos = GameView.screenHeight - this.map.getHeight();
		} else if (yPos <= 0) {
			this.yVelo = -yVelo / 2;
			this.yPos = 1;
		}
	}
	
	public void slowDown() {
		// Reduce velocity to zero
		if (xVelo > 0) {
			xVelo -= 1;
		} else if (xVelo < 0) {
			xVelo += 1;
		}
		if (yVelo > 0) {
			yVelo -= 1;
		} else if (yVelo < 0) {
			yVelo += 1;
		}
		// Make sure velocity doesn't oscillate around 0
		if (xVelo > 0 && xVelo < 1) {
			xVelo = 0;
		} else if (xVelo < 0 && xVelo > -1) {
			xVelo = 0;
		}
		if (yVelo > 0 && yVelo < 1) {
			yVelo = 0;
		} else if (yVelo < 0 && yVelo > -1) {
			yVelo = 0;
		}
		if (xVelo == 0 && yVelo == 0) {
			gameView.slowDown = false;
		}
	}
}
