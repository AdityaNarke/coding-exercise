package com.aditya.exercise.employee;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

@Service
public class EmployeeService {
	
	
	@Autowired
	private EmployeeErrorRepository employeeError;

	
	public boolean validateXmlData(String xsdPath, String xmlPath) {
		try {
        	String language = "http://www.w3.org/2001/XMLSchema";
            SchemaFactory factory = 
                    SchemaFactory.newInstance(language);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
        	employeeError.save(new EmployeeError("Invalid xml",e.toString()));
            System.out.println("Exception: "+e.getMessage());
            return false;
        }
        return true;
	}
	
	public Employee getEmployeeFromXml(String xmlPath) {
		Employee employee = new Employee();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Employee.class);
			Unmarshaller marshaller = jaxbContext.createUnmarshaller();
	        employee = (Employee) marshaller.unmarshal(new File(xmlPath));
		} catch (JAXBException e) {
			employeeError.save(new EmployeeError("Object Created Error",e.toString()));
			e.printStackTrace();
		}
        return employee;
	}
	
	public long getDayDifference(Date fromDate, Date toDate) {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		long diff = toDate.getTime() - fromDate.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		return diffDays;
		
	}
	
}
