// Define the package
package org.tmed.consultoriosback.logging;

// Import necessary classes and interfaces
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.stream.Collectors.joining;
import static java.util.stream.StreamSupport.stream;

// Annotate as a Service for Spring's component scanning
@Service
public class LoggingServiceImpl implements LoggingService {

    // Initialize a logger instance specific to LoggingInterceptor class
    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    // Implement the logRequest method from the LoggingService interface
    public void logRequest(HttpServletRequest request, Object body) {
        // Log the request URL
        logger.info("Request = " + request.getRequestURL());

        // Retrieve and log request parameters, if any
        var parameters = request.getParameterMap();
        if (!parameters.isEmpty()) {
            var s = parameters.keySet().stream()
                    .map(key -> key + ":" + parameters.get(key)[0]) // Build a string representation of parameters
                    .toList();
            logger.info("Parameters = " + s); // Log the parameters
        }

        // Log the request body if it's not null
        if (body != null) {
            logger.info("body=[" + body + "]");
        }
    }

    // Implement the logResponse method from the LoggingService interface
    @Override
    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {
        // Log the URL of the response
        logger.info("Response = " + httpServletRequest.getRequestURL());

        // Log the response body if it's not null
        if (body != null) {
            var msg = body instanceof Iterable<?> ?
                    stream(((Iterable<?>) body).spliterator(), false)
                            .map(Object::toString)
                            .collect(joining(",\n    ", "[\n    ", "\n]")) // Format if body is an Iterable
                    : body.toString(); // Convert body to string if not Iterable
            logger.info("body=" + msg);
        }
    }
}
