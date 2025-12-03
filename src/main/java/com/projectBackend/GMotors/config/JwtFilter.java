package com.projectBackend.GMotors.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;



// Spring intercepta cada request, extrae el token y valida.
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // ⭐ Permitir preflight (CORS)
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        String path = request.getRequestURI();

        // ⭐ RUTAS PÚBLICAS
        if (path.equals("/api/usuarios/login") ||
            (path.equals("/api/usuarios") && request.getMethod().equals("POST"))) {

            filterChain.doFilter(request, response);
            return;
        }

        // ⭐ EXTRAER TOKEN
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            if (!jwtUtil.validarToken(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            filterChain.doFilter(request, response);
            return;
        }

        // ⭐ Si llegó aquí, requiere token
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
    
}
