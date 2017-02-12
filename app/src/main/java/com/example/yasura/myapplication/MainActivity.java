package com.example.yasura.myapplication;

import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;


import java.util.List;

public class MainActivity extends AppCompatActivity {
private Button btn;
    TextView txt,txt2;
    WifiManager wifiManager;
    WifiInfo connection;
    String display;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        txt2 = (TextView)findViewById(R.id.textView);
        btn = (Button) findViewById(R.id.button);
        txt = (TextView) findViewById(R.id.text2);
        aSwitch = (Switch) findViewById(R.id.switch1);
        wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && !wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(true);
                } else if (!isChecked && wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(false);
                }

            }});

    MyBrodecastReceive myBrodecastReceive = new MyBrodecastReceive();
        //register BroadCastReciver
        registerReceiver(myBrodecastReceive,new IntentFilter(wifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }
class MyBrodecastReceive extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        final WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        int state = wifi.getWifiState();
        StringBuffer stringBuffer =new StringBuffer();
        wifiManager.getScanResults();
      List<android.net.wifi.ScanResult> list = wifiManager.getScanResults();
        for(android.net.wifi.ScanResult scanResult:list){

            //stringBuffer.append("WifiSSID"+scanResult.BSSID+"SignalLevel :"+scanResult.level);
           // txt2.setText(stringBuf
            int levels = wifiManager.calculateSignalLevel(wifi.getConnectionInfo().getRssi(),scanResult.level);
            int diff = levels;
          // double distance = calculateDistance(scanResult.level,scanResult.frequency);
           stringBuffer.append(wifi.getConnectionInfo().getLinkSpeed());
            txt.setText(stringBuffer);

            /*if(scanResult.BSSID.equals(wifiManager.getConnectionInfo().getBSSID())) {
                int level = WifiManager.calculateSignalLevel(wifiManager.getConnectionInfo().getRssi(),
                        scanResult.level);
                int difference = level * 100 / scanResult.level;
                int signalStrangth= 0;
                if(difference >= 100)
                    signalStrangth = 4;
                else if(difference >= 75)
                    signalStrangth = 3;
                else if(difference >= 50)
                    signalStrangth = 2;
                else if(difference >= 25)
                    signalStrangth = 1;
                stringBuffer.append(signalStrangth);


                txt.setText(txt.getText() + "\nDifference :" + difference + " signal state:" + stringBuffer);

            }
*/
        }


    }
    public double calculateDistance(double signalLevelInDb, double freqInMHz) {
       double exp = (27.55 - (20 * Math.log10(freqInMHz)) + Math.abs(signalLevelInDb)) / 20.0;

       // distance = pow(10,(rssi at one meter-recived RSSI)/20)
        return Math.pow(10.0, exp);
    }
}

}
