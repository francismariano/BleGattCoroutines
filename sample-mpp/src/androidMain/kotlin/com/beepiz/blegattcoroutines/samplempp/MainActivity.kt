package com.beepiz.blegattcoroutines.samplempp

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import splitties.arch.lifecycle.ObsoleteSplittiesLifecycleApi
import splitties.arch.lifecycle.activityScope
import splitties.dimensions.dip
import splitties.lifecycle.coroutines.lifecycleScope
import splitties.views.dsl.core.*
import splitties.views.gravityCenterHorizontal
import splitties.views.onClick
import splitties.views.padding

@SuppressLint("SetTextI18n") // This is just a sample, English is enough.
class MainActivity : AppCompatActivity() {

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    private val viewModel by activityScope<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            ensureLocationPermissionOrFinishActivity()
            //if (SDK_INT >= 21) registerWhileStarted(BleScanHeater())
            contentView = verticalLayout {
                padding = dip(16)
                val lp = lParams(gravity = gravityCenterHorizontal)
                add(button {
                    text = "Log name and appearance of default device"
                    onClick { viewModel.logNameAndAppearance() }
                }, lp)
            }
        }
    }

    private suspend fun ensureLocationPermissionOrFinishActivity() = ensurePermission(
            permission = Manifest.permission.ACCESS_FINE_LOCATION,
            askDialogTitle = "Location permission required",
            askDialogMessage = "Bluetooth Low Energy can be used for location, " +
                    "so the permission is required."
    ) { finish(); throw CancellationException() }
}
