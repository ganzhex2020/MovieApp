package com.phi.moviecloud.auth.service;

import com.phi.moviecloud.auth.exception.AppException;
import com.phi.moviecloud.auth.mapper.RoleMapper;
import com.phi.moviecloud.auth.mapper.UserMapper;
import com.phi.moviecloud.auth.model.Role;
import com.phi.moviecloud.auth.model.RoleName;
import com.phi.moviecloud.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public void SaveUser(String username, String password, boolean admin) {
        // Creating user's account
        User user = User.builder()
                .username(username)
                .password(password)
                .build();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.saveUser(user);

        Role userRole = roleMapper.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));
        if (admin) {
            userRole = roleMapper.findByName(RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new AppException("Admin Role not set."));
        }

        Map<String, Object> userRoleParams = new HashMap<>();
        userRoleParams.put("userId", user.getId());
        userRoleParams.put("roleIds", Arrays.asList(userRole.getId()));
        userMapper.saveUserRole(userRoleParams);
    }

    public boolean usernameExists(String username) {
        return userMapper.existsByUsername(username);
    }
}
