package edu.sjsu.cmpe275.lab2;

import java.util.*;

public class Employee {
	private long id;
    private String name;
    private String email;
    private String title;
    private Address address;
    private Employer employer;
    private Employee Manager;
    private List<Employee> reports;
    private List<Employee> collaborators;
	
    public long getId() {
		return id;
	}
	public void setId(long id) {
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
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Employer getEmployer() {
		return employer;
	}
	public void setEmployer(Employer employer) {
		this.employer = employer;
	}
	public Employee getManager() {
		return Manager;
	}
	public void setManager(Employee manager) {
		Manager = manager;
	}
	public List<Employee> getReports() {
		return reports;
	}
	public void setReports(List<Employee> reports) {
		this.reports = reports;
	}
	public List<Employee> getCollaborators() {
		return collaborators;
	}
	public void setCollaborators(List<Employee> collaborators) {
		this.collaborators = collaborators;
	}

}



