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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import de.micmun.android.deufeitage.util.FeiTagCalc;
import de.micmun.android.deufeitage.util.HolydayFilter;
import de.micmun.android.deufeitage.util.HolydayItem;
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

   private final String TAG = "FeiTagDetailFragment";

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
         // Load the state content specified by the fragment
         // arguments.
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
      if (rootView != null && mItem != null) {
         // current year and the map of holydays.
         int year = Calendar.getInstance().get(Calendar.YEAR);
         final FeiTagCalc ftc = new FeiTagCalc(getActivity(), year);

         // get the ressource from layout
         final ListView lv = (ListView) rootView.findViewById(R.id
               .holydayListId);
         Spinner spinner = (Spinner) rootView.findViewById(R.id.spYear);

         // year selection
         ArrayList<Integer> lYears = new ArrayList<>();
         for (int i = year - 4; i <= year + 6; ++i) {
            lYears.add(i);
         }
         ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<>(getActivity()
               , android.R.layout.simple_spinner_item, lYears);
         spinner.setAdapter(yearAdapter);
         spinner.setSelection(yearAdapter.getPosition(year));
         spinner.setOnItemSelectedListener(new AdapterView.
               OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
               if (parent.getItemAtPosition(position) != null) {
                  int selYear = (Integer) parent.getItemAtPosition(position);
                  ftc.setYear(selYear);
                  lv.setAdapter(null);
                  lv.setAdapter(getHolydayAdapter(ftc));
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               // Do nothing
            }
         });

         // Adapter setzen
         lv.setAdapter(getHolydayAdapter(ftc));
      }

      return rootView;
   }

   /**
    * Returns the adapter for the holyday list.
    *
    * @param calc FeiTagCalc with the holyday map.
    * @return HolydayItemAdapter with the holyday list.
    */
   private HolydayItemAdapter getHolydayAdapter(FeiTagCalc calc) {
      // list of holydays
      HashMap<String, Calendar> holydayMap = calc.getHolydayMap();
      ArrayList<HolydayItem> listOfHolyday = new ArrayList<>(holydayMap.size());
      SimpleDateFormat df = new SimpleDateFormat("c, dd.MM.yyyy");

      for (String k : calc.getHOLYDAYS()) {
         HolydayItem hi = new HolydayItem(k,
               df.format(holydayMap.get(k).getTime()));
         listOfHolyday.add(hi);
      }

      try {
         HolydayFilter hf = new HolydayFilter(getActivity());
         hf.getFilteredList(listOfHolyday, mItem.getId());
      } catch (IOException e) {
         Log.e(TAG, "ERROR: " + e.getLocalizedMessage());
      }

      return new HolydayItemAdapter(getActivity(), listOfHolyday);
   }
}
