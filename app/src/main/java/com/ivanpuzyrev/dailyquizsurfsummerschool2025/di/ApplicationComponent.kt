package com.ivanpuzyrev.dailyquizsurfsummerschool2025.di

import android.app.Application
import android.content.Context
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [DataModule::class, ViewModelModule::class]
)
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}