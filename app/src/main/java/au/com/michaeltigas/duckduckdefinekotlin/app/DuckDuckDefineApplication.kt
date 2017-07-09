package au.com.michaeltigas.duckduckdefinekotlin.app

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import au.com.michaeltigas.duckduckdefinekotlin.injection.component.ApplicationComponent
import au.com.michaeltigas.duckduckdefinekotlin.injection.component.DaggerApplicationComponent
import au.com.michaeltigas.duckduckdefinekotlin.injection.module.ApplicationModule
import timber.log.BuildConfig
import timber.log.Timber


/**
 * Created by Michael Tigas on 7/7/17.
 *
 * Global Application class
 */
class DuckDuckDefineApplication : Application() {
    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        initialiseDependencyInjection()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        if (BuildConfig.DEBUG) {
            initialiseTimber()
        }
    }

    /**
     * Build Application Component and inject this class
     */
    private fun initialiseDependencyInjection() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()

        applicationComponent.inject(this)

    }

    /**
     * Attach a Timber debug tree, to enable manipulation of log output text
     */
    private fun initialiseTimber() {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement?): String {
                return String.format("%s:%s",
                        super.createStackElementTag(element), element?.lineNumber)
            }
        })
    }

    /**
     * Set the ApplicationComponent object to the parameter ApplicationComponent object.
     * This method is only used when passing a Mock instance of ApplicationComponent for test purposes
     */
    fun setApplicationComponent(applicationComponent: ApplicationComponent) {
        DuckDuckDefineApplication.applicationComponent = applicationComponent
    }
}
