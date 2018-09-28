package com.casaba.aspect;

import java.lang.annotation.*;

@Target(value={ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SsoFilter {
		boolean  isDoFilter() default true;
}
