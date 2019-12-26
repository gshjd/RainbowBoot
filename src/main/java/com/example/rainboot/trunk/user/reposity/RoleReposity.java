package com.example.rainboot.trunk.user.reposity;

import com.example.rainboot.trunk.user.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleReposity extends JpaRepository<UserRole,Long> {
    /**
     * 根据用户id查询角色id
     * @param userId 用户id
     * @return 角色
     */
    @Query(value = "select a.permission_id from user_role_permission a, user_user_role b" +
            " where b.user_id = ?", nativeQuery = true)
    List<Long> findPermissionIdByUserId(Long userId);
}
