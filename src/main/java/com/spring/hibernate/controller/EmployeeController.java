package com.spring.hibernate.controller;

import com.spring.hibernate.model.Employee;
import com.spring.hibernate.model.EmployeeList;
import com.spring.hibernate.service.ManageEmployeeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/employee")
public class HomeController {
	@Autowired
	private ManageEmployeeImpl manageEmployee;

    @Transactional
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeList> get(){
		return new ResponseEntity<EmployeeList>(manageEmployee.listAll(), HttpStatus.OK);
	}

    @Transactional
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> getUser(@PathVariable("id") int id) {
		Employee employee = manageEmployee.getEmployee(id);
		if (employee == null) {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Employee> updateUser(@PathVariable("id") int id, @RequestBody Employee employee) {
        Employee currentEmployee = manageEmployee.getEmployee(id);
        if (currentEmployee == null) {
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }

        currentEmployee.setSalary(employee.getSalary());
        currentEmployee.setFirstName(employee.getFirstName());
        currentEmployee.setLastName(employee.getLastName());

        manageEmployee.updateEmployee(currentEmployee);

        return new ResponseEntity<Employee>(currentEmployee, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody Employee employee, UriComponentsBuilder ucBuilder) {


        manageEmployee.createEmployee(employee);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(employee.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Employee> deleteUser(@PathVariable("id") int id) {

        Employee employee = manageEmployee.getEmployee(id);
        if (employee == null) {
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }

        manageEmployee.deleteEmployee(employee);
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }

}
