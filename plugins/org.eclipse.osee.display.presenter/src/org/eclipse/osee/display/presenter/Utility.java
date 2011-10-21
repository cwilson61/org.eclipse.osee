/*******************************************************************************
 * Copyright (c) 2011 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.osee.display.presenter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.osee.display.api.data.StyledText;
import org.eclipse.osee.framework.jdk.core.type.MatchLocation;
import org.eclipse.osee.framework.jdk.core.util.Strings;

/**
 * @author Roberto E. Escobar
 */
public final class Utility {

   private Utility() {
      //
   }

   public static List<StyledText> getMatchedText(String data, List<MatchLocation> matches) {
      List<StyledText> text = new ArrayList<StyledText>();
      if (matches.isEmpty()) {
         text.add(new StyledText(data, false));
      } else {
         int lastStop = 0;
         for (MatchLocation location : matches) {
            int start = location.getStartPosition();
            int stop = location.getEndPosition();
            String section;
            if (start > lastStop) {
               section = data.substring(lastStop, start - 1);
               text.add(new StyledText(section, false));
            }

            int startAt = start;
            if (startAt - 1 < 0) {
               startAt = 0;
            } else {
               startAt = start - 1;
            }
            section = data.substring(startAt, stop);
            text.add(new StyledText(section, true));
            lastStop = location.getEndPosition();
         }
         if (lastStop < data.length()) {
            String section = data.substring(lastStop, data.length());
            text.add(new StyledText(section, false));
         }
      }
      return text;
   }

   public static Map<String, String> decode(String url) {
      String toParse = url;
      if (toParse.startsWith("/")) {
         toParse = toParse.substring(1, toParse.length());
      }

      Map<String, String> values = new HashMap<String, String>();
      String[] lines = toParse.split("&");
      for (String line : lines) {
         String[] data = line.split("=");
         if (data.length == 2) {
            String key = data[0];
            String value = data[1];
            if (Strings.isValid(value) && Strings.isValid(key)) {
               try {
                  value = URLDecoder.decode(value, "UTF-8");
                  key = URLDecoder.decode(key, "UTF-8");
                  values.put(key, value);
               } catch (UnsupportedEncodingException ex) {
                  //
               }
            }
         }
      }
      return values;
   }

   public static String encode(String value) throws UnsupportedEncodingException {
      return URLEncoder.encode(value, "UTF-8");
   }

}
