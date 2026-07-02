package com.sliceform.demo.service;

import com.sliceform.demo.dto.EmployeeDTO;
import com.sliceform.demo.dto.AddressDTO;

import java.util.List;

public interface EmployeeService {

     public EmployeeDTO save(EmployeeDTO emp);

      public EmployeeDTO getEmp(Long id);

      public List<EmployeeDTO> getAll();

      public EmployeeDTO update(Long id, EmployeeDTO emp);

      public EmployeeDTO partialUpdate(Long id, EmployeeDTO emp);

      public void delete(Long id);

      // Address management methods
      public AddressDTO addAddress(Long employeeId, AddressDTO address);

      public AddressDTO updateAddress(Long employeeId, Long addressId, AddressDTO address);

      public void deleteAddress(Long employeeId, Long addressId);

      public List<AddressDTO> getEmployeeAddresses(Long employeeId);

}
