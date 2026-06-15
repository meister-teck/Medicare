package com.medicare.app.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.medicare.app.BuildConfig
import com.medicare.app.data.api.AuthInterceptor
import com.medicare.app.data.api.MediCareApi
import com.medicare.app.data.api.UnauthorizedInterceptor
import com.medicare.app.data.local.DoseDao
import com.medicare.app.data.local.MediCareDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }

    @Provides @Singleton
    fun provideOkHttp(
        auth: AuthInterceptor,
        unauthorized: UnauthorizedInterceptor
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(auth)
            .addInterceptor(unauthorized)
            .addInterceptor(logging)
            .build()
    }

    @Provides @Singleton
    fun provideRetrofit(client: OkHttpClient, json: Json): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides @Singleton
    fun provideApi(retrofit: Retrofit): MediCareApi = retrofit.create(MediCareApi::class.java)

    @Provides @Singleton
    fun provideDb(@ApplicationContext ctx: Context): MediCareDatabase =
        Room.databaseBuilder(ctx, MediCareDatabase::class.java, "medicare.db")
            .fallbackToDestructiveMigration().build()

    @Provides fun provideDoseDao(db: MediCareDatabase): DoseDao = db.doseDao()
}
