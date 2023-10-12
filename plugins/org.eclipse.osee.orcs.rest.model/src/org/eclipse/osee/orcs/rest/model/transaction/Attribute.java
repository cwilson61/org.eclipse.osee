/*********************************************************************
 * Copyright (c) 2023 Boeing
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Boeing - initial API and implementation
 **********************************************************************/
package org.eclipse.osee.orcs.rest.model.transaction;

/**
 * @author David W. Miller
 * @author autogenerated by jsonschema2pojo
 */
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"typeName", "value", "typeId"})
public class Attribute extends AttributeTransfer {

   @JsonProperty("typeName")
   private String typeName;

   @JsonIgnore
   private final Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

   public Attribute() {
   }

   public Attribute(String typeId) {
      this.setTypeId(typeId);
   }

   public Attribute(String typeId, List<String> value) {
      this.setTypeId(typeId);
      this.setValue(value);
   }

   @JsonProperty("typeName")
   public String getTypeName() {
      return typeName;
   }

   @JsonProperty("typeName")
   public void setTypeName(String typeName) {
      this.typeName = typeName;
   }

   @JsonAnyGetter
   public Map<String, Object> getAdditionalProperties() {
      return this.additionalProperties;
   }

   @JsonAnySetter
   public void setAdditionalProperty(String name, Object value) {
      this.additionalProperties.put(name, value);
   }

}
