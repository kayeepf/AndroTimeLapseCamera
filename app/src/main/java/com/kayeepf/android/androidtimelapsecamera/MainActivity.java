package com.kayeepf.android.androidtimelapsecamera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by huangshifeng on 2017/5/13.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(getApplicationContext(),TestPortalActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
