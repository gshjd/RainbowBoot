package com.example.rainboot.trunk.user.reposity;

import com.example.rainboot.trunk.user.model.UserUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserReposity extends JpaRepository<UserUser, Long> {
}
