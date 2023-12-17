// Define the package for the class
package org.tmed.consultoriosback.logging;

// Import required classes and interfaces
import org.springframework.boot.web.servlet.DispatcherType;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Annotate the class as a Component to enable Spring to manage it as a Spring Bean
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    // Declare a reference to LoggingService
    final LoggingService loggingService;

    // Constructor to inject LoggingService instance through dependency injection
    public LoggingInterceptor(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    // Override the preHandle method from the HandlerInterceptor interface
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {

        // Check if the dispatcher type is REQUEST and the HTTP method is GET
        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name())
                && request.getMethod().equals(HttpMethod.GET.name())) {
            // If the conditions are met, log the request using the loggingService
            loggingService.logRequest(request, null);
        }

        // Return true to indicate that the execution chain should proceed with the next interceptor or the handler itself
        return true;
    }

}
