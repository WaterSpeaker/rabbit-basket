package cc.wangchen.rabbitbasket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;

public class OverlayIcon {
	// Views
	private View view;
	private View appview;
	private WindowManager.LayoutParams params;
	private WindowManager.LayoutParams params2;
	private ImageView[] slowAppIcons;
	private ImageView[] fastAppIcons;
	private String[] fastApps;
	private String[] slowApps;
	private ImageView homeScreenIcon;
	
	private boolean isThroughLauncher = false;

	public OverlayIcon() {
		
	}

	public void init() {
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
		appview = Share.inflater.inflate(R.layout.app_list, null);
		view = Share.inflater.inflate(R.layout.overlay_icon, null);

		// Add layout to window manager, and display
		Share.wm.addView(view, params);

		// Add touch listener
		view.setOnTouchListener(new IconOnTouch());
		
		

		// Set slow and fast app icons
		slowAppIcons = new ImageView[3];
		fastAppIcons = new ImageView[3];

		fastAppIcons[0] = (ImageView) appview.findViewById(R.id.fastAppIcon1);
		fastAppIcons[1] = (ImageView) appview.findViewById(R.id.fastAppIcon2);
		fastAppIcons[2] = (ImageView) appview.findViewById(R.id.fastAppIcon3);
		slowAppIcons[0] = (ImageView) appview.findViewById(R.id.slowAppIcon1);
		slowAppIcons[1] = (ImageView) appview.findViewById(R.id.slowAppIcon2);
		slowAppIcons[2] = (ImageView) appview.findViewById(R.id.slowAppIcon3);
		
		homeScreenIcon = (ImageView) appview.findViewById(R.id.homeScreenIcon);
	}
	
	public void setFastApp(String[] packages) {
		fastApps = packages;
		int i;
		for (i = 0; i < Math.min(packages.length, fastAppIcons.length); i++) {
			Drawable icon;
			try {
				icon = Share.pm.getApplicationIcon(packages[i]);
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
				icon = Share.pm.getApplicationIcon(packages[i]);
				slowAppIcons[i].setImageDrawable(icon);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	private class IconOnTouch implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// Get absolute position on screen
			int x = (int) event.getRawX();
			int y = (int) event.getRawY();
			
			switch (event.getAction()) {
			// Touch down: show icons
			case MotionEvent.ACTION_DOWN:
				showAppIcons();
				break;
			case MotionEvent.ACTION_MOVE:
				slowAppIcons[0].setBackgroundColor(Color.TRANSPARENT);
				slowAppIcons[1].setBackgroundColor(Color.TRANSPARENT);
				slowAppIcons[2].setBackgroundColor(Color.TRANSPARENT);
				fastAppIcons[0].setBackgroundColor(Color.TRANSPARENT);
				fastAppIcons[1].setBackgroundColor(Color.TRANSPARENT);
				fastAppIcons[2].setBackgroundColor(Color.TRANSPARENT);
				homeScreenIcon.setBackgroundColor(Color.TRANSPARENT);
				if(inViewInBounds(slowAppIcons[0], x, y)) {
					slowAppIcons[0].setBackgroundColor(Color.BLACK);
				} else if (inViewInBounds(slowAppIcons[1], x, y)) {
					slowAppIcons[1].setBackgroundColor(Color.BLACK);
				} else if (inViewInBounds(slowAppIcons[2], x, y)) {
					slowAppIcons[2].setBackgroundColor(Color.BLACK);
				} else if (inViewInBounds(fastAppIcons[0], x, y)) {
					fastAppIcons[0].setBackgroundColor(Color.BLACK);
				} else if (inViewInBounds(fastAppIcons[1], x, y)) {
					fastAppIcons[1].setBackgroundColor(Color.BLACK);
				} else if (inViewInBounds(fastAppIcons[2], x, y)) {
					fastAppIcons[2].setBackgroundColor(Color.BLACK);
				} else if (inViewInBounds(homeScreenIcon, x, y)) {
					homeScreenIcon.setBackgroundColor(Color.BLACK);
				}
				break;
			case MotionEvent.ACTION_UP:
				
				if(inViewInBounds(slowAppIcons[0], x, y)) {
					launchApp(slowApps[0]);
				} else if (inViewInBounds(slowAppIcons[1], x, y)) {
					launchApp(slowApps[1]);
				} else if (inViewInBounds(slowAppIcons[2], x, y)) {
					launchApp(slowApps[2]);
				} else if (inViewInBounds(fastAppIcons[0], x, y)) {
					launchApp(fastApps[0]);
				} else if (inViewInBounds(fastAppIcons[1], x, y)) {
					launchApp(fastApps[1]);
				} else if (inViewInBounds(fastAppIcons[2], x, y)) {
					launchApp(fastApps[2]);
				} else if (inViewInBounds(homeScreenIcon, x, y)) {
					Intent launchIntent = new Intent(Intent.ACTION_MAIN);
					launchIntent.addCategory(Intent.CATEGORY_HOME);
					launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					Share.activityContext.startActivity(launchIntent);
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
		Share.wm.addView(appview, params2);
	}
	
	private void hideAppIcons() {
		Share.wm.removeView(appview);
	}
	
	public int dpToPx(int dp) {
	    DisplayMetrics displayMetrics = Share.context.getResources().getDisplayMetrics();
	    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
	    return px;
	}
	
	public int pxToDp(float px) {
	    DisplayMetrics displayMetrics = Share.context.getResources().getDisplayMetrics();
	    int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
	    return dp;
	}
	
	public boolean isThroughLauncher() {
		// Every time checked this flag, it will be reset to false
		boolean is = isThroughLauncher;
		isThroughLauncher = false;
		return is;
	}
	
	private boolean inViewInBounds(View view, int x, int y) {
		Rect outRect = new Rect();
		int[] location = new int[2];
		view.getDrawingRect(outRect);
		view.getLocationOnScreen(location);
		outRect.offset(location[0], location[1]);
		return outRect.contains(x, y);
	}
	
	private void launchApp(String pkg) {
		if(appInstalledOrNot(pkg)) {
			Intent launchIntent = Share.pm.getLaunchIntentForPackage(pkg);
			launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Share.context.startActivity(launchIntent);
			isThroughLauncher = true;
		}
	}

	private boolean appInstalledOrNot(String pkg) {
		boolean app_installed;
		try {
			Share.pm.getPackageInfo(pkg, PackageManager.GET_ACTIVITIES);
			app_installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			app_installed = false;
		}
		return app_installed;
	}
}
