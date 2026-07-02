package com.sliceform.demo.controller;

import com.sliceform.demo.entity.Employee;
import com.sliceform.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emp")
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    @PostMapping
    public ResponseEntity<Employee> crete(@RequestBody Employee emp){
        return new  ResponseEntity<Employee>(service.save(emp),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> crete(@PathVariable Long id){
        return new  ResponseEntity<Employee>(service.getEmp(id),HttpStatus.OK);
    }

}
