package com.teste._network.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.teste._network.dto.RegisterProductDTO;
import com.teste._network.dto.ResponseProductDTO;
import com.teste._network.service.ProductService;
import com.teste._network.service.TokenService;
import com.teste._network.utils.Return;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product")
@Tag(name = "Product", description = "Endpoints para gerenciar produtos")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private TokenService tokenService;

    @Operation(summary = "Criar produto", description = "Registra um novo produto no sistema")
    @PostMapping()
    public ResponseEntity<?> createProduct(
            @Parameter(description = "Token de autorização", required = true, example = "Bearer <token>")
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid RegisterProductDTO data,
            BindingResult result) {

        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest()
                    .body(new Return.MessageWithArray<String>("Erros de validação", errorMessages));
        }

        try {
            productService.createProduct(data, tokenService.getIdFromToken(token.replace("Bearer ", "")));
            return ResponseEntity.ok(new Return.Message("Produto registrado!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Return.Message(e.getMessage()));
        }
    }

    @Operation(summary = "Salvar imagem do produto", description = "Salva a imagem associada a um produto")
    @PostMapping("/image")
    public ResponseEntity<?> saveProductImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("idProduct") Long id) {

        try {
            productService.saveProductImage(file.getBytes(), id);
            return ResponseEntity.ok(new Return.Message("Imagem salva!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Return.Message(e.getMessage()));
        }
    }

    @Operation(summary = "Listar produtos", description = "Retorna uma lista de produtos paginada")
    @GetMapping
    public ResponseEntity<?> getProducts(
            @RequestParam(required = false, name = "client") UUID id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @Parameter(description = "Token de autorização", required = true, example = "Bearer <token>")
            @RequestHeader("Authorization") String token) {

        try {
            Page<ResponseProductDTO> res = productService.getProducts(page, size, id);
            return ResponseEntity.ok(new Return.MessageWithArray<ResponseProductDTO>("Produtos", res.getContent()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Return.Message(e.getMessage()));
        }
    }
}
