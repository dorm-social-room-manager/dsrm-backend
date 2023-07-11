package com.dsrm.dsrmbackend.validators

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass


@MustBeDocumented
@Constraint(validatedBy = [UserRolesValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class UserRolesConstraint(
    val message: String = "roles.not.exist",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)