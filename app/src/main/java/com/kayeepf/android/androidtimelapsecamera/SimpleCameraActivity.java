package com.kayeepf.android.androidtimelapsecamera;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

/**
 * Created by huangshifeng on 2017/5/14.
 */
public class SimpleCameraActivity extends Activity implements SurfaceHolder.Callback {

    public static final String log_tag = "[SimpleCameraActivity]";
    RelativeLayout relativeLayout;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;

    static boolean checkHardwareOk = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(log_tag, ">>>onCreate()");
        super.onCreate(savedInstanceState);
        relativeLayout = new RelativeLayout(getBaseContext());
        surfaceView = new SurfaceView(getBaseContext());
        relativeLayout.addView(surfaceView);

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        setContentView(relativeLayout);
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

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(log_tag,">>>surfaceCreated()");
        Log.i(log_tag,"<<<surfaceCreated()");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(log_tag,">>>surfaceChanged()");
        Log.i(log_tag,"<<<surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(log_tag,">>>surfaceDestroyed()");
        Log.i(log_tag,"<<<surfaceDestroyed()");
    }
}
