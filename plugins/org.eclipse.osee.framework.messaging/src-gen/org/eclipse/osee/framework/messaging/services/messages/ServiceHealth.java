//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2010.03.30 at 03:47:04 PM MST
//

package org.eclipse.osee.framework.messaging.services.messages;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Java class for ServiceHealth complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceHealth">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="serviceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="serviceVersion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="serviceUniqueId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="brokerURI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="refreshRateInSeconds" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="stopping" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="serviceDescription" type="{}ServiceDescriptionPair" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceHealth", propOrder = {
   "serviceName",
   "serviceVersion",
   "serviceUniqueId",
   "brokerURI",
   "refreshRateInSeconds",
   "stopping",
   "serviceDescription"})
public class ServiceHealth {

   @XmlElement(required = true)
   protected String serviceName;
   @XmlElement(required = true)
   protected String serviceVersion;
   @XmlElement(required = true)
   protected String serviceUniqueId;
   @XmlElement(required = true)
   protected String brokerURI;
   protected int refreshRateInSeconds;
   protected boolean stopping;
   @XmlElement(required = true)
   protected List<ServiceDescriptionPair> serviceDescription;

   /**
    * Gets the value of the serviceName property.
    * 
    * @return possible object is {@link String }
    */
   public String getServiceName() {
      return serviceName;
   }

   /**
    * Sets the value of the serviceName property.
    * 
    * @param value allowed object is {@link String }
    */
   public void setServiceName(String value) {
      this.serviceName = value;
   }

   /**
    * Gets the value of the serviceVersion property.
    * 
    * @return possible object is {@link String }
    */
   public String getServiceVersion() {
      return serviceVersion;
   }

   /**
    * Sets the value of the serviceVersion property.
    * 
    * @param value allowed object is {@link String }
    */
   public void setServiceVersion(String value) {
      this.serviceVersion = value;
   }

   /**
    * Gets the value of the serviceUniqueId property.
    * 
    * @return possible object is {@link String }
    */
   public String getServiceUniqueId() {
      return serviceUniqueId;
   }

   /**
    * Sets the value of the serviceUniqueId property.
    * 
    * @param value allowed object is {@link String }
    */
   public void setServiceUniqueId(String value) {
      this.serviceUniqueId = value;
   }

   /**
    * Gets the value of the brokerURI property.
    * 
    * @return possible object is {@link String }
    */
   public String getBrokerURI() {
      return brokerURI;
   }

   /**
    * Sets the value of the brokerURI property.
    * 
    * @param value allowed object is {@link String }
    */
   public void setBrokerURI(String value) {
      this.brokerURI = value;
   }

   /**
    * Gets the value of the refreshRateInSeconds property.
    */
   public int getRefreshRateInSeconds() {
      return refreshRateInSeconds;
   }

   /**
    * Sets the value of the refreshRateInSeconds property.
    */
   public void setRefreshRateInSeconds(int value) {
      this.refreshRateInSeconds = value;
   }

   /**
    * Gets the value of the stopping property.
    */
   public boolean isStopping() {
      return stopping;
   }

   /**
    * Sets the value of the stopping property.
    */
   public void setStopping(boolean value) {
      this.stopping = value;
   }

   /**
    * Gets the value of the serviceDescription property.
    * <p>
    * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
    * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
    * the serviceDescription property.
    * <p>
    * For example, to add a new item, do as follows:
    * 
    * <pre>
    * getServiceDescription().add(newItem);
    * </pre>
    * <p>
    * Objects of the following type(s) are allowed in the list {@link ServiceDescriptionPair }
    */
   public List<ServiceDescriptionPair> getServiceDescription() {
      if (serviceDescription == null) {
         serviceDescription = new ArrayList<>();
      }
      return this.serviceDescription;
   }

}
