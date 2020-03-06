package org.gojek.github.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import org.gojek.github.di.base.ViewModelFactory

/**
 * Created by Shahbaz Hashmi on 2020-03-06.
 */
@Module
abstract class ViewModelModule {

    /**
     * Binding NewsArticleViewModel using this key "NewsArticleViewModel::class"
     * So you can get NewsArticleViewModel using "NewsArticleViewModel::class" key from factory
     *//*
    @Binds
    @IntoMap
    @ViewModelKey(NewsArticleViewModel::class)
    abstract fun bindNewsArticleViewModel(newsArticleViewModel: NewsArticleViewModel): ViewModel

    *//**
     * Countries List View Model
     *//*
    @Binds
    @IntoMap
    @ViewModelKey(CountriesViewModel::class)
    abstract fun bindCountriesViewModel(countriesViewModel: CountriesViewModel): ViewModel*/

    /**
     * Binds ViewModels factory to provide ViewModels.
     */
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}