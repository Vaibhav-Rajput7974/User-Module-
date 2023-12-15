package com.grapplermodule1.GrapplerEnhancement.dto;

import com.grapplermodule1.GrapplerEnhancement.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserPayload {

    private String name;

    private String email;

    private String designation;

    private String password;

    private List<UserPayload> subordinates;

    private Long reportingUserId;

    private Role role;

    @Override
    public String toString() {
        return "UserPayload{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", designation='" + designation + '\'' +
                ", password='" + password + '\'' +
                ", subordinates=" + subordinates +
                ", reportingUser=" + reportingUserId +
                ", role=" + role +
                '}';
    }
}
