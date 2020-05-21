package com.beepiz.blegattcoroutines.samplempp

import com.beepiz.blegattcoroutines.samplempp.log.logHelper
import com.beepiz.bluetooth.gattcoroutines.CMBluetoothDevice
import com.beepiz.bluetooth.gattcoroutines.CMBluetoothGattService
import com.beepiz.bluetooth.gattcoroutines.GattConnection
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach

@ObsoleteCoroutinesApi
fun GattConnection.logConnectionChanges() {
    GlobalScope.launch(Dispatchers.Main) {
        stateChangeChannel.consumeEach {
            logHelper("connection state changed: $it")
        }
    }
}

object BluetoothRepository {

    /**
     * Connects to the device, discovers services, executes [block] and finally closes the connection.
     */
    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    suspend inline fun connect(
            device: CMBluetoothDevice,
            connectionTimeoutInMillis: Long = 5000L,
            block: (GattConnection, List<CMBluetoothGattService>) -> Unit
    ) {
        val deviceConnection = GattConnection(device)
        try {
            deviceConnection.logConnectionChanges()
            withTimeout(connectionTimeoutInMillis) {
                deviceConnection.connect()
            }
            logHelper("Connected!")
            val services = deviceConnection.discoverServices()
            logHelper("Services discovered!")
            block(deviceConnection, services)
        } catch (e: TimeoutCancellationException) {
            logHelper("Connection timed out after $connectionTimeoutInMillis milliseconds!")
            throw e
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            logHelper(e.message.toString())
        } finally {
            deviceConnection.close()
            logHelper("Closed!")
        }
    }

}