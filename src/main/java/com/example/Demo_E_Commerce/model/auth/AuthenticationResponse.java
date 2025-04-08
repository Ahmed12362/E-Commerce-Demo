package com.example.Demo_E_Commerce.model.auth;

import com.example.Demo_E_Commerce.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private Role role;  // إضافة هذا الحقل
}