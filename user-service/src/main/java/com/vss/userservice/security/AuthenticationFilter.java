package com.vss.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vss.userservice.exception.BusinessException;
import com.vss.userservice.exception.StatusTemplate;
import com.vss.userservice.modal.response.BaseResponse;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends GenericFilterBean {

    private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
    private final TokenAuthenticator authenticator;
    private ObjectMapper mapper;


    public AuthenticationFilter(TokenAuthenticator authenticator, ObjectMapper mapper) {
        this.authenticator = authenticator;
        this.mapper = mapper;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        Authentication userAuth;
        String token = ((HttpServletRequest) req).getHeader(AUTH_HEADER_NAME);

        try {
            userAuth = this.authenticator.getAuthentication(token);
        } catch (InvalidJwtException e) {
            HttpServletResponse resp = (HttpServletResponse) res;
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            BusinessException error;
            if (e.getMessage().contains("no longer valid")) {
                error = new BusinessException(StatusTemplate.EXPIRE_TOKEN);
            } else {
                error = new BusinessException(StatusTemplate.TOKEN_IN_VALID);
            }

            this.mapper.writeValue(resp.getOutputStream(), BaseResponse.ofFail(error));
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(userAuth);
        chain.doFilter(req, res);
    }
}
