package gr.movieinsights.config;

import gr.movieinsights.config.converters.ExtendedInformationParameterResolver;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ExtendedInformationParameterResolver());
    }

    @Bean
    public WebMvcRegistrations webMvcRegistrationsHandlerMapping() {
        return new WebMvcRegistrations() {
            @Override
            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
                return new InheritedRequestMappingHandlerMapping();
            }
        };
    }


    private static class RequestMappingHelper {
        List<String> mappings = new ArrayList<>();
        RequestMappingHelper parent;
        RequestMappingHelper child;

        public void submit(List<String> mappings, RequestMappingHelper helper) {
            if (helper != null) {
                helper.parent = this;
                child = helper;
            }
            this.mappings = mappings;
        }


        public List<String> build() {
            return build(getRoot(this), null);
        }

        private static List<String> build(RequestMappingHelper child, List<String> parentMappings) {
            List<String> maps = new ArrayList<>();

            if (parentMappings == null) {
                maps = child.mappings;
            } else {
                for (String parentMapping : parentMappings) {
                    for (String mapping : child.mappings) {
                        maps.add(String.join("", parentMapping, mapping));
                    }
                }
            }
            if (child.child == null)
                return maps;
            else
                return build(child.child, maps);
        }

        public static RequestMappingHelper getRoot(RequestMappingHelper helper) {
            RequestMappingHelper root = helper;
            while (root.parent != null) {
                root = root.parent;
            }
            return root;
        }
    }



    private static class InheritedRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
        @Override
        protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
            RequestMappingInfo methodMapping = super.getMappingForMethod(method, handlerType);
            if (methodMapping == null)
                return null;

            List<String> superclassUrlPatterns = new ArrayList<>();
            Map<Integer, List<String>> superclassUrlPatternsMap = new HashMap<>();
            boolean springPath = false;
            RequestMappingHelper helper = null;
            for (Class<?> clazz = handlerType; clazz != Object.class; clazz = clazz.getSuperclass()) {
                if (clazz.isAnnotationPresent(RequestMapping.class))
                    if (springPath) {
                        RequestMappingHelper activeHelper = new RequestMappingHelper();
                        activeHelper.submit(Arrays.asList(clazz.getAnnotation(RequestMapping.class).value()), helper);
                        helper = activeHelper;
                    } else
                        springPath = true;
            }
            if (helper != null) {
                Collections.reverse(superclassUrlPatterns);
                String[] mappings = helper.build().toArray(new String[0]);
                RequestMappingInfo superclassRequestMappingInfo = new RequestMappingInfo("",
                    new PatternsRequestCondition(mappings), null, null, null, null, null, null);// TODO implement specific method, consumes, produces, etc depending on your merging policies
                return superclassRequestMappingInfo.combine(methodMapping);
            } else {
                return methodMapping;
            }
        }
    }
}
