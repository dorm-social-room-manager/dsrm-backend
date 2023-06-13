package com.dsrm.dsrmbackend.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import java.security.Key


@ConfigurationProperties(prefix = "dsrm.auth.jwt")
class JwtProperties {
    var expirationMs = 0

    var refreshExpirationMs = 0

    var secretKey: Key? = null
}