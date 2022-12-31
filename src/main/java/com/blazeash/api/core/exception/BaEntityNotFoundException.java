/*
 * Copyright 2021 Blazeash Co.,Ltd. AllRights Reserved.
 */
package com.blazeash.api.core.exception;



import com.blazeash.api.core.attribute.BaErrorCode;
import com.blazeash.api.core.attribute.ID;

public class BaEntityNotFoundException extends BaException
{
    private static final long serialVersionUID = 1L;
    private static final BaErrorCode ERROR_CODE;
    private static final String MESSAGE_TEMPLATE = "Entity '%s' specified '%s' = '%s' does not exists.";

    static {
        ERROR_CODE = BaErrorCode.RESOURCE_NOT_FOUND;
    }

    public BaEntityNotFoundException(Class<?> entityClass, ID<?> id) {
        super(ERROR_CODE, MESSAGE_TEMPLATE, entityClass.getSimpleName(), "id", id);
    }

    public BaEntityNotFoundException(Class<?> entityClass, String email) {
        super(ERROR_CODE, MESSAGE_TEMPLATE, entityClass.getSimpleName(), "email", email);
    }
}
