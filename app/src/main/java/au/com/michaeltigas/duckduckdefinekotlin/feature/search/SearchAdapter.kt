package au.com.michaeltigas.duckduckdefinekotlin.feature.search

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import au.com.michaeltigas.duckduckdefinekotlin.R
import au.com.michaeltigas.duckduckdefinekotlin.util.ViewUtil

import kotlinx.android.synthetic.main.view_searchterm.view.*

/**
 * Created by Michael Tigas on 7/7/17.
 *
 * Generate custom views for the SearchActivity recycler adapter
 *
 * Depending on the type of View, various ViewHolders will be called to create various styled layouts
 */
class SearchAdapter(val itemClick: (String) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // View Types
    object ViewType {
        const val SEARCHTERM = 0
    }

    // Adapter item list
    private val items = ArrayList<String>()

    /**
     * If the ViewType is ViewType.SEARCHTERM, create a Search Term item view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val searchTermView = LayoutInflater.from(parent.context).inflate(R.layout.view_searchterm, null)
        ViewUtil.setListLayoutBoundsMaxWidth(searchTermView)

        return SearchTermViewHolder(searchTermView, itemClick)
    }

    /**
     * If the Item Type is of ViewType.SEARCHTERM, bind the Search Term item view to the SearchTermViewHolder
     *
     * Manage view recycling here
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemType = getItemViewType(position)

        if (itemType == ViewType.SEARCHTERM) {
            (holder as SearchTermViewHolder).setTitle(items.get(position))
        }
    }

    /**
     * Determine the type of view for an item in the adapter's ArrayList
     */
    override fun getItemViewType(position: Int): Int = ViewType.SEARCHTERM

    /**
     * Return adapter list
     */
    override fun getItemCount(): Int = items.size

    /**
     * Add an item to the adapter
     */
    fun insertItem(term: String) {
        if (!itemAlreadyExists(term)) items.add(term.trim())
        notifyDataSetChanged()
    }

    /**
     * Check if string value already exists in adapter
     */
    private fun itemAlreadyExists(value: String): Boolean {
        items.forEach { item -> if (TextUtils.equals(item.trim(), value.trim())) return true }

        return false
    }

    /**
     * ViewHolder inner class for views of type ViewType.SEARCHTERM
     */
    inner class SearchTermViewHolder(itemView: View?,
                                     val itemClick: (String) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun setTitle(term: String) {
            with(itemView) {
                searchTerm.text = term
                searchTerm.setOnClickListener { itemClick(term) }
            }
        }
    }
}
