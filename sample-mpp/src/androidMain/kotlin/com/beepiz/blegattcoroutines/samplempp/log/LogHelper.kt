package com.beepiz.blegattcoroutines.samplempp.log

import timber.log.Timber

actual fun logHelper(message: String) {
    Timber.d(message)
}