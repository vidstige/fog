package com.fog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;

public class FogActivity extends Activity implements Runnable {
	
    private FludDynamics fluidDynamics;
    private Timer timer;
    private View fogDrawer;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        fluidDynamics = new FludDynamics(32, 32);
        
        fogDrawer = new FogDrawer(this, fluidDynamics);
        fogDrawer.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        setContentView(fogDrawer);
        
        timer = new Timer(this, new Handler(), 40);
    }
    
    @Override
    protected void onResume()
    {
    	super.onResume();
    	timer.start();
    }
    
    @Override 
    protected void onPause()
    {
    	timer.stop();
    	super.onPause();
    }

	@Override
	public void run() {
		// and some flow...
		fluidDynamics.addSomeRandomFlow();
		
		fluidDynamics.step(0.04f);
		fogDrawer.invalidate();
		
		fluidDynamics.clearStartingConditions();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.options, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.openPreferences:
	        Intent intent = new Intent(this, SimulationPreferences.class);
	        startActivity(intent);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
}