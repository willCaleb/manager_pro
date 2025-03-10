package com.project.pro.repository;

import com.project.pro.enums.EnumRole;
import com.project.pro.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

    Role findByEnumRole(EnumRole role);

}
