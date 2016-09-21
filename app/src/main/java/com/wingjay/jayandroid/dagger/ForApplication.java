package com.wingjay.jayandroid.dagger;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * It means related object injection is for whole Application
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ForApplication {
}
