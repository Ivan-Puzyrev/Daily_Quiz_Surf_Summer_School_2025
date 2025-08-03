package com.ivanpuzyrev.dailyquizsurfsummerschool2025

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.di.ApplicationComponent
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.di.DaggerApplicationComponent

class QuizApp: Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as QuizApp).component
}