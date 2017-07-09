package au.com.michaeltigas.duckduckdefinekotlin.injection.module

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier


/**
 * Created by Michael Tigas on 7/7/17.
 */

/**
 * An annotation used for annotating a module dependency to guarantee returning a type of class object,
 * but specific to the context of an Activity class. There may be many scenarios to inject a
 * common type of class object, but may need differentiation depending context of task
 */
@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class ActivityContext

/**
 * Provide Activity-related class dependencies in this module (including Fragments)
 */
@Module
class ActivityModule(private var activity: Activity) {

    @Provides
    fun provideActivity(): Activity = activity

    @Provides
    @ActivityContext
    fun providesContext(): Context = activity
}
