package com.beepiz.bluetooth.gattcoroutines

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel

@ExperimentalBleGattCoroutinesCoroutinesApi
actual interface GattConnection {
    actual companion object {
        @ExperimentalCoroutinesApi
        @ObsoleteCoroutinesApi
        actual operator fun invoke(
                bluetoothDevice: CMBluetoothDevice,
                connectionSettings: ConnectionSettings
        ): GattConnection = GattConnectionImpl(bluetoothDevice, connectionSettings)

        actual val clientCharacteristicConfiguration: CMUUID =
                CMUUID.UUIDWithString("00002902-0000-1000-8000-00805f9b34fb")
    }

    actual class ConnectionSettings actual constructor(
            val autoConnect: Boolean,
            val allowAutoConnect: Boolean,
            val disconnectOnClose: Boolean,
            var transport: Int,
            var phy: Int
    ) {
        init {
            // TODO: Check transport variable
            // transport = CMBluetoothDevice.TRANSPORT_AUTO
            // TODO: Check phy variable
            // phy = CMBluetoothDevice.PHY_LE_1M_MASK
            if (autoConnect) require(allowAutoConnect)
        }
    }

    actual val bluetoothDevice: CMBluetoothDevice

    actual val isConnected: Boolean

    actual suspend fun connect()

    actual suspend fun disconnect()

    actual fun close(notifyStateChangeChannel: Boolean)

    actual suspend fun readRemoteRssi(): Int

    actual fun requestPriority(priority: Int)

    actual suspend fun discoverServices(): List<CMBluetoothGattService>

    actual fun setCharacteristicNotificationsEnabled(characteristic: BGC, enable: Boolean)

    actual fun openNotificationSubscription(
            characteristic: BGC,
            disableNotificationsOnChannelClose: Boolean
    ): ReceiveChannel<BGC>

    actual suspend fun setCharacteristicNotificationsEnabledOnRemoteDevice(
            characteristic: BGC,
            enable: Boolean
    )

    actual fun getService(uuid: CMUUID): CMBluetoothGattService?

    actual suspend fun readCharacteristic(characteristic: BGC): BGC

    actual suspend fun writeCharacteristic(characteristic: BGC): BGC

    actual suspend fun reliableWrite(writeOperations: suspend GattConnection.() -> Unit)

    actual suspend fun readDescriptor(desc: BGD): BGD

    actual suspend fun writeDescriptor(desc: BGD): BGD

    actual suspend fun readPhy(): Phy

    actual suspend fun requestMtu(mtu: Int): Int

    actual val stateChangeChannel: ReceiveChannel<StateChange>

    actual val notifyChannel: ReceiveChannel<BGC>
}

actual typealias CMUUID = platform.CoreBluetooth.CBUUID
actual typealias CMBluetoothDevice = platform.CoreBluetooth.CBCentralManager
actual typealias CMBluetoothGattService = platform.CoreBluetooth.CBService