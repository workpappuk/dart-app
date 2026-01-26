package com.dart.server.app.todo;

import com.dart.server.common.db.Auditable;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "todos")
public class TodoEntity extends Auditable {
    private String description;
    private boolean completed;

}
