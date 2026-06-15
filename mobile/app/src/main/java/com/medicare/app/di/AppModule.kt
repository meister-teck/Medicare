package com.medicare.app.di

import android.content.Context
import com.medicare.app.util.AuthEventBus
import com.medicare.app.util.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides @Singleton
    fun providePrefs(@ApplicationContext ctx: Context) = PreferencesManager(ctx)

    @Provides @Singleton
    fun provideAuthBus() = AuthEventBus()
}
