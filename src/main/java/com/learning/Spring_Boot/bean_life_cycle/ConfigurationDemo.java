package com.learning.Spring_Boot.bean_life_cycle;

import com.learning.outside_package.CustomAnnotation;
import com.learning.outside_package.CustomService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;


/**
 *
 * The includeFilters attribute in @ComponentScan is mainly used when you want to selectively include specific beans
 * in the component scan, particularly those that might not be in the default package structure
 */
@Configuration
@ComponentScan(
        basePackages = "com.learning.outside_package",
//        includeFilters = {
//                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = CustomAnnotation.class),
//                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = CustomService.class)
//        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = CustomAnnotation.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = CustomService.class),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.learning\\.outside_package\\.first_package\\..*")
        }
) // @ComponentScan should be used along with @Configuration
public class ConfigurationDemo {

}
