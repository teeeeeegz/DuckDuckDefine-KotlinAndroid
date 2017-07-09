package au.com.michaeltigas.duckduckdefinekotlin.feature.search

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.text.TextUtils
import au.com.michaeltigas.duckduckdefinekotlin.R
import au.com.michaeltigas.duckduckdefinekotlin.data.remote.model.SearchDefinition
import au.com.michaeltigas.duckduckdefinekotlin.feature.common.BaseActivity
import au.com.michaeltigas.duckduckdefinekotlin.feature.definition.DefinitionActivity
import au.com.michaeltigas.duckduckdefinekotlin.util.ViewUtil
import au.com.michaeltigas.duckduckdefinekotlin.util.setIsRefreshing
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Created by Michael Tigas on 7/7/17.
 *
 * This activity enables a user to search a term, locally store it in the table, and display a definition of it
 */
class SearchActivity : BaseActivity(), SearchMvpView {
    @Inject lateinit var searchPresenter: SearchPresenter
    @Inject lateinit var moshi: Moshi

    private var recyclerAdapter: SearchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivityComponent().inject(this)
        setContentView(R.layout.activity_main)
        searchPresenter.attachView(this)
        init()
    }

    override fun onDestroy() {
        searchPresenter.detachView()
        super.onDestroy()
    }

    private fun init() {
        initialiseToolbar()

        recyclerAdapter = SearchAdapter { performSearchForTerm(it) }

        searchRecyclerView.apply {
            setHasFixedSize(true)
            adapter = recyclerAdapter

            if (layoutManager == null) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }

        searchSwipeRefresh.setColorSchemeColors(* ViewUtil.colorPalleteSwipeRefresh)

        searchSearchView.apply {
            setIconified(false)
            setIconifiedByDefault(false)

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    performSearchForTerm(query) // Search query when enter pressed
                    searchSearchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

        searchPresenter.loadLastSearchTerm()
    }

    private fun initialiseToolbar() {
        setSupportActionBar(searchToolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setTitle(getResources().getString(R.string.activity_search))
        }
    }

    /**
     * Perform search for a term
     */
    fun performSearchForTerm(term: String?) {
        val termValue = term.let { it } ?: return

        if (!TextUtils.isEmpty(termValue)) searchPresenter.performSearch(termValue)
    }

    /**
     * Display a snackbar message
     */
    private fun showSnackbarMessage(message: String) {
        searchCoordinatorLayout?.let { coordinatorLayout ->
            Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show()
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////  ========== MVP INTERFACE METHODS ========== ////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Display swipe refresh layout when search in progress
     */
    override fun showSearchInProgress() {
        searchSwipeRefresh.setIsRefreshing(true)
    }

    /**
     * Save search term if matching definition returned, and pass definition details to
     * DefinitionActivity via intent
     */
    override fun showSearchDefinition(term: String, searchDefinition: SearchDefinition) {
        searchSwipeRefresh.setIsRefreshing(false)

        if (TextUtils.isEmpty(searchDefinition.heading) || TextUtils.isEmpty(searchDefinition.abstractText)) {
            val noDefinitionMessage = "No definition could be found for ${term}. Try searching for something else?"
            showSnackbarMessage(noDefinitionMessage)
            return
        }

        recyclerAdapter?.insertItem(term)

        val definitionIntent: Intent = Intent(this, DefinitionActivity::class.java)
        definitionIntent.putExtra("definition", moshi.adapter(SearchDefinition::class.java)?.toJson(searchDefinition))
        startActivity(definitionIntent)
    }

    /**
     * Display last search term in search bar
     */
    override fun displayLastSearchTerm(lastSearchTerm: String) {
        searchSearchView.setQuery(lastSearchTerm, false)
    }

    /**
     * Handle error results
     */
    override fun handleErrorResult(errorMessage: String) {
        searchSwipeRefresh.setIsRefreshing(false)
        showSnackbarMessage(errorMessage)
    }
}
