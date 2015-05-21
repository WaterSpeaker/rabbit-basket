package cc.wangchen.rabbitbasket;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class OverlayIcon {
	private Context context;
	private View view;
	private View appview;
	private View taskview;
	private WindowManager wm;
	private LayoutInflater inflater;
	private WindowManager.LayoutParams params;
	private WindowManager.LayoutParams params2;
	private PackageManager pm;
	private ImageView[] slowAppIcons;
	private ImageView[] fastAppIcons;
	private ImageView taskAppIcon;
	private TextView taskAppName;
	private String[] fastApps;
	private String[] slowApps;
	private static final String TAG = "AppIcon";
	
	private boolean isThroughLauncher = false;

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

		// Initialize view
		appview = inflater.inflate(R.layout.app_list, null);
		view = inflater.inflate(R.layout.overlay_icon, null);
		taskview = inflater.inflate(R.layout.task, null);

		// Add layout to window manager, and display
		wm.addView(view, params);

		// Add touch listener
		view.setOnTouchListener(new IconOnTouch());
		
		// Task exit listener
		taskview.findViewById(R.id.buttonExit).setOnClickListener(new ExitButtonOnClick());
		taskview.findViewById(R.id.buttonGo).setOnClickListener(new GoButtonOnClick());

		// Set slow and fast app icons
		slowAppIcons = new ImageView[3];
		fastAppIcons = new ImageView[3];

		fastAppIcons[0] = (ImageView) appview.findViewById(R.id.fastAppIcon1);
		fastAppIcons[1] = (ImageView) appview.findViewById(R.id.fastAppIcon2);
		fastAppIcons[2] = (ImageView) appview.findViewById(R.id.fastAppIcon3);
		slowAppIcons[0] = (ImageView) appview.findViewById(R.id.slowAppIcon1);
		slowAppIcons[1] = (ImageView) appview.findViewById(R.id.slowAppIcon2);
		slowAppIcons[2] = (ImageView) appview.findViewById(R.id.slowAppIcon3);
		
		// Task app icon and name text views
		taskAppIcon = (ImageView) taskview.findViewById(R.id.taskAppIcon);
		taskAppName = (TextView) taskview.findViewById(R.id.taskAppName);
	}
	
	public void setFastApp(String[] packages) {
		fastApps = packages;
		int i;
		for (i = 0; i < Math.min(packages.length, fastAppIcons.length); i++) {
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
	
	public void setSlowApp (String[] packages) {
		slowApps = packages;
		int i;
		for (i = 0; i < Math.min(packages.length, slowAppIcons.length); i++) {
			Drawable icon;
			try {
				icon = pm.getApplicationIcon(packages[i]);
				slowAppIcons[i].setImageDrawable(icon);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setTaskApp (String pkg, String name) {
		Drawable icon;
		try {
			icon = pm.getApplicationIcon(pkg);
			taskAppIcon.setImageDrawable(icon);
			taskAppName.setText("name");
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		isThroughLauncher = false;
	}

	private class IconOnTouch implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			// Touch down: show icons
			case MotionEvent.ACTION_DOWN:
				showAppIcons();
				break;
			case MotionEvent.ACTION_UP:
				//Log.v(TAG, "up " + event.getX() + ", " + event.getY());
				int x = pxToDp(event.getX());
				int y = 530 + pxToDp(event.getY());
				Log.v(TAG, "   " + x + ", " + y);
				if(x > 35 && x < 95 && y > 395 && y < 455) {
					Intent launchIntent = pm.getLaunchIntentForPackage(slowApps[0]);
					context.startActivity(launchIntent);
					isThroughLauncher = true;
				} else if (x > 95 && x < 155 && y > 335 && y < 395) {
					Intent launchIntent = pm.getLaunchIntentForPackage(slowApps[1]);
					context.startActivity(launchIntent);
					isThroughLauncher = true;
				} else if (x > 155 && x < 215 && y > 275 && y < 335) {
					Intent launchIntent = pm.getLaunchIntentForPackage(slowApps[2]);
					context.startActivity(launchIntent);
					isThroughLauncher = true;
				}  else if (x > 95 && x < 155 && y > 435 && y < 495) {
					Intent launchIntent = pm.getLaunchIntentForPackage(fastApps[0]);
					context.startActivity(launchIntent);
					isThroughLauncher = true;
				} else if (x > 155 && x < 215 && y > 375 && y < 435) {
					Intent launchIntent = pm.getLaunchIntentForPackage(fastApps[1]);
					context.startActivity(launchIntent);
					isThroughLauncher = true;
				} else if (x > 215 && x < 275 && y > 315 && y < 375) {
					Intent launchIntent = pm.getLaunchIntentForPackage(fastApps[2]);
					context.startActivity(launchIntent);
					isThroughLauncher = true;
				}
				
				// Touch up: hide icons
				hideAppIcons();
				break;
			default:
				break;
			}
			return true;
		}

	}
	
	private void showAppIcons() {
		wm.addView(appview, params2);
	}
	
	private void hideAppIcons() {
		wm.removeView(appview);
	}
	
	private class ExitButtonOnClick implements OnClickListener {
		
		@Override
		public void onClick(View arg0) {
			System.exit(0);
		}

	}

	private class GoButtonOnClick implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			hideTask();
		}

	}
	
	public void showTask() {
		wm.addView(taskview, params2);
	}
	
	public void hideTask() {
		wm.removeView(taskview);
	}
	
	public int dpToPx(int dp) {
	    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
	    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
	    return px;
	}
	
	public int pxToDp(float px) {
	    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
	    int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
	    return dp;
	}
	
	public boolean isThroughLauncher() {
		return isThroughLauncher;
	}
}
