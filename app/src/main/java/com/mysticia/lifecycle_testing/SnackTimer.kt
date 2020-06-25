package com.mysticia.lifecycle_testing

import android.os.Handler
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class SnackTimer(lifecycle : Lifecycle) : LifecycleObserver {

    var secondsCount = 0
    private var handler = Handler()
    private lateinit var runnable : Runnable

    init {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startTimer() {



    }

    fun stopTimer() {



    }

}