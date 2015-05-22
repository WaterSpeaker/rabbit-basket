package cc.wangchen.rabbitbasket;

import cc.wangchen.rabbitbasket.R.color;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskScreen {
	// Views
	private View taskOpenIcon; // Icon to open task screen
	private View taskScreen; // Full screen to show task
	private ImageView taskAppIcon; // Task application icon
	private TextView taskAppName; // Task application name
	private TextView trailText; // Trail/real task text
	
	// Parameters
	private LayoutParams iconLayoutParams;
	private LayoutParams screenLayoutParams;
	
	// Data
	private long startTime;
	
	public TaskScreen() {
		
	}
	
	public void init() {
		setLayoutParams();
		initViews();
		addListeners();
		showIcon();
	}
	
	private void setLayoutParams() {
		iconLayoutParams = new LayoutParams(150, 150,
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
				PixelFormat.TRANSLUCENT);
		iconLayoutParams.gravity=Gravity.TOP|Gravity.RIGHT;
		iconLayoutParams.x=20;
		iconLayoutParams.y=100;

		screenLayoutParams = new LayoutParams(
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
				PixelFormat.TRANSLUCENT);
	}
	
	private void initViews() {
		taskScreen = Share.inflater.inflate(R.layout.task_screen, null);
		taskOpenIcon = Share.inflater.inflate(R.layout.task_icon, null);
		// Task app icon and name text views
		taskAppIcon = (ImageView) taskScreen.findViewById(R.id.taskAppIcon);
		taskAppName = (TextView) taskScreen.findViewById(R.id.taskAppName);
		// Description texts
		trailText = (TextView) taskScreen.findViewById(R.id.trailText);
	}
	
	private void showIcon() {
		Share.wm.addView(taskOpenIcon, iconLayoutParams);
	}
	
	private void hideIcon() {
		Share.wm.removeView(taskOpenIcon);
	}
	
	private void showScreen() {
		Share.wm.addView(taskScreen, screenLayoutParams);
	}
	
	private void hideScreen() {
		Share.wm.removeView(taskScreen);
	}
	
	public void setTask(String pkg, String name) {
		Drawable icon;
		try {
			icon = Share.pm.getApplicationIcon(pkg);
			taskAppIcon.setImageDrawable(icon);
			taskAppName.setText(name);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void realTask() {
		trailText.setTextColor(Color.RED);
		trailText.setText("Real Task");
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public void setStartTime() {
		startTime = System.currentTimeMillis();
	}
	
	private void addListeners() {
		// Open task screen
		taskOpenIcon.setOnClickListener(new OpenIconOnClick());
		
		// Exit task mode and application
		taskScreen.findViewById(R.id.buttonExit).setOnClickListener(
				new ExitButtonOnClick());
		// Start perform task
		taskScreen.findViewById(R.id.buttonGo).setOnClickListener(
				new GoButtonOnClick());
	}
	
	private class OpenIconOnClick implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			showScreen();
		}
		
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
			hideScreen();
			setStartTime();
		}

	}
}
