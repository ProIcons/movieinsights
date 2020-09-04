package gr.movieinsights.config.converters;


import org.elasticsearch.common.Strings;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class ExtendedInformationParameterResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(ExtendedParam.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        if (!methodParameter.getParameterType().isAssignableFrom(boolean.class)) {
            throw new TypeMismatchException(ExtendedParam.class.getSimpleName(),boolean.class);
        }

        String e = nativeWebRequest.getParameter("e");
        return Boolean.parseBoolean(e) || (Strings.isEmpty(e) && e != null);
    }
}
