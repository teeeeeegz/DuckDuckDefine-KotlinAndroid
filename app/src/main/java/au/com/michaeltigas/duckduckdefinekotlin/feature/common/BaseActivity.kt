package au.com.michaeltigas.duckduckdefinekotlin.feature.common

import android.support.v7.app.AppCompatActivity
import au.com.michaeltigas.duckduckdefinekotlin.app.DuckDuckDefineApplication
import au.com.michaeltigas.duckduckdefinekotlin.injection.component.ActivityComponent
import au.com.michaeltigas.duckduckdefinekotlin.injection.component.DaggerActivityComponent
import au.com.michaeltigas.duckduckdefinekotlin.injection.module.ActivityModule


/**
 * Created by Michael Tigas on 7/7/17.
 *
 * Common base class for all Activity classes to extend from, providing common functionality
 */
open class BaseActivity : AppCompatActivity() {
    private var activityComponent: ActivityComponent? = null

    /**
     * Build Activity Component graph
     */
    fun getActivityComponent(): ActivityComponent {
        activityComponent?.let { return it }

        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(DuckDuckDefineApplication.applicationComponent)
                //.activityModule(ActivityModule(this))
                .build()

        return activityComponent!!
    }
}
