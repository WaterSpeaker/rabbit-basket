package cc.wangchen.rabbitbasket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;

public class OverlayIcon {
	private Context context;
	private View view;
	private View appview;
	private View iconContainer;
	private WindowManager wm;
	private LayoutInflater inflater;
	private WindowManager.LayoutParams params;
	private WindowManager.LayoutParams params2;
	private PackageManager pm;
	private ImageView[] slowAppIcons;
	private ImageView[] fastAppIcons;
	private static final String TAG = "AppIcon";

	public OverlayIcon(Context context) {
		this.context = context;
	}

	public void init() {
		// Get system services
		wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    pm = context.getPackageManager();
	    
		// Set layout parameters
		params = new WindowManager.LayoutParams(
				150,
				150,
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
				PixelFormat.TRANSLUCENT);
		params2 = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
				PixelFormat.TRANSLUCENT);
		// Change position
	    params.gravity=Gravity.BOTTOM|Gravity.LEFT;
	    params.x=0;
	    params.y=0;
	    
	    //params2.x = 999999;
	    //params2.y = 999999;

		// Initialize view
		appview = inflater.inflate(R.layout.app_list, null);
		view = inflater.inflate(R.layout.overlay_icon, null);

		// Add layout to window manager, and display
		//wm.addView(appview, params2);
		wm.addView(view, params);

		// Add touch listener
		view.setOnTouchListener(new IconOnTouch());

		// Set slow and fast app icons
		slowAppIcons = new ImageView[5];
		fastAppIcons = new ImageView[5];

		fastAppIcons[0] = (ImageView) appview.findViewById(R.id.fastAppIcon1);
		fastAppIcons[1] = (ImageView) appview.findViewById(R.id.fastAppIcon2);
		fastAppIcons[2] = (ImageView) appview.findViewById(R.id.fastAppIcon3);
		fastAppIcons[3] = (ImageView) appview.findViewById(R.id.fastAppIcon4);
		fastAppIcons[4] = (ImageView) appview.findViewById(R.id.fastAppIcon5);
	}
	
	public void setFastApp (String[] packages) {
		int i;
		for(i = 0; i < packages.length; i++) {
			Drawable icon;
			try {
				icon = pm.getApplicationIcon(packages[i]);
				fastAppIcons[i].setImageDrawable(icon);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private class IconOnTouch implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			// Touch down: show icons
			case MotionEvent.ACTION_DOWN:
				Log.v(TAG, "down");
				showAppIcons();
				break;
			case MotionEvent.ACTION_UP:
				// Touch up: hide icons and open application if selected
				Log.v(TAG, "up");
				Intent launchIntent = pm.getLaunchIntentForPackage("com.facebook.orca");
				context.startActivity(launchIntent);
				hideAppIcons();
				break;
			default:
				break;
			}
			Log.v(TAG, "event");
			return true;
		}

	}
	
	private void showAppIcons() {
		wm.addView(appview, params2);
	}
	
	private void hideAppIcons() {
		wm.removeView(appview);
	}
}
