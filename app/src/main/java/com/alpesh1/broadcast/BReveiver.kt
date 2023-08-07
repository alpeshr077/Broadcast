package com.alpesh1.broadcast

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log

class BReveiver : BroadcastReceiver() {

    private val TAG = "PhoneStatReceiver"

    private var incomingFlag = false

    private var incoming_number: String? = null

    override fun onReceive(p0: Context?, p1: Intent?) {

        if (p1?.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            incomingFlag = false
            val phoneNumber: String? = p1?.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
            Log.e(TAG, "call OUT:$phoneNumber")
        } else {

            val tm = p0?.getSystemService(Service.TELEPHONY_SERVICE) as TelephonyManager

            when (tm.callState) {
                TelephonyManager.CALL_STATE_RINGING -> {
                    incomingFlag = true
                    incoming_number = p1?.getStringExtra("incoming_number")
                    Log.e(TAG, "RINGING :$incoming_number")
                }

                TelephonyManager.CALL_STATE_OFFHOOK -> if (incomingFlag) {
                    Log.e(TAG, "incoming ACCEPT :$incoming_number")
                }

                TelephonyManager.CALL_STATE_IDLE -> if (incomingFlag) {
                    Log.e(TAG, "incoming IDLE")
                }
            }
        }

    }
}