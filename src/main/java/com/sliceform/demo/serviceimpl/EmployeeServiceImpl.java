package com.sliceform.demo.serviceimpl;

import com.sliceform.demo.entity.Employee;
import com.sliceform.demo.entity.Address;
import com.sliceform.demo.dto.EmployeeDTO;
import com.sliceform.demo.dto.AddressDTO;
import com.sliceform.demo.repository.EmployeeRepo;
import com.sliceform.demo.repository.AddressRepository;
import com.sliceform.demo.exception.EmployeeNotFoundException;
import com.sliceform.demo.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;



@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepo repo;
    private final AddressRepository addressRepo;

    public EmployeeServiceImpl(EmployeeRepo repo, AddressRepository addressRepo) {
        this.repo = repo;
        this.addressRepo = addressRepo;
    }

    // Convert Address Entity to DTO
    private AddressDTO convertAddressEntityToDTO(Address address) {
        AddressDTO dto = new AddressDTO();
        dto.setId(address.getId());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setZipCode(address.getZipCode());
        dto.setCountry(address.getCountry());
        return dto;
    }

    // Convert Address DTO to Entity
    private Address convertAddressDTOToEntity(AddressDTO dto, Employee employee) {
        Address address = new Address();
        if (dto.getId() != null && dto.getId() > 0) {
            address.setId(dto.getId());
        }
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());
        address.setCountry(dto.getCountry());
        address.setEmployee(employee);
        return address;
    }

    // Convert Entity to DTO (with addresses)
    private EmployeeDTO convertEntityToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setSalary(employee.getSalary());
        dto.setCity(employee.getCity());

        if (employee.getAddresses() != null) {
            dto.setAddresses(employee.getAddresses().stream()
                    .map(this::convertAddressEntityToDTO)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    // Convert DTO to Entity (with addresses)
    private Employee convertDTOToEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        if (dto.getId() != null && dto.getId() > 0) {
            employee.setId(dto.getId());
        }
        employee.setName(dto.getName());
        employee.setSalary(dto.getSalary());
        employee.setCity(dto.getCity());

        if (dto.getAddresses() != null && !dto.getAddresses().isEmpty()) {
            employee.setAddresses(dto.getAddresses().stream()
                    .map(addrDto -> convertAddressDTOToEntity(addrDto, employee))
                    .collect(Collectors.toList()));
        }
        return employee;
    }

    @Override
    @Transactional
    public EmployeeDTO save(EmployeeDTO dto) {
        Employee employee = convertDTOToEntity(dto);
        Employee savedEmployee = repo.save(employee);
        return convertEntityToDTO(savedEmployee);
    }


    @Override
    @Transactional(readOnly = true)
    public EmployeeDTO getEmp(Long id) {
        Employee employee = repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return convertEntityToDTO(employee);
    }


    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAll() {
        return repo.findAll().stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EmployeeDTO update(Long id, EmployeeDTO dto) {
        Employee employee = repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        employee.setName(dto.getName());
        employee.setSalary(dto.getSalary());
        employee.setCity(dto.getCity());
        Employee updatedEmployee = repo.save(employee);
        return convertEntityToDTO(updatedEmployee);
    }

    @Override
    @Transactional
    public EmployeeDTO partialUpdate(Long id, EmployeeDTO dto) {
        Employee employee = repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

        // Update only non-null fields
        if (dto.getName() != null && !dto.getName().isBlank()) {
            employee.setName(dto.getName());
        }
        if (dto.getSalary() != null) {
            employee.setSalary(dto.getSalary());
        }
        if (dto.getCity() != null && !dto.getCity().isBlank()) {
            employee.setCity(dto.getCity());
        }

        Employee updatedEmployee = repo.save(employee);
        return convertEntityToDTO(updatedEmployee);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        repo.deleteById(id);
    }

    @Override
    @Transactional
    public AddressDTO addAddress(Long employeeId, AddressDTO addressDTO) {
        Employee employee = repo.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        Address address = convertAddressDTOToEntity(addressDTO, employee);
        Address savedAddress = addressRepo.save(address);
        return convertAddressEntityToDTO(savedAddress);
    }

    @Override
    @Transactional
    public AddressDTO updateAddress(Long employeeId, Long addressId, AddressDTO addressDTO) {
        Employee employee = repo.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        Address address = addressRepo.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId));

        if (!address.getEmployee().getId().equals(employeeId)) {
            throw new RuntimeException("Address does not belong to this employee");
        }

        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setZipCode(addressDTO.getZipCode());
        address.setCountry(addressDTO.getCountry());

        Address updatedAddress = addressRepo.save(address);
        return convertAddressEntityToDTO(updatedAddress);
    }

    @Override
    @Transactional
    public void deleteAddress(Long employeeId, Long addressId) {
        Employee employee = repo.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        Address address = addressRepo.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId));

        if (!address.getEmployee().getId().equals(employeeId)) {
            throw new RuntimeException("Address does not belong to this employee");
        }

        addressRepo.deleteById(addressId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressDTO> getEmployeeAddresses(Long employeeId) {
        Employee employee = repo.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        return addressRepo.findByEmployeeId(employeeId).stream()
                .map(this::convertAddressEntityToDTO)
                .collect(Collectors.toList());
    }
}
