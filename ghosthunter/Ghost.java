package com.example.splashscreen;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Ghost {
	private double xPos;
	private double yPos;
	private double xVelo;
	private double yVelo;
	private Bitmap map;
	private double cChange;
	private double gChange;
	
	public Ghost(Bitmap bitmap, double x, double y, GameView gameView) {
		this.map = bitmap;
		this.xPos = x;
		this.yPos = y;
		this.xVelo = 3 * Math.random() + 1;
		this.yVelo = 3 * Math.random() + 1;
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
	
	public boolean intersect(Character c) {
		gChange = 65;
		cChange = 65;
		if (c.isShooting()) {
			cChange = 20;
		}
		
		Rect cRect = new Rect((int) (c.getxPos() + cChange), (int) (c.getyPos() + cChange), (int) (c.getxPos() + c.getMap().getWidth() - cChange), (int) (c.getyPos() + c.getMap().getHeight() - cChange));
		Rect gRect = new Rect((int) (this.xPos + gChange), (int) (this.yPos + gChange), (int) (this.xPos + this.map.getWidth() - gChange), (int) (this.yPos + this.map.getHeight() - gChange));
		
		if (cRect.intersect(gRect)) {
			return true;
		}
		return false;
	}
	
	public void update() {
		this.xPos += this.xVelo;
		this.yPos += this.yVelo;

		
		// Create walls around the edges
		if (xPos >= GameView.screenWidth - this.map.getWidth()) {
			this.xVelo =  -xVelo;
			this.xPos = GameView.screenWidth - this.map.getWidth();;
		} else if (xPos <= 0) {
			this.xVelo = -xVelo;
			this.xPos = 1;
		}
		if (yPos >= GameView.screenHeight - this.map.getHeight()) {
			this.yVelo = -yVelo;
			this.yPos = GameView.screenHeight - this.map.getHeight();
		} else if (yPos <= 0) {
			this.yVelo = -yVelo;
			this.yPos = 1;
		}
	}
	
}
