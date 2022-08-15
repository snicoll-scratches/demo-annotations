package com.example.annotation.web;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 *
 * @author Stephane Nicoll
 */
@Target({ ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(method = "POST")
public @interface PostMapping {

	@AliasFor(annotation = RequestMapping.class)
	String name() default "";

}
