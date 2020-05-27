package com.beepiz.bluetooth.gattcoroutines.extensions

import com.beepiz.bluetooth.gattcoroutines.*

import com.beepiz.bluetooth.gattcoroutines.BGC
import com.beepiz.bluetooth.gattcoroutines.BGD
import com.beepiz.bluetooth.gattcoroutines.ExperimentalBleGattCoroutinesCoroutinesApi
import com.beepiz.bluetooth.gattcoroutines.GattConnection

@ExperimentalBleGattCoroutinesCoroutinesApi
expect operator fun CMBluetoothGattService.get(characteristicUUID: CMUUID): BGC?

private const val ensureDiscoveryMsg = "Make sure the service discovery has been performed!"

/**
 * Returns a [BluetoothGattService] for the given [uuid], or throws a [NoSuchElementException] if
 * there's no such a service.
 */
@ExperimentalBleGattCoroutinesCoroutinesApi
fun GattConnection.requireService(uuid: CMUUID): CMBluetoothGattService = getService(uuid)
        ?: throw NoSuchElementException("service($uuid) not found. $ensureDiscoveryMsg")

/**
 * Returns a [BGC] for the given [uuid], or throws a [NoSuchElementException] if there's no such a
 * characteristic.
 */
@ExperimentalBleGattCoroutinesCoroutinesApi
expect fun CMBluetoothGattService.requireCharacteristic(uuid: CMUUID): BGC

/**
 * Returns a [BGD] for the given [uuid], or throws a [NoSuchElementException] if there's no such a
 * descriptor.
 */
@ExperimentalBleGattCoroutinesCoroutinesApi
expect fun BGC.requireDescriptor(uuid: CMUUID): BGD

/**
 * Returns a [BGC] for the given [serviceUuid] and [characteristicUuid], or throws a
 * [NoSuchElementException] if there's no such a characteristic.
 */
@ExperimentalBleGattCoroutinesCoroutinesApi
fun GattConnection.requireCharacteristic(
        serviceUuid: CMUUID,
        characteristicUuid: CMUUID
): BGC = requireService(serviceUuid).requireCharacteristic(characteristicUuid)

/**
 * Returns a [BGD] for the given [serviceUuid], [characteristicUuid] and [descriptorUuid],
 * or throws a [NoSuchElementException] if there's no such a descriptor.
 */
@ExperimentalBleGattCoroutinesCoroutinesApi
fun GattConnection.requireDescriptor(
        serviceUuid: CMUUID,
        characteristicUuid: CMUUID,
        descriptorUuid: CMUUID
): BGD = requireService(serviceUuid).requireCharacteristic(characteristicUuid).requireDescriptor(
        descriptorUuid
)

