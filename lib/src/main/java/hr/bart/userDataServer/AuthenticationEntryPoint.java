package hr.bart.userDataServer;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

//@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	@Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx) throws IOException {
        response.addHeader("WWW-Authenticate", String.format("Basic realm=%s", getRealmName()));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        PrintWriter writer = response.getWriter();
        writer.println(String.format("HTTP Status 401 - %s", authEx.getMessage()));
    }

	@Override
    public void afterPropertiesSet() {
        setRealmName("DeveloperStack");
        
        super.afterPropertiesSet();
    }

}