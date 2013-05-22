package uk.ks.simple.graphviewers.activities;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import uk.ks.simple.graphviewers.R;
import uk.ks.simple.graphviewers.holders.BaseHolder;
import uk.ks.simple.graphviewers.utils.SystemInformation;

public class BaseActivity extends Activity {

	private View baseHolder;
	private DisplayMetrics displayMetrics;
    private BaseHolder view;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		getSystemInformation();
        view = new BaseHolder(getBaseContext());
		setContentView(view);
    }

	private void getSystemInformation() {
		displayMetrics = getBaseContext().getResources().getDisplayMetrics();
		SystemInformation.DISPLAY_WIDTH = displayMetrics.widthPixels;
		SystemInformation.DISPLAY_HEIGHT = displayMetrics.heightPixels;
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.generate:
                view.reDraw();
                return true;
            case R.id.save:
                view.saveToDB();
                return true;
            case R.id.clear:
                view.clear();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
}
