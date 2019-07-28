package com.aditya.exercise.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.aditya.exercise.common.BaseEntity;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class EmployeeError extends BaseEntity {
	
	
	
	public EmployeeError() {
		super();
	}

	public EmployeeError(String errorName, String errorMessage) {
		super();
		this.errorName = errorName;
		this.errorMessage = errorMessage;
	}

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name="ID", updatable = false, length = 200)
	private String id;
     
	private String errorName;
	
	private String errorMessage;
}
