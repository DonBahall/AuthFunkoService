package com.example.authfunkoservice.Service;


import com.example.authfunkoservice.Model.User.Role;
import com.example.authfunkoservice.Model.User.User;
import com.example.authfunkoservice.Repository.UserRepo;
import com.example.authfunkoservice.Request.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public String authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        return jwtService.generateToken(user);
    }

    public User registerUser(User request) {
        if (repository.existsByEmail(request.getEmail())) {
            return request;
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(hashedPassword);
        request.setRole(Role.USER);

        return repository.save(request);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public User updateUser(User request) {
        User user = getAutentificatedUser();
        if (request.getId() == null || !user.getId().equals(request.getId())) {
            return null;
        }
        User existing = repository.findById(request.getId()).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setEmail(request.getEmail());
        existing.setPhone(request.getPhone());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        existing.setPassword(passwordEncoder.encode(request.getPassword()));

        existing.setPassword(request.getPassword());
        existing.setAddress(request.getAddress());
        existing.setOrders(request.getOrders());
        existing.setRole(request.getRole());
        existing.setFavorite(request.getFavorite());

        return repository.save(existing);
    }

    public User getAutentificatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
