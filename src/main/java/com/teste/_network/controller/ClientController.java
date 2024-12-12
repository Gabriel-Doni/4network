package com.teste._network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.teste._network.entity.Client;
import com.teste._network.service.ClientService;
import com.teste._network.service.TokenService;
import com.teste._network.utils.Return;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping("/api/client")
@Tag(name = "Client", description = "Endpoints para gerenciar clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private TokenService tokenService;

    @Operation(security = @SecurityRequirement(name = "bearerAuth"), summary = "Salvar imagem do cliente", description = "Salva a imagem do cliente no sistema", method = "POST")
    @PostMapping("/image")
    public ResponseEntity<?> saveClientImage(
            @RequestParam("file") MultipartFile file,
            @RequestHeader("Authorization") String token) {

        try {
            clientService.saveClientImage(file.getBytes(),
                    tokenService.getIdFromToken(token.replace("Bearer ", "")));

            return ResponseEntity.ok(new Return.Message("Imagem salva!"));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Return.Message(e.getMessage()));
        }
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"), summary = "Listar clientes", description = "Retorna uma lista de clientes paginada", method = "GET")
    @GetMapping
    public ResponseEntity<?> getClients(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size, @RequestHeader("Authorization") String token) {

        try {
            var res = clientService.getClients(page, size);
            return ResponseEntity.ok(new Return.MessageWithArray<Client>("Clientes", res.getContent()));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Return.Message(e.getMessage()));
        }
    }
}
