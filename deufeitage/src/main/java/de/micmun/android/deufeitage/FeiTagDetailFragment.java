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

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import de.micmun.android.deufeitage.util.FeiTagCalc;
import de.micmun.android.deufeitage.util.HolidayFilter;
import de.micmun.android.deufeitage.util.HolidayItem;
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
    * The fragment argument representing the item YEAR for the holidays.
    */
   public static final String ARG_ITEM_YEAR = "item_year";

   private final String TAG = "FeiTagDetailFragment";

   /**
    * The state content this fragment is presenting.
    */
   private StateItem mItem;

   private int mYear;

   private ActionBar mActionBar;

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

      mActionBar = getActivity().getActionBar();
      mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

      if (getArguments().containsKey(ARG_ITEM_ID)) {
         // Load the state content specified by the fragment
         // arguments.
         mItem = StateArrayAdapter.ITEM_MAP.get(getArguments().getString
               (ARG_ITEM_ID));
         mYear = getArguments().getInt(ARG_ITEM_YEAR);
         if (mYear == 0) {
            mYear = Calendar.getInstance().get(Calendar.YEAR);
         }
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
         // current year and the map of holidays.
         int year = mYear;
         final FeiTagCalc ftc = new FeiTagCalc(getActivity(), year);

         // get the ressource from layout
         final ListView lv = (ListView) rootView.findViewById(R.id
               .holidayListId);

         // year selection
         ArrayList<Integer> lYears = new ArrayList<>();
         for (int i = year - 4; i <= year + 6; ++i) {
            lYears.add(i);
         }
         final ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<>(
               mActionBar.getThemedContext(), android.R.layout.simple_spinner_item, lYears);
         mActionBar.setListNavigationCallbacks(yearAdapter,
               new ActionBar.OnNavigationListener() {
                  @Override
                  public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                     if (yearAdapter.getItem(itemPosition) != null) {
                        int selYear = yearAdapter.getItem(itemPosition);
                        ftc.setYear(selYear);
                        lv.setAdapter(null);
                        lv.setAdapter(getHolidayAdapter(ftc));
                        SharedPreferences sp = getActivity().getSharedPreferences
                              (FeiTagListActivity.PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt(FeiTagListActivity.KEY_YEAR, selYear);
                        editor.commit();
                        return true;
                     }
                     return false;
                  }
               });
         mActionBar.setSelectedNavigationItem(yearAdapter.getPosition(year));

         // Adapter setzen
         lv.setAdapter(getHolidayAdapter(ftc));
      }

      return rootView;
   }

   /**
    * Returns the adapter for the holiday list.
    *
    * @param calc FeiTagCalc with the holiday map.
    * @return HolidayItemAdapter with the holiday list.
    */
   private HolidayItemAdapter getHolidayAdapter(FeiTagCalc calc) {
      // list of holidays
      HashMap<String, Calendar> holidayMap = calc.getHolidayMap();
      ArrayList<HolidayItem> listOfHoliday = new ArrayList<>(holidayMap.size());
      SimpleDateFormat df = new SimpleDateFormat("c, dd.MM.yyyy");

      for (String k : calc.getHolidayS()) {
         HolidayItem hi = new HolidayItem(k,
               df.format(holidayMap.get(k).getTime()));
         listOfHoliday.add(hi);
      }

      try {
         HolidayFilter hf = new HolidayFilter(getActivity());
         hf.getFilteredList(listOfHoliday, mItem.getId());
      } catch (IOException e) {
         Log.e(TAG, "ERROR: " + e.getLocalizedMessage());
      }

      return new HolidayItemAdapter(getActivity(), listOfHoliday);
   }

}
