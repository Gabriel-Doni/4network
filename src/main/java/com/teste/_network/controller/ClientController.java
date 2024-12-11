package com.teste._network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.teste._network.entity.Client;
import com.teste._network.service.ClientService;
import com.teste._network.utils.Return;

@Controller
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<?> getClients(@RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name= "size" , defaultValue = "10") int size) {

        try {
            var res = clientService.getClients(page, size);

            return ResponseEntity.ok(new Return.MessageWithArray<Client>("Clientes", res.getContent()));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Return.Message(e.getMessage()));
        }
    }

}
