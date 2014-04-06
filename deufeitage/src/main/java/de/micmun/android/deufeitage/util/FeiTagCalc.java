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

import java.util.Calendar;
import java.util.HashMap;

/**
 * Calculates the holydays for a year.
 *
 * @author: Michael Munzert
 * @version: 1.0, 09.03.14
 */
public class FeiTagCalc {
   /**
    *
    */
   public static final String[] HOLYDAYS = {"Neujahr", "Heilige Drei Könige",
         "Tag der Arbeit", "Karfreitag", "Ostersonntag", "Ostermontag",
         "Christi Himmelfahrt", "Pfingstsonntag", "Pfingstmontag",
         "Fronleichnam", "Friedensfest", "Mariä Himmelfahrt",
         "Tag der dt. Einheit", "Allerheiligen", "Buss- und Bettag",
         "Heiliger Abend", "1. Weihnachtsfeiertag",
         "2. Weihnachtsfeiertag", "Silvester"};
   private int year;
   private HashMap<String, Calendar> holydayMap;

   /**
    * Creates a new FeiTagCalc with the current year.
    */
   public FeiTagCalc() {
      year = Calendar.getInstance().get(Calendar.YEAR);
      createHolydayMap();
   }

   /**
    * Creates a new FeiTagCalc with the given year.
    *
    * @param year Year to calculate the holydays.
    */
   public FeiTagCalc(int year) {
      this.year = year;
      createHolydayMap();
   }

   /**
    * Setter for the year.
    *
    * @param year Year to calculate the holydays.
    */
   public void setYear(int year) {
      this.year = year;
      createHolydayMap();
   }

   /**
    * Getter for the current holyday map.
    *
    * @return Current holyday map.
    */
   public HashMap<String, Calendar> getHolydayMap() {
      return holydayMap;
   }

   /**
    * Creates the map with holyday name and calendar.
    */
   private void createHolydayMap() {
      holydayMap = new HashMap<>();
      Calendar easterSunday = getEasterSunday();
      Calendar bubetDay = getBuBetDay(easterSunday);

      Calendar[] cals = new Calendar[HOLYDAYS.length];
      // new year
      cals[0] = Calendar.getInstance();
      cals[0].set(year, Calendar.JANUARY, 1);
      // three kings
      cals[1] = Calendar.getInstance();
      cals[1].set(year, Calendar.JANUARY, 6);
      // day of work
      cals[2] = Calendar.getInstance();
      cals[2].set(year, Calendar.MAY, 1);
      // karfriday
      cals[3] = Calendar.getInstance();
      cals[3].set(year, easterSunday.get(Calendar.MONTH),
            easterSunday.get(Calendar.DAY_OF_MONTH));
      cals[3].add(Calendar.DAY_OF_MONTH, -2);
      // easter sunday
      cals[4] = easterSunday;
      // easter monday
      cals[5] = Calendar.getInstance();
      cals[5].set(year, easterSunday.get(Calendar.MONTH),
            easterSunday.get(Calendar.DAY_OF_MONTH));
      cals[5].add(Calendar.DAY_OF_MONTH, 1);
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
      // party of piece
      cals[10] = Calendar.getInstance();
      cals[10].set(year, Calendar.AUGUST, 8);
      // Mariä to heaven
      cals[11] = Calendar.getInstance();
      cals[11].set(year, Calendar.AUGUST, 15);
      // day of the german unit (national holyday)
      cals[12] = Calendar.getInstance();
      cals[12].set(year, Calendar.OCTOBER, 3);
      // all holy
      cals[13] = Calendar.getInstance();
      cals[13].set(year, Calendar.NOVEMBER, 1);
      // Buss- und Bettag
      cals[14] = bubetDay;
      // holy evening
      cals[15] = Calendar.getInstance();
      cals[15].set(year, Calendar.DECEMBER, 24);
      // 1. Weihnachtsfeiertag
      cals[16] = Calendar.getInstance();
      cals[16].set(year, Calendar.DECEMBER, 25);
      // 2. Weihnachtsfeiertag
      cals[17] = Calendar.getInstance();
      cals[17].set(year, Calendar.DECEMBER, 26);
      // Silvester
      cals[18] = Calendar.getInstance();
      cals[18].set(year, Calendar.DECEMBER, 31);

      for (int i = 0; i < HOLYDAYS.length; ++i) {
         cals[i].set(Calendar.HOUR_OF_DAY, 0);
         cals[i].set(Calendar.MINUTE, 0);
         cals[i].set(Calendar.SECOND, 0);
         holydayMap.put(HOLYDAYS[i], cals[i]);
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
