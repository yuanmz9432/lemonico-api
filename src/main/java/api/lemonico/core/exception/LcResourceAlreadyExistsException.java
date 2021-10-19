/*
 * Copyright 2021 Lemonico Co.,Ltd. AllRights Reserved.
 */
package api.lemonico.core.exception;



import api.lemonico.core.attribute.LcErrorCode;

public class LcResourceAlreadyExistsException extends LcException
{
    private static final long serialVersionUID = 1L;
    private static final LcErrorCode ERROR_CODE;
    private static final String MESSAGE_TEMPLATE = "Resource '%s' specified email = '%s' was already exists.";

    static {
        ERROR_CODE = LcErrorCode.DUPLICATE_ENTRY;
    }

    public LcResourceAlreadyExistsException(Class<?> resourceClass, String email) {
        super(ERROR_CODE, MESSAGE_TEMPLATE, resourceClass.getSimpleName(), email);
    }
}