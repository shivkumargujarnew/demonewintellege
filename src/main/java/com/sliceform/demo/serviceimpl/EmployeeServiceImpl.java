package com.sliceform.demo.serviceimpl;

import com.sliceform.demo.entity.Employee;
import com.sliceform.demo.repository.EmployeeRepo;
import com.sliceform.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepo repo;

    @Override
    public Employee save(Employee emp) {

        return repo.save(emp);
    }

    @Override
    public Employee getEmp(Long id) {
        return repo.findById(id).get();
    }


    @Override
    public List<Employee> getAll() {
        return repo.findAll();
    }
}
