package de.micmun.android.deufeitage;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

/**
 * BESCHREIBUNG.
 *
 * @author MicMun
 * @version 1.0, 02.11.14.
 */
public abstract class BaseActivity extends ActionBarActivity {
   private Toolbar toolbar;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(getLayoutResource());
      toolbar = (Toolbar) findViewById(R.id.toolbar);
      if (toolbar != null) {
         setSupportActionBar(toolbar);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         setToolbarTitle(getResources().getString(R.string.app_name), null);
      }
   }

   /**
    * Sets the new title of the toolbar.
    *
    * @param title new title of the toolbar.
    */
   protected void setToolbarTitle(String title, String subtitle) {
      toolbar.setTitle(title);
      if (subtitle != null)
         toolbar.setSubtitle(subtitle);
   }

   protected abstract int getLayoutResource();

   protected void setActionBarIcon(int iconRes) {
      toolbar.setNavigationIcon(iconRes);
   }
}
