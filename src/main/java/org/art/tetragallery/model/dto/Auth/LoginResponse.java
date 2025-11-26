package org.art.tetragallery.model.dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.art.tetragallery.model.entity.Role;

@Data
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private String token;
}
