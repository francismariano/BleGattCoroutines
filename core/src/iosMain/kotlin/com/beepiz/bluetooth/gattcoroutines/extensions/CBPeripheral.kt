package com.beepiz.bluetooth.gattcoroutines.extensions

import com.beepiz.bluetooth.gattcoroutines.BGC
import com.beepiz.bluetooth.gattcoroutines.CMBluetoothGattService
import com.beepiz.bluetooth.gattcoroutines.CMUUID
import platform.CoreBluetooth.CBPeripheral

fun CBPeripheral.extReadRSSI(): Boolean {
    this.readRSSI()
    return true
}

fun CBPeripheral.extDiscoverServices(): Boolean {
    this.discoverServices(null)
    return true
}

fun CBPeripheral.extReadCharacteristic(characteristc: BGC): Boolean {
    this.readValueForCharacteristic(characteristc)
    return true
}

fun CBPeripheral.extWriteCharacteristic(characteristc: BGC): Boolean {
//    this.write(characteristc)
    return true
}