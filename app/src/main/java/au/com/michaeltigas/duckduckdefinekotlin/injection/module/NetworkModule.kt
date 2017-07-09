package au.com.michaeltigas.duckduckdefinekotlin.injection.module

import au.com.michaeltigas.duckduckdefinekotlin.BuildConfig
import au.com.michaeltigas.duckduckdefinekotlin.data.remote.DuckDuckGoApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Singleton


/**
 * Created by Michael Tigas on 7/7/17.
 *
 * Provide Application level Network-related dependencies in this module
 */
@Module
class NetworkModule {
    /**
     * Provide instance of HttpUrl, with a parsed API Property URL value from the gradle build config
     */
    @Provides @Singleton
    fun provideBaseUrl(): HttpUrl = HttpUrl.parse(BuildConfig.DUCKDUCKGO_API_URL)!!

    /**
     * Provide instance of Moshi
     */
    @Provides @Singleton
    fun provideMoshi() = Moshi.Builder().build()

    /**
     * Provide an instance of OkHttpClient for use with API interfaces/libraries
     */
    @Provides @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        // Enable/Disable HTTP log statements
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = if (BuildConfig.DEBUG) BODY else NONE

        return OkHttpClient().newBuilder()
                .connectTimeout(30, SECONDS)
                .readTimeout(30, SECONDS)
                .writeTimeout(30, SECONDS)
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build()
    }

    /**
     * Provide instance of Retrofit, utilising Moshi as the JSON converter to connect
     * to the DuckDuckGoAPI
     */
    @Provides @Singleton
    fun provideDuckDuckGoRetrofit(baseUrl: HttpUrl, client: OkHttpClient, moshi: Moshi): Retrofit =
            Retrofit.Builder()
                    .client(client)
                    .baseUrl(baseUrl)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

    /**
     * Provide instance of Retrofit, interfaced with DuckDuckGoApiService
     */
    @Provides @Singleton
    fun provideDuckDuckGoApiService(retrofit: Retrofit): DuckDuckGoApiService =
            retrofit.create(DuckDuckGoApiService::class.java)
}