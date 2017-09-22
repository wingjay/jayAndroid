package com.wingjay.jayandroid.richlist;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RichViewHolder
 *
 * @author wingjay
 * @date 2017/09/22
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface RichViewHolder {
    String key();
}
