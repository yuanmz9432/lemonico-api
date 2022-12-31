/*
 * Copyright 2021 Blazeash Co.,Ltd. AllRights Reserved.
 */
package com.blazeash.api.core.exception;



import com.blazeash.api.core.attribute.BaErrorCode;

public class BaException extends RuntimeException
{
    private final BaErrorCode code;

    public BaException(BaErrorCode code, String format, Object... args) {
        super(String.format(format, args));
        this.code = code;
    }

    public BaException(BaErrorCode code, Throwable cause, String format, Object... args) {
        super(String.format(format, args), cause);
        this.code = code;
    }

    public BaErrorCode getCode() {
        return this.code;
    }
}
