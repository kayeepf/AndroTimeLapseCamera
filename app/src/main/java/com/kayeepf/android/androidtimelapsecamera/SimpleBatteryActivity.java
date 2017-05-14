package com.kayeepf.android.androidtimelapsecamera;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.usb.UsbManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by huangshifeng on 2017/5/14.
 */
public class SimpleBatteryActivity extends Activity {

    public static final String log_tag = "[SimplePowerActivity]";

    LinearLayout linearLayout;
    TextView textView_BatteryInfo;

    static Handler handler = null;

    IntentFilter intentFilter;
    BatteryConnectionReceiver batteryConnectionReceiver = null;

    boolean isCharging = false;
    boolean usbCharge = false;
    boolean acCharge = false;
    int batteryLevel = 0;
    int batteryScale = 0;
    int batteryTemperature = 0;
    int batteryVoltage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(handler == null)
            handler = new Handler();

        linearLayout = new LinearLayout(getApplicationContext());

        textView_BatteryInfo = new TextView(getApplicationContext());
        textView_BatteryInfo.setTextColor(Color.BLUE);
        textView_BatteryInfo.setText("");
        textView_BatteryInfo.setScroller(new Scroller(getApplicationContext()));
        linearLayout.addView(textView_BatteryInfo);

        setContentView(linearLayout);

        intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        batteryConnectionReceiver = new BatteryConnectionReceiver();
        registerReceiver(batteryConnectionReceiver,intentFilter);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryConnectionReceiver);
        batteryConnectionReceiver = null;
    }

    public class BatteryConnectionReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;

            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
            acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
            batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
            batteryScale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
            batteryTemperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,-1);
            batteryVoltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,-1);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //String tmp = textView_BatteryInfo.getText().toString();
                    String text = "";
                    text += "isCharging = "+isCharging+"\n";
                    text += "usbCharge = "+usbCharge+"\n";
                    text += "acCharge = "+acCharge+"\n";
                    text += "batteryLevel = "+batteryLevel+"\n";
                    text += "batteryScale = "+batteryScale+"\n";
                    text += "batteryTemperature = "+batteryTemperature+"\n";
                    text += "batteryVoltage = "+batteryVoltage+"\n";
                    textView_BatteryInfo.setText(text);
                }
            });
        }
    }
}
