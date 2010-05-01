//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.04.30 at 04:08:15 PM NZST 
//


package rabbit.data.internal.xml.schema.events;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for perspectiveEventListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="perspectiveEventListType">
 *   &lt;complexContent>
 *     &lt;extension base="{}eventGroupType">
 *       &lt;sequence>
 *         &lt;element name="perspectiveEvent" type="{}perspectiveEventType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "perspectiveEventListType", propOrder = {
    "perspectiveEvent"
})
public class PerspectiveEventListType
    extends EventGroupType
{

    @XmlElement(required = true)
    protected List<PerspectiveEventType> perspectiveEvent;

    /**
     * Gets the value of the perspectiveEvent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the perspectiveEvent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPerspectiveEvent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PerspectiveEventType }
     * 
     * 
     */
    public List<PerspectiveEventType> getPerspectiveEvent() {
        if (perspectiveEvent == null) {
            perspectiveEvent = new ArrayList<PerspectiveEventType>();
        }
        return this.perspectiveEvent;
    }

}
