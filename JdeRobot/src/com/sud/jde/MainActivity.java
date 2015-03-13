package com.sud.jde;

import com.example.bletemp.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements View.OnClickListener{

	Button Start;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Start=(Button)findViewById(R.id.bStart);
		Start.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		startActivity(new Intent(getApplicationContext(),MyAdapter.class));
	}
}
