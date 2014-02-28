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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.micmun.android.deufeitage.dummy.DummyContent;
import de.micmun.android.deufeitage.util.StateItem;

/**
 * A fragment representing a single FeiTag detail screen.
 * This fragment is either contained in a {@link FeiTagListActivity}
 * in two-pane mode (on tablets) or a {@link FeiTagDetailActivity}
 * on handsets.
 *
 * @author MicMun
 * @version 1.0, 28.02.2014
 */
public class FeiTagDetailFragment extends Fragment {
   /**
    * The fragment argument representing the item ID that this fragment
    * represents.
    */
   public static final String ARG_ITEM_ID = "item_id";

   /**
    * The state content this fragment is presenting.
    */
   private StateItem mItem;

   /**
    * Mandatory empty constructor for the fragment manager to instantiate the
    * fragment (e.g. upon screen orientation changes).
    */
   public FeiTagDetailFragment() {
   }

   /**
    * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
    */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      if (getArguments().containsKey(ARG_ITEM_ID)) {
         // Load the dummy content specified by the fragment
         // arguments. In a real-world scenario, use a Loader
         // to load content from a content provider.
         mItem = StateArrayAdapter.ITEM_MAP.get(getArguments().getString
               (ARG_ITEM_ID));

      }
   }

   /**
    * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
    */
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_feitag_detail, container, false);

      // Show the state content as text in a TextView.
      if (mItem != null) {
         ((TextView) rootView.findViewById(R.id.feitag_detail)).setText(mItem
               .getName());
      }

      return rootView;
   }
}
