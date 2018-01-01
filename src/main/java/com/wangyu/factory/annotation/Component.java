package com.wangyu.factory.annotation;

import javax.annotation.Resource;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Resource
public @interface Component {
}
