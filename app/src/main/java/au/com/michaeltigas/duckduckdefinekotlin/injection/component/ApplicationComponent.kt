package au.com.michaeltigas.duckduckdefinekotlin.injection.component

import android.app.Application
import android.content.Context
import au.com.michaeltigas.duckduckdefinekotlin.app.DuckDuckDefineApplication
import au.com.michaeltigas.duckduckdefinekotlin.data.DataManager
import au.com.michaeltigas.duckduckdefinekotlin.data.local.PreferencesHelper
import au.com.michaeltigas.duckduckdefinekotlin.data.remote.DuckDuckGoApiService
import au.com.michaeltigas.duckduckdefinekotlin.injection.module.ApplicationContext
import au.com.michaeltigas.duckduckdefinekotlin.injection.module.ApplicationModule
import au.com.michaeltigas.duckduckdefinekotlin.injection.module.NetworkModule
import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso
import dagger.Component
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


/**
 * Created by Michael Tigas on 7/7/17.
 *
 * Provide references to dependencies that have been defined in the component modules, to enable
 * access via @Inject field annotation injection, or class constructor injection
 */
@Singleton
@Component(
        modules = arrayOf(ApplicationModule::class, NetworkModule::class)
)
interface ApplicationComponent {
    fun inject(duckDefineApplication: DuckDuckDefineApplication)

    @ApplicationContext fun context(): Context
    fun application(): Application

    // Architecture-based helpers
    fun dataManager(): DataManager
    fun preferencessHelper(): PreferencesHelper

    // Network related
    fun moshi(): Moshi

    fun httpUrl(): HttpUrl
    fun retrofit(): Retrofit
    fun okHttpClient(): OkHttpClient
    fun duckDuckGoApiService(): DuckDuckGoApiService

    // Custom services
    fun picasso(): Picasso
}
