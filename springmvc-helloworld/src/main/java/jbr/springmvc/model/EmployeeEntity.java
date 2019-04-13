package jbr.springmvc.model;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;


@Entity
@Table(name="EMPLOYEE")
public class EmployeeEntity implements Serializable{

	@Id
	@GeneratedValue
	private Integer EmployeeId;
	
	private String Name;
	
	private String Email;
	private String Title;
	private String Address;
	private Integer Employer;
	private Integer Manager;
	private Integer Collab;
	
	
	public EmployeeEntity() {}
	
	public EmployeeEntity(String name) {
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

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public Integer getCollob() {
		return Collab;
	}

	public void setCollob(Integer collab) {
		Collab = collab;
	}

	public Integer getManager() {
		return Manager;
	}

	public void setManager(Integer manager) {
		Manager = manager;
	}

	public Integer getEmployer() {
		return Employer;
	}

	public void setEmployer(Integer employer) {
		Employer = employer;
	}
	
}
