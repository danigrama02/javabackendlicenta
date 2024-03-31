package com.licenta.licenta.filters;

import com.licenta.licenta.service.UserDetailServiceDB;
import com.licenta.licenta.utils.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtSecurityFilter extends OncePerRequestFilter {

    private static final int CUTTINGPOINT = 7;

    private final JwtTokenUtils tokenUtils;

    private final UserDetailServiceDB userDetailsServiceDB;

    @Autowired
    public JwtSecurityFilter(JwtTokenUtils tokenUtils, UserDetailServiceDB userDetailServiceDB) {
        this.tokenUtils = tokenUtils;
        this.userDetailsServiceDB = userDetailServiceDB;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader !=null && requestTokenHeader.startsWith("Bearer ")){
            jwtToken = requestTokenHeader.substring(CUTTINGPOINT);
            try {
                username = tokenUtils.getUsernameFromToken(jwtToken);
            }
            catch (Exception e){
                logger.warn("Jwt token proglems " + e.getMessage());
            }
        }
        else {
            logger.warn("Jwt Token is not starting with Bearer");
        }

        if (username!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsServiceDB.loadUserByUsername(username);
            if (Boolean.TRUE.equals(tokenUtils.validateToken(jwtToken,userDetails))){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
