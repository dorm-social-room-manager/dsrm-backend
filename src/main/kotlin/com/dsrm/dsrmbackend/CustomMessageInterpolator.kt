package com.dsrm.dsrmbackend

import org.hibernate.validator.internal.engine.MessageInterpolatorContext
import org.springframework.context.MessageSource
import java.util.*
import javax.validation.MessageInterpolator


class CustomMessageInterpolator(private val defaultInterpolator: MessageInterpolator, private val messageSource: MessageSource) : MessageInterpolator {

    override fun interpolate(messageTemplate: String, context: MessageInterpolator.Context): String {
        val fieldName = (context as MessageInterpolatorContext).propertyPath.toString()

        val messageFromProperties = messageSource.getMessage(messageTemplate, null, messageTemplate, Locale.getDefault())

        var interpolatedMessage = defaultInterpolator.interpolate(messageFromProperties, context)

        interpolatedMessage = interpolatedMessage.replace("{field}", fieldName)

        return interpolatedMessage
    }

    override fun interpolate(messageTemplate: String, context: MessageInterpolator.Context, locale: Locale): String {
        val fieldName = (context as MessageInterpolatorContext).propertyPath.toString()

        val messageFromProperties = messageSource.getMessage(messageTemplate, null, messageTemplate, locale)

        var interpolatedMessage = defaultInterpolator.interpolate(messageFromProperties, context)

        interpolatedMessage = interpolatedMessage.replace("{field}", fieldName)

        return interpolatedMessage
    }
}
