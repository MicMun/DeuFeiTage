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

package de.micmun.android.deufeitage.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Filter for holidays in a state of germany.
 *
 * @author MicMun
 * @version 1.0, 14.04.2014
 */
public class HolidayFilter {
   private final String CONFIG_FILE = "holidayForStates.txt";
   private final Context mCtx;
   private HashMap<String, Integer[]> filterMap;

   /**
    * Creates a new HolidayFilter with Context.
    *
    * @param context Context of app.
    * @throws IOException if config file can't be read.
    */
   public HolidayFilter(Context context) throws IOException {
      mCtx = context;
      filterMap = new HashMap<>();
      readFile();
   }

   /**
    * Returns the filtered list of holidays.
    *
    * @param listItems list of holidays.
    * @param state     State of germany is the key of filter.
    * @return filtered list of holidays.
    */
   public ArrayList<HolidayItem> getFilteredList(ArrayList<HolidayItem>
                                                       listItems,
                                                 String state) {
      Integer[] filter = filterMap.get(state);
      if (filter == null)
         return listItems;
      int count = 0;

      for (int i = 0; i < filter.length; ++i) {
         if (filter[i] == 0) {
            listItems.remove(i - count);
            count++;
         }
      }

      return listItems;
   }

   /**
    * Reads the file and builds the filter map.
    *
    * @throws IOException if an error occurs while reading the file.
    */
   private void readFile() throws IOException {
      AssetManager assetManager = mCtx.getAssets();
      InputStream is = assetManager.open(CONFIG_FILE);
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      String line;

      while ((line = br.readLine()) != null) {
         String[] split = line.split(";");
         String key = split[0];
         Integer[] filterInt = new Integer[split.length - 1];

         for (int i = 1; i < split.length; ++i) {
            Integer n = Integer.valueOf(split[i]);
            filterInt[i - 1] = n;
         }

         filterMap.put(key, filterInt);
      }

      br.close();
   }
}
