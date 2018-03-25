package de.micmun.android.deufeitage

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.view.*

/**
 * Base activity for all activities.
 *
 * @author MicMun
 * @version 1.0, 04.02.18
 */
abstract class BaseActivity : AppCompatActivity() {
    var toolbarId: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(getLayoutResource())

        toolbarId = toolbar.toolbar

        if (toolbarId != null) {
            setSupportActionBar(toolbarId)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setToolbarTitle(resources.getString(R.string.app_name), null)
        }
    }

    /**
     * Sets the toolbar title.
     *
     * @param title title of the activity.
     * @param subtitle sub title of the activity.
     */
    private fun setToolbarTitle(title: String, subtitle: String?) {
        toolbarId?.title = title
        if (subtitle != null)
            toolbarId?.subtitle = subtitle
    }

    /**
     * Get the layout resource from child.
     *
     * @return layout id of the resource.
     */
    protected abstract fun getLayoutResource(): Int

    /**
     * Sets the action bar icon to null.
     *
     * @param iconRes resource id of the icon.
     */
    protected fun setActionBarIcon(iconRes: Int) {
        toolbarId?.navigationIcon = null
    }
}