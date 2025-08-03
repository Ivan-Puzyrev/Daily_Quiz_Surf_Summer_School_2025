package com.ivanpuzyrev.dailyquizsurfsummerschool2025.di

import androidx.lifecycle.ViewModel
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.HistoryViewModel
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    fun bindHistoryViewModel(viewModel: HistoryViewModel): ViewModel
}