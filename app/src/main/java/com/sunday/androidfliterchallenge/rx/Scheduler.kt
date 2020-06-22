package com.sunday.androidfliterchallenge.rx
import io.reactivex.Scheduler

interface Scheduler{
    /**
     * Returns UI schedule provider.
     * @return
     */
    fun ui(): Scheduler

    /**
     * Returns worker (background) schedule provider.
     * @return
     */
    fun background(): Scheduler
}