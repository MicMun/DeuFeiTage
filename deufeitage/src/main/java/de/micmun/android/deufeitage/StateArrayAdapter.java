/*
 * *
 *  * Copyright 2014 MicMun
 *  *
 *  * This program is free software: you can redistribute it and/or modify it under
 *  * the terms of the GNU >General Public License as published by the
 *  * Free Software Foundation, either version 3 of the License, or >
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful, but
 *  * WITHOUT ANY WARRANTY; >without even the implied warranty of MERCHANTABILITY
 *  * or FITNESS FOR A PARTICULAR PURPOSE. >See the GNU General Public License
 *  * for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License along with
 *  * this program. If not, see >http://www.gnu.org/licenses/.
 *
 */

package de.micmun.android.deufeitage;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Array Adapter for a list of ITEMS with the short form as id.
 *
 * @author: Michael Munzert
 * @version: 1.0, 28.02.2014
 */
public class StateArrayAdapter extends ArrayAdapter<StateArrayAdapter.StateItem> {
   public static final String[] STATE_IDS = {"BW", "BY", "BE", "BB", "BR",
         "HH", "HE", "NI", "MV", "NW", "RP", "SL", "SX", "SA", "SH", "TH"};

   /**
    * List of items.
    */
   public static final ArrayList<StateItem> ITEMS = new ArrayList<>();
   /**
    * Map with the id and the state item.
    */
   public static final HashMap<String, StateItem> ITEM_MAP = new HashMap<>();

   /**
    * Creates a new StateArrayAdapter for a list of ITEMS.
    *
    * @see android.widget.ArrayAdapter#ArrayAdapter(android.content.Context, int, int)
    */
   public StateArrayAdapter(Context context, int resource, int textViewResourceId) {
      super(context, resource, textViewResourceId, ITEMS);

      String[] stateStrings = context.getResources().getStringArray(R.array
            .states_of_germany);
      for (int i = 0; i < stateStrings.length; ++i) {
         addItem(new StateItem(STATE_IDS[i], stateStrings[i]));
      }

      notifyDataSetChanged();
   }

   /**
    * Adds an item to the list and the map.
    *
    * @param item state with id and name.
    */
   private void addItem(StateItem item) {
      ITEMS.add(item);
      ITEM_MAP.put(item.id, item);
   }

   /**
    * A dummy item representing a piece of content.
    */
   public static class StateItem {
      public String id;
      public String content;

      public StateItem(String id, String content) {
         this.id = id;
         this.content = content;
      }

      @Override
      public String toString() {
         return content;
      }
   }
}
