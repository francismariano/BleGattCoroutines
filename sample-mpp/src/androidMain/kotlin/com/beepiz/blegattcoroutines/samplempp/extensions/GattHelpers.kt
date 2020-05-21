package com.beepiz.blegattcoroutines.samplempp.extensions

import android.bluetooth.BluetoothDevice
import androidx.annotation.RequiresApi
import splitties.systemservices.bluetoothManager

@RequiresApi(18)
fun deviceFor(macAddress: String): BluetoothDevice =
        bluetoothManager.adapter.getRemoteDevice(macAddress)
