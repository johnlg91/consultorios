package org.tmed.consultoriosback.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.stream.Collectors.joining;
import static java.util.stream.StreamSupport.stream;

@Service
public class LoggingServiceImpl implements LoggingService {
    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    public void logRequest(HttpServletRequest request, Object body) {
        logger.info("Request = " + request.getRequestURL());
        var parameters = request.getParameterMap();
        if (!parameters.isEmpty()) {
            var s = parameters.keySet().stream()
                    .map(key -> key + ":" + parameters.get(key)[0])
                    .toList();
            logger.info("Parameters = " + s);
        }

        if (body != null) {
            logger.info("body=[" + body + "]");
        }

    }

    @Override
    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {
        logger.info("Response = " + httpServletRequest.getRequestURL());
        if (body != null) {
            var msg = body instanceof Iterable<?> ?
                    stream(((Iterable<?>) body).spliterator(), false)
                            .map(Object::toString)
                            .collect(joining(",\n    ", "[\n    ", "\n]"))
                    : body.toString();
            logger.info("body=" + msg);
        }
    }
}