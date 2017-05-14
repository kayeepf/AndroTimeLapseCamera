package com.kayeepf.android.androidtimelapsecamera;

import android.app.Activity;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;

/**
 * Created by huangshifeng on 2017/5/14.
 */
public class SimplePowerWakeLockActivity extends Activity {

    static final String log_tag = "[PowerWakeActivity]";
    static PowerManager pm = null;
    PowerManager.WakeLock wl = null;

    public SimplePowerWakeLockActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pm = (PowerManager)getApplicationContext().getSystemService(
                getApplicationContext().POWER_SERVICE);
        wl = pm.newWakeLock(
                PowerManager.FULL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE,
                log_tag);
    }

    @Override
    protected void onResume() {
        Log.i(log_tag,">>>onResume()");
        super.onResume();
        wl.acquire();
        Log.i(log_tag, "<<<onResume()");
    }

    @Override
    protected void onPause() {
        Log.i(log_tag,">>>onPause()");
        super.onPause();
        wl.release();
        Log.i(log_tag, "<<<onPause()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
