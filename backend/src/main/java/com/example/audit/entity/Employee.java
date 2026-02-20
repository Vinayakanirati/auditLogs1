package com.example.audit.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;

@Data
@Entity
@SQLDelete(sql = "UPDATE employee SET deleted = true WHERE id=?")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private String department;
    
    private Double salary;

    private boolean deleted = false;
}
