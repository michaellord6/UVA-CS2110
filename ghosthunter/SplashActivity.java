package com.example.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class SplashActivity extends Activity {
	private ImageView splashImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		splashImageView = new ImageView(this);
		splashImageView.setScaleType(ScaleType.FIT_XY);
		splashImageView.setImageResource(R.drawable.splash);
		setContentView(splashImageView);
		
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent(SplashActivity.this, MenuActivity.class);
				startActivity(intent);
				finish();
			}
		} , 2000);
	}

}
