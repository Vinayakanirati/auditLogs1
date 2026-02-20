package com.example.audit.service;

import com.example.audit.entity.Employee;
import com.example.audit.repository.EmployeeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuditLogService auditLogService;
    private final ObjectMapper objectMapper;

    // Hardcoded user for demonstration purposes 
    // In a real app this would come from SecurityContextHolder
    private final String currentUser = "admin";

    public List<Employee> getAllActive() {
        return employeeRepository.findAllActive();
    }

    public List<Employee> getAllDeleted() {
        return employeeRepository.findAllDeleted();
    }
    
    public Employee getById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Transactional
    public Employee create(Employee employee) {
        Employee saved = employeeRepository.save(employee);
        auditLogService.logAction("Employee", "CREATE", null, toJson(saved), currentUser);
        return saved;
    }

    @Transactional
    public Employee update(Long id, Employee employeeDetails) {
        Employee existing = getById(id);
        String oldJson = toJson(existing);
        
        existing.setName(employeeDetails.getName());
        existing.setDepartment(employeeDetails.getDepartment());
        existing.setSalary(employeeDetails.getSalary());
        
        Employee updated = employeeRepository.save(existing);
        String newJson = toJson(updated);
        
        auditLogService.logAction("Employee", "UPDATE", oldJson, newJson, currentUser);
        return updated;
    }

    @Transactional
    public void delete(Long id) {
        Employee existing = getById(id);
        String oldJson = toJson(existing);
        
        employeeRepository.deleteById(id);
        
        // After soft delete, we can get it via custom method to show new state
        Employee afterDelete = employeeRepository.findByIdWithDeleted(id);
        String newJson = toJson(afterDelete);
        
        auditLogService.logAction("Employee", "DELETE", oldJson, newJson, currentUser);
    }

    @Transactional
    public void restore(Long id) {
        Employee existing = employeeRepository.findByIdWithDeleted(id);
        if (existing == null || !existing.isDeleted()) {
            throw new RuntimeException("Cannot restore active or non-existent employee");
        }
        
        String oldJson = toJson(existing);
        employeeRepository.restoreById(id);
        
        Employee restored = employeeRepository.findByIdWithDeleted(id);
        String newJson = toJson(restored);
        
        auditLogService.logAction("Employee", "RESTORE", oldJson, newJson, currentUser);
    }

    private String toJson(Object object) {
        if (object == null) return null;
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}
