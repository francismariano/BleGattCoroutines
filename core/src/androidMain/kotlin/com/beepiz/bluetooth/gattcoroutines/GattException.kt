@file:Suppress("MemberVisibilityCanPrivate")

package com.beepiz.bluetooth.gattcoroutines

import android.bluetooth.BluetoothGatt

@ExperimentalBleGattCoroutinesCoroutinesApi
actual sealed class GattException actual constructor(message: String?) : Exception(message) {

    actual companion object {
        /**
         * See all codes here: [https://android.googlesource.com/platform/external/bluetooth/bluedroid/+/android-5.1.0_r1/stack/include/gatt_api.h]
         */
        actual fun humanReadableStatusCode(statusCode: Int) = when (statusCode) {
            BluetoothGatt.GATT_SUCCESS -> "GATT_SUCCESS"
            BluetoothGatt.GATT_READ_NOT_PERMITTED -> "GATT_READ_NOT_PERMITTED"
            BluetoothGatt.GATT_WRITE_NOT_PERMITTED -> "GATT_WRITE_NOT_PERMITTED"
            BluetoothGatt.GATT_INSUFFICIENT_AUTHENTICATION -> "GATT_INSUFFICIENT_AUTHENTICATION"
            BluetoothGatt.GATT_REQUEST_NOT_SUPPORTED -> "GATT_REQUEST_NOT_SUPPORTED"
            BluetoothGatt.GATT_INSUFFICIENT_ENCRYPTION -> "GATT_INSUFFICIENT_ENCRYPTION"
            BluetoothGatt.GATT_INVALID_OFFSET -> "GATT_INVALID_OFFSET"
            BluetoothGatt.GATT_INVALID_ATTRIBUTE_LENGTH -> "GATT_INVALID_ATTRIBUTE_LENGTH"
            BluetoothGatt.GATT_CONNECTION_CONGESTED -> "GATT_CONNECTION_CONGESTED"
            BluetoothGatt.GATT_FAILURE -> "GATT_FAILURE"
            else -> "$statusCode"
        }
    }
}

@ExperimentalBleGattCoroutinesCoroutinesApi
actual class OperationInitiationFailedException : GattException()

/** @see BluetoothGatt */
@ExperimentalBleGattCoroutinesCoroutinesApi
actual class OperationFailedException actual constructor(
    val statusCode: Int
) : GattException(
    "status: ${humanReadableStatusCode(
        statusCode
    )}"
)