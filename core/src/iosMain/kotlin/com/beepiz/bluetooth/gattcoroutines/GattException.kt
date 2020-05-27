package com.beepiz.bluetooth.gattcoroutines

@ExperimentalBleGattCoroutinesCoroutinesApi
actual sealed class GattException actual constructor(message: String?) : Exception() {
    actual companion object {
        actual fun humanReadableStatusCode(statusCode: Int): String {
            TODO("Not yet implemented")
        }
    }
}

@ExperimentalBleGattCoroutinesCoroutinesApi
actual class OperationInitiationFailedException : GattException()

@ExperimentalBleGattCoroutinesCoroutinesApi
actual class OperationFailedException actual constructor(
        val statusCode: Int
) : GattException(
        "status: ${humanReadableStatusCode(
                statusCode
        )}"
)
