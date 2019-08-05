package com.example.rainboot.trunk.user.service.impl;

import com.example.rainboot.trunk.user.reposity.PermissionReposity;
import com.example.rainboot.trunk.user.reposity.RoleReposity;
import com.example.rainboot.trunk.user.service.PermissionService;
import com.github.pagehelper.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final RoleReposity roleReposity;
    private final PermissionReposity permissionReposity;

    @Autowired
    public PermissionServiceImpl(RoleReposity roleReposity, PermissionReposity permissionReposity) {
        this.roleReposity = roleReposity;
        this.permissionReposity = permissionReposity;
    }

    @Override
    public List<String> findPermissionIdByUserId(long userId) {
        List<Long> permissionIds = roleReposity.findPermissionIdByUserId(userId);
        List<String> permissions = new ArrayList<>();
        for (Long id : permissionIds) {
            permissions.add(permissionReposity.findPermissionsById(id));
        }
        return permissions;
    }
}
