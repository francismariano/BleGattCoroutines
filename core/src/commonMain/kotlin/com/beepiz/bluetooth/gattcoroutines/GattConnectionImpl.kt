package com.beepiz.bluetooth.gattcoroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex

expect val STATUS_SUCCESS: Int

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExperimentalBleGattCoroutinesCoroutinesApi
expect class GattConnectionImpl(
        bluetoothDevice: CMBluetoothDevice,
        connectionSettings: GattConnection.ConnectionSettings
) : GattConnection, CoroutineScope {

    val rssiChannel: Channel<GattResponse<Int>>
    val servicesDiscoveryChannel: Channel<GattResponse<List<CMBluetoothGattService>>>
    val readChannel: Channel<GattResponse<BGC>>
    val writeChannel: Channel<GattResponse<BGC>>
    val reliableWriteChannel: Channel<GattResponse<Unit>>
    val characteristicChangedChannel: BroadcastChannel<BGC>
    val readDescChannel: Channel<GattResponse<BGD>>
    val writeDescChannel: Channel<GattResponse<BGD>>
    val mtuChannel: Channel<GattResponse<Int>>
    val phyReadChannel: Channel<GattResponse<Phy>>

    val isConnectedBroadcastChannel: ConflatedBroadcastChannel<Boolean>

    val isConnectedFlow: Flow<Boolean>

    var isClosed: Boolean

    var closedException: ConnectionClosedException?

    val stateChangeBroadcastChannel: ConflatedBroadcastChannel<StateChange>

    var bluetoothGatt: BG?
    fun requireGatt(): BG

    fun closeInternal(notifyStateChangeChannel: Boolean, cause: ConnectionClosedException)

    val callback: CMBluetoothGattCallback

    fun Boolean.checkOperationInitiationSucceeded()

    /** @see gattRequest */
    val bleOperationMutex: Mutex
    val reliableWritesMutex: Mutex
    var reliableWriteOngoing: Boolean

    /**
     * We need to wait for one operation to fully complete before making another one to avoid
     * Bluetooth Gatt errors.
     */
    suspend inline fun <E> gattRequest(
            ch: ReceiveChannel<GattResponse<E>>,
            operation: CMBluetoothGatt.() -> Boolean
    ): E

    /**
     * This code is currently not fault tolerant. The channel is irrevocably closed if the GATT
     * status is not success.
     */
    fun <E> SendChannel<GattResponse<E>>.launchAndSendResponse(e: E, status: Int)

    @Suppress("NOTHING_TO_INLINE")
    inline fun checkNotClosed()

    class GattResponse<out E>(e: E, status: Int)

}

expect abstract class CMBluetoothGattCallback
expect class CMBluetoothGatt