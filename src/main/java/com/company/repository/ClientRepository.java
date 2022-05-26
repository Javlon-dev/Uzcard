package com.company.repository;

import com.company.entity.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, String> {

    Page<ClientEntity> findAllByProfileName(Pageable pageable, String profileName);

    Optional<ClientEntity> findByIdAndProfileName(String id, String profileName);

}