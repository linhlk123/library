package com.lms.library.dto.request;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.Data;
import lombok.AccessLevel;

@Data
@NoArgsConstructor  
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {
    String name;
    String description;
    Set<PermissionRequest> permissions;
}
