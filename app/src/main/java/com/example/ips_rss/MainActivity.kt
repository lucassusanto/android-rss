package com.example.ips_rss

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var resultList = ArrayList<ScanResult>()
    lateinit var wifiManager: WifiManager

    val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            resultList = wifiManager.scanResults as ArrayList<ScanResult>
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wifiManager = this.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        btnGetRss.setOnClickListener{
            startScanning()
        }
    }

    fun startScanning() {
        registerReceiver(broadcastReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        wifiManager.startScan()

        tvRss.text = "Processing..";
        Handler().postDelayed({
            stopScanning()
        }, 3000)
    }

    fun stopScanning() {
        unregisterReceiver(broadcastReceiver)
        val axisList = ArrayList<Axis>()

        for (result in resultList) {
            axisList.add(Axis(result.SSID, result.BSSID, result.frequency, result.level))
        }

        var rssText: String
        rssText = ""

        for (axis in axisList) {
            Log.d("Result", axis.toString())
            rssText += axis.toString() + "\n"
        }

        tvRss.text = rssText;
    }
}
