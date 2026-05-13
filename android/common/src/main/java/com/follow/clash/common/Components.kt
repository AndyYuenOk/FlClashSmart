package com.follow.clash.common

import android.content.ComponentName

object Components {
    // Used only for Flutter MethodChannel names. Keep in sync with Dart packageName.
    const val CHANNEL_PACKAGE = "com.follow.clash.smart"

    // Used only for Android component class FQCN. This matches Kotlin source package.
    const val COMPONENT_CLASS_PACKAGE = "com.follow.clash"

    val MAIN_ACTIVITY =
        ComponentName(GlobalState.packageName, "${COMPONENT_CLASS_PACKAGE}.MainActivity")

    val TEMP_ACTIVITY =
        ComponentName(GlobalState.packageName, "${COMPONENT_CLASS_PACKAGE}.TempActivity")

    val BROADCAST_RECEIVER =
        ComponentName(GlobalState.packageName, "${COMPONENT_CLASS_PACKAGE}.BroadcastReceiver")
}
