package com.teste._network.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.teste._network.entity.Client;
import com.teste._network.entity.Image;
import com.teste._network.repository.ClientRepository;
import com.teste._network.repository.ImageRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ImageRepository imageRepository;

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

    public void saveClientImage(byte[] bytes, UUID idClient) throws Exception {

        Client client = clientRepository.findById(idClient)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        Image image = new Image();

        image.setPhoto(bytes);

        Image savedImage = imageRepository.save(image);

        client.setImage(savedImage);

        clientRepository.save(client);
    }
}
