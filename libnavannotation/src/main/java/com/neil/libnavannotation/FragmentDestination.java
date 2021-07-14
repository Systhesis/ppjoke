package com.neil.libnavannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author zhongnan
 * @time 2021/7/13 20:39
 *
 */
@Target(ElementType.TYPE)
public @interface FragmentDestination {
    String pageUrl();

    boolean needLogin() default false;

    boolean asStarter() default false;
}
