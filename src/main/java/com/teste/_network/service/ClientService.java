package com.teste._network.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.teste._network.entity.Client;
import com.teste._network.repository.ClientRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client loadUserByEmail(String name) throws EntityNotFoundException {

        return null;

    }

    public Page<Client> getClients(int page, int size) throws Exception {

        Pageable pageable = PageRequest.of(page, size);

        Page<Client> clients = clientRepository.findAll(pageable);

        if (page == 0 && clients.isEmpty()) {
            throw new Exception("Nenhum cliente cadastrado");
        }

        if (clients.isEmpty()) {
            throw new Exception("Nenhum cliente encontrado");
        }

        return clients;

    }
}
