package giuseppeperna.U5_W3_D1.security;

import giuseppeperna.U5_W3_D1.entities.Dipendente;
import giuseppeperna.U5_W3_D1.exceptions.UnauthorizedException;
import giuseppeperna.U5_W3_D1.services.DipendenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTCheckerFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private DipendenteService dipendenteService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Inserire token nell'Authorization Header nel formato corretto!");
        }

        String accessToken = authHeader.substring(7);

        jwtTools.verifyToken(accessToken);

        String email = jwtTools.extractEmailFromToken(accessToken);
        Dipendente currentDipendente = this.dipendenteService.findByEmail(email);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                currentDipendente,
                null,
                currentDipendente.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}

