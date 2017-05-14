package com.kayeepf.android.androidtimelapsecamera;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

/**
 * Created by huangshifeng on 2017/5/14.
 */
public class SimpleCameraActivity extends Activity {

    public static final String log_tag = "[SimpleCameraActivity]";
    RelativeLayout relativeLayout;
    SurfaceView surfaceView;

    static boolean checkHardwareOk = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(log_tag, ">>>onCreate()");
        super.onCreate(savedInstanceState);
        relativeLayout = new RelativeLayout(getBaseContext());
        surfaceView = new SurfaceView(getBaseContext());
        relativeLayout.addView(surfaceView);
        Log.i(log_tag,"<<<onCreate()");
    }

    @Override
    protected void onResume() {
        Log.i(log_tag, ">>>onResume()");
        super.onResume();
        if(!getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Log.w(log_tag,"No Camera Feature");
            checkHardwareOk = false;
            this.finish();
        }
        checkHardwareOk = true;
        Log.i(log_tag, "<<<onResume()");
    }

    @Override
    protected void onPause() {
        Log.i(log_tag,">>>onPause()");
        super.onPause();
        if(!checkHardwareOk)
        {

        }
        else
        {

        }
        Log.i(log_tag, "<<<onPause()");
    }

    @Override
    protected void onDestroy() {
        Log.i(log_tag,">>>onDestroy()");
        super.onDestroy();
        Log.i(log_tag, "<<<onDestroy()");
    }

}
