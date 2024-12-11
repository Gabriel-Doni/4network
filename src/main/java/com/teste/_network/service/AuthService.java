package com.teste._network.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.teste._network.dto.RegisterDTO;
import com.teste._network.entity.Address;
import com.teste._network.entity.Client;
import com.teste._network.entity.Role;
import com.teste._network.repository.ClientRepository;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clientRepository.findByName(username);
    }

    public void register(RegisterDTO data) throws Exception {

        if (clientRepository.findByName(data.name()) != null) {
            throw new DuplicateKeyException("Cliente já registrado com nome:" + data.name());
        }

        if (clientRepository.findByCpf(data.cpf()) != null) {
            throw new DuplicateKeyException("Cliente já registrado com CPF: " + data.cpf());
        }

        Address address = new Address();
        address.setCep(data.cep());
        address.setState(data.state());
        address.setCity(data.city());
        address.setStreet(data.street());
        address.setNumber(data.number());
        address.setComplement(data.complement());

        Client client = new Client();
        client.setCpf(data.cpf());
        client.setName(data.name());
        client.setPassword(new BCryptPasswordEncoder().encode(data.password()));
        client.setRole(Role.CLIENT);
        client.setAddress(address);

        clientRepository.save(client);
    }

}
