package com.aditya.exercise.employee;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import com.aditya.exercise.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@XmlRootElement
public class Employee extends BaseEntity {
	
	public Employee() {
		
	}
	
	public Employee(Integer employeeId, String firstName, String lastName, Date joiningDate, Department department) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.joiningDate = joiningDate;
		this.department = department;
	}

	@Id
	private Integer employeeId;
	
	private String firstName;
	
	private String lastName;
	
	private Date joiningDate;
	
	private Department department;
	
	@Override
	public String toString() {
		return this.employeeId +" "+ this.firstName +  " " + this.lastName + " " + this.joiningDate + " " + this.department;
	}
	
	@Override 
	public boolean equals(Object obj) 
	{ 
		if (obj == this) { 
			return true; 
		} 
		if (obj == null || obj.getClass() != this.getClass()) { 
			return false; 
		} 
		Employee emp = (Employee) obj; 
		return this.employeeId.equals(emp.getEmployeeId()) && 
				this.firstName.equals(emp.getFirstName()) &&
				this.lastName.equals(emp.getLastName()) &&
				this.joiningDate.equals(emp.getJoiningDate()) && 
				this.department.equals(emp.getDepartment());
	}

	

}
