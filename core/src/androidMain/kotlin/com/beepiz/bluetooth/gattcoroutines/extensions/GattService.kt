package com.beepiz.bluetooth.gattcoroutines.extensions

import android.bluetooth.BluetoothGattService
import androidx.annotation.RequiresApi
import com.beepiz.bluetooth.gattcoroutines.BGC
import com.beepiz.bluetooth.gattcoroutines.BGD
import com.beepiz.bluetooth.gattcoroutines.ExperimentalBleGattCoroutinesCoroutinesApi
import java.util.*

@RequiresApi(18)
@ExperimentalBleGattCoroutinesCoroutinesApi
actual operator fun BluetoothGattService.get(characteristicUUID: UUID): BGC? {
    return getCharacteristic(characteristicUUID)
}

/**
 * Returns a [BGC] for the given [uuid], or throws a [NoSuchElementException] if there's no such a
 * characteristic.
 */
@RequiresApi(18)
@ExperimentalBleGattCoroutinesCoroutinesApi
actual fun BluetoothGattService.requireCharacteristic(uuid: UUID): BGC = getCharacteristic(uuid)
    ?: throw NoSuchElementException("service(${this.uuid}).characteristic($uuid)")

/**
 * Returns a [BGD] for the given [uuid], or throws a [NoSuchElementException] if there's no such a
 * descriptor.
 */
@RequiresApi(18)
@ExperimentalBleGattCoroutinesCoroutinesApi
actual fun BGC.requireDescriptor(uuid: UUID): BGD = getDescriptor(uuid) ?: throw NoSuchElementException(
    "service(${this.service.uuid}).characteristic(${this.uuid}).descriptor($uuid)"
)