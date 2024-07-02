package app.portfolio_samuel_santinelli.vercel.todolist.security;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import app.portfolio_samuel_santinelli.vercel.todolist.repository.UserRepository;
import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.equals("/tasks/")) {
            var auth = request.getHeader("Authorization");
            if (auth != null && auth.startsWith("Basic ")) {
                var authEncoded = auth.substring("Basic".length()).trim();
                byte[] authDecode = Base64.getDecoder().decode(authEncoded);

                var authString = new String(authDecode);
                String[] credentials = authString.split(":");
                String username = credentials[0];
                String password = credentials[1];

                var user = this.userRepository.findByUsername(username);
                if (user == null) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified) {
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}

