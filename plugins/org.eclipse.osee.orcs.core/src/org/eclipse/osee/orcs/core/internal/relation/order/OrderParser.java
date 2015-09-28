/*******************************************************************************
 * Copyright (c) 2004, 2007 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.osee.orcs.core.internal.relation.order;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.eclipse.osee.framework.core.data.IRelationSorterId;
import org.eclipse.osee.framework.core.data.IRelationType;
import org.eclipse.osee.framework.core.data.IRelationTypeSide;
import org.eclipse.osee.framework.core.data.TokenFactory;
import org.eclipse.osee.framework.core.enums.RelationOrderBaseTypes;
import org.eclipse.osee.framework.core.enums.RelationSide;
import org.eclipse.osee.framework.core.exception.OseeExceptions;
import org.eclipse.osee.framework.jdk.core.type.OseeCoreException;
import org.eclipse.osee.framework.jdk.core.util.Conditions;
import org.eclipse.osee.framework.jdk.core.util.Lib;
import org.eclipse.osee.framework.jdk.core.util.Strings;
import org.eclipse.osee.orcs.data.RelationTypes;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * @author Roberto E. Escobar
 */
public class OrderParser {

   private static final String ROOT_ELEMENT = "OrderList";
   private static final String START_TAG = "Order";
   private static final String RELATION_TYPE_TAG = "relType";
   private static final String ORDER_TYPE_TAG = "orderType";
   private static final String SIDE_TAG = "side";
   private static final String LIST_TAG = "list";

   private static final ThreadLocal<XMLInputFactory> inputFactory = new ThreadLocal<XMLInputFactory>() {

      @Override
      protected XMLInputFactory initialValue() {
         return XMLInputFactory.newInstance();
      }
   };

   private static final ThreadLocal<XMLOutputFactory> outputFactory = new ThreadLocal<XMLOutputFactory>() {

      @Override
      protected XMLOutputFactory initialValue() {
         return XMLOutputFactory.newInstance();
      }
   };

   private final RelationTypes relationCache;

   public OrderParser(RelationTypes relationCache) {
      this.relationCache = relationCache;
   }

   public void loadFromXml(HasOrderData hasOrderData, String rawData) throws OseeCoreException {
      Conditions.checkNotNull(hasOrderData, "orderData");
      if (Strings.isValid(rawData) && rawData.trim().length() > 0) {
         Reader reader = new StringReader(rawData);
         try {
            XMLStreamReader streamReader = inputFactory.get().createXMLStreamReader(reader);
            while (streamReader.hasNext()) {
               process(streamReader, hasOrderData);
               streamReader.next();
            }
         } catch (XMLStreamException ex) {
            OseeExceptions.wrapAndThrow(ex);
         } finally {
            Lib.close(reader);
         }
      }
   }

   private void process(XMLStreamReader reader, HasOrderData hasOrderData) throws OseeCoreException {
      int eventType = reader.getEventType();
      switch (eventType) {
         case XMLStreamConstants.START_ELEMENT:
            String localName = reader.getLocalName();
            String uri = reader.getNamespaceURI();
            if (START_TAG.equals(localName)) {
               final String relationTypeName = reader.getAttributeValue(uri, RELATION_TYPE_TAG);
               String orderType = reader.getAttributeValue(uri, ORDER_TYPE_TAG);
               String relationSide = reader.getAttributeValue(uri, SIDE_TAG);
               String rawList = reader.getAttributeValue(uri, LIST_TAG);
               if (relationTypeName != null && orderType != null && relationSide != null) {
                  List<String> list = Collections.emptyList();
                  if (rawList != null) {
                     list = new ArrayList<>();
                     StringTokenizer tokenizer = new StringTokenizer(rawList, ",");
                     while (tokenizer.hasMoreTokens()) {
                        list.add(tokenizer.nextToken());
                     }
                  }

                  // TODO don't store relation type by name - use type UUID
                  IRelationType type = Iterables.find(relationCache.getAll(), new Predicate<IRelationType>() {
                     @Override
                     public boolean apply(IRelationType type) {
                        return type.getName().equalsIgnoreCase(relationTypeName);
                     }
                  });

                  RelationSide side = RelationSide.fromString(relationSide);
                  IRelationTypeSide typeSide =
                     TokenFactory.createRelationTypeSide(side, type.getGuid(), type.getName());
                  IRelationSorterId sorterId = RelationOrderBaseTypes.getFromGuid(orderType);
                  OrderData orderData = new OrderData(sorterId, list);
                  hasOrderData.add(typeSide, orderData);
               }
            }
            break;
         default:
            break;
      }
   }

   public String toXml(HasOrderData hasOrderData) throws OseeCoreException {
      Conditions.checkNotNull(hasOrderData, "orderData");
      StringWriter writer = new StringWriter();
      XMLStreamWriter xmlWriter = null;
      try {
         xmlWriter = outputFactory.get().createXMLStreamWriter(writer);
         xmlWriter.writeStartElement(ROOT_ELEMENT);
         for (Entry<IRelationTypeSide, OrderData> entry : hasOrderData) {
            writeEntry(xmlWriter, entry.getKey(), entry.getValue());
         }
         xmlWriter.writeEndElement();
         xmlWriter.writeEndDocument();
      } catch (XMLStreamException ex) {
         OseeExceptions.wrapAndThrow(ex);
      } finally {
         if (xmlWriter != null) {
            try {
               xmlWriter.close();
            } catch (XMLStreamException ex) {
               OseeExceptions.wrapAndThrow(ex);
            }
         }
      }
      return writer.toString();
   }

   private void writeEntry(XMLStreamWriter xmlWriter, IRelationTypeSide typeAndSide, OrderData orderData) throws XMLStreamException {
      xmlWriter.writeStartElement(START_TAG);
      // TODO don't store relation type by name - use type UUID
      xmlWriter.writeAttribute(RELATION_TYPE_TAG, typeAndSide.getName());
      xmlWriter.writeAttribute(SIDE_TAG, typeAndSide.getSide().name());
      xmlWriter.writeAttribute(ORDER_TYPE_TAG, orderData.getSorterId().getGuid());

      List<String> guids = orderData.getOrderIds();
      if (!guids.isEmpty()) {
         xmlWriter.writeAttribute(LIST_TAG, org.eclipse.osee.framework.jdk.core.util.Collections.toString(",", guids));
      }
      xmlWriter.writeEndElement();
   }
}
