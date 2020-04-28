package org.personal.salesmgmt.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personal.salesmgmt.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class HttpRequestFilter extends OncePerRequestFilter {

    @Value("${salesmgmt.authentication.url}")
    private String authenticationUrl;

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String bearerToken = request.getHeader("Authorization");
        try {
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                RestTemplate restTemplate = new RestTemplate();

                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.set("Authorization", bearerToken);
                HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);

                ResponseEntity<String> userPrincipalResponse = restTemplate.exchange(authenticationUrl, HttpMethod.POST, entity, String.class);

                UserPrincipal userPrincipal = new ObjectMapper().readValue(Objects.requireNonNull(userPrincipalResponse.getBody()), UserPrincipal.class);

                log.info("userPrincipal {}", userPrincipal);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userPrincipal.getUsername(), bearerToken, userPrincipal.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (RuntimeException | ServletException | IOException exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}