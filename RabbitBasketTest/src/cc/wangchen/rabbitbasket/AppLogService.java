package cc.wangchen.rabbitbasket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.provider.MediaStore.Files;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Log application usage and store in database
 * @author Wang Chen <waterspeakercity@gmail.com>
 *
 */

public class AppLogService extends Service {
	// Service
	private Looper mServiceLooper;
	private ServiceHandler mServiceHandler;
	// System resource
	private ActivityManager mActivityManager;
	// Views
	private OverlayIcon icon;
	private TaskScreen task;
	// Task data
	private int taskCount;
	private String currentPackage;
	private String lastPackage;
	private String result;
	// Constants and Flags
	private static final String TAG = "AppLog";

	// Handler that receives messages from the thread
	private final class ServiceHandler extends Handler {
		public ServiceHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO read current running application and write to database
			while (true) {
				synchronized (this) {
					try {
						wait(10); // detect every 0.01 second

						lastPackage = currentPackage;
						currentPackage = getCurrentApp();
						
						if (!currentPackage.equals(lastPackage)
								&& !currentPackage.equals("com.google.android.googlequicksearchbox")
								&& !currentPackage.equals("cc.wangchen.rabbitbasket")) {
							// User action done, record data
							record();
							// Next task
							nextTask();
						}

						if (taskCount >= AppList.TASK_APP_NUMBER) {
							// If all task done
							// Save data
							writeToFile(result, "rabbit-basket-result.txt");
							// Exit
							android.os.Process.killProcess(android.os.Process
									.myPid());
							System.exit(1);
						}
					} catch (Exception e) {
					}
				}
			}
		}
	}

	@Override
	public void onCreate() {
		// Start up the thread running the service. Note that we create a
		// separate thread because the service normally runs in the process's
		// main thread, which we don't want to block. We also make it
		// background priority so CPU-intensive work will not disrupt our UI.
		HandlerThread thread = new HandlerThread("ServiceStartArguments",
				Process.THREAD_PRIORITY_BACKGROUND);
		thread.start();
		
		Share.context = this;
		Share.wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Share.inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Share.pm = getPackageManager();

		// Get the HandlerThread's Looper and use it for our Handler
		mServiceLooper = thread.getLooper();
		mServiceHandler = new ServiceHandler(mServiceLooper);
		mActivityManager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
		
		// Create UI
		icon = new OverlayIcon();
		icon.init();
		icon.setSlowApp(AppList.SLOW_APP_LIST);
		// Create task notification UI
		task = new TaskScreen();
		task.init();
		
		// Initial task
		taskCount = 0;
		icon.setFastApp(AppList.FAST_APP_LIST[taskCount]);
		task.setTask(AppList.TASK_APP_LIST[taskCount][1], AppList.TASK_APP_LIST[taskCount][0]);
		result = "";
		// Get current running app
		currentPackage = getCurrentApp();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

		// For each start request, send a message to start a job and deliver the
		// start ID so we know which request we're stopping when we finish the
		// job
		Message msg = mServiceHandler.obtainMessage();
		msg.arg1 = startId;
		mServiceHandler.sendMessage(msg);

		// If we get killed, after returning from here, restart
		//return START_STICKY;
		return START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// We don't provide binding, so return null
		return null;
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
	}
	
	private String getCurrentApp() {
		List<RunningAppProcessInfo> appProcessList = mActivityManager.getRunningAppProcesses();
		if(appProcessList == null) {
			return "";
		}
		
		String pkg = appProcessList.get(0).pkgList[0];
		return pkg;
	}
	
	private String getTaskApp() {
		return AppList.TASK_APP_LIST[taskCount][1];
	}
	
	private void nextTask() {
		taskCount++;
		icon.setFastApp(AppList.FAST_APP_LIST[taskCount]);
		task.setTask(AppList.TASK_APP_LIST[taskCount][1], AppList.TASK_APP_LIST[taskCount][0]);
		
		if(taskCount == AppList.TRAIL_TASK_APP_NUMBER) {
			task.realTask();
		}
	}
	
	private void record() {

		// Task id
		String row = taskCount + ",";
		
		// Fast app
		row += AppList.FAST_APP_LIST[taskCount][0] + ",";
		row += AppList.FAST_APP_LIST[taskCount][1] + ",";
		row += AppList.FAST_APP_LIST[taskCount][2] + ",";
		
		// Task app
		row += getTaskApp() + ",";
		
		// Result app
		row += currentPackage + ",";
		
		// Start time
		row += task.getStartTime() + ",";
		
		// End time
		row += System.currentTimeMillis() + ",";
		
		// If through launcher
		row += icon.isThroughLauncher() + "\n";
		
		Log.v(TAG, row);
		result += row;
	}
	
	private void writeToFile(String data, String filename) {
		File file = new File(Environment.getExternalStorageDirectory(), filename);
		if (file.exists ()) file.delete (); 
	    try {
	           FileOutputStream out = new FileOutputStream(file);
	           out.write(data.getBytes());
	           out.flush();
	           out.close();
	    } catch (Exception e) {
	    	Log.e("Exception", "File write failed: " + e.toString());
	    }
	}
}
