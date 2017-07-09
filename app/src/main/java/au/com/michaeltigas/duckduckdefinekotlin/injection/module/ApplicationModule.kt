package au.com.michaeltigas.duckduckdefinekotlin.injection.module

import android.app.Application
import android.content.Context
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import timber.log.Timber
import javax.inject.Qualifier
import javax.inject.Singleton


/**
 * Created by Michael Tigas on 7/7/17.
 */

/**
 * An annotation used for annotating a module dependency to guarantee returning a type of class object,
 * but specific to the context of the application class.
 */
@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class ApplicationContext

/**
 * Provide Application level dependencies in this module, such as singleton objects for injection
 * in any Activity, Fragment, Service etc..
 */
@Module
class ApplicationModule(val application: Application) {
    /**
     * Provide instance of Application class
     */
    @Provides
    fun provideApplication() = application

    /**
     * Provide instance of application-level Context
     */
    @Provides @ApplicationContext
    fun provideContext(): Context = application

    /**
     * Provide instance of Picasso
     *
     * Picasso is set to use the OkHttp3 library as its downloader
     */
    @Provides @Singleton
    fun providePicasso(application: Application, client: OkHttpClient): Picasso =
            Picasso.Builder(application)
                    .downloader(OkHttp3Downloader(client))
                    .listener { _, uri, exception -> Timber.e(exception, "Failed to load image: ${uri}") }
                    .build()
}
