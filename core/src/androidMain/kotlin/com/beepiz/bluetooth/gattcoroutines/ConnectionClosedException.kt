package com.beepiz.bluetooth.gattcoroutines

import kotlinx.coroutines.CancellationException

@ExperimentalBleGattCoroutinesCoroutinesApi
actual class ConnectionClosedException actual constructor(
        cause: Throwable?,
        messageSuffix: String
) : CancellationException("The connection has been irrevocably closed$messageSuffix.") {
    init {
        initCause(cause)
    }
}
