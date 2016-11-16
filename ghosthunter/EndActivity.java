package com.example.splashscreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class EndActivity extends Activity {
	
	
	SharedPreferences scores;
	TextView message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end);
		
		//New Stuff
		
		message = (TextView)findViewById(R.id.summary);
		scores = getSharedPreferences("scores", Context.MODE_PRIVATE);
		int currentScore = scores.getInt("currentScore", 0);
		Log.d("currentScore summary", Integer.toString(currentScore));
		int highScore = scores.getInt("highScore", currentScore);
		message.setText("Your current Score:" + Integer.toString(currentScore)  +"\n" 
					+ "Your high Score:" + Integer.toString(highScore));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.end, menu);
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
	
	public void returnToStart(View view) {
		Intent intent = new Intent(EndActivity.this, MenuActivity.class);
		startActivity(intent);
		finish();
	}
	
	//This stuff is new
//	
//	public void EndGame() {
//		
//    }
//	
	
}
