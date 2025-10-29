package com.cristianlopera.biblioteca.controller;

import com.cristianlopera.biblioteca.dto.*;
import com.cristianlopera.biblioteca.entity.Usuario;
import com.cristianlopera.biblioteca.repository.UsuarioRepository;
import com.cristianlopera.biblioteca.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioRepository usuarioRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil){
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest req){
        if(usuarioRepo.findByUsername(req.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("Usuario ya existe");
        }
        Usuario u = new Usuario();
        u.setUsername(req.getUsername());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setRole("ROLE_USER");
        usuarioRepo.save(u);
        return ResponseEntity.ok("Usuario creado");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req){
        Optional<Usuario> ou = usuarioRepo.findByUsername(req.getUsername());
        if(ou.isEmpty()) return ResponseEntity.status(401).body("Credenciales inválidas");
        Usuario u = ou.get();
        if(!passwordEncoder.matches(req.getPassword(), u.getPassword())){
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
        String token = jwtUtil.generateToken(u.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
