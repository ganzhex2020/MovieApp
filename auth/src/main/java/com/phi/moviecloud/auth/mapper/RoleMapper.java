package com.phi.moviecloud.auth.mapper;


import com.phi.moviecloud.auth.model.Role;
import com.phi.moviecloud.auth.model.RoleName;
import org.apache.ibatis.annotations.Mapper;
import java.util.Optional;


@Mapper
public interface RoleMapper {
    Optional<Role> findByName(RoleName roleName);
}
