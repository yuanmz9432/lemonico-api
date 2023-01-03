package com.blazeash.api.auth.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.blazeash.api.ApplicationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;

@EnableJpaRepositories(bootstrapMode = BootstrapMode.LAZY)
class AuthenticationControllerTest extends ApplicationTest
{

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    @Test
    @DisplayName("テスト_ログイン")
    void login() {}

    @Test
    @DisplayName("テスト_新規登録")
    void register() {}
}
