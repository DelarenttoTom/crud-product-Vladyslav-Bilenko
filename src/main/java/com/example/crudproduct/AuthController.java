package com.example.crudproduct;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String login = body.get("login");
        String password = body.get("password");

        if (login == null || login.length() < 3)
            return ResponseEntity.badRequest().body("Login musi mieć min. 3 znaki");
        if (password == null || password.length() < 6)
            return ResponseEntity.badRequest().body("Hasło musi mieć min. 6 znaków");
        if (userRepository.existsByLogin(login))
            return ResponseEntity.badRequest().body("Użytkownik już istnieje");

        User u = new User();
        u.setLogin(login);
        u.setPasswordHash(passwordEncoder.encode(password));
        u.setRole("USER");
        u.setCreatedAt(java.time.LocalDateTime.now());
        userRepository.save(u);

        return ResponseEntity.ok("Zarejestrowano pomyślnie");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String login = body.get("login");
        String password = body.get("password");

        User user = userRepository.findByLogin(login).orElse(null);
        if (user == null || !passwordEncoder.matches(password, user.getPasswordHash())) {
            return ResponseEntity.status(401).body("Nieprawidłowy login lub hasło");
        }

        String token = jwtUtils.generateToken(user);
        // ✅ Вариант с явным типом
        return ResponseEntity.ok(Map.<String,String>of("token", token));
    }
}
