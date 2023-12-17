// Define the package
package org.tmed.consultoriosback.logging;

// Import necessary Spring framework classes
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Annotate the class with @Configuration to indicate that it defines beans and Spring configuration
@Configuration
public class LogMvcConfig implements WebMvcConfigurer {

    // Declare a reference to LoggingInterceptor
    final LoggingInterceptor logInterceptor;

    // Constructor to inject the LoggingInterceptor
    public LogMvcConfig(LoggingInterceptor logInterceptor) {
        this.logInterceptor = logInterceptor;
    }

    // Override the addInterceptors method from the WebMvcConfigurer interface
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Register the LoggingInterceptor with the InterceptorRegistry
        registry.addInterceptor(logInterceptor);
    }
}
