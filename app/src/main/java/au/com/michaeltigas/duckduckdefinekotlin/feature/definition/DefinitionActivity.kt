package au.com.michaeltigas.duckduckdefinekotlin.feature.definition

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.text.TextUtils
import android.view.MenuItem
import au.com.michaeltigas.duckduckdefinekotlin.R
import au.com.michaeltigas.duckduckdefinekotlin.data.remote.model.SearchDefinition
import au.com.michaeltigas.duckduckdefinekotlin.feature.common.BaseActivity
import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_definition.*
import java.io.IOException
import javax.inject.Inject


/**
 * Created by Michael Tigas on 7/7/17.
 *
 * This activity displays information relating to a returned Definition
 */
class DefinitionActivity : BaseActivity() {
    @Inject lateinit var moshi: Moshi
    @Inject lateinit var picasso: Picasso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_definition)
        getActivityComponent().inject(this)
        init()
    }

    private fun init() {
        initialiseToolbar()
        displayDefinitionDetails()
    }

    private fun initialiseToolbar() {
        setSupportActionBar(definitionToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * Populate definition details
     */
    private fun displayDefinitionDetails() {
        val definitionIntentExtra = intent.getStringExtra("definition")

        if (TextUtils.isEmpty(definitionIntentExtra)) {
            finish()
            return
        }

        // Parse JSON to SearchDefinition POJO
        val searchDefinition: SearchDefinition?
        try {
            searchDefinition = moshi.adapter(SearchDefinition::class.java)?.fromJson(definitionIntentExtra)
        } catch (exception: IOException) {
            exception.printStackTrace()
            finish()
            return
        }

        searchDefinition?.let {
            supportActionBar?.title = it.heading
            definitionDescriptionLabel.text = it.abstractText

            if (!TextUtils.isEmpty(it.imageUrl)) {
                picasso.load(it.imageUrl).into(object : Target {
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    }

                    override fun onBitmapFailed(errorDrawable: Drawable?) {
                    }

                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        val circleCropBitmap = RoundedBitmapDrawableFactory.create(resources, bitmap)
                        circleCropBitmap.isCircular = true
                        definitionImageView.setImageDrawable(circleCropBitmap)
                    }
                })
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                // Respond to the action bar's Up/Home button in the same way as the Back button
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}