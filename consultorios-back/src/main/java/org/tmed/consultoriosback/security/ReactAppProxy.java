package org.tmed.consultoriosback.security;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class ReactAppProxy {

    private String server = "localhost";
    private int port = 3000;

    @RequestMapping("/")
    public ResponseEntity<String> mirrorReactHtml(@RequestBody(required = false) String body,
                                                  HttpMethod method, HttpServletRequest request, HttpServletResponse response)
            throws URISyntaxException {
        return doMirror(body, method, request);
    }

    @RequestMapping("/files/**")
    public ResponseEntity<String> mirrorReactFiles(@RequestBody(required = false) String body,
                                                   HttpMethod method, HttpServletRequest request, HttpServletResponse response)
            throws URISyntaxException {
        return doMirror(body, method, request);
    }

    @RequestMapping("/static/**")
    public ResponseEntity<String> mirrorReactStatic(@RequestBody(required = false) String body,
                                                    HttpMethod method, HttpServletRequest request, HttpServletResponse response)
            throws URISyntaxException {
        return doMirror(body, method, request);
    }

    private ResponseEntity<String> doMirror(String body, HttpMethod method, HttpServletRequest request) throws URISyntaxException {
        String requestUrl = request.getRequestURI();

        URI uri = new URI("http", null, server, port, null, null, null);
        uri = UriComponentsBuilder.fromUri(uri)
                .path(requestUrl)
                .query(request.getQueryString())
                .build(true).toUri();

        HttpHeaders headers = new HttpHeaders();
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            headers.set(headerName, request.getHeader(headerName));
//        }

        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.exchange(uri, method, httpEntity, String.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getRawStatusCode())
                    .headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }
    }
}
