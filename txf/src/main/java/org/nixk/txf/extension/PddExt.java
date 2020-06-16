package org.nixk.txf.extension;

import java.lang.annotation.*;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/6/12
 */

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface PddExt {
    String[] codes() default {""};

    String scenario() default "";
}
