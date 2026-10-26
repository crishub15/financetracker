package com.puregold.financetracker

import android.app.Application
import com.puregold.financetracker.di.AppContainer

class FinanceTrackerApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer()
    }
}
