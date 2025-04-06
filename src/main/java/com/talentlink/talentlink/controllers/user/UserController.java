package com.talentlink.talentlink.controllers.user;

import com.talentlink.talentlink.domain.enums.Role;
import com.talentlink.talentlink.domain.AuthenticationDTO;
import com.talentlink.talentlink.domain.user.UserRegisterDTO;
import com.talentlink.talentlink.domain.user.User;
import com.talentlink.talentlink.infra.security.TokenService;
import com.talentlink.talentlink.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/user")
public class UserController {

    @Autowired
    @Qualifier("userAuthenticationManager")
    private AuthenticationManager userAuthenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = userAuthenticationManager.authenticate(usernamePassword);

        var user = userRepository.findByEmail(data.email());
        if (user == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado!");
        }

        String token = tokenService.generateToken((User) user);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO data) {
        if (userRepository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().body("E-mail já cadastrado!");
        }

        String encryptedPassword = passwordEncoder.encode(data.password());
        User newUser = new User(data.email(), encryptedPassword, Role.USER);
        userRepository.save(newUser);

        String token = tokenService.generateToken(newUser);
        return ResponseEntity.ok().body(token);
    }
}
