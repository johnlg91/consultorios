// Define the package
package org.tmed.consultoriosback.logging;

// Import necessary classes
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;

// Annotate the class with @ControllerAdvice to make it applicable across all @RequestMapping methods
@ControllerAdvice
public class RequestAdapter extends RequestBodyAdviceAdapter {

    // Declare a reference to LoggingService
    final LoggingService loggingService;

    // Declare a reference to HttpServletRequest
    final HttpServletRequest httpServletRequest;

    // Constructor to inject LoggingService and HttpServletRequest
    public RequestAdapter(LoggingService loggingService, HttpServletRequest httpServletRequest) {
        this.loggingService = loggingService;
        this.httpServletRequest = httpServletRequest;
    }

    // Override the supports method to indicate that this advice applies to all controller methods
    @Override
    public boolean supports(MethodParameter methodParameter, Type type,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return true; // Return true to apply the advice to all requests
    }

    // Override afterBodyRead to log the request body after it's read and converted
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
                                MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {

        // Log the request using the loggingService
        loggingService.logRequest(httpServletRequest, body);
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
