package com.bluelotus.search1.ui.di

import com.bluelotus.search1.ui.navigation.SearchFeatureApi
import com.bluelotus.search1.ui.navigation.SearchFeatureApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UiModule {

    @Provides
    fun provideSearchFeatureApi() : SearchFeatureApi {
        return SearchFeatureApiImpl()
    }
}