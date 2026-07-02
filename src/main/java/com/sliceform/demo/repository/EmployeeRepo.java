package com.sliceform.demo.repository;
import com.sliceform.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EmployeeRepo extends JpaRepository<Employee,Long> {
}
