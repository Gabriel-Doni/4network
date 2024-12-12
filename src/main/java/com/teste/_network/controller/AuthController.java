package com.teste._network.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.teste._network.dto.AuthDTO;
import com.teste._network.dto.RegisterDTO;
import com.teste._network.entity.Client;
import com.teste._network.service.AuthService;
import com.teste._network.service.TokenService;
import com.teste._network.utils.Return;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Endpoints para autenticação e registro de usuário")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @Operation(summary = "Realiza a autenticação do usuário", description = "Autentica um usuário e retorna um token JWT", method = "POST")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthDTO data, BindingResult result) {

        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest()
                    .body(new Return.MessageWithArray<String>("Erros de validação", errorMessages));
        }

        try {
            var namePassword = new UsernamePasswordAuthenticationToken(data.name(), data.password());
            var auth = this.authManager.authenticate(namePassword);

            var token = tokenService.generateToken((Client) auth.getPrincipal());

            return ResponseEntity.ok(new Return.MessageWithToken("Usuario logado!", token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Return.MessageWithToken(e.getMessage(), null));
        }
    }

    @Operation(summary = "Registra um novo usuário", description = "Cadastra um novo usuário no sistema", method = "POST")
    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterDTO data,
            BindingResult result) {

        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest()
                    .body(new Return.MessageWithArray<String>("Erros de validação", errorMessages));
        }

        try {
            authService.register(data);
            return ResponseEntity.ok(new Return.Message("Usuário registrado!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Return.Message(e.getMessage()));
        }
    }
}
