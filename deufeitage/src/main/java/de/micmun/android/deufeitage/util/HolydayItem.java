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

/**
 * One Holyday with name and date.
 *
 * @author: Michael Munzert
 * @version: 1.0, 06.04.14
 */
public class HolydayItem {
   private String name;
   private String date;

   /**
    * Creates a new HolydayItem with name and date.
    *
    * @param name name of the holyday.
    * @param date date of the holyday.
    */
   public HolydayItem(String name, String date) {
      this.name = name;
      this.date = date;
   }

   /**
    * Returns the name of the holyday.
    *
    * @return name of the holyday.
    */
   public String getName() {
      return name;
   }

   /**
    * Returns the date of the holyday.
    *
    * @return date of the holyday.
    */
   public String getDate() {
      return date;
   }
}
