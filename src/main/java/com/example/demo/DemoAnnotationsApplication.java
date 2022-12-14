package com.example.demo;

import java.lang.reflect.Method;
import java.util.Optional;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.SynthesizedAnnotation;
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
	public ApplicationRunner run(ApplicationContext applicationContext) {
		return args -> {
			if (args.getNonOptionArgs().size() == 0) {
				return;
			}
			Class<?> beanType = applicationContext.getBean(args.getNonOptionArgs().get(0)).getClass();
			Set<Method> methods = MethodIntrospector.selectMethods(beanType, REQUEST_MAPPING_FILTER);
			System.out.println("Found " + methods.size() + " methods");
			System.out.println("-----------------");
			for (Method method : methods) {
				System.out.println("Checking method " + method);
				RequestMapping annotation = MergedAnnotations.from(method).get(RequestMapping.class).synthesize();
				System.out.println("synthesized -> " + (annotation instanceof SynthesizedAnnotation));
				System.out.println("name -> " + annotation.name());
				System.out.println("method -> " + annotation.method());
				System.out.println("====");
			}
		};

	}

	static class DemoAnnotationsRuntimeHints implements RuntimeHintsRegistrar {
		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			hints.reflection().registerType(WebSample.class, type ->
					type.withMembers(MemberCategory.INTROSPECT_DECLARED_METHODS));
			hints.proxies().registerJdkProxy(RequestMapping.class, SynthesizedAnnotation.class);
		}
	}

}
