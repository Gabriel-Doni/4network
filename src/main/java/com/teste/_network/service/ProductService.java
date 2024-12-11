package com.teste._network.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.teste._network.dto.RegisterProductDTO;
import com.teste._network.dto.ResponseProductDTO;
import com.teste._network.entity.Product;
import com.teste._network.repository.ClientRepository;
import com.teste._network.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

    public void createProduct(RegisterProductDTO data, UUID id) {

        final Product product = new Product();

        product.setClient(clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado")));
        product.setName(data.name());
        product.setPrice(data.price());
        product.setPhoto(null);

        productRepository.save(product);

    }

    public Page<ResponseProductDTO> getProducts(int page, int size, UUID id) throws Exception {
        Pageable pageable = PageRequest.of(page, size);

        Page<ResponseProductDTO> res;

        if (id != null) {
            res = getByClient(pageable, id); // Chama o método para buscar por cliente
        } else {
            // Alterado para buscar todos os produtos, retornando uma página de DTOs
            res = productRepository.findAll(pageable).map(product -> new ResponseProductDTO(
                    product.getIdProduct(),
                    product.getClient().getIdClient(),
                    product.getName(),
                    product.getPrice(),
                    product.getPhoto(),
                    product.getCreatedAt()));
        }

        if (page == 0 && res.isEmpty()) {
            throw new Exception("Nenhum produto cadastrado");
        }

        if (res.isEmpty()) {
            throw new Exception("Nenhum produto encontrado");
        }

        return res;
    }

    private Page<ResponseProductDTO> getByClient(Pageable pageable, UUID id) {
        clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        return productRepository.findByClient_IdClient(id, pageable)
                .map(product -> new ResponseProductDTO(
                        product.getIdProduct(),
                        product.getClient().getIdClient(),
                        product.getName(),
                        product.getPrice(),
                        product.getPhoto(),
                        product.getCreatedAt()));
    }

}
