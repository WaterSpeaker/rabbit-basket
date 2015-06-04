package cc.wangchen.rabbitbasket;

import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OverlayIcon {
	// Views
	private View view;
	private View appview;
	private View allappview;
	private WindowManager.LayoutParams params;
	private WindowManager.LayoutParams params2;
	private ImageView[] slowAppIcons;
	private ImageView[] fastAppIcons;
	private String[] fastApps;
	private String[] slowApps;
	private ImageView homeScreenIcon;
	
	private boolean isThroughLauncher = false;
	private boolean isThroughFullList = false;
	
	private int iconSize;

	public OverlayIcon() {
		
	}

	public void init() {
		// Set layout parameters
		params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
						| WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
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
	    params.x = 0;
	    params.y = 0;
	    
		// Initialize view
		appview = Share.inflater.inflate(R.layout.app_list, null);
		view = Share.inflater.inflate(R.layout.overlay_icon, null);
		allappview = Share.inflater.inflate(R.layout.all_app_list, null);

		// Add layout to window manager, and display
		Share.wm.addView(view, params);

		// Add touch listener
		view.setOnTouchListener(new IconOnTouch());
		
		iconSize = getIconSize();
		

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
		
		// Initial all application list
		GridLayout layout = (GridLayout) allappview.findViewById(R.id.all_app_list);
		
		List<ApplicationInfo> packages = Share.pm.getInstalledApplications(PackageManager.GET_META_DATA);
		
		Collections.sort(packages, new ApplicationInfo.DisplayNameComparator(Share.pm));

		for (final ApplicationInfo packageInfo : packages) {
			LinearLayout appgroup = new LinearLayout(Share.context);
			ImageView appicon = new ImageView(Share.context);
			TextView appname = new TextView(Share.context);
			Drawable icon;
			try {
				if(isSystemPackage(packageInfo)) {
					continue;
				}
				icon = Share.pm.getApplicationIcon(packageInfo.packageName);
				icon = resize(icon);
				appicon.setImageDrawable(icon);
				
				appgroup.addView(appicon);
				appgroup.addView(appname);
				layout.addView(appgroup);
				
				Display display = Share.wm.getDefaultDisplay();
				Point size = new Point();
				display.getSize(size);
				int screenWidth = size.x;
				int gridSize = screenWidth / 4;
				Log.v("H", screenWidth + ", " + iconSize);
				int padding = ( screenWidth / 4 - iconSize ) / 2;
				
				appicon.setPadding(padding, padding, padding, (int)(padding * 0.5));
				appicon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				appname.setText(Share.pm.getApplicationLabel(packageInfo).toString());
				appname.setTextColor(Color.BLACK);
				appname.setWidth(gridSize);
				appname.setPadding(0, 0, 0, padding);
				appname.setGravity(Gravity.CENTER);
				appname.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
				appgroup.setOrientation(LinearLayout.VERTICAL);
				
				appgroup.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						isThroughFullList = true;
						launchApp(packageInfo.packageName);
						hideAllAppIcons();
					}
					
				});
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void setFastApp(String[] packages) {
		fastApps = packages;
		int i;
		for (i = 0; i < Math.min(packages.length, fastAppIcons.length); i++) {
			Drawable icon;
			try {
				icon = Share.pm.getApplicationIcon(packages[i]);
				icon = resize(icon);
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
				icon = resize(icon);
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
				slowAppIcons[0].setBackgroundResource(0);
				slowAppIcons[1].setBackgroundResource(0);
				slowAppIcons[2].setBackgroundResource(0);
				fastAppIcons[0].setBackgroundResource(0);
				fastAppIcons[1].setBackgroundResource(0);
				fastAppIcons[2].setBackgroundResource(0);
				homeScreenIcon.setBackgroundResource(0);
				if(inViewInBounds(slowAppIcons[0], x, y)) {
					slowAppIcons[0].setBackgroundResource(R.drawable.round);
				} else if (inViewInBounds(slowAppIcons[1], x, y)) {
					slowAppIcons[1].setBackgroundResource(R.drawable.round);
				} else if (inViewInBounds(slowAppIcons[2], x, y)) {
					slowAppIcons[2].setBackgroundResource(R.drawable.round);
				} else if (inViewInBounds(fastAppIcons[0], x, y)) {
					fastAppIcons[0].setBackgroundResource(R.drawable.round);
				} else if (inViewInBounds(fastAppIcons[1], x, y)) {
					fastAppIcons[1].setBackgroundResource(R.drawable.round);
				} else if (inViewInBounds(fastAppIcons[2], x, y)) {
					fastAppIcons[2].setBackgroundResource(R.drawable.round);
				} else if (inViewInBounds(homeScreenIcon, x, y)) {
					homeScreenIcon.setBackgroundResource(R.drawable.round);
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
					showAllAppIcons();
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
	
	private void showAllAppIcons() {
		Share.wm.addView(allappview, params2);
	}
	
	private void hideAllAppIcons() {
		Share.wm.removeView(allappview);
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
	
	public boolean isThroughFullList() {
		// Every time checked this flag, it will be reset to false
		boolean is = isThroughFullList;
		isThroughFullList = false;
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
	
	private boolean isSystemPackage(ApplicationInfo pkgInfo) {
		if(pkgInfo.packageName.equals("com.hp.android.printservice")) {
			return true;
		}
		if(pkgInfo.packageName.equals("com.google.android.apps.maps") ||
				pkgInfo.packageName.equals("com.google.android.gm") ||
				pkgInfo.packageName.equals("com.google.android.apps.plus") ||
				pkgInfo.packageName.equals("com.google.android.youtube") ||
				pkgInfo.packageName.equals("com.android.vending")) {
			return false;
		}
	    return ((pkgInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true
	            : false;
	}
	
	private Drawable resize(Drawable image) {
	    Bitmap b = ((BitmapDrawable)image).getBitmap();
	    Bitmap bitmapResized = Bitmap.createScaledBitmap(b, iconSize, iconSize, false);
	    return new BitmapDrawable(Share.context.getResources(), bitmapResized);
	}
	
	private int getIconSize() {
		ActivityManager am = (ActivityManager) Share.activityContext.getSystemService(Context.ACTIVITY_SERVICE);
		return am.getLauncherLargeIconSize();
	}
}
