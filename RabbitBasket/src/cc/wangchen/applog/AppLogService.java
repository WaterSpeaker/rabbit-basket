package cc.wangchen.applog;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

/**
 * Log application usage and store in database
 * @author Wang Chen <waterspeakercity@gmail.com>
 *
 */

public class AppLogService extends Service {
	private Looper mServiceLooper;
	private ServiceHandler mServiceHandler;
	private static final String TAG = "AppLog";
	private ActivityManager mActivityManager;

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
						wait(1000);
						List<RunningAppProcessInfo> appProcessList = mActivityManager.getRunningAppProcesses();
						String packages = "";
						if(appProcessList != null) {
							String packageList[] = appProcessList.get(0).pkgList;
							for(int j = 0; j < packageList.length; j++) {
								packages += packageList[j];
								packages += ' ';
							}
						}
						Log.v(TAG, packages);
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

		// Get the HandlerThread's Looper and use it for our Handler
		mServiceLooper = thread.getLooper();
		mServiceHandler = new ServiceHandler(mServiceLooper);
		mActivityManager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
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
		return START_STICKY;
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
}
