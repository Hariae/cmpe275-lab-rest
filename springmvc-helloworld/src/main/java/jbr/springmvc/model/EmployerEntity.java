package jbr.springmvc.model;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;


@Entity
@Table(name="EMPLOYER")
public class EmployerEntity implements Serializable{

	@Id
	@GeneratedValue
	private Integer EmployerId;
	
	private String Name;
	
	private String Description;
	private String Address;
	
	
	public EmployerEntity() {}
	
	public EmployerEntity(String name) {
		this.Name = name;
	}
	
	public String getName() {
		return this.Name;
	}
	
	public void setName(String name) {
		Name = name;
	}

	/*
	 * public int getColloborationId() { return ColloborationId; }
	 * 
	 * public void setColloborationId(int colloborationId) { ColloborationId =
	 * colloborationId; }
	 * 
	 * public int getManagerId() { return ManagerId; }
	 * 
	 * public void setManagerId(int managerId) { ManagerId = managerId; }
	 * 
	 * public int getEmployerId() { return EmployerId; }
	 * 
	 * public void setEmployerId(int employerId) { EmployerId = employerId; }
	 */
	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	
	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
    
	
}
