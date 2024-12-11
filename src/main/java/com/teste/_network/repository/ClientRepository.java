package com.teste._network.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.teste._network.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    boolean existsByNameAndPassword(String name, String password);

    UserDetails findByName(String name);

    UserDetails findByCpf(String cpf);

}
