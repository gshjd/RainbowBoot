package com.example.rainboot.system.config.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 预先加载
 *
 * @Author 小熊
 * @Created 2019-07-16
 */
@Component
@Order(1)
public class RunnerApplication implements CommandLineRunner {

    @Async
    @Override
    public void run(String... args) {
    }
}
