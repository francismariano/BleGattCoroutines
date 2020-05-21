@file:Suppress("MemberVisibilityCanPrivate")

package com.beepiz.bluetooth.gattcoroutines

@ExperimentalBleGattCoroutinesCoroutinesApi
expect sealed class GattException(message: String? = null) : Exception {
    companion object {
        fun humanReadableStatusCode(statusCode: Int): String
    }
}

@ExperimentalBleGattCoroutinesCoroutinesApi
expect class OperationInitiationFailedException : GattException

/** @see BluetoothGatt */
@ExperimentalBleGattCoroutinesCoroutinesApi
expect class OperationFailedException(statusCode: Int) : GattException