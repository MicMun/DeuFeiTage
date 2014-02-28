/**
 * Copyright 2014 MicMun
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU >General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or >
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; >without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. >See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see >http://www.gnu.org/licenses/.
 */
package de.micmun.android.deufeitage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

import de.micmun.android.deufeitage.util.StateItem;

/**
 * An activity representing a single FeiTag detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link FeiTagListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link FeiTagDetailFragment}.
 *
 * @author MicMun
 * @version 1.0, 28.02.2014
 */
public class FeiTagDetailActivity extends FragmentActivity {
   /**
    * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
    */
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_feitag_detail);

      // Show the Up button in the action bar.
      getActionBar().setDisplayHomeAsUpEnabled(true);

      // savedInstanceState is non-null when there is fragment state
      // saved from previous configurations of this activity
      // (e.g. when rotating the screen from portrait to landscape).
      // In this case, the fragment will automatically be re-added
      // to its container so we don't need to manually add it.
      // For more information, see the Fragments API guide at:
      //
      // http://developer.android.com/guide/components/fragments.html
      //
      if (savedInstanceState == null) {
         // Create the detail fragment and add it to the activity
         // using a fragment transaction.
         String title = getTitle().toString();
         String STATE_ID = getIntent().getStringExtra(FeiTagDetailFragment
               .ARG_ITEM_ID);
         StateItem st = StateArrayAdapter.ITEM_MAP.get(STATE_ID);
         title += " " + st;
         setTitle(title);

         Bundle arguments = new Bundle();
         arguments.putString(FeiTagDetailFragment.ARG_ITEM_ID, STATE_ID);
         FeiTagDetailFragment fragment = new FeiTagDetailFragment();
         fragment.setArguments(arguments);
         getSupportFragmentManager().beginTransaction()
               .add(R.id.feitag_detail_container, fragment)
               .commit();
      }
   }

   /**
    * @see android.support.v4.app.FragmentActivity#onOptionsItemSelected(android.view.MenuItem)
    */
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();
      if (id == android.R.id.home) {
         // This ID represents the Home or Up button. In the case of this
         // activity, the Up button is shown. Use NavUtils to allow users
         // to navigate up one level in the application structure. For
         // more details, see the Navigation pattern on Android Design:
         //
         // http://developer.android.com/design/patterns/navigation.html#up-vs-back
         //
         NavUtils.navigateUpTo(this, new Intent(this, FeiTagListActivity.class));
         return true;
      }
      return super.onOptionsItemSelected(item);
   }
}
