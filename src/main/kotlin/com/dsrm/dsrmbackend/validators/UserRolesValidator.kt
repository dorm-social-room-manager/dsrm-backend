package com.dsrm.dsrmbackend.validators

import com.dsrm.dsrmbackend.repositories.RoleRepo
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class UserRolesValidator : ConstraintValidator<UserRolesConstraint, List<String>> {

    @Autowired
    lateinit var roleRepo : RoleRepo

    override fun isValid(userRoles: List<String>?, context: ConstraintValidatorContext?): Boolean {
        if (userRoles == null) {
            return true
        }
        val invalidRoles: MutableList<String> = mutableListOf()
        for (roleId: String in userRoles) {
            val maybeRole = roleRepo.findById(roleId)
            if (maybeRole.isEmpty) {
                invalidRoles.add(roleId)
            }
        }
        if (invalidRoles.isNotEmpty()) {
            val hibernateContext: HibernateConstraintValidatorContext? =
                context?.unwrap(HibernateConstraintValidatorContext::class.java)
            hibernateContext?.disableDefaultConstraintViolation()
            hibernateContext?.addMessageParameter("roles", invalidRoles)
                ?.buildConstraintViolationWithTemplate(hibernateContext.defaultConstraintMessageTemplate)
                ?.addConstraintViolation()
            return false
        }
        return true
    }

}
