package jbr.springmvc.dao;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;


@Entity
@Table(name="EmployeesTest")
public class EmployeeEntity implements Serializable{

	@Id
	@GeneratedValue
	private Integer EmployeeId;
	
	private String Name;
	
	public EmployeeEntity() {}
	
	public EmployeeEntity(String name) {
		this.Name = name;
	}
	
	public String getName() {
		return this.Name;
	}
	
}
