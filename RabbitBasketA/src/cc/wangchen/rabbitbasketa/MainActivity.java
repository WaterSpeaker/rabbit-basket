package cc.wangchen.rabbitbasketa;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.premnirmal.Magnet.*;


public class MainActivity extends Activity {
	
	Magnet mMagnet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ImageView iconView = new ImageView(this);
        iconView.setImageResource(R.drawable.ic_launcher);
        mMagnet = new Magnet.Builder(this)
                .setIconView(iconView) // required
                .setIconCallback(this.mIconCallback)
                .setRemoveIconResId(R.drawable.trash)
                .setRemoveIconShadow(R.drawable.bottom_shadow)
                .setShouldFlingAway(true)
                .setShouldStickToWall(true)
                .setRemoveIconShouldBeResponsive(true)
                .build();
        mMagnet.show();
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
    
    IconCallback mIconCallback = new IconCallback(){

		@Override
		public void onFlingAway() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onMove(float x, float y) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onIconClick(View icon, float iconXPose, float iconYPose) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onIconDestroyed() {
			// TODO Auto-generated method stub
			
		}
    	
    }; 
}
