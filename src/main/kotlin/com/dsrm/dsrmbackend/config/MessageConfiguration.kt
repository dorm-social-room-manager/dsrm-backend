package com.dsrm.dsrmbackend.config

import com.dsrm.dsrmbackend.CustomMessageInterpolator
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import javax.validation.Validation


@Configuration
open class MessageConfiguration {
    @Bean
    open fun messageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:messages")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }

    @Bean
    open fun getValidator(): LocalValidatorFactoryBean? {
        val bean = LocalValidatorFactoryBean()
        messageSource().let { bean.setValidationMessageSource(it) }
        bean.messageInterpolator = CustomMessageInterpolator(Validation.byDefaultProvider().configure().defaultMessageInterpolator, messageSource())
        return bean
    }
}