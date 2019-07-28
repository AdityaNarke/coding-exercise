package com.aditya.exercise.employee;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aditya.exercise.common.ResponseInfo;


@RestController
@RequestMapping("/employee")
@CrossOrigin(origins="http://localhost:4200")
public class EmployeeController {
	
	private static final String UPLOADED_FOLDER = "C://logs//";
	
	@Autowired
	private EmployeeRespository employeeRepo;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeErrorRepository employeeError;
	

	@RequestMapping(value="/save")
	public ResponseEntity<ResponseInfo> saveEmployeeXml(@RequestParam("file") MultipartFile file) throws IOException {
		// return employeeRepo.save(employee);
		if (file.isEmpty()) {
			employeeError.save(new EmployeeError("fileNotFound","No xml file found"));
			return new ResponseEntity<ResponseInfo>(new ResponseInfo("failed","File is empty"), HttpStatus.OK);
        }
		
		byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
        
		try {
            Files.write(path, bytes);
		} catch (Exception e) {
			employeeError.save(new EmployeeError("fileWriteError",e.toString()));
			return new ResponseEntity<ResponseInfo>(new ResponseInfo("failed","Error in saving the file"), HttpStatus.OK);
		}
		
		if(employeeService.validateXmlData("employee.xsd",path.toString())) {
			Employee employee = employeeService.getEmployeeFromXml(path.toString());
			Employee employee2 = employeeRepo.findByEmployeeId(employee.getEmployeeId());
			if(employee2 == null) {
				employeeRepo.save(employee);
			} else {
				long dayDifference = employeeService.getDayDifference(employee2.getUpdatedDate(),new Date());
				if(dayDifference > 0) {
					employeeRepo.save(employee);
				} else {
					employeeError.save(new EmployeeError("cannotUpdateRecord","Same record cannot be updated withing 24 hours"));
					return new ResponseEntity<ResponseInfo>(new ResponseInfo("failed","Same record cannot be updated withing 24 hours"), HttpStatus.OK);
				}
			}
		} else {
			return new ResponseEntity<ResponseInfo>(new ResponseInfo("failed","Error in xml validation"), HttpStatus.OK);
		};
		return new ResponseEntity<ResponseInfo>(new ResponseInfo("success","Employee Created successfully"), HttpStatus.OK);
	}


}
