package com.dsrm.dsrmbackend.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.security.Key


@ConfigurationProperties(prefix = "dsrm.auth.jwt")
@ConstructorBinding
data class JwtProperties (
    var expirationMs: Int = 0,
    var refreshExpirationMs: Int = 0,
    var secretKey: Key? = null
)