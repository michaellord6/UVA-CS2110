package com.example.splashscreen;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private GameView gameView;
	private Button shoot;
	TextView scoreView;
	MediaPlayer mp;
	
	//ADDED THIS <--JISU
	SharedPreferences settings;
	SharedPreferences scores;
	private boolean music;
	private int level;
	//END ADDITION

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//I moved this lower ---> gameView = (GameView) findViewById(R.id.game_view);
		shoot = (Button) findViewById(R.id.shoot);
		shoot.setOnClickListener(new ShootListener());
		mp= MediaPlayer.create(MainActivity.this, R.raw.gb);
		
		//ADDED STUFF
		settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		scores = getSharedPreferences("scores", Context.MODE_PRIVATE);
		music = settings.getBoolean("music", true);	
		
		String levelMode = settings.getString("level", "Easy");
		Log.d("first moment of level", levelMode);
		if (levelMode.equals("Easy")) {
			level = 1;
			}
		else if (levelMode.equals("Medium")) {
			level = 2;
			}
		else if (levelMode.equals("Hard")) {
			level = 3;
			}
		else {
			level = 1;
			}
		Log.d("level number", Integer.toString(level));
		
		gameView = (GameView) findViewById(R.id.game_view);
		//currentScore = gameView.getScore();
		//Log.d("current score is", Integer.toString(currentScore));
		//END OF NEW STUFF
		
		
		//if statement is new
		if (music == true) {
			mp.setLooping(true);
			mp.start();
        
		}
		scoreView = (TextView) findViewById(R.id.score);
		
		
		
		//testing <- Jisu
		//Log.d("level", settings.getString("level", "one"));
		
		//boolean music = settings.getBoolean("music", true);
 	   	boolean sound = settings.getBoolean("sound_effects", true);
 	   
 	   	String musicOn = "not working";
 	   	String soundOn = "not working";
 	   	if (music == false) musicOn = "false";
 	   	else musicOn = "true";
 	   	if (sound == false) soundOn = "false";
 	   	else soundOn = "true";
 	   	
		//Log.d("music", musicOn);
		//Log.d("sound", soundOn);
		
		
		//END OF TESTING
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void menuClickReturn(View view) {
		Intent intent = new Intent(MainActivity.this, MenuActivity.class);
		startActivity(intent);
		finish();
	}
	
	@Override 
    public void onPause() {
    	super.onPause();
    	
    	//ADDED IF STATEMENT
    	if (music == true) {
    	mp.release();
    	}
    	
    	gameView.thread.setRunning(false);
    }
	
	
	//ADDED NEW THING <-- JISU
	
	@Override
	public void onBackPressed() {
		
		onPause();
		
        new AlertDialog.Builder(this)
               .setMessage("Are you sure you want to exit?")
               .setCancelable(false)
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                   }
               })
               .setNegativeButton("No", null)
               .show();
    }
	
	public int getLevel() {
		//Log.d("returning level", Integer.toString(level));
		return level;
		
	}
	

	
	
	


	//END OF ADDITION
	
	
	public void updateScoreView(final String str) {
		MainActivity.this.runOnUiThread(new Runnable() {
			public void run() {
				
				//ADDED NEW STUFF
				
				
				Editor edit = scores.edit();
				edit.putInt("currentScore", gameView.getScore());
				edit.commit();
				int currentScore = gameView.getScore();
				Log.d("gameView Score", Integer.toString(gameView.getScore()));
				Log.d("current score 2", Integer.toString(scores.getInt("currentScore", 100)));
				int oldScore = scores.getInt("highScore", 0);
				if(currentScore > oldScore ){
					  edit.putInt("highScore", currentScore);
					  edit.commit();
				}
				
				//END OF NEW STUFF
				scoreView.setText(str);
			}
		});
	}
	
	
	
	
	
//	public void endGame(GameView gameview) {
//		Intent intent = new Intent(MainActivity.this, EndActivity.class);
//		startActivity(intent);
//		finish();
//	}

	public class ShootListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Bitmap mainCharBit = BitmapFactory.decodeResource(getResources(), R.drawable.hunterdownlamp);
			gameView.mainChar.setIsShooting(true);
			
			Bitmap charDown = BitmapFactory.decodeResource(getResources(), R.drawable.hunterdown);
			Bitmap charUp = BitmapFactory.decodeResource(getResources(), R.drawable.hunterup);
			Bitmap charRight = BitmapFactory.decodeResource(getResources(), R.drawable.hunterright);
			Bitmap charLeft = BitmapFactory.decodeResource(getResources(), R.drawable.hunterleft);
			Bitmap charDownLight = BitmapFactory.decodeResource(getResources(), R.drawable.hunterdownlamp);
			Bitmap charUpLight = BitmapFactory.decodeResource(getResources(), R.drawable.hunteruplight);
			Bitmap charRightLight = BitmapFactory.decodeResource(getResources(), R.drawable.hunterrightlight);
			Bitmap charLeftLight = BitmapFactory.decodeResource(getResources(), R.drawable.hunterleftlight);
			
			if (gameView.mainChar.getMap().equals(charDown)) {
				mainCharBit = charDownLight;
			} else if (gameView.mainChar.getMap().equals(charUp)) {
				mainCharBit = charUpLight;
			} else if (gameView.mainChar.getMap().equals(charRight)) {
				mainCharBit = charRightLight;
			} else if (gameView.mainChar.getMap().equals(charLeft)) {
				mainCharBit = charLeftLight;
			}
			
			gameView.mainChar.setMap(mainCharBit);
		}
	}
}
