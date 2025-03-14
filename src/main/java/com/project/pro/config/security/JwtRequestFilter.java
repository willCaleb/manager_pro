package com.project.pro.config.security;

import com.project.pro.config.context.ContextImpl;
import com.project.pro.context.Context;
import com.project.pro.exception.CustomRuntimeException;
import com.project.pro.pattern.Constants;
import com.project.pro.service.impl.ClienteService;
import com.project.pro.service.impl.ProfissionalService;
import com.project.pro.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

//    @Autowired
    private final UserDetailsService userDetailsService;

//    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.getUsernameFromJWT(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails != null) {
                resolverAuthentication(request, userDetails);

                String claim = jwtTokenProvider.getTypeClaim(token, "userType");

                if (Utils.isEmpty(claim)) throw new CustomRuntimeException("Formato do token inválido");

                if (Constants.LOGIN_TYPE_PROFESSIONAL.equals(claim)) {
                    Context.setCurrentProfissional(ContextImpl.getBean(ProfissionalService.class).findByEmail(username));
                }else if(Constants.LOGIN_TYPE_CLIENT.equals(claim)) {
                    Context.setCurrentCliente(ContextImpl.getBean(ClienteService.class).buscarPorUsuario(username));
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private static void resolverAuthentication(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
