package com.dsrm.dsrmbackend.properties

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.security.Key

@Component
@ConfigurationPropertiesBinding
class SigningKeyConverter : Converter<String, Key>{
    override fun convert(source: String): Key {
        val keyBytes = Decoders.BASE64.decode(source)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}