package com.dsrm.dsrmbackend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import org.springframework.security.crypto.password.PasswordEncoder




@Configuration
open class PasswordConfiguration {
    @Bean
    open fun encoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }
}