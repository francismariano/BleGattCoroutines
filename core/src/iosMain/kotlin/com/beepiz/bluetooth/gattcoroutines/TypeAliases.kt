package com.beepiz.bluetooth.gattcoroutines

import platform.CoreBluetooth.CBCharacteristic
import platform.CoreBluetooth.CBDescriptor
import platform.CoreBluetooth.CBPeripheral

actual typealias BG = CBPeripheral
actual typealias BGC = CBCharacteristic
actual typealias BGD = CBDescriptor