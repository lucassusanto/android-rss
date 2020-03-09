package com.example.ips_rss

class Axis(val SSID: String, val BSSID: String, val frequency: Int, val level: Int) {
    override fun toString(): String {
        return "SSID: $SSID, BSSID: $BSSID, MHz: $frequency, RSS: $level"
    }
}