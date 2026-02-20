package com.example.audit.repository;

import com.example.audit.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT * FROM employee WHERE deleted = false", nativeQuery = true)
    List<Employee> findAllActive();

    @Query(value = "SELECT * FROM employee WHERE deleted = true", nativeQuery = true)
    List<Employee> findAllDeleted();

    @Modifying
    @Query(value = "UPDATE employee SET deleted = false WHERE id = ?1", nativeQuery = true)
    void restoreById(Long id);
    
    @Query(value = "SELECT * FROM employee WHERE id = ?1", nativeQuery = true)
    Employee findByIdWithDeleted(Long id);
}
