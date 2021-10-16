/*
 * Copyright 2021 Lemonico Co.,Ltd. AllRights Reserved.
 */
package api.lemonico.core.annotation;



import java.lang.annotation.*;

@Target({
    ElementType.PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LcSortParam
{
    String defaultValue() default "id:ASC";

    String[] allowedValues() default {
        "id:ASC"
    };
}
