package com.dart.server.app.auth;

import com.dart.server.common.db.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "permissions")
@Data
public class PermissionEntity extends Auditable {
    @Column(nullable = false, unique = true)
    private String name;

}