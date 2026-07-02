package com.sliceform.demo.service;

import com.sliceform.demo.entity.Employee;

import java.util.List;

public interface EmployeeService {

     public Employee save(Employee emp);

      public Employee getEmp(Long id);

      public List<Employee> getAll();

}
