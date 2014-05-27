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
import java.util.List;

import de.micmun.android.deufeitage.util.FeiTagCalc;
import de.micmun.android.deufeitage.util.HolidayFilter;
import de.micmun.android.deufeitage.util.HolidayItem;
import de.micmun.android.deufeitage.util.StateItem;
import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.HListView;

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
   private int mYear = 0;
   private HolidayFilter mFilter = null;
   private ArrayAdapter<Integer> mAdapter;

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
         mYear = getArguments().getInt(ARG_ITEM_YEAR);
      }

      if (mYear == 0) {
         mYear = Calendar.getInstance().get(Calendar.YEAR);
      }
   }

   /**
    * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
    */
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_feitag_detail,
            container, false);

      // Show the state content as text in a TextView.
      if (rootView != null && mItem != null) {
         // calculation the map of holidays.
         final FeiTagCalc ftc = new FeiTagCalc(getActivity(), mYear);
         // get the ressource from layout
         final ListView lv = (ListView) rootView.findViewById(R.id
               .holidayListId);

         // create the list selection in horizontal list view
         List<Integer> yearList = new ArrayList<>(10);
         int currentYear = Calendar.getInstance().get(Calendar.YEAR);
         for (int i = currentYear - 4; i <= currentYear + 5; ++i) {
            yearList.add(i);
         }
         mAdapter = new ArrayAdapter<>(getActivity(),
               R.layout.year_item, R.id.yearSelectTV, yearList);

         HListView mListView = (HListView) rootView.findViewById(R.id.hListView1);
         mListView.setHeaderDividersEnabled(false);
         mListView.setFooterDividersEnabled(false);
         mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

         // analyse the selected year and change the holiday dates
         mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int i, long l) {
               mYear = mAdapter.getItem(i);
               ftc.setYear(mYear);
               lv.setAdapter(null);
               lv.setAdapter(getHolidayAdapter(ftc));
               SharedPreferences sp = getActivity().getSharedPreferences
                     (FeiTagListActivity.PREF_NAME, Context.MODE_PRIVATE);
               SharedPreferences.Editor editor = sp.edit();
               editor.putInt(FeiTagListActivity.KEY_YEAR, mYear);
               editor.commit();
            }
         });
         mListView.setAdapter(mAdapter);
         int pos = mAdapter.getPosition(mYear);
         if (pos < 0) {
            mYear = currentYear;
            pos = mAdapter.getPosition(mYear);
         }
         mListView.setItemChecked(pos, true);
         mListView.smoothScrollToPosition(pos);

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

      for (String k : calc.getHolidays()) {
         HolidayItem hi = new HolidayItem(k,
               df.format(holidayMap.get(k).getTime()));
         listOfHoliday.add(hi);
      }

      try {
         if (mFilter == null)
            mFilter = new HolidayFilter(getActivity());
         mFilter.getFilteredList(listOfHoliday, mItem.getId());
      } catch (IOException e) {
         Log.e(TAG, "ERROR: " + e.getLocalizedMessage());
      }

      return new HolidayItemAdapter(getActivity(), listOfHoliday);
   }
}
