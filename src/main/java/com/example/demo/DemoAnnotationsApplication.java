package com.example.demo;

import java.lang.reflect.Method;
import java.util.Set;

import com.example.annotation.web.RequestMapping;
import com.example.demo.DemoAnnotationsApplication.DemoAnnotationsRuntimeHints;
import com.example.demo.sample.WebSample;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.util.ReflectionUtils.MethodFilter;

@SpringBootApplication
@ImportRuntimeHints(DemoAnnotationsRuntimeHints.class)
public class DemoAnnotationsApplication {

	private static final MethodFilter REQUEST_MAPPING_FILTER = method ->
			AnnotatedElementUtils.hasAnnotation(method, RequestMapping.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoAnnotationsApplication.class, args);
	}

	@Bean
	public ApplicationRunner run(WebSample webSample) {
		return args -> {
			Set<Method> methods = MethodIntrospector.selectMethods(webSample.getClass(), REQUEST_MAPPING_FILTER);
			System.out.println("Found " + methods.size() + " methods");
			System.out.println("-----------------");
			for (Method method : methods) {
				System.out.println("Checking method " + method);
				MergedAnnotation<RequestMapping> annotation = MergedAnnotations.from(method).get(RequestMapping.class);
				System.out.println("name -> " + annotation.getValue("name"));
				System.out.println("method -> " + annotation.getValue("method"));
				System.out.println("====");
			}
		};

	}

	static class DemoAnnotationsRuntimeHints implements RuntimeHintsRegistrar {
		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			hints.reflection().registerType(WebSample.class, type ->
					type.withMembers(MemberCategory.INTROSPECT_DECLARED_METHODS));
		}
	}

}
