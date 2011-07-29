package com.github.yacaqueryservice.example;

import java.util.Collection;

import com.sun.xml.bind.CycleRecoverable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;


/**
	* 
	**/

@XmlAccessorType(XmlAccessType.NONE)

@XmlType(name = "Organization", namespace="com.github.yacaqueryservice.example", propOrder = {"id", "name", "identifier", "street", "city", "country", "state", "zipCode", "units"})
@XmlRootElement(name="Organization", namespace="com.github.yacaqueryservice.example")
public class Organization  implements Serializable, CycleRecoverable
{
	/**
	* An attribute to allow serialization of the domain objects
	*/
	private static final long serialVersionUID = 1234567890L;

	
	/**
	* 
	**/
	
	public String id;
	/**
	* Retrieves the value of the id attribute
	* @return id
	**/
    @XmlAttribute
	public String getId(){
		return id;
	}

	/**
	* Sets the value of id attribute
	**/

	public void setId(String id){
		this.id = id;
	}
	
	/**
	* 
	**/
	
	public String name;
	/**
	* Retrieves the value of the name attribute
	* @return name
	**/
    @XmlAttribute
	public String getName(){
		return name;
	}

	/**
	* Sets the value of name attribute
	**/

	public void setName(String name){
		this.name = name;
	}
	
	/**
	* 
	**/
	
	public String identifier;
	/**
	* Retrieves the value of the identifier attribute
	* @return identifier
	**/
    @XmlAttribute
	public String getIdentifier(){
		return identifier;
	}

	/**
	* Sets the value of identifier attribute
	**/

	public void setIdentifier(String identifier){
		this.identifier = identifier;
	}
	
	/**
	* 
	**/
	
	public String street;
	/**
	* Retrieves the value of the street attribute
	* @return street
	**/
    @XmlAttribute
	public String getStreet(){
		return street;
	}

	/**
	* Sets the value of street attribute
	**/

	public void setStreet(String street){
		this.street = street;
	}
	
	/**
	* 
	**/
	
	public String city;
	/**
	* Retrieves the value of the city attribute
	* @return city
	**/
    @XmlAttribute
	public String getCity(){
		return city;
	}

	/**
	* Sets the value of city attribute
	**/

	public void setCity(String city){
		this.city = city;
	}
	
	/**
	* 
	**/
	
	public String country;
	/**
	* Retrieves the value of the country attribute
	* @return country
	**/
    @XmlAttribute
	public String getCountry(){
		return country;
	}

	/**
	* Sets the value of country attribute
	**/

	public void setCountry(String country){
		this.country = country;
	}
	
	/**
	* 
	**/
	
	public String state;
	/**
	* Retrieves the value of the state attribute
	* @return state
	**/
    @XmlAttribute
	public String getState(){
		return state;
	}

	/**
	* Sets the value of state attribute
	**/

	public void setState(String state){
		this.state = state;
	}
	
	/**
	* 
	**/
	
	public String zipCode;
	/**
	* Retrieves the value of the zipCode attribute
	* @return zipCode
	**/
    @XmlAttribute
	public String getZipCode(){
		return zipCode;
	}

	/**
	* Sets the value of zipCode attribute
	**/

	public void setZipCode(String zipCode){
		this.zipCode = zipCode;
	}
	
		
	/**
	* An associated info.minnesotapartnership.chn.models.Unit object's collection 
	**/
	@XmlElementWrapper(name="units",
		namespace="com.github.yacaqueryservice.example")
	@XmlElement(name="Unit",
		namespace="com.github.yacaqueryservice.example")
	private Collection<Unit> units;
	/**
	* Retrieves the value of the units attribute
	* @return units
	**/
	@XmlTransient
	public Collection<Unit> getUnits(){
		return units;
	}

	/**
	* Sets the value of units attribute
	**/

	public void setUnits(Collection<Unit> units){
		this.units = units;
	}
		
	/**
	* Compares <code>obj</code> to itself and returns true if they both are same
	*
	* @param obj
	**/
	public boolean equals(Object obj)
	{
		if(obj instanceof Organization) 
		{
			Organization c =(Organization)obj; 			 
			if(getId() != null && getId().equals(c.getId()))
				return true;
		}
		return false;
	}
		
	/**
	* Returns hash code for the primary key of the object
	**/
	public int hashCode()
	{
		if(getId() != null)
			return getId().hashCode();
		return 0;
	}
	    public Object onCycleDetected(Context arg0) {
		return null;
	}

	
}