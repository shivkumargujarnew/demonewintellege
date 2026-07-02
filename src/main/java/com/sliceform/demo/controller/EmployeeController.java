package com.sliceform.demo.controller;

import com.sliceform.demo.dto.EmployeeDTO;
import com.sliceform.demo.dto.AddressDTO;
import com.sliceform.demo.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emp")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> create(@Valid @RequestBody EmployeeDTO emp){
        return new ResponseEntity<>(service.save(emp), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getEmp(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable Long id, @Valid @RequestBody EmployeeDTO emp){
        return ResponseEntity.ok(service.update(id, emp));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeDTO> partialUpdate(@PathVariable Long id, @RequestBody EmployeeDTO emp){
        return ResponseEntity.ok(service.partialUpdate(id, emp));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    // Address Management Endpoints
    @PostMapping("/{employeeId}/addresses")
    public ResponseEntity<AddressDTO> addAddress(@PathVariable Long employeeId, @Valid @RequestBody AddressDTO address){
        return new ResponseEntity<>(service.addAddress(employeeId, address), HttpStatus.CREATED);
    }

    @GetMapping("/{employeeId}/addresses")
    public ResponseEntity<List<AddressDTO>> getEmployeeAddresses(@PathVariable Long employeeId){
        return ResponseEntity.ok(service.getEmployeeAddresses(employeeId));
    }

    @PutMapping("/{employeeId}/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long employeeId, @PathVariable Long addressId,
                                                     @Valid @RequestBody AddressDTO address){
        return ResponseEntity.ok(service.updateAddress(employeeId, addressId, address));
    }

    @DeleteMapping("/{employeeId}/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long employeeId, @PathVariable Long addressId){
        service.deleteAddress(employeeId, addressId);
        return ResponseEntity.ok("Address deleted successfully");
    }

}
