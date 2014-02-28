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
 * Represents a state of germany.
 *
 * @author: Michael Munzert
 * @version: 1.0, 28.02.14
 */
public class StateItem {
   /**
    * IDs for the states, id must be one of this.
    */
   public static final String[] ITEM_IDS = {"BW", "BY", "BE", "BB", "BR",
         "HH", "HE", "NI", "MV", "NW", "RP", "SL", "SX", "SA", "SH", "TH"};
   private final String id;
   private final String name;

   /**
    * Creates a new StateItem with ID and Name.
    *
    * @param id   ID of the state.
    * @param name Name of the state.
    */
   public StateItem(String id, String name) {
      this.id = id;
      this.name = name;
   }

   /**
    * Returns the id of the state.
    *
    * @return id of the state.
    */
   public String getId() {
      return id;
   }

   /**
    * Returns the name of the state.
    *
    * @return name of the state.
    */
   public String getName() {
      return name;
   }

   /**
    * Returns the name as string.
    *
    * @return name.
    */
   @Override
   public String toString() {
      return name;
   }
}
