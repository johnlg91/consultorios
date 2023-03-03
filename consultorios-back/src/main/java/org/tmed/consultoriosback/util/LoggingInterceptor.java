package org.tmed.consultoriosback.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;

@Component
public class LoggingInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Request URL:: " + request.getRequestURL());
        logger.info("Request Payload:: " + getRequestBody(request));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("Request URL:: " + request.getRequestURL());
        logger.info("Request Payload:: " + getResponseBody(response));
    }


    private String getRequestBody(HttpServletRequest request) {
        return "";
//        String payload = null;
//        try {
//            payload = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//        } catch (IOException e) {
//            logger.error("Error reading request payload", e);
//        }
//        return payload;
    }

    private String getResponseBody(HttpServletResponse response) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            byte[] bytes = wrapper.getContentAsByteArray();
            if (bytes.length > 0) {
                try {
                    return new String(bytes, 0, bytes.length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException e) {
                    logger.error("Error getting response payload", e);
                }
            }
        }
        return null;
    }
}

