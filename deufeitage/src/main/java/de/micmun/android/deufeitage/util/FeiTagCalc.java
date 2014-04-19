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

import java.util.Calendar;
import java.util.HashMap;

import de.micmun.android.deufeitage.R;

/**
 * Calculates the holidays for a year.
 *
 * @author: Michael Munzert
 * @version: 1.0, 09.03.14
 */
public class FeiTagCalc {
   private String[] holidays;
   private int year;
   private HashMap<String, Calendar> holidayMap;

   /**
    * Creates a new FeiTagCalc with the given year.
    *
    * @param year Year to calculate the holidays.
    */
   public FeiTagCalc(Context context, int year) {
      this.year = year;
      holidays = context.getResources().getStringArray(R.array
            .names_of_holidays);
      createHolidayMap();
   }

   /**
    * Returns the names of the holidays.
    *
    * @return names of the holidays.
    */
   public String[] getHolidays() {
      return holidays;
   }

   /**
    * Setter for the year.
    *
    * @param year Year to calculate the holidays.
    */
   public void setYear(int year) {
      this.year = year;
      createHolidayMap();
   }

   /**
    * Getter for the current holiday map.
    *
    * @return Current holiday map.
    */
   public HashMap<String, Calendar> getHolidayMap() {
      return holidayMap;
   }

   /**
    * Creates the map with holiday name and calendar.
    */
   private void createHolidayMap() {
      holidayMap = new HashMap<>();
      Calendar easterSunday = getEasterSunday();
      Calendar bubetDay = getBuBetDay(easterSunday);

      Calendar[] cals = new Calendar[holidays.length];
      // new year
      cals[0] = Calendar.getInstance();
      cals[0].set(year, Calendar.JANUARY, 1);
      // three kings
      cals[1] = Calendar.getInstance();
      cals[1].set(year, Calendar.JANUARY, 6);
      // karfriday
      cals[2] = Calendar.getInstance();
      cals[2].set(year, easterSunday.get(Calendar.MONTH),
            easterSunday.get(Calendar.DAY_OF_MONTH));
      cals[2].add(Calendar.DAY_OF_MONTH, -2);
      // easter sunday
      cals[3] = easterSunday;
      // easter monday
      cals[4] = Calendar.getInstance();
      cals[4].set(year, easterSunday.get(Calendar.MONTH),
            easterSunday.get(Calendar.DAY_OF_MONTH));
      cals[4].add(Calendar.DAY_OF_MONTH, 1);
      // day of work
      cals[5] = Calendar.getInstance();
      cals[5].set(year, Calendar.MAY, 1);
      // christ to heaven
      cals[6] = Calendar.getInstance();
      cals[6].set(year, easterSunday.get(Calendar.MONTH),
            easterSunday.get(Calendar.DAY_OF_MONTH));
      cals[6].add(Calendar.DAY_OF_MONTH, 39);
      // whit sunday
      cals[7] = Calendar.getInstance();
      cals[7].set(year, easterSunday.get(Calendar.MONTH),
            easterSunday.get(Calendar.DAY_OF_MONTH));
      cals[7].add(Calendar.DAY_OF_MONTH, 49);
      // whit monday
      cals[8] = Calendar.getInstance();
      cals[8].set(year, easterSunday.get(Calendar.MONTH),
            easterSunday.get(Calendar.DAY_OF_MONTH));
      cals[8].add(Calendar.DAY_OF_MONTH, 50);
      // Fronleichnam
      cals[9] = Calendar.getInstance();
      cals[9].set(year, easterSunday.get(Calendar.MONTH),
            easterSunday.get(Calendar.DAY_OF_MONTH));
      cals[9].add(Calendar.DAY_OF_MONTH, 60);
      // Mariä to heaven
      cals[10] = Calendar.getInstance();
      cals[10].set(year, Calendar.AUGUST, 15);
      // day of the german unit (national holiday)
      cals[11] = Calendar.getInstance();
      cals[11].set(year, Calendar.OCTOBER, 3);
      // day of reformation
      cals[12] = Calendar.getInstance();
      cals[12].set(year, Calendar.OCTOBER, 31);
      // all holy
      cals[13] = Calendar.getInstance();
      cals[13].set(year, Calendar.NOVEMBER, 1);
      // Buss- und Bettag
      cals[14] = bubetDay;
      // 1. Weihnachtsfeiertag
      cals[15] = Calendar.getInstance();
      cals[15].set(year, Calendar.DECEMBER, 25);
      // 2. Weihnachtsfeiertag
      cals[16] = Calendar.getInstance();
      cals[16].set(year, Calendar.DECEMBER, 26);

      for (int i = 0; i < holidays.length; ++i) {
         cals[i].set(Calendar.HOUR_OF_DAY, 0);
         cals[i].set(Calendar.MINUTE, 0);
         cals[i].set(Calendar.SECOND, 0);
         holidayMap.put(holidays[i], cals[i]);
      }
   }

   /**
    * Returns the date of easter sunday of the year.
    *
    * @return Calendar with date of easter sunday.
    */
   private Calendar getEasterSunday() {
      Calendar es = Calendar.getInstance();

      // Calculate the date of easter sunday.
      int K = year / 100;
      int M = 15 + ((3 * K + 3) / 4) - ((8 * K + 13) / 25);
      int S = 2 - ((3 * K + 3) / 4);
      int A = year % 19;
      int D = (19 * A + M) % 30;
      int R = D / 29 + (D / 28 - D / 29) * A / 11;
      int OG = 21 + D - R;
      int SZ = 7 - ((year + year / 4 + S) % 7);
      int OE = 7 - ((OG - SZ) % 7);
      int OS = OG + OE - 1; // add OS to 01.03. for easter sunday

      es.set(year, Calendar.MARCH, 1);
      es.add(Calendar.DAY_OF_MONTH, OS);

      return es;
   }

   /**
    * Returns the date of Bu&szlig;- und Bettag.
    *
    * @param easterSunday Calendar for easter sunday.
    * @return Calendar with date of Bu&szlig;- und Bettag.
    */
   private Calendar getBuBetDay(Calendar easterSunday) {
      int dayOfOS = easterSunday.get(Calendar.DAY_OF_MONTH);
      int number = easterSunday.get(Calendar.MONTH) == Calendar.MARCH ? 33 :
            30;

      int dayNr = (-1) * ((number - dayOfOS) % 7);
      Calendar cal = Calendar.getInstance();
      cal.set(year, Calendar.NOVEMBER, 22);
      cal.add(Calendar.DAY_OF_MONTH, dayNr);

      return cal;
   }
}
