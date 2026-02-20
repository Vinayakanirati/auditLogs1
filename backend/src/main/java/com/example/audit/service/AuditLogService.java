package com.example.audit.service;

import com.example.audit.entity.AuditLog;
import com.example.audit.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public void logAction(String entityName, String action, String oldValues, String newValues, String username) {
        AuditLog log = new AuditLog();
        log.setEntityName(entityName);
        log.setAction(action);
        log.setOldValues(oldValues);
        log.setNewValues(newValues);
        log.setUsername(username);
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }
}
