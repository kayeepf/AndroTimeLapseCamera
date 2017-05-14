package com.kayeepf.android.androidtimelapsecamera;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by huangshifeng on 2017/5/14.
 */
public class TestPortalActivity extends Activity {

    public static final String log_tag = "[TestPortalActivity]";
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(log_tag,">>>onCreate()");
        super.onCreate(savedInstanceState);
        linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        setContentView(linearLayout);
        Log.i(log_tag, "<<<onCreate()");
    }

    @Override
    protected void onResume() {
        Log.i(log_tag,">>>onResume()");
        super.onResume();
        Log.i(log_tag,"<<<onResume()");
    }

    @Override
    protected void onPause() {
        Log.i(log_tag,">>>onPause()");
        super.onPause();
        Log.i(log_tag,"<<<onPause()");
    }

    @Override
    protected void onDestroy() {
        Log.i(log_tag,">>>onDestroy()");
        super.onDestroy();
        Log.i(log_tag, "<<<onDestroy()");
    }

}
