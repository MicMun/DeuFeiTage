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
import android.util.Log;


/**
 * An activity representing a list of german ITEMS. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link FeiTagDetailActivity} representing
 * item details (= holydays in this state). On tablets, the activity presents
 * the list of items and
 * item details (= holydays in this state) side-by-side using two vertical
 * panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link FeiTagListFragment} and the item details
 * (if present) is a {@link FeiTagDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link FeiTagListFragment.Callbacks} interface to listen for item selections.
 *
 * @author MicMun
 * @version 1.0, 28.02.2014
 */
public class FeiTagListActivity extends FragmentActivity
      implements FeiTagListFragment.Callbacks {

   /**
    * Whether or not the activity is in two-pane mode, i.e. running on a tablet
    * device.
    */
   private boolean mTwoPane;

   /**
    * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
    */
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_feitag_list);

      if (findViewById(R.id.feitag_detail_container) != null) {
         // The detail container view will be present only in the
         // large-screen layouts (res/values-large and
         // res/values-sw600dp). If this view is present, then the
         // activity should be in two-pane mode.
         mTwoPane = true;

         // In two-pane mode, list items should be given the
         // 'activated' state when touched.
         ((FeiTagListFragment) getSupportFragmentManager()
               .findFragmentById(R.id.feitag_list))
               .setActivateOnItemClick(true);
      }

      // TODO: If exposing deep links into your app, handle intents here.
   }

   /**
    * @see de.micmun.android.deufeitage.FeiTagListFragment.Callbacks#onItemSelected(String)
    */
   @Override
   public void onItemSelected(String id) {
      if (mTwoPane) {
         // In two-pane mode, show the detail view in this activity by
         // adding or replacing the detail fragment using a
         // fragment transaction.
         Log.d("FeiTagListActivity", "ID = " + id);
         Bundle arguments = new Bundle();
         arguments.putString(FeiTagDetailFragment.ARG_ITEM_ID, id);
         FeiTagDetailFragment fragment = new FeiTagDetailFragment();
         fragment.setArguments(arguments);
         getSupportFragmentManager().beginTransaction()
               .replace(R.id.feitag_detail_container, fragment)
               .commit();

      } else {
         // In single-pane mode, simply start the detail activity
         // for the selected item ID.
         Intent detailIntent = new Intent(this, FeiTagDetailActivity.class);
         detailIntent.putExtra(FeiTagDetailFragment.ARG_ITEM_ID, id);
         startActivity(detailIntent);
      }
   }
}
