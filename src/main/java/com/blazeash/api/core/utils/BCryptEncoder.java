/*
 * Copyright 2021 Blazeash Co.,Ltd. AllRights Reserved.
 */
package com.blazeash.api.core.utils;



import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptEncoder extends BCryptPasswordEncoder
{

    private static volatile BCryptPasswordEncoder INSTANCE = null;

    private BCryptEncoder() {

    }

    public static BCryptPasswordEncoder getInstance() {
        if (INSTANCE == null) {
            synchronized (BCryptPasswordEncoder.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BCryptPasswordEncoder();
                }
            }
        }
        return INSTANCE;
    }

}