package com.beepiz.bluetooth.gattcoroutines

import kotlinx.coroutines.CancellationException

@ExperimentalBleGattCoroutinesCoroutinesApi
expect class ConnectionClosedException internal constructor(
        cause: Throwable? = null,
        messageSuffix: String = ""
) : CancellationException
