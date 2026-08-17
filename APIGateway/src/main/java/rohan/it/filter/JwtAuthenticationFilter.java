package rohan.it.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import rohan.it.config.JwtUtil;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    // Paths that do NOT need JWT
    private static final List<String> PUBLIC_PATHS = List.of(
            "/auth/",
            "/actuator",
            "/error",
            "/favicon.ico"
    );

    // This method tells Spring Security: "Do not even run this filter for public paths"
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            response.getWriter().write("Missing or invalid Authorization header");
//            return;
//        }
//
//        String token = authHeader.substring(7);
//
//        if (!jwtUtil.isTokenValid(token)) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            response.getWriter().write("Invalid or expired JWT token");
//            return;
//        }
//
//        // Token is valid → add username header for downstream services
//        String username = jwtUtil.extractUsername(token);
//        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);
//        mutableRequest.putHeader("X-Username", username);
//
//        filterChain.doFilter(mutableRequest, response);
//    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.isTokenValid(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid or expired JWT token");
            return;
        }

        // === THIS WAS MISSING ===
        String username = jwtUtil.extractUsername(token);

        // Create Authentication object and put it in SecurityContext
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, null, List.of()); // no roles for now

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // ========================

        // Optional: still add header for downstream services
        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);
        mutableRequest.putHeader("X-Username", username);

        filterChain.doFilter(mutableRequest, response);
    }
}