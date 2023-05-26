package com.spring.basics.repository;

import com.spring.basics.model.Encryption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncryptionRepository extends JpaRepository<Encryption,Integer>
{

}
