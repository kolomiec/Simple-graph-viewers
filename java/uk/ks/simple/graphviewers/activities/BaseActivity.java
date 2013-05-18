package uk.ks.simple.graphviewers.activities;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import uk.ks.simple.graphviewers.R;
import uk.ks.simple.graphviewers.holders.BaseHolder;
import uk.ks.simple.graphviewers.utils.SystemInformation;

public class BaseActivity extends Activity {

	private View baseHolder;
	private DisplayMetrics displayMetrics;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		getSystemInformation();
		setContentView(new BaseHolder(getBaseContext()));
    }

	private void getSystemInformation() {
		displayMetrics = getBaseContext().getResources().getDisplayMetrics();
		SystemInformation.DISPLAY_WIDTH = displayMetrics.widthPixels;
		SystemInformation.DISPLAY_HEIGHT = displayMetrics.heightPixels;
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }
    
}
