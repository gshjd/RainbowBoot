package com.example.rainboot.trunk.user.reposity;

import com.example.rainboot.trunk.user.model.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PermissionReposity extends JpaRepository<UserPermission, Long> {

    /**
     * 根据id查出权限名称
     * @param id id
     * @return 权限名称
     */
    @Query(value = "select permissions from user_permission where id = ?", nativeQuery = true)
    String findPermissionsById(Long id);
}
