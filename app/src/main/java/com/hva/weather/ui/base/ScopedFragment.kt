package com.hva.weather.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Extending the Fragment with a Coroutine because if you call a Coroutine with GlobalScope on the Fragment
 * and the job is not done and we destroy the Fragment again which happens with Fragments then the App will crash4
 */
abstract class ScopedFragment: Fragment(), CoroutineScope {
    private lateinit var job: Job

    /**
     * Dispatchers.Main because otherwise we cant do work on the UI-Thread
     * So we give back a job and the Main thread -> UI Thread
     */
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    /*
     * Overriding the onCreate a for starting the Job
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    /**
     * Overriding onDestroy for canceling the Job from the Coroutines so the App doesnt crash
     */
    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}