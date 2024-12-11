package com.teste._network.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teste._network.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {

}
