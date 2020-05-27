package com.beepiz.bluetooth.gattcoroutines.extensions

import com.beepiz.bluetooth.gattcoroutines.*

@Suppress("UNCHECKED_CAST")
@ExperimentalBleGattCoroutinesCoroutinesApi
actual operator fun CMBluetoothGattService.get(characteristicUUID: CMUUID): BGC? {
    return (characteristics as List<BGC>).firstOrNull {
        it.UUID == characteristicUUID
    }
}

/**
 * Returns a [BGC] for the given [uuid], or throws a [NoSuchElementException] if there's no such a
 * characteristic.
 */
@Suppress("UNCHECKED_CAST")
@ExperimentalBleGattCoroutinesCoroutinesApi
actual fun CMBluetoothGattService.requireCharacteristic(uuid: CMUUID): BGC =
        (characteristics as List<BGC>).firstOrNull {
            it.UUID == uuid
        } ?: throw NoSuchElementException("service(${this.UUID}).characteristic($uuid)")


/**
 * Returns a [BGD] for the given [uuid], or throws a [NoSuchElementException] if there's no such a
 * descriptor.
 */
@Suppress("UNCHECKED_CAST")
@ExperimentalBleGattCoroutinesCoroutinesApi
actual fun BGC.requireDescriptor(uuid: CMUUID): BGD =
        (descriptors as List<BGD>).firstOrNull {
            it.UUID == uuid
        } ?: throw NoSuchElementException(
                "service(${this.service.UUID}).characteristic(${this.UUID}).descriptor($uuid)")