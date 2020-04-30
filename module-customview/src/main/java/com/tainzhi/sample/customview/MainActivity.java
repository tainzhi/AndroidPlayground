package com.tainzhi.sample.customview;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

@Route(path = "/customview/main")
public class MainActivity extends AppCompatActivity {
	
	private CirclePercentView mCirclePercentView;
	private Button mButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mCirclePercentView = findViewById(R.id.circlePercentView);
		mButton = findViewById(R.id.button);
		mButton.setOnClickListener((View) -> {
			mCirclePercentView.setPercent((int)(Math.random() * 100));
		});
	}
 
}
