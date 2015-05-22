package cc.wangchen.rabbitbasket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;

/**
 * Main activity of Rabbit Basket
 * @author Wang Chen <waterspeakercity@gmail.com>
 *
 */

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Share.activityContext = this;

		// Start service
		Intent intent = new Intent(this, AppLogService.class);
		startService(intent);
	}

}
