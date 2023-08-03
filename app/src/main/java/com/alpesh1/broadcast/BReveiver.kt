package com.alpesh1.broadcast

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log

class BReveiver: BroadcastReceiver() {

    private val TAG = "BReveiver"
    private var incomingFlag = false
    private var incoming_number: String? = null

    override fun onReceive(p0: Context?, intent: Intent?) {


        if (intent!!.action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            incomingFlag = false
            val phoneNumber = intent!!.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
            Log.i(TAG, "call OUT:$phoneNumber")
        } else {


            when (p0?.getSystemService(Service.TELEPHONY_SERVICE)?.javaClass) {
                TelephonyManager.CALL_STATE_RINGING.javaClass -> {
                    incomingFlag = true
                    incoming_number = intent!!.getStringExtra("incoming_number")
                    Log.i(TAG, "RINGING :$incoming_number")
                }

                TelephonyManager.CALL_STATE_OFFHOOK.javaClass -> if (incomingFlag) {
                    Log.i(TAG, "incoming ACCEPT :$incoming_number")
                }

                TelephonyManager.CALL_STATE_IDLE.javaClass -> if (incomingFlag) {
                    Log.i(TAG, "incoming IDLE")
                }
            }
        }

    }
}