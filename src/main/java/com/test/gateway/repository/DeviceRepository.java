package com.test.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.gateway.model.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

}
