package au.com.michaeltigas.duckduckdefinekotlin.injection.component

import au.com.michaeltigas.duckduckdefinekotlin.feature.definition.DefinitionActivity
import au.com.michaeltigas.duckduckdefinekotlin.feature.search.SearchActivity
import au.com.michaeltigas.duckduckdefinekotlin.injection.module.ActivityModule
import dagger.Component
import javax.inject.Scope


/**
 * Created by Michael Tigas on 7/7/17.
 */

/**
 * Provide scoping of the ActivityComponent interface for ActivityModule module dependencies
 */
@Scope @Retention(AnnotationRetention.RUNTIME) annotation class PerActivity

/**
 * Provide dependencies to Activity/Fragment classes listed with an interface method below
 */
@PerActivity
@Component(
        dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(ActivityModule::class)
)
interface ActivityComponent {
    // Activity classes
    fun inject(searchActivity: SearchActivity)
    fun inject(definitionActivity: DefinitionActivity)
}
