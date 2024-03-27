package com.davidtomas.taskyapp.util

import android.util.Log
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class AddLogExtension : BeforeEachCallback {
    override fun beforeEach(context: ExtensionContext?) {
        mockkStatic(Log::class)
        every { android.util.Log.d(any(), any()) } returns 0
    }
}