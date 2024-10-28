package com.itMentor.Task312.filter;

import com.itMentor.Task312.service.Impl.UserAuthDetailServiceImpl;
import com.itMentor.Task312.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtUtil tokenUtil;
    private final UserAuthDetailServiceImpl userAuthDetailService;

    @Autowired
    public SecurityFilter(JwtUtil tokenUtil, UserAuthDetailServiceImpl userAuthDetailService) {
        this.tokenUtil = tokenUtil;
        this.userAuthDetailService = userAuthDetailService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) {
        try {
            // Проверяем, является ли запрос запросом на /login
            if (request.getRequestURI().contains("/login")) {
                // Если да, пропускаем фильтр и передаем запрос дальше
                filterChain.doFilter(request, response);
                return;
            }
            var token = this.recoverToken(request);
            var login = tokenUtil.extractUsername(token);
            var user = userAuthDetailService.loadUserByUsername(login);
            var authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new RuntimeException("Fail to filter " + e.getMessage());
        }
    }

    /**
     * Метод для извлечения токена из HTTP запроса.
     *
     * @param request объект HttpServletRequest, представляющий HTTP запрос
     * @return строковое значение токена или {@code null}, если токен отсутствует
     */

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        return authHeader == null ? null : authHeader.replace("Bearer ", "");
    }
}
