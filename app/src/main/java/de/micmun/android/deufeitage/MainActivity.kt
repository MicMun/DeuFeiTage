package de.micmun.android.deufeitage

import android.os.Bundle

/**
 * Main activity with start view of the app.
 *
 * @author MicMun
 * @version 1.0, 04.02.18
 */
class MainActivity : BaseActivity() {
    override fun getLayoutResource(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
