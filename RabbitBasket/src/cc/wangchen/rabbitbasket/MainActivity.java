package cc.wangchen.rabbitbasket;

import cc.wangchen.applog.AppLogService;
import cc.wangchen.applog.AppPredictService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Main activity of Rabbit Basket
 * @author Wang Chen <waterspeakercity@gmail.com>
 *
 */

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createOverlayIcon();

		Intent intent = new Intent(this, AppLogService.class);
		startService(intent);
		
		OverlayIcon icon = new OverlayIcon(this);
		icon.init();
		String[] packages = {"com.facebook.orca", "com.facebook.orca","com.facebook.orca","com.facebook.orca","com.facebook.orca"};
		icon.setFastApp(packages);
		// Predict service is not used at this time
		//Intent intent2 = new Intent(this, AppPredictService.class);
		//startService(intent2);
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
	
	private void createOverlayIcon() {
	    
	}
}
