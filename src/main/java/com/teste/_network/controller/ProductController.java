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

import com.teste._network.dto.RegisterProductDTO;
import com.teste._network.dto.ResponseProductDTO;
import com.teste._network.service.ProductService;
import com.teste._network.service.TokenService;
import com.teste._network.utils.Return;
import org.springframework.data.domain.Page;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private TokenService tokenService;

    @PostMapping()
    public ResponseEntity<?> createProduct(@RequestHeader("Authorization") String token,
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

            productService.createProduct(data, tokenService.getIdFromToken(token = token.replace("Bearer ", "")));

            return ResponseEntity.ok(new Return.Message("Produto registrado!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Return.Message(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getProducts(
            @RequestParam(required = false, name = "client") UUID id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        try {
            Page<ResponseProductDTO> res = productService.getProducts(page, size, id);

            return ResponseEntity.ok(new Return.MessageWithArray<ResponseProductDTO>("Produtos", res.getContent()));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Return.Message(e.getMessage()));
        }
    }

}
