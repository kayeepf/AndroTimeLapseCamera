package com.kayeepf.android.androidtimelapsecamera;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import java.io.IOException;

/**
 * Created by huangshifeng on 2017/5/14.
 */
public class SimpleCameraActivity extends Activity implements SurfaceHolder.Callback {

    public static final String log_tag = "[SimpleCameraActivity]";
    RelativeLayout relativeLayout;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;

    Camera camera = null;

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
            this.finish();
        }
        Log.i(log_tag, "<<<onResume()");
    }

    @Override
    protected void onPause() {
        Log.i(log_tag,">>>onPause()");
        super.onPause();
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
        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            camera = camera.open();
            Camera.Parameters cameraParam = camera.getParameters();
            camera.setParameters(cameraParam);
            camera.setDisplayOrientation(90);
        }
        Log.i(log_tag,"<<<surfaceCreated()");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(log_tag,">>>surfaceChanged()");
        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) &&
                camera != null) {
            try {
                camera.setPreviewDisplay(surfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            camera.startPreview();
        }
        Log.i(log_tag,"<<<surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(log_tag,">>>surfaceDestroyed()");
        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) &&
                camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
        Log.i(log_tag,"<<<surfaceDestroyed()");
    }
}
