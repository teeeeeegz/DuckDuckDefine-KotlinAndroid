package au.com.michaeltigas.duckduckdefinekotlin.feature.search

import au.com.michaeltigas.duckduckdefinekotlin.data.DataManager
import au.com.michaeltigas.duckduckdefinekotlin.data.remote.model.SearchDefinition
import au.com.michaeltigas.duckduckdefinekotlin.feature.common.BasePresenter
import au.com.michaeltigas.duckduckdefinekotlin.feature.common.MvpView
import au.com.michaeltigas.duckduckdefinekotlin.util.isHttpException
import au.com.michaeltigas.duckduckdefinekotlin.util.isIoException
import au.com.michaeltigas.duckduckdefinekotlin.util.isSSLHandshakeException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by Michael Tigas on 7/7/17.
 */

/**
 * Interface to connect the following classes: SearchActivity and SearchPresenter
 */
interface SearchMvpView : MvpView {
    fun showSearchInProgress()
    fun showSearchDefinition(term: String, searchDefinition: SearchDefinition)
    fun displayLastSearchTerm(lastSearchTerm: String)
    fun handleErrorResult(errorMessage: String)
}

/**
 * Presenter that handles business logic for SearchActivity
 */
class SearchPresenter @Inject constructor(private val dataManager: DataManager) : BasePresenter<SearchMvpView>() {
    private val disposables: CompositeDisposable

    init {
        disposables = CompositeDisposable()
    }

    override fun detachView() {
        super.detachView()
        if (!disposables.isDisposed) disposables.clear()
    }

    /**
     * Perform search based on param
     */
    fun performSearch(term: String) {
        disposables.add(
                dataManager.getSearchResults(term)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe {
                            getMvpView()?.showSearchInProgress()
                        }
                        .subscribe(
                                { getMvpView()?.showSearchDefinition(term, it) },
                                { handleRequestError(it, "performSearch()") }
                        )
        )
    }

    /**
     * Load last search term and display on UI
     */
    fun loadLastSearchTerm() {
        getMvpView()?.displayLastSearchTerm(dataManager.getLastSearchTerm())
    }

    /**
     * Save last search term
     */
    fun saveLastSearchTerm(term: String) = dataManager.setLastSearchTerm(term)

    /**
     * Handle request errors gracefully
     */
    private fun handleRequestError(throwable: Throwable, methodName: String) {
        Timber.e("Request Error: Class - '${javaClass.name}', Method - '${methodName}', Reason - ${throwable.message}")

        if (throwable.isIoException() && throwable.isSSLHandshakeException()) {
            val errorMessage = "SSL Authentication error"
            getMvpView()?.handleErrorResult(errorMessage)
            return
        }

        if (throwable.isIoException()) {
            val errorMessage = "Not connected to the internet"
            getMvpView()?.handleErrorResult(errorMessage)
            return
        }

        if (throwable.isHttpException()) {
            val httpException = throwable as HttpException
            val errorMessage = "HTTP ${httpException.code()} error"
            if (isViewAttached()) getMvpView()?.handleErrorResult(errorMessage)
        }
    }
}
