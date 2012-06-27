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
package org.eclipse.osee.ats.core.workdef;

import org.eclipse.osee.ats.workdef.api.IAtsWidgetDefinitionFloatMinMaxConstraint;


/**
 * @author Donald G. Dunne
 */
public class SimpleWidgetDefinitionFloatMinMaxConstraint implements IAtsWidgetDefinitionFloatMinMaxConstraint {
   private Double minValue = null;
   private Double maxValue = null;

   public SimpleWidgetDefinitionFloatMinMaxConstraint(Double minValue, Double maxValue) {
      set(minValue, maxValue);
   }

   public SimpleWidgetDefinitionFloatMinMaxConstraint(String minValue, String maxValue) {
      if (minValue != null) {
         this.minValue = new Double(minValue);
      }
      if (maxValue != null) {
         this.maxValue = new Double(maxValue);
      }
   }

   @Override
   public void set(Double minValue, Double maxValue) {
      this.minValue = minValue;
      this.maxValue = maxValue;
   }

   @Override
   public Double getMinValue() {
      return minValue;
   }

   @Override
   public Double getMaxValue() {
      return maxValue;
   }

}
