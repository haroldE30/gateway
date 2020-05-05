package com.test.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.gateway.model.Gateway;

@Repository
public interface GatewayRepository extends JpaRepository<Gateway, Long>{

}
