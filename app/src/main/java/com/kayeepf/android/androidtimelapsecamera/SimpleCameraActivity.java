package com.kayeepf.android.androidtimelapsecamera;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.IOException;

/**
 * Created by huangshifeng on 2017/5/14.
 */
public class SimpleCameraActivity extends Activity implements SurfaceHolder.Callback, Camera.AutoFocusCallback, Camera.ShutterCallback, Camera.PictureCallback {

    public static final String log_tag = "[SimpleCameraActivity]";
    //RelativeLayout relativeLayout;
    LinearLayout linearLayoutAll;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;

    LinearLayout linearLayoutUi;
    Button btn_cameraAutoFucus;
    Button btn_shutter;

    static Camera camera = null;
    final static int cameraId = 0;

    static Handler handler = null;

    SimpleCameraActivity getInstance(){
        return this;
    }

    void enableAllUi()
    {
        btn_cameraAutoFucus.setEnabled(true);
        btn_shutter.setEnabled(true);
    }

    void disableAllUi()
    {
        btn_cameraAutoFucus.setEnabled(false);
        btn_shutter.setEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(log_tag, ">>>onCreate()");
        super.onCreate(savedInstanceState);
        linearLayoutAll = new LinearLayout(getApplicationContext());
        linearLayoutAll.setOrientation(LinearLayout.VERTICAL);
        surfaceView = new SurfaceView(getBaseContext());

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        linearLayoutUi = new LinearLayout(getApplicationContext());
        linearLayoutUi.setOrientation(LinearLayout.HORIZONTAL);

        btn_cameraAutoFucus = new Button(getApplicationContext());
        btn_cameraAutoFucus.setText("AF");
        btn_cameraAutoFucus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableAllUi();
                getInstance().handler.post(new Runnable() {
                    @Override
                    public void run() {
                        camera.autoFocus(getInstance());
                    }
                });
            }
        });
        linearLayoutUi.addView(btn_cameraAutoFucus);

        btn_shutter = new Button(getApplicationContext());
        btn_shutter.setText("Shutter");
        btn_shutter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableAllUi();
                getInstance().handler.post(new Runnable() {
                    @Override
                    public void run() {
                        camera.takePicture(getInstance(), null, getInstance());
                    }
                });
            }
        });
        linearLayoutUi.addView(btn_shutter);

        linearLayoutAll.addView(linearLayoutUi);
        linearLayoutAll.addView(surfaceView);

        setContentView(linearLayoutAll);

        if(handler == null)
        {
            handler = new Handler();
        }
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

    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        Log.w(log_tag,"rotation = "+rotation);
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(log_tag,">>>surfaceCreated()");
        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            if(camera == null)
                camera = camera.open();
            Camera.Parameters cameraParam = camera.getParameters();
            camera.setParameters(cameraParam);
        }
        Log.i(log_tag,"<<<surfaceCreated()");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(log_tag,">>>surfaceChanged()");
        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) &&
                camera != null) {
            camera.stopPreview();
            setCameraDisplayOrientation(this,cameraId,camera);
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

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        Log.i(log_tag,">>>onAutoFocus()");
        Log.d(log_tag, "auto focus " + ((success == true) ? "success":"fail"));
        enableAllUi();
        Log.i(log_tag, "<<<onAutoFocus()");
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Log.i(log_tag,">>>onPictureTaken()");
        enableAllUi();
        Log.i(log_tag,"<<<onPictureTaken()");
    }

    @Override
    public void onShutter() {
        Log.i(log_tag, ">>>onShutter()");
        camera.startPreview();
        Log.i(log_tag,"<<<onShutter()");
    }
}
