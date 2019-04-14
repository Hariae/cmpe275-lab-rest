package jbr.springmvc;

import java.util.*;

public class Employee {
	private Integer id;
    private String name;
    private String email;
    private String title;
    private Address Address;
    private Employer employer;
    private EmployeeResult manager;
    private List<EmployeeResult> Reports;
    private List<Employee> collaborators;
	
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Address getAddress() {
		return Address;
	}
	public void setAddress(Address Address) {
		this.Address = Address;
	}
	public Employer getEmployer() {
		return employer;
	}
	public void setEmployer(Employer employer) {
		this.employer = employer;
	}
	public EmployeeResult getManager() {
		return manager;
	}
	public void setManager(EmployeeResult manager) {
		this.manager = manager;
	}
	public List<EmployeeResult> getReports() {
		return Reports;
	}
	public void setReports(List<EmployeeResult> Reports) {
		this.Reports = Reports;
	}
	public List<Employee> getCollaborators() {
		return collaborators;
	}
	public void setCollaborators(List<Employee> collaborators) {
		this.collaborators = collaborators;
	}

}



