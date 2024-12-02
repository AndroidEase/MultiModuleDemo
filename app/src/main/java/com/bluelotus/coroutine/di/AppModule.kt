package com.bluelotus.coroutine.di

import android.content.Context
import com.bluelotus.coroutine.local.AppDatabase
import com.bluelotus.coroutine.navigation.NavigationSubGraph
import com.bluelotus.data.local.RecipeDao
import com.bluelotus.search1.ui.navigation.SearchFeatureApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideNavigationSubGraph(searchFeatureApi: SearchFeatureApi) :NavigationSubGraph {
        return NavigationSubGraph(searchFeatureApi)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context : Context) = AppDatabase.getInstance(context)


    @Provides
    @Singleton
    fun provideRecipeDao(appDatabase: AppDatabase) : RecipeDao {
        return appDatabase.getRecipeDao()
    }

}