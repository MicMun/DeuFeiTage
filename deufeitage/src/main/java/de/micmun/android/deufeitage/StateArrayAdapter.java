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
import java.util.List;

import de.micmun.android.deufeitage.util.StateItem;

/**
 * Array Adapter for a list of ITEMS with the short form as id.
 *
 * @author: Michael Munzert
 * @version: 1.0, 28.02.2014
 */
public class StateArrayAdapter extends ArrayAdapter<StateItem> {
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
   public StateArrayAdapter(Context context, List<StateItem> objects) {
      super(context, android.R.layout.simple_list_item_activated_1, objects);

      // save the objects to get the id and the name
      for (StateItem si : objects) {
         ITEMS.add(si);
         ITEM_MAP.put(si.getId(), si);
      }
   }
}
