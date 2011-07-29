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

@XmlType(name = "Unit", namespace="com.github.yacaqueryservice.example", propOrder = {"id", "name", "building", "floor", "typeCode", "organization"})
@XmlRootElement(name="Unit", namespace="com.github.yacaqueryservice.example")
public class Unit  implements Serializable, CycleRecoverable
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
	
	public String building;
	/**
	* Retrieves the value of the building attribute
	* @return building
	**/
    @XmlAttribute
	public String getBuilding(){
		return building;
	}

	/**
	* Sets the value of building attribute
	**/

	public void setBuilding(String building){
		this.building = building;
	}
	
	/**
	* 
	**/
	
	public String floor;
	/**
	* Retrieves the value of the floor attribute
	* @return floor
	**/
    @XmlAttribute
	public String getFloor(){
		return floor;
	}

	/**
	* Sets the value of floor attribute
	**/

	public void setFloor(String floor){
		this.floor = floor;
	}
	
	/**
	* 
	**/
	
	public String typeCode;
	/**
	* Retrieves the value of the typeCode attribute
	* @return typeCode
	**/
    @XmlAttribute
	public String getTypeCode(){
		return typeCode;
	}

	/**
	* Sets the value of typeCode attribute
	**/

	public void setTypeCode(String typeCode){
		this.typeCode = typeCode;
	}
	
	/**
	* An associated info.minnesotapartnership.chn.models.Organization object
	**/
	
	@XmlElement(name="organization", 
				type=Organization.class,
				namespace="com.github.yacaqueryservice.example")
	private Organization organization;
	/**
	* Retrieves the value of the organization attribute
	* @return organization
	**/
	@XmlTransient
	public Organization getOrganization(){
		return organization;
	}
	/**
	* Sets the value of organization attribute
	**/

	public void setOrganization(Organization organization){
		this.organization = organization;
	}
			
		
	/**
	* Compares <code>obj</code> to itself and returns true if they both are same
	*
	* @param obj
	**/
	public boolean equals(Object obj)
	{
		if(obj instanceof Unit) 
		{
			Unit c =(Unit)obj; 			 
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