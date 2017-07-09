package au.com.michaeltigas.duckduckdefinekotlin.feature.common


/**
 * Created by Michael Tigas on 7/7/17.
 */

/**
 * Common base interface, which enables a common interface type to communicate between
 * Views and Presenters
 */
interface MvpView

/**
 * Common base interface, to share common methods amongst presenters of base-class-type MvpView
 */
interface Presenter<in V : MvpView> {
    fun attachView(mvpView: V)
    fun detachView()
}

/**
 * Common base class for Presenters to extend from, of type MvpView, providing common functionality
 * This enables a presenter class to attach an MvpView interface and detatch from it
 */
open class BasePresenter<T : MvpView> : Presenter<T> {
    private var mvpView: T? = null

    override fun attachView(mvpView: T) {
        this.mvpView = mvpView
    }

    override fun detachView() {
        this.mvpView = null
    }

    fun isViewAttached(): Boolean = this.mvpView != null

    fun getMvpView(): T? = this.mvpView

    fun checkViewAttached() {
        if (!isViewAttached()) throw MvpViewNotAttachedException()
    }

    inner class MvpViewNotAttachedException : RuntimeException("Please call Presenter.attachView(MvpView) before requesting data to the Presenter")
}
