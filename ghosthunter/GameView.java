package com.example.splashscreen;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	public GameThread thread;
	public Character mainChar;
	private Bitmap bit;
	boolean firstRun;
	public static double screenWidth;
	public static double screenHeight;
	private float touchX;
	private float touchY;
	public boolean slowDown;
	private ArrayList<Ghost> ghosts;
	private long sinceLastGhost;
	private Ghost ghost;
	ArrayList<Bitmap> ghostPngs;
	ArrayList<Bitmap> charPngs;
	private int score = 0;
	private int health = 2;
	
	//New Stuff
	private int level;
	private int ghostLag;
	private int firstTime;
	


	public GameView(Context context, AttributeSet attr) {
		super(context, attr);
		Bitmap mainCharBit = BitmapFactory.decodeResource(getResources(), R.drawable.hunterdown);
		mainChar = new Character(mainCharBit, 250, 250, this);
		thread = new GameThread(getHolder(), this);
		getHolder().addCallback(this);
		setFocusable(true);
		firstRun = true;
		this.slowDown = false;
		ghosts = new ArrayList<Ghost>();
		ghostPngs = new ArrayList<Bitmap>();
		charPngs = new ArrayList<Bitmap>();
		Bitmap ghostDown = BitmapFactory.decodeResource(getResources(), R.drawable.ghostdown);
		Bitmap ghostUp = BitmapFactory.decodeResource(getResources(), R.drawable.ghostup);
		Bitmap ghostRight = BitmapFactory.decodeResource(getResources(), R.drawable.ghostright);
		Bitmap ghostLeft = BitmapFactory.decodeResource(getResources(), R.drawable.ghostleft);
		ghostPngs.add(ghostDown);
		ghostPngs.add(ghostUp);
		ghostPngs.add(ghostRight);
		ghostPngs.add(ghostLeft);
		Bitmap charDown = BitmapFactory.decodeResource(getResources(), R.drawable.hunterdown);
		Bitmap charUp = BitmapFactory.decodeResource(getResources(), R.drawable.hunterup);
		Bitmap charRight = BitmapFactory.decodeResource(getResources(), R.drawable.hunterright);
		Bitmap charLeft = BitmapFactory.decodeResource(getResources(), R.drawable.hunterleft);
		Bitmap charDownLight = BitmapFactory.decodeResource(getResources(), R.drawable.hunterdownlamp);
		Bitmap charUpLight = BitmapFactory.decodeResource(getResources(), R.drawable.hunteruplight);
		Bitmap charRightLight = BitmapFactory.decodeResource(getResources(), R.drawable.hunterrightlight);
		Bitmap charLeftLight = BitmapFactory.decodeResource(getResources(), R.drawable.hunterleftlight);
		charPngs.add(charDown);
		charPngs.add(charUp);
		charPngs.add(charRight);
		charPngs.add(charLeft);
		charPngs.add(charDownLight);
		charPngs.add(charUpLight);
		charPngs.add(charRightLight);
		charPngs.add(charLeftLight);
		
		
		//new stuff
		
		firstTime = 0;
		
//		level = ((MainActivity) getContext()).getLevel();
//		if(level == 1) ghostLag = 4000;
//		if(level == 2) ghostLag = 2000;
//		if(level == 3) ghostLag = 1000;
//		else ghostLag = 4000;
//		
//		Log.d("c.level", Integer.toString(level));
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();	
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

	public void render(Canvas canvas) {
		if (firstRun) {
			Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
			bit = Bitmap.createScaledBitmap(background, getWidth(), getHeight(), true);
			screenWidth = getWidth();
			screenHeight = getHeight();
			firstRun = false;
		}

		// Draw background and main character
		canvas.drawBitmap(bit, 0, 0, null);
		
		if (Math.abs(mainChar.getxVelo()) > Math.abs(mainChar.getyVelo()) && mainChar.getxVelo() > 0) {
			if (mainChar.isShooting()) {
				mainChar.setMap(charPngs.get(6));
			} else {
				mainChar.setMap(charPngs.get(2));
			}
		} else if (Math.abs(mainChar.getxVelo()) > Math.abs(mainChar.getyVelo()) && mainChar.getxVelo() < 0) {
			if (mainChar.isShooting()) {
				mainChar.setMap(charPngs.get(7));
			} else {
				mainChar.setMap(charPngs.get(3));
			}
		} else if (Math.abs(mainChar.getxVelo()) < Math.abs(mainChar.getyVelo()) && mainChar.getyVelo() > 0) {
			if (mainChar.isShooting()) {
				mainChar.setMap(charPngs.get(4));
			} else {
				mainChar.setMap(charPngs.get(0));
			}
		} else if (Math.abs(mainChar.getxVelo()) < Math.abs(mainChar.getyVelo()) && mainChar.getyVelo() < 0) {
			if (mainChar.isShooting()) {
				mainChar.setMap(charPngs.get(5));
			} else {
				mainChar.setMap(charPngs.get(1));
			}
		}
		canvas.drawBitmap(mainChar.getMap(), (float) mainChar.getxPos(), (float) mainChar.getyPos(), null);
		// Draw ghost
		for (int i = 0; i < ghosts.size(); i++) {
			ghosts.get(i).update();
			canvas.drawBitmap(ghosts.get(i).getMap(), (float) ghosts.get(i).getxPos(), (float) ghosts.get(i).getyPos(), null);
		}

		// Draw gamepad
		Paint white = new Paint();
		white.setColor(Color.WHITE);
		white.setAlpha(50);
		canvas.drawCircle((float) getWidth() / 9, (float) getHeight() * 19 / 24, (float) getHeight() / 6, white);
		Paint black = new Paint();
		black.setColor(Color.BLACK);
		canvas.drawCircle((float) getWidth() / 9, (float) getHeight() * 19 / 24, (float) 10, black);

	}

	public void update() {
		if (this.slowDown) {
			mainChar.slowDown();
		}
		mainChar.update();
		
		//new stuff
		
		if (firstTime == 0) {
		level = ((MainActivity) getContext()).getLevel();
		if(level == 1) ghostLag = 4000;
		if(level == 2) ghostLag = 2000;
		if(level == 3) ghostLag = 1000;
		else ghostLag = 4000;
		
		//Log.d("c.level", Integer.toString(level));
		firstTime ++;
		}
		
		//Change the System.currentTimeMillis() - sinceLastGhost > ghostLag
		if (System.currentTimeMillis() - sinceLastGhost > ghostLag) {
	
			ghost = new Ghost(ghostPngs.get(0), (float) Math.random() * 1200, (float) Math.random() * 800, this);
			ghosts.add(ghost);
			sinceLastGhost = System.currentTimeMillis();
		}
		for (int i = ghosts.size() - 1; i >= 0; i--) {
			Ghost g = ghosts.get(i);
			
			if (Math.abs(g.getxVelo()) > Math.abs(g.getyVelo()) && g.getxVelo() > 0) {
				g.setMap(ghostPngs.get(2));
			} else if (Math.abs(g.getxVelo()) > Math.abs(g.getyVelo()) && g.getxVelo() < 0) {
				g.setMap(ghostPngs.get(3));
			} else if (Math.abs(g.getxVelo()) < Math.abs(g.getyVelo()) && g.getyVelo() > 0) {
				g.setMap(ghostPngs.get(0));
			} else if (Math.abs(g.getxVelo()) < Math.abs(g.getyVelo()) && g.getyVelo() < 0) {
				g.setMap(ghostPngs.get(1));
			}

			if (g.intersect(mainChar) && mainChar.isShooting()) {
				ghosts.remove(i);
				score++;
			} else if (g.intersect(mainChar)) {
				mainChar.setxPos(0);
				mainChar.setyPos(0);
				mainChar.setxVelo(0);
				mainChar.setyVelo(0);
				ghosts.remove(i);
				health--;
			}
			((MainActivity) getContext()).updateScoreView("Score: " + score + "      Health: " + health);

			if (health == 0) {
				endGame();
			}
			g.update();
			
		}

	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();

		float x = event.getX();
		float y = event.getY();

		this.touchX = x;
		this.touchY = y;

		double centerX = getWidth() / 9;
		double centerY = getHeight() * 19 / 24;
		double radius = getHeight() / 6;
		// double direction;
		// double newXVelo;
		double distance;
		// double vQ = 3;

		distance = Math.sqrt(Math.pow((centerY - y), 2) + Math.pow((centerX - x), 2));
		if (action == MotionEvent.ACTION_DOWN) {
			if (distance < radius) {
				updateTouch(x, y);
				return true;
			}
		} else if (action == MotionEvent.ACTION_MOVE){
			//Log.d("TEST", "MOVING");
			if (distance < radius) {
				updateTouch(x, y);
			} else if (distance > radius) {
				updateTouch(x, y);
			}
			return true;
		} 
		if (action == MotionEvent.ACTION_UP) {
			this.slowDown = true;
			return true;
		}

		return super.onTouchEvent(event);
	}

	public void updateTouch(float x, float y) {

		double centerX = getWidth() / 9;
		double centerY = getHeight() * 19 / 24;
		double radius = getHeight() / 6;
		double direction;
		double newXVelo;
		double distance;
		double vQ = 9;
		
		distance = Math.sqrt(Math.pow((centerY - touchY), 2) + Math.pow((centerX - touchX), 2));
		if (distance > radius) {
			distance = radius;
		}
		double deltaX = centerX- touchX;
		double deltaY = centerY - touchY;
		if (deltaX <= 0) {
			direction = deltaY / deltaX;
		} else {
			direction = deltaY / deltaX;
			distance = distance * -1;
		}
		newXVelo = distance / vQ / Math.sqrt(1 + Math.pow(direction, 2));
		mainChar.setxVelo(newXVelo);
		mainChar.setyVelo(newXVelo * direction);
	}
	
	public void endGame() {		
		//SharedPreferences scores = getSharedPreferences();
		thread.setRunning(false);
		//((MainActivity) getContext()).highScorePopUp();
		Intent intent = new Intent(((MainActivity) getContext()), EndActivity.class);
		((MainActivity) getContext()).startActivity(intent);
		((MainActivity) getContext()).finish();
	}
	
	//ADDED THIS PART
	
	public int getScore() {
		return this.score;
	}
	

}

