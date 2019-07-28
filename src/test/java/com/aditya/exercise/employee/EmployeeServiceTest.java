package com.aditya.exercise.employee;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
class EmployeeServiceTest {
	
	@Autowired
	EmployeeService employeeService;
	

	@Test
	void validateXmlData() {
		assertThat(employeeService.validateXmlData("employee.xsd", "employee_test.xml")).isEqualTo(true);
	}
	
	
	@Test 
	void validateForWrongData() { 
	  assertThat(employeeService.validateXmlData("employee.xsd", "houses.xml")).isEqualTo(false);
	}
	
	@Test
	void validateUnmarshalling() {
		Date joiningDate =new Date(948306600000L);
		Employee emp = new Employee(10, "AAA", "BBB",joiningDate, Department.FINANCE);
		assertThat(employeeService.getEmployeeFromXml("employee_test.xml")).isEqualTo(emp);
	}
	
	@Test
	void validateDayDifference() {
		assertThat(employeeService.getDayDifference(new Date(), new Date())).isEqualTo(0);
	}
	
	 

}
