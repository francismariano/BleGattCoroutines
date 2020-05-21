package com.beepiz.blegattcoroutines.samplempp

import androidx.annotation.RequiresApi
import com.beepiz.blegattcoroutines.genericaccess.GenericAccess
import com.beepiz.blegattcoroutines.samplempp.extensions.deviceFor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import splitties.toast.toast
import timber.log.Timber

class MainViewModel : ScopedViewModel() {

    private val myEddystoneUrlBeaconMacAddress = "F2:D6:43:93:70:7A"
    private val iBks12MacAddress = "F6:61:CF:AF:D0:07"
    private val defaultDeviceMacAddress = iBks12MacAddress

    private var operationAttempt: Job? = null

    @RequiresApi(18)
    fun logNameAndAppearance(deviceMacAddress: String = defaultDeviceMacAddress,
                             connectionTimeoutInMillis: Long = 5000L) {
        operationAttempt?.cancel()
        operationAttempt = launch {
            BluetoothRepository.connect(deviceFor(deviceMacAddress)) { device, services ->
                services.forEach { Timber.d("Service found with UUID: ${it.uuid}") }
                with(GenericAccess) {
                    device.readAppearance()
                    Timber.d("Device appearance: ${device.appearance}")
                    device.readDeviceName()
                    Timber.d("Device name: ${device.deviceName}".also { toast(it) })
                }
            }
            operationAttempt = null
        }
    }
}
