package cc.wangchen.applog;

import java.util.LinkedList;
import java.util.Random;

import edu.umass.cs.sensors.falcon.model.APPM;
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

public class AppPredictService extends Service {
	private Looper mServiceLooper;
	private ServiceHandler mServiceHandler;
	private static final String TAG = "AppPredict";	

	// Handler that receives messages from the thread
	private final class ServiceHandler extends Handler {
		public ServiceHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO read applog and predict
			/**
			 * This is only an example scenario to demonstrate the use
			 * of program. APPM is not the best algorithm if the sequence
			 * of events are randomly generated as in this example.
			 */
			
			int TOPK=5;
			int TIME_BINS=1;
			int N_FIRST_CTX = 0;
			int N_SECOND_CTX= 0;
			
			APPM appm = new APPM(TOPK,N_FIRST_CTX,N_SECOND_CTX,TIME_BINS);
			
			//Events for which temporal model is needed
			LinkedList<String> eventMonitorList = new LinkedList<String>();
			eventMonitorList.add("Event0");
			String targetEvent[] = new String[]{"Event0"};
			
			//Register events for temporal model
			appm.registerEventsForTemporalModeling(eventMonitorList);
			
			Random random = new Random(); //Random data generator
			long startTime = System.currentTimeMillis();
			int i = 0;
			while (i++<100) {
				synchronized (this) {
					try {
						
						// Generate random event and random time
						String event = "Event" + random.nextInt(5); // random
																	// event
						long currentTime = startTime + random.nextInt(8)
								* 10000; // random time

						// Add generated event observation to the model
						appm.addEventObservation(currentTime, event, 0, 0);
						Log.v(TAG, "Observed Event: " + event + "\n");

						// Get Next Event predictions
						String predictions[] = appm.getEventPredictions(0, 0);
						
						// Get Event Likelihood in delta time
						long deltaTime = 40000;
						double likelihood[] = appm
								.getEventLikelihoodInDeltaTime(currentTime,
										deltaTime, 0, 0, targetEvent);
						String predictResults = "";
						for (int j = 0; j < likelihood.length; j++) {
							String predictResult = "Likelihood[" + targetEvent[j]
									+ "]=" + likelihood[j];
							predictResults += predictResult;
						}
						startTime = currentTime;
						
						Log.v(TAG, predictResults);
						
					} catch (Exception e) {
					}
				}
			}
			// Stop the service using the startId, so that we don't stop
			// the service in the middle of handling another job
			stopSelf(msg.arg1);
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
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "predict service starting", Toast.LENGTH_SHORT).show();

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
		Toast.makeText(this, "predict service done", Toast.LENGTH_SHORT).show();
	}
}
