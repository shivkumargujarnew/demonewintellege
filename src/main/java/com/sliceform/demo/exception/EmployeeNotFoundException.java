package com.sliceform.demo.exception;

/**
 * Thrown when an Employee with the requested id does not exist.
 */
public class EmployeeNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmployeeNotFoundException(Long id) {
        super("Employee not found with id: " + id);
    }
}

